package com.cody.repository.business.interaction;

import com.google.gson.JsonObject;
import com.cody.repository.Domain;
import com.cody.repository.business.bean.CouponChannelBean;
import com.cody.repository.business.bean.CouponListBean;
import com.cody.repository.business.bean.CouponStoreListBean;
import com.cody.repository.business.interaction.constant.CouponUrl;
import com.cody.repository.framework.interaction.ICallback;
import com.cody.repository.framework.interaction.QueryCallBack;
import com.cody.repository.framework.interaction.QueryClass;
import com.cody.repository.framework.interaction.QueryJson;
import com.cody.repository.framework.interaction.QueryMap;
import com.cody.repository.framework.interaction.QueryString;
import com.cody.repository.framework.interaction.QueryTag;
import com.cody.repository.framework.interaction.RequestMapping;
import com.cody.repository.framework.interaction.RequestMethod;
import com.cody.repository.framework.interaction.ResultType;
import com.cody.repository.framework.interaction.Server;
import com.cody.xf.utils.http.SimpleBean;

import java.util.List;
import java.util.Map;

/**
 * Create by jiquan.zhong  on 2018/8/14.
 * description://优惠券
 */
@Server(Domain.PROMOTION_URL)
public interface CouponInteraction {

    //优惠券列表
    @RequestMapping(
            value = CouponUrl.COUPON_LIST,
            method = RequestMethod.POST,
            type = ResultType.BEAN)
    void getCouponList(@QueryTag Object tag, @QueryJson JsonObject params, @QueryClass Class<CouponListBean> clazz,
                       @QueryCallBack ICallback<CouponListBean> callback);

    //优惠券增发渠道查询
    @RequestMapping(
            value = CouponUrl.COUPON_ADD_QUERY,
            method = RequestMethod.GET,
            type = ResultType.LIST_BEAN)
    void getCouponAddChannel(@QueryTag Object tag, @QueryString("id") String couponId, @QueryClass Class<CouponChannelBean> clazz,
                             @QueryCallBack ICallback<List<CouponChannelBean>> callback);

    //优惠券增发
    @RequestMapping(
            value = CouponUrl.COUPON_ADD,
            method = RequestMethod.POST,
            type = ResultType.SIMPLE)
    void couponAdd(@QueryTag Object tag, @QueryJson JsonObject params, @QueryClass Class<SimpleBean> clazz,
                   @QueryCallBack ICallback<SimpleBean> callback);

    //优惠券增发
    @RequestMapping(
            value = CouponUrl.COUPON_NEW,
            method = RequestMethod.GET,
            type = ResultType.SIMPLE)
    void couponHasNew(@QueryTag Object tag, @QueryClass Class<SimpleBean> clazz,
                      @QueryCallBack ICallback<SimpleBean> callback);

    //优惠券增发
    @RequestMapping(
            value = CouponUrl.COUPON_NEW_DELETE,
            method = RequestMethod.GET,
            type = ResultType.SIMPLE)
    void couponAsOld(@QueryTag Object tag, @QueryClass Class<SimpleBean> clazz,
                     @QueryCallBack ICallback<SimpleBean> callback);

    //优惠券发券查询列表
    @RequestMapping(
            value = CouponUrl.COUPON_STORE_LIST,
            method = RequestMethod.GET,
            type = ResultType.BEAN)
    void getCuponByStore(@QueryTag Object tag, @QueryMap Map<String, String> params, @QueryClass Class<CouponStoreListBean> clazz,
                         @QueryCallBack ICallback<CouponStoreListBean> callback);

    //优惠券批量发券
    @RequestMapping(
            value = CouponUrl.COUPON_TAKE_SEND,
            method = RequestMethod.POST,
            type = ResultType.SIMPLE)
    void usersTakeCoupon(@QueryTag Object tag, @QueryJson JsonObject params, @QueryClass Class<SimpleBean> clazz,
                         @QueryCallBack ICallback<SimpleBean> callback);

    //优惠券批量发券(全选)
    @RequestMapping(
            value = CouponUrl.COUPON_BATCH_USER,
            method = RequestMethod.POST,
            type = ResultType.SIMPLE)
    void batchUsersTakeCoupons(@QueryTag Object tag, @QueryJson JsonObject params, @QueryClass Class<SimpleBean> clazz,
                               @QueryCallBack ICallback<SimpleBean> callback);

}
