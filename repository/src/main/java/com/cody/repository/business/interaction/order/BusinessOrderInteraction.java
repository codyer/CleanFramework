package com.cody.repository.business.interaction.order;


import com.cody.repository.Domain;
import com.cody.repository.business.bean.order.BusinessOrderDetailBean;
import com.cody.repository.business.bean.order.BusinessOrderListBean;
import com.cody.repository.business.bean.order.OrderQrCodeBean;
import com.cody.repository.business.interaction.constant.BusinessOrderUrl;
import com.cody.repository.framework.interaction.ICallback;
import com.cody.repository.framework.interaction.QueryCallBack;
import com.cody.repository.framework.interaction.QueryClass;
import com.cody.repository.framework.interaction.QueryMap;
import com.cody.repository.framework.interaction.QueryTag;
import com.cody.repository.framework.interaction.RequestMapping;
import com.cody.repository.framework.interaction.RequestMethod;
import com.cody.repository.framework.interaction.ResultType;
import com.cody.repository.framework.interaction.Server;

import java.util.List;
import java.util.Map;

/**
 * Created by Cody.yi on 2018-07-16.
 * 商户订单
 */
@Server(Domain.ORDER_BUSINESS_URL)
public interface BusinessOrderInteraction {
    //订单列表
    @RequestMapping(
            value = BusinessOrderUrl.ORDER_LIST,
            method = RequestMethod.GET,
            type = ResultType.LIST_BEAN)
    void getOrderList(@QueryTag Object tag, @QueryMap Map<String, String> params, @QueryClass Class<BusinessOrderListBean> clazz,
                      @QueryCallBack ICallback<List<BusinessOrderListBean>> callback);
 //使用优惠券的订单列表
    @RequestMapping(
            value = BusinessOrderUrl.COUPON_ORDER_LIST,
            method = RequestMethod.GET,
            type = ResultType.LIST_BEAN)
    void getCouponOrderList(@QueryTag Object tag, @QueryMap Map<String, String> params, @QueryClass Class<BusinessOrderListBean> clazz,
                      @QueryCallBack ICallback<List<BusinessOrderListBean>> callback);

    //获取支付二维码
    @RequestMapping(
            value = BusinessOrderUrl.ORDER_QR_CODE,
            method = RequestMethod.GET,
            type = ResultType.BEAN)
    void getQrCode(@QueryTag Object tag, @QueryMap Map<String, String> params, @QueryClass Class<OrderQrCodeBean> clazz,
                   @QueryCallBack ICallback<OrderQrCodeBean> callback);


    @RequestMapping(
            value = BusinessOrderUrl.ORDER_DETAIL,
            method = RequestMethod.GET,
            type = ResultType.BEAN)
    void getOrderDetail(@QueryTag Object tag, @QueryMap Map<String, String> params, @QueryClass Class<BusinessOrderDetailBean> clazz,
                        @QueryCallBack ICallback<BusinessOrderDetailBean> callback);

}
