package com.cody.repository.business.interaction;


import com.google.gson.JsonObject;
import com.cody.repository.Domain;
import com.cody.repository.business.bean.ImInfo;
import com.cody.repository.business.bean.RemarkBean;
import com.cody.repository.business.bean.SaleBean;
import com.cody.repository.business.bean.entity.UserGroupBean;
import com.cody.repository.business.bean.entity.UserInfoBean;
import com.cody.repository.business.bean.im.ChatLogBean;
import com.cody.repository.business.bean.im.ReplyInfoBean;
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

import java.util.List;
import java.util.Map;

/**
 * Created by cody.yi on 2017/5/17.
 * 登录接口
 * when use #getDictionary
 *
 * @see com.cody.xf.utils.CommonUtil#reBuildString(List)
 */
@Server(Domain.IM_URL)
public interface LogImInteraction {

    //登录
    @RequestMapping(
            value = LongGuoUrl.LOGIN_IM_URL,
            method = RequestMethod.GET,
            type = ResultType.BEAN)
    void doIMLogin(@QueryTag Object tag, @QueryMap Map<String, String> params, @QueryClass Class<?> clazz,
                   @QueryCallBack ICallback<ImInfo> callback);

    // 更新IM在线状态
    @RequestMapping(
            value = LongGuoUrl.UPDATE_IM_STATUS_URL,
            method = RequestMethod.POST,
            type = ResultType.SIMPLE)
    void updateIMStatus(@QueryTag Object tag, @QueryJson JsonObject params, @QueryClass Class<SimpleBean> clazz,
                        @QueryCallBack ICallback<SimpleBean> callback);


    // 获取用户信息
    @RequestMapping(
            value = LongGuoUrl.USER_INFO_URL,
            method = RequestMethod.POST,
            type = ResultType.LIST_BEAN)
    void getUserInfo(@QueryTag Object tag, @QueryJson JsonObject params, @QueryClass Class<UserInfoBean> clazz,
                     @QueryCallBack ICallback<List<UserInfoBean>> callback);

    //获取客户同店聊天记录
    @RequestMapping(
            value = LongGuoUrl.GET_MESSAGE_BY_SHOPID,
            method = RequestMethod.POST,
            type = ResultType.LIST_BEAN)
    void getMessageByShopId(@QueryTag Object tag, @QueryJson JsonObject params, @QueryClass
            Class<ChatLogBean> clazz,
                            @QueryCallBack ICallback<List<ChatLogBean>> callback);

    // 更新用户备注名称
    @RequestMapping(
            value = LongGuoUrl.UPDATE_IM_REMARK,
            method = RequestMethod.POST,
            type = ResultType.SIMPLE)
    void updateIMRemark(@QueryTag Object tag, @QueryJson JsonObject params, @QueryClass Class<SimpleBean> clazz,
                        @QueryCallBack ICallback<SimpleBean> callback);

    //获取导购员回复信息,包含欢迎语和快捷回复
    @RequestMapping(
            value = LongGuoUrl.GET_REPLY_INFO,
            method = RequestMethod.GET,
            type = ResultType.BEAN)
    void getReplyInfo(@QueryTag Object tag, @QueryString("ownerId") String imId, @QueryClass
            Class<ReplyInfoBean> clazz,
                      @QueryCallBack ICallback<ReplyInfoBean> callback);

    //添加或修改快捷回复语
    @RequestMapping(
            value = LongGuoUrl.SAVE_OR_UPDATE_QUICKREPLY,
            method = RequestMethod.POST,
            type = ResultType.BEAN)
    void saveOrUpdateQuickReply(@QueryTag Object tag, @QueryJson JsonObject params, @QueryClass
            Class<ReplyInfoBean.QuickReplyVoListBean> clazz,
                                @QueryCallBack ICallback<ReplyInfoBean.QuickReplyVoListBean> callback);

    //更新快捷回复启用状态
    @RequestMapping(
            value = LongGuoUrl.UPDATE_QUICK_REPLY_STATUS,
            method = RequestMethod.POST,
            type = ResultType.SIMPLE)
    void updateQuickReplyStatus(@QueryTag Object tag,
                                @QueryJson JsonObject params,
                                @QueryClass Class<SimpleBean> clazz,
                                @QueryCallBack ICallback<SimpleBean> callback);

    //更新问候语启用状态
    @RequestMapping(
            value = LongGuoUrl.UPDATE_GREETING_REPLY_STATUS,
            method = RequestMethod.POST,
            type = ResultType.SIMPLE)
    void updateGreetingReplyStatus(@QueryTag Object tag,
                                   @QueryJson JsonObject params,
                                   @QueryClass Class<SimpleBean> clazz,
                                   @QueryCallBack ICallback<SimpleBean> callback);

    //添加或修改问候语
    @RequestMapping(
            value = LongGuoUrl.SAVE_OR_UPDATE_GREETING_REPLY,
            method = RequestMethod.POST,
            type = ResultType.BEAN)
    void saveOrUpdateGreetingReply(@QueryTag Object tag,
                                   @QueryJson JsonObject params,
                                   @QueryClass Class<ReplyInfoBean.GreetingReplyVoListBean> clazz,
                                   @QueryCallBack ICallback<ReplyInfoBean.GreetingReplyVoListBean> callback);

    //获取备注
    @RequestMapping(
            value = LongGuoUrl.GET_REMARK,
            method = RequestMethod.POST,
            type = ResultType.BEAN)
    void getRemark(@QueryTag Object tag, @QueryJson JsonObject params, @QueryClass Class<RemarkBean> clazz,
                   @QueryCallBack ICallback<RemarkBean> callback);

    //修改备注
    @RequestMapping(
            value = LongGuoUrl.SAVE_OR_UPDATE_REMARK,
            method = RequestMethod.POST,
            type = ResultType.BEAN)
    void updateRemark(@QueryTag Object tag, @QueryJson JsonObject params, @QueryClass Class<SimpleBean> clazz,
                      @QueryCallBack ICallback<SimpleBean> callback);

    //添加或修改用户分组
    @RequestMapping(
            value = LongGuoUrl.GROUP_SAVE_OR_UPDATE,
            method = RequestMethod.POST,
            type = ResultType.SIMPLE)
    void saveOrUpdateUserGroup(@QueryTag Object tag, @QueryJson JsonObject params, @QueryClass Class<SimpleBean> clazz,
                               @QueryCallBack ICallback<SimpleBean> callback);


    //删除用户分组
    @RequestMapping(
            value = LongGuoUrl.GROUP_DELETE_USER,
            method = RequestMethod.POST,
            type = ResultType.SIMPLE)
    void deleteUserGroup(@QueryTag Object tag, @QueryJson JsonObject params, @QueryClass Class<SimpleBean> clazz,
                         @QueryCallBack ICallback<SimpleBean> callback);

    //获取用户分组列表
    @RequestMapping(
            value = LongGuoUrl.GROUP_LIST,
            method = RequestMethod.GET,
            type = ResultType.LIST_BEAN)
    void getGroupList(@QueryTag Object tag, @QueryString("imId") String imId, @QueryClass Class<?> clazz,
                      @QueryCallBack ICallback<List<UserGroupBean>> callback);


    //导购员列表
    @RequestMapping(
            value = LongGuoUrl.GET_SALES_ASSISTANT_LIST_URL,
            method = RequestMethod.GET,
            type = ResultType.LIST_BEAN)
    void getSales(@QueryTag Object tag, @QueryMap Map<String, String> params, @QueryClass Class<SaleBean> clazz,
                   @QueryCallBack ICallback<List<SaleBean>> callback);
}
