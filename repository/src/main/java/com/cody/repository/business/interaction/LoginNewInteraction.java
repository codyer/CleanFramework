package com.cody.repository.business.interaction;


import com.cody.repository.Domain;
import com.cody.repository.business.bean.UserBean;
import com.cody.repository.business.interaction.constant.LongGuoUrl;
import com.cody.repository.framework.interaction.ICallback;
import com.cody.repository.framework.interaction.QueryCallBack;
import com.cody.repository.framework.interaction.QueryClass;
import com.cody.repository.framework.interaction.QueryHeaderListener;
import com.cody.repository.framework.interaction.QueryMap;
import com.cody.repository.framework.interaction.QueryString;
import com.cody.repository.framework.interaction.QueryTag;
import com.cody.repository.framework.interaction.RequestMapping;
import com.cody.repository.framework.interaction.RequestMethod;
import com.cody.repository.framework.interaction.ResultType;
import com.cody.repository.framework.interaction.Server;
import com.cody.xf.utils.http.IHeaderListener;
import com.cody.xf.utils.http.SimpleBean;

import java.util.List;
import java.util.Map;

/**
 * Created by cody.yi on 2017/5/17.
 * 登录接口
 * when use #getDictionary
 *
 * @see com.cody.xf.utils.CommonUtil#reBuildString(List)
 */
@Server(Domain.LOGIN_URL)
public interface LoginNewInteraction {

    //登录
    @RequestMapping(
            value = LongGuoUrl.LOGIN_URL,
            method = RequestMethod.POST,
            type = ResultType.BEAN)
    void doLogin(@QueryTag Object tag, @QueryMap Map<String, String> params, @QueryClass Class<?> clazz,
                 @QueryHeaderListener IHeaderListener headerListener, @QueryCallBack ICallback<UserBean> callback);


    //登出
    @RequestMapping(
            value = LongGuoUrl.LOGOUT_URL,
            method = RequestMethod.POST,
            type = ResultType.SIMPLE)
    void doLogout(@QueryTag Object tag, @QueryMap Map<String, String> params, @QueryClass Class<?> clazz,
                  @QueryCallBack ICallback<SimpleBean> callback);

    //发送短信验证码
    @RequestMapping(
            value = LongGuoUrl.SEND_MSG,
            method = RequestMethod.POST,
            type = ResultType.SIMPLE)
    void sendMsg(@QueryTag Object tag, @QueryMap Map<String, String> params, @QueryClass Class<?> clazz,
                 @QueryCallBack ICallback<SimpleBean> callback);

    //
    //发送验证码修改密码后重新登录
    @RequestMapping(
            value = LongGuoUrl.VERIFY_MSG,
            method = RequestMethod.POST,
            type = ResultType.SIMPLE)
    void verifySmsLogin(@QueryTag Object tag, @QueryMap Map<String, String> params, @QueryClass Class<?> clazz,
                        @QueryHeaderListener IHeaderListener headerListener, @QueryCallBack ICallback<SimpleBean>
                                callback);

    //获取手机号
    @RequestMapping(
            value = LongGuoUrl.GET_USER_MOBILE_BY_OPENID,
            method = RequestMethod.GET,
            type = ResultType.SIMPLE)
    void getUserMobileByOpenId(@QueryTag Object tag, @QueryString("openId") String openId
            , @QueryClass Class<SimpleBean> clazz
            , @QueryCallBack ICallback<SimpleBean> callback);
}
