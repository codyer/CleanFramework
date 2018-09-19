package com.cody.repository.business.interaction.constant;

/**
 * Create by jiquan.zhong  on 2018/8/14.
 * description: //优惠券
 */
public interface CouponUrl {
    String COUPON_LIST = "/api-sale-b/coupon/queryAllCouponByShop";//优惠券管理查询列表
    String COUPON_ADD_QUERY = "/api-sale-b/coupon/getCouponChannelByLongGuoApp?id={id}";//优惠券增发展示查询
    String COUPON_ADD = "/api-sale-b/coupon/additional";//优惠券增发提交
    String COUPON_NEW = "/api-sale-b/longGuoApp/hasNewData/1";//查询是否存在新优惠券
    String COUPON_NEW_DELETE = "/api-sale-b/longGuoApp/markAsOld/1";//查看新数据后删除新数据标记
    String COUPON_STORE_LIST = "/api-sale-b/storeCupon/getCuponByStore";//优惠券发券查询列表
    String COUPON_TAKE_SEND = "/api-sale-b/coupon/usersTakeCoupons";//优惠券批量发券
    String COUPON_BATCH_USER = "/api-sale-b/coupon/batchUsersTakeCoupons";//优惠券批量发券

    String COUPON_FAIL_LISt = "/ocean/store/sendCouponFailInfo"; //发券失败列表
}
