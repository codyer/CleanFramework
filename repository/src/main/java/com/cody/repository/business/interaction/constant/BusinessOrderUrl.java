package com.cody.repository.business.interaction.constant;

/**
 * Created by liuliwei on 2018-07-11.
 * 商户订单
 */
public interface BusinessOrderUrl {
    String BASE_URL = "/p-trade-oc-bweb";

    //订单列表
    String ORDER_LIST = BASE_URL + "/orderApi/bApp/queryOrder/list/{shopId}";
    //使用优惠券的订单列表
    String COUPON_ORDER_LIST = BASE_URL + "/orderApi/bApp/queryOrder/v2/list/promotion/{shopId}";
    //获取支付二维码
    String ORDER_QR_CODE = BASE_URL + "/orderApi/bPc/orcode/{serialNumber}/{width}/{height}";
    //订单详情
    String ORDER_DETAIL = BASE_URL + "/orderApi/golable/orderInfo/{id}";
}
