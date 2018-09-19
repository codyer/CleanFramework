package com.cody.handler.business.presenter.order;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.cody.handler.business.viewmodel.ItemBusinessOrderListViewModel;
import com.cody.handler.business.viewmodel.SearchBusinessOrderViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.business.mapper.BusinessOrderListMapper;
import com.cody.handler.framework.presenter.AbsListPresenter;
import com.cody.repository.business.bean.order.BusinessOrderListBean;
import com.cody.repository.business.bean.order.OrderQrCodeBean;
import com.cody.repository.business.interaction.order.BusinessOrderInteraction;
import com.cody.repository.framework.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuliwei on 2018-07-17.
 * 商户订单搜索
 */
public class BusinessOrderSearchPresenter extends AbsListPresenter<SearchBusinessOrderViewModel,ItemBusinessOrderListViewModel> {
    private BusinessOrderListMapper mMapper;
    private BusinessOrderInteraction mInteraction;
    private String shopId;

    public BusinessOrderSearchPresenter(String shopId) {
        this.shopId = shopId;
        mMapper = new BusinessOrderListMapper();
        mInteraction = Repository.getInteraction(BusinessOrderInteraction.class);
    }

    @Override
    public void getRecycleList(Object tag, @NonNull Map<String, String> params) {
        if (TextUtils.isEmpty(getViewModel().getSearchViewModel().getKeyWord().get())) {
            getView().hideLoading();
            return;
        }
        if (getViewModel() != null) {
            getViewModel().setPageSize(10);
            params.clear();//这是一个特立独行的接口！！！pageNO和pageSize取了别名
            params.put("currentPage", getViewModel().getPageNO() + "");
            params.put("showCount", getViewModel().getPageSize() + "");
            params.put("orderStatus", "st_0");
            params.put("shopId", shopId);
            params.put("keyWords",getViewModel().getSearchViewModel().getKeyWord().get());
            mInteraction.getOrderList(tag, params, BusinessOrderListBean.class, new DefaultCallback<List<BusinessOrderListBean>>(this) {
                @Override
                public void onSuccess(List<BusinessOrderListBean> bean) {
                    super.onSuccess(bean);
                    if (bean != null) {
                        boolean isHasNextPage = bean.size() >= getViewModel().getPageSize();
                        mMapper.mapperList(getViewModel(), bean, getViewModel().getPosition(), isHasNextPage);
                    }
                    refreshUI();
                }
            });
        }
    }

    public void getQrCode(Object tag, String serialNumber, int width, int height, final OrderQrCodeListener listener){
        HashMap<String,String> params=new HashMap<>();
        params.put("serialNumber",serialNumber);
        params.put("width",width+"");
        params.put("height",height+"");
        mInteraction.getQrCode(tag, params, OrderQrCodeBean.class, new DefaultCallback<OrderQrCodeBean>(this) {
            @Override
            public void onSuccess(OrderQrCodeBean bean) {
                super.onSuccess(bean);
                if(listener!=null){
                    listener.onQrCodeSuccess(bean);
                }
                hideLoading();
            }
        });
    }

    public interface OrderQrCodeListener{
        void onQrCodeSuccess(OrderQrCodeBean qrCodeBean);
    }
}
