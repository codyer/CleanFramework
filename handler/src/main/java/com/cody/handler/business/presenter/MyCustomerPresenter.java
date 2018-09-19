package com.cody.handler.business.presenter;

import com.cody.handler.business.viewmodel.MyCustomerViewModel;
import com.cody.handler.framework.DefaultCallback;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.repository.business.bean.MyCustomerBean;
import com.cody.repository.business.interaction.LongInteraction;
import com.cody.repository.framework.Repository;

/**
 * Created by cody.yi on 2018/8/16.
 * 我的客户
 */
public class MyCustomerPresenter extends Presenter<MyCustomerViewModel> {
    private LongInteraction mInteraction;

    public MyCustomerPresenter() {
        mInteraction = Repository.getInteraction(LongInteraction.class);
    }

    /**
     * 获取我的客户信息
     *
     * @param tag
     */
    public void getCustomerInfo(Object tag) {
        mInteraction.getMyCustomer(tag, MyCustomerBean.class
                , new DefaultCallback<MyCustomerBean>(this) {

                    @Override
                    public void onBegin(Object tag) {

                    }

                    @Override
                    public void onSuccess(MyCustomerBean bean) {
                        super.onSuccess(bean);
                        if (bean != null) {
                            getViewModel().setMarketingInMonth(bean.getMarketingUserCount());
                            getViewModel().setNewCustomerInMonth(bean.getNewUserCount());
                            getViewModel().setSalesConversion(bean.getConversionUserCount());
                            getViewModel().setTotalCustomer(bean.getTotalCount());
                            //潜在客户
                            getViewModel().getPotentialCustomer().setTotalCount(bean.getPotentialUserCount());
                            getViewModel().getPotentialCustomer().setNewCount(bean.getNewPotentialCount());
                            //意向客户
                            getViewModel().getIntentCustomer().setTotalCount(bean.getIntentionUserCount());
                            getViewModel().getIntentCustomer().setNewCount(bean.getNewIntentionCount());
                            //成交客户
                            getViewModel().getBargainCustomer().setTotalCount(bean.getDealUserCount());
                            getViewModel().getBargainCustomer().setNewCount(bean.getNewDealCount());

                            refreshUI();
                        }
                    }
                });
    }
}
