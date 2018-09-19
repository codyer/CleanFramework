package com.cody.repository.business.local;

import com.cody.repository.framework.local.BaseLocalKey;

/**
 * Created by cody.yi on 2017/3/30.
 * 本地数据存储
 * 目前只提供sharePreference方式
 */
public interface LocalKey extends BaseLocalKey {
    String IM_ID = BASE + "im_id";//用户Im Id
    String IS_MONEY_VISIBLE = BASE + "is_money_visible";
    String INTRODUCTION = "introduction";//自我介绍
    String SHOP_GUIDE_PATH = BASE + "shop_guide_path";//导购手册文件路径
    /*
    新数据
     */
    String CACHE_NAME = BASE + "cache_name";//上次用户登录账户
    String MOBILE_PHONE = BASE + "mobile_phone";//用户手机
    String USER_NAME = BASE + "user_name";//系统生成的名称
    String NICK_NAME = BASE + "nick_name";//用户昵称
    String REAL_NAME = BASE + "real_name";//用户真实姓名
    String USER_GENDER = BASE + "user_gender";//用户性别
    String PICTURE_URL = BASE + "picture_url";//用户头像
    String USER_ROLE = BASE + "user_role";//获取用户类型角色
    String IDENTITY = BASE + "identity";//获取是店长还是店员角色  1 - 店长  2 - 导购
    String USER_STATUS = BASE + "user_status"; //用户状态  0 - 隐身，1 - 在线， 2 - 离开
    String SHOP_ID = BASE + "shop_id";//店铺ID
    String REPLY_INFO = BASE + "reply_info";//系统回复信息
}
