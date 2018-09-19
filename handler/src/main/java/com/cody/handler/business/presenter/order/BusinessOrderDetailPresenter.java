package com.cody.handler.business.presenter.order;

import com.cody.handler.business.viewmodel.BusinessOrderDetailViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.business.mapper.BusinessOrderDetailMapper;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.repository.business.bean.order.BusinessOrderDetailBean;
import com.cody.repository.business.interaction.order.BusinessOrderInteraction;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuliwei on 2018-07-13.
 * 商户订单详情
 */
public class BusinessOrderDetailPresenter extends Presenter<BusinessOrderDetailViewModel> {
    private BusinessOrderInteraction mInteraction;
    private BusinessOrderDetailMapper mMapper;

    public BusinessOrderDetailPresenter() {
        mInteraction = Repository.getInteraction(BusinessOrderInteraction.class);
        mMapper = new BusinessOrderDetailMapper();
    }

    public void getOrderDetail(Object tag, String id, String shopId) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("shopId", shopId);
        mInteraction.getOrderDetail(tag, params, BusinessOrderDetailBean.class, new DefaultCallback<BusinessOrderDetailBean>(this) {
            @Override
            public void onSuccess(BusinessOrderDetailBean bean) {
                super.onSuccess(bean);
                hideLoading();
                if (bean != null) {
                    mMapper.mapper(getViewModel(), bean);
                    ToastUtil.showToast(bean.getOrderItems().size() + "==" + bean.getPromotions().size());
                }
                refreshUI();
            }
        });
    }

}
