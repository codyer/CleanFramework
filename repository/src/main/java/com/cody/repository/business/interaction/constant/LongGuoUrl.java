package com.cody.repository.business.interaction.constant;

/**
 * Created by Emcy-fu ;
 * on data:  2018/7/12 ;
 */

public interface LongGuoUrl {

    String LOGIN_URL = "/settle/app/login";//登录 post
    String LOGIN_IM_URL = "/api/imvAccount/loginIm";//登录im get
    String LOGOUT_URL = "/logout";//登出 post
    String SEND_MSG = "/sms/send";//B端APP登录/入驻(发送短信)
    String VERIFY_MSG = "/settle/merge/password/retrieve";//B端APP登录/入驻（验证手机验证码并设置新密码） post
    String RETRIEVE = "/settle/password/retrieve";//B端APP登录/入驻（找回密码）
    String SET_PWD = "/settle/password/modify";// post  B端经纪人入驻（设置密码）
    String RESET_PWD = "/password/modify";//post 修改密码
    String USER_SALE_RANK_URL = "/userInfo/getUserSaleRank/{rank}";//get 导购员排名前n名
    String GROUP_LIST = "/api/userGroup/getGroupList";//分组列表
    String GROUP_SAVE_OR_UPDATE = "/api/userGroup/saveOrUpdateUserGroup";//添加或修改用户分组
    String GROUP_DELETE_USER = "/api/userGroup/deleteUserGroup";//删除用户分组
    String UPDATE_IM_STATUS_URL = "/api/imvAccount/updateUserStatus";//get 导购员客户列表，含分组
    String USER_INFO_URL = "/api/imvAccount/getUserExtraInfoList";//post 获取用户信息
    String EDIT_USER_GROUP = "/userGroup/editUserGroup";//修改用户分组
    String ADD_USER_GROUP = "/userGroup/addUserToGroup";//添加用户到分组
    String URL_GET_SYS_MSG = "/message/list";//系统消息
    String J_PUSH_REGISTER = "/message/register";//推送
    String DEL_GROUP_URL = "/userGroup/delUserGroup";//删除用户分组
    String GET_MESSAGE_BY_SHOPID = "/api/imvMessage/getMessageByShopId";//获取客户同店聊天记录
    String UPDATE_IM_REMARK = "/api/imvAccount/updateRelationRemark";//更改IM备注
    String GET_REPLY_INFO = "/api/imvReply/getReplyInfo";//获取导购员回复信息,包含欢迎语和快捷回复
    String UPDATE_QUICK_REPLY_STATUS = "/api/imvReply/updateQuickReplyStatus";//更新快捷回复启用状态
    String SAVE_OR_UPDATE_QUICKREPLY = "/api/imvReply/saveOrUpdateQuickReply";//添加或修改快捷回复语
    String UPDATE_GREETING_REPLY_STATUS = "/api/imvReply/updatGreetingReplyStatus";//更新问候语启用状态
    String SAVE_OR_UPDATE_GREETING_REPLY = "/api/imvReply/saveOrUpdateGreetingReply";//添加或修改问候语
    String GET_REMARK = "/api/relationRemarkInfo/getRelationRemarkInfo";//获取备注
    String SAVE_OR_UPDATE_REMARK = "/api/relationRemarkInfo/saveOrUpdateRelationRemarkInfo";//添加或修改备注
    String GET_MY_CUSTOMER = "/userInfo/getMyCustomer";//获取我的客户首页数据
    String GET_CUSTOMER_LIST = "/userInfo/getCustomerList";//获取客户列表
    String GET_USER_MOBILE_BY_OPENID = "/user/getUserMobileByOpenId";//获取手机号

    /**
     * 导购员接口
     */
    String GUIDE_INFORMATION = "/userInfo/updateUserInfo";//导购员信息更新
    String GET_SALES_ASSISTANT_LIST_URL = "/api/imvShop/getSalesAssistantList";//获取报价单详情
    String UPDATE_SALES_HEAD_PORTRAIT_URL = "/salesValidate/updateSalesHeadPortrait/{openId}";//更换头像
}
