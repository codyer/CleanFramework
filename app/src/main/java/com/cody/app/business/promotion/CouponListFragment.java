package com.cody.app.business.promotion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cody.app.R;
import com.cody.app.framework.adapter.BaseRecycleViewAdapter;
import com.cody.app.framework.fragment.ListFragment;
import com.cody.handler.business.presenter.CouponListPresenter;
import com.cody.handler.business.viewmodel.ItemCouponViewModel;
import com.cody.repository.framework.statistics.BuryingPointUtils;
import com.cody.xf.utils.ToastUtil;

/**
 * Create by jiquan.zhong  on 2018/8/15.
 * description:优惠券列表
 */
public class CouponListFragment extends ListFragment<CouponListPresenter, ItemCouponViewModel> {
    public static final String COUPON_STATUS = "coupon_status";
    public static final String COUPON_ADD_COUNT = "coupon_add_count";
    public static final int COUPON_ADD_REQUEST_CODE = 0x005;
    private ItemCouponViewModel mSelectedItem;

    public static CouponListFragment newInstance(int couponStatus) {
        Bundle args = new Bundle();
        args.putInt(COUPON_STATUS, couponStatus);
        CouponListFragment fragment = new CouponListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BaseRecycleViewAdapter<ItemCouponViewModel> buildRecycleViewAdapter() {
        return new BaseRecycleViewAdapter<ItemCouponViewModel>(getViewModel()) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.item_coupon_list;
            }
        };
    }

    @Override
    protected CouponListPresenter buildPresenter() {
        int couponStatus = getArguments().getInt(COUPON_STATUS, 0);
        return new CouponListPresenter(couponStatus);
    }

    @Override
    protected void onUserVisible() {
        super.onUserVisible();
        onRefresh();
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position, long id) {
        mSelectedItem = getViewModel().get(position);
        if (mSelectedItem == null) return;
        switch (view.getId()) {
            case R.id.useList:
                if (mSelectedItem.isUsed()) {
                    CouponOrderListActivity.startCouponOrderList(mSelectedItem.getCouponName(), mSelectedItem.getCouponId());
                }
                break;
            case R.id.addCoupon:
                CouponAddActivity.startAddCoupon(this, mSelectedItem.getCouponId(), COUPON_ADD_REQUEST_CODE);
                BuryingPointUtils.build(CouponListFragment.class, 4277).submitF();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == COUPON_ADD_REQUEST_CODE) {
            if (data != null && mSelectedItem != null) {
                int count = data.getIntExtra(COUPON_ADD_COUNT, 0);
                mSelectedItem.setRemainAmount(mSelectedItem.getRemainAmount() + count);
                mSelectedItem.setTotalAmount(mSelectedItem.getTotalAmount() + count);
                ToastUtil.showToast(R.string.add_success);
            }
        }
    }
}
