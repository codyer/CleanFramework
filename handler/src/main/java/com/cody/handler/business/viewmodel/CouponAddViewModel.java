package com.cody.handler.business.viewmodel;

import com.cody.handler.framework.viewmodel.ListWithHeaderViewModel;

/**
 * Create by jiquan.zhong  on 2018/8/16.
 * description:增发优惠券
 */
public class CouponAddViewModel extends ListWithHeaderViewModel<ItemCouponChannelViewModel> {
    private int couponId;

    public String getCouponId() {
        return couponId + "";
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }
}
