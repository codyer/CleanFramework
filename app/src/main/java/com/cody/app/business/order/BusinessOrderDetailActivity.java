package com.cody.app.business.order;

import android.os.Bundle;
import android.view.View;

import com.cody.app.R;
import com.cody.app.databinding.ActivityBusinessOrderDetailBinding;
import com.cody.app.framework.activity.WithHeaderActivity;
import com.cody.handler.business.viewmodel.BusinessOrderDetailViewModel;
import com.cody.handler.business.presenter.order.BusinessOrderDetailPresenter;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.ToastUtil;

/**
 * 商品订单详情
 */
public class BusinessOrderDetailActivity extends WithHeaderActivity<BusinessOrderDetailPresenter,BusinessOrderDetailViewModel,ActivityBusinessOrderDetailBinding>{

    private String orderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderId = getIntent().getExtras().getString("orderId");
        ToastUtil.showToast(orderId);
        getPresenter().getOrderDetail(TAG,orderId, Repository.getLocalValue(LocalKey.SHOP_ID));
    }

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setLeft(true);
        header.setTitle("订单详情");
        header.setVisible(true);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_business_order_detail;
    }

    @Override
    protected BusinessOrderDetailPresenter buildPresenter() {
        return new BusinessOrderDetailPresenter();
    }

    @Override
    protected BusinessOrderDetailViewModel buildViewModel(Bundle savedInstanceState) {
        return new BusinessOrderDetailViewModel();
    }

    @Override
    public void onClick(View v) {

    }


}
