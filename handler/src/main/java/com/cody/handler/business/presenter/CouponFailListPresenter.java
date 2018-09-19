package com.cody.handler.business.presenter;

import android.support.annotation.NonNull;

import com.cody.handler.business.mapper.CouponFailListMapper;
import com.cody.handler.business.viewmodel.ItemCouponFailViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.presenter.ListWithHeaderPresenter;
import com.cody.repository.business.bean.CouponFailBean;
import com.cody.repository.business.interaction.CouponFailInteraction;
import com.cody.repository.framework.Repository;

import java.util.Map;

/**
 * Created by liuliwei on 2018-09-03.
 * 发券失败列表
 */

public class CouponFailListPresenter extends ListWithHeaderPresenter<ItemCouponFailViewModel> {
    private CouponFailInteraction mInteraction;
    private CouponFailListMapper mMapper;
    private String version;
    private String shopId;

    public CouponFailListPresenter(String version, String shopId){
        mInteraction = Repository.getInteraction(CouponFailInteraction.class);
        mMapper = new CouponFailListMapper();
        this.version = version;
        this.shopId = shopId;
    }

    @Override
    public void getRecycleList(Object tag, @NonNull Map<String, String> params) {
        params.clear();
        params.put("pageNo", getViewModel().getPageNO() + "");
        params.put("pageSize", getViewModel().getPageSize() + "");
        params.put("version",version);
        params.put("storeId",shopId);

        mInteraction.getSendCouponFailList(tag, params, CouponFailBean.class, new DefaultCallback<CouponFailBean>(this) {
            @Override
            public void onSuccess(CouponFailBean bean) {
                super.onSuccess(bean);
                if(bean!=null){
                    boolean isHasNextPage=false;
                    if(bean.getList()!=null&&bean.getList().size()>=20){
                        isHasNextPage=true;
                    }

                    mMapper.mapperList(getViewModel(), bean.getList(), getViewModel().getPosition(), isHasNextPage);
                }
                refreshUI();
            }
        });


    }
}
