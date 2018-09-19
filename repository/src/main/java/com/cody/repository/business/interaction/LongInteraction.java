package com.cody.repository.business.interaction;

import com.google.gson.JsonObject;
import com.cody.repository.Domain;
import com.cody.repository.business.bean.MyCustomerBean;
import com.cody.repository.business.bean.PotentialCustomerListBean;
import com.cody.repository.business.bean.RankBean;
import com.cody.repository.business.interaction.constant.LongGuoUrl;
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

import java.util.Map;

/**
 * Created by cody.yi on 2018/7/18.
 * 龙果服务器
 */
@Server(Domain.LONG_GUO_URL)
public interface LongInteraction {
    //更新用户信息
    @RequestMapping(
            value = LongGuoUrl.GUIDE_INFORMATION,
            method = RequestMethod.POST,
            type = ResultType.BEAN)
    void updateUserInfo(Object tag, JsonObject params, Class<SimpleBean> clazz, ICallback<SimpleBean> callback);

    //前n排名
    @RequestMapping(
            value = LongGuoUrl.USER_SALE_RANK_URL,
            method = RequestMethod.GET,
            type = ResultType.BEAN)
    void getRank(@QueryTag Object tag, @QueryString("rank") int rank, @QueryClass Class<RankBean> clazz,
                 @QueryCallBack ICallback<RankBean> callback);

    //添加用户到分组
    @RequestMapping(
            value = LongGuoUrl.ADD_USER_GROUP,
            method = RequestMethod.POST,
            type = ResultType.SIMPLE)
    void addUserToGroup(@QueryTag Object tag, @QueryJson JsonObject params, @QueryClass Class<SimpleBean> clazz,
                        @QueryCallBack ICallback<SimpleBean> callback);


    //编辑用户分组
    @RequestMapping(
            value = LongGuoUrl.EDIT_USER_GROUP,
            method = RequestMethod.POST,
            type = ResultType.SIMPLE)
    void editUserToGroup(@QueryTag Object tag, @QueryJson JsonObject params, @QueryClass Class<SimpleBean> clazz,
                         @QueryCallBack ICallback<SimpleBean> callback);

    //删除用户分组
    @RequestMapping(
            value = LongGuoUrl.DEL_GROUP_URL,
            method = RequestMethod.POST,
            type = ResultType.LIST_BEAN)
    void deleteUserFromGroup(@QueryTag Object tag, @QueryJson JsonObject params, @QueryClass Class<SimpleBean> clazz,
                             @QueryCallBack ICallback<SimpleBean> callback);

    //获取我的客户首页数据
    @RequestMapping(
            value = LongGuoUrl.GET_MY_CUSTOMER,
            method = RequestMethod.GET,
            type = ResultType.BEAN)
    void getMyCustomer(@QueryTag Object tag, @QueryClass Class<MyCustomerBean> clazz
            , @QueryCallBack ICallback<MyCustomerBean> callback);

    //获取客户列表
    @RequestMapping(
            value = LongGuoUrl.GET_CUSTOMER_LIST,
            method = RequestMethod.GET,
            type = ResultType.BEAN)
    void getCustomerList(@QueryTag Object tag
            , @QueryMap Map<String, String> params
            , @QueryClass Class<PotentialCustomerListBean> clazz
            , @QueryCallBack ICallback<PotentialCustomerListBean> callback);

}
