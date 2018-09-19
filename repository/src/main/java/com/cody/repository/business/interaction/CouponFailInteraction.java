package com.cody.repository.business.interaction;

import com.cody.repository.Domain;
import com.cody.repository.business.bean.CouponFailBean;
import com.cody.repository.business.interaction.constant.CouponUrl;
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
 * Created by liuliwei on 2018-09-03.
 */

@Server(Domain.OCEN_BIZ_URL)
public interface CouponFailInteraction {
    //获取发券失败列表
    @RequestMapping(
            value = CouponUrl.COUPON_FAIL_LISt,
            method = RequestMethod.GET,
            type = ResultType.BEAN)
    void getSendCouponFailList(@QueryTag Object tag, @QueryMap Map params, @QueryClass Class<CouponFailBean> clazz,
                               @QueryCallBack ICallback<CouponFailBean> callback);
}
