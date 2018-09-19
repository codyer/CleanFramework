package com.cody.repository.business.interaction.order;


import com.cody.repository.Domain;
import com.cody.repository.business.bean.order.CustomerOrderListBean;
import com.cody.repository.business.interaction.constant.CustomerOrderUrl;
import com.cody.repository.framework.interaction.ICallback;
import com.cody.repository.framework.interaction.QueryCallBack;
import com.cody.repository.framework.interaction.QueryClass;
import com.cody.repository.framework.interaction.QueryMap;
import com.cody.repository.framework.interaction.QueryTag;
import com.cody.repository.framework.interaction.RequestMapping;
import com.cody.repository.framework.interaction.RequestMethod;
import com.cody.repository.framework.interaction.ResultType;
import com.cody.repository.framework.interaction.Server;

import java.util.Map;

/**
 * Created by Cody.yi on 2018-07-16.
 * 客户订单
 */
@Server(Domain.ORDER_CUSTOMER_URL)
public interface CustomerOrderInteraction {
    //订单列表
    @RequestMapping(
            value = CustomerOrderUrl.ORDER_LIST,
            method = RequestMethod.GET,
            type = ResultType.BEAN)
    void getOrderListAll(@QueryTag Object tag,@QueryMap Map<String, String> params, @QueryClass Class<CustomerOrderListBean> clazz,
                      @QueryCallBack ICallback<CustomerOrderListBean> callback);

}
