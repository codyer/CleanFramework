package com.cody.app.business.order;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.cody.app.R;
import com.cody.app.framework.activity.HtmlActivity;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.app.framework.fragment.ListFragment;
import com.cody.app.business.widget.OrderQrCodePopWindow;
import com.cody.handler.business.presenter.order.BusinessOrderListPresenter;
import com.cody.handler.business.viewmodel.ItemBusinessOrderListViewModel;
import com.cody.repository.business.interaction.constant.H5Url;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;


/**
 * 商户订单列表
 */

public class BusinessOrderListFragment extends ListFragment<BusinessOrderListPresenter, ItemBusinessOrderListViewModel> {
    private static final String ORDER_STATUS = "order_status";
    private final static String COUPON_ID = "couponId";

    public static BusinessOrderListFragment newInstance(int couponId) {
        BusinessOrderListFragment fragment = new BusinessOrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(COUPON_ID, couponId);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static BusinessOrderListFragment newInstance(String orderStatus) {
        BusinessOrderListFragment fragment = new BusinessOrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ORDER_STATUS, orderStatus);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected BusinessOrderListPresenter buildPresenter() {
        int couponId = getArguments().getInt(COUPON_ID,-1);
        String orderStatus = getArguments().getString(ORDER_STATUS);
        return new BusinessOrderListPresenter(couponId, orderStatus, Repository.getLocalValue(LocalKey.SHOP_ID));
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().getRefreshPage(TAG);
    }

    @Override
    protected BaseRecycleViewAdapter<ItemBusinessOrderListViewModel> buildRecycleViewAdapter() {
        return new BaseRecycleViewAdapter<ItemBusinessOrderListViewModel>(getViewModel()) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.item_business_order_list;
            }
        };
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(RecyclerView parent, final View view, int position, long id) {
        switch ((int) id) {
            case R.id.tv_pay:
                getPresenter().getQrCode(TAG, getViewModel().get(position).getSerialNumber(), 300, 300, new BusinessOrderListPresenter.OrderQrCodeListener() {
                    @Override
                    public void onQrCodeSuccess(com.cody.repository.business.bean.order.OrderQrCodeBean qrCodeBean) {
                        OrderQrCodePopWindow splashPopWindow = new OrderQrCodePopWindow(getContext(), qrCodeBean);
                        splashPopWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                    }
                });
                break;
            case R.id.mobile:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + getViewModel().get(position).getUserMobile());
                intent.setData(data);
                startActivity(intent);
                break;
            default:
                int type = getViewModel().get(position).getOrderType();
                String serialNumber = getViewModel().get(position).getSerialNumber();
                String url = H5Url.ORDER_DETAIL + getViewModel().get(position).getOrderId();
                url += "&serialNumber=" + serialNumber + "&orderTypeValue=" + type;
                if ("UNPAID".equals(getViewModel().get(position).getOrderStatus())) {
                    url += "&flag=1";
                }
                HtmlActivity.startHtml(null, url);
                break;
        }
    }
}
