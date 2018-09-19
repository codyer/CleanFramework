package com.cody.repository.business.interaction;

import com.cody.repository.Domain;
import com.cody.repository.business.bean.ChooseCouponListBean;
import com.cody.repository.business.bean.CouponNumBean;
import com.cody.repository.business.interaction.constant.CouponChannelUrl;
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
 * Created by chy on 2016/10/10.
 * 优惠券相关请求
 */
@Server(Domain.COUPON_CHANNEL_URL)
public interface ChooseCouponInteraction {
    //优惠券列表
    @RequestMapping(
            value = CouponChannelUrl.STORE_COUPON_QUERY,
            method = RequestMethod.GET,
            type = ResultType.BEAN)
    void getCouponList(@QueryTag Object tag, @QueryMap Map<String, String> params, @QueryClass Class<ChooseCouponListBean> clazz,
                       @QueryCallBack ICallback<ChooseCouponListBean> callback);

    @RequestMapping(
            value = CouponChannelUrl.RECEIVE_COUPON,
            method = RequestMethod.POST,
            type = ResultType.BEAN)
    void getReceiveCoupon(@QueryTag Object tag, @QueryMap Map<String, String> params, @QueryClass Class<CouponNumBean> clazz,
                       @QueryCallBack ICallback<CouponNumBean> callback);
}
