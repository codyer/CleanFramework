package com.cody.repository.business.interaction.constant;

/**
 * Created by Cody.yi on 2018-07-11.
 * 客户订单
 */
public interface CustomerOrderUrl {
    String BASE_URL = "/p-trade-oc-web";

    //订单列表
    String ORDER_LIST = BASE_URL + "/orderApi/bApp/queryOrder/list/all/";
}
