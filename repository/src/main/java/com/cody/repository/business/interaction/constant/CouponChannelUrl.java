package com.cody.repository.business.interaction.constant;

/**
 * Created by cody.yi on 2018/8/23.
 * 优惠券渠道
 */
public interface CouponChannelUrl {
    String STORE_COUPON_QUERY = "/coupon/list/saleClerk";//get 查询导购员店铺下所有可用优惠券（不需要传入导购员openId，需要导购员登录后）
    String RECEIVE_COUPON = "/channel/1/subChannel/1/userOpenId/{userOpenId}/couponId/{couponId}/way/clerk";//POST 导购员帮用户领取优惠券接口
}
