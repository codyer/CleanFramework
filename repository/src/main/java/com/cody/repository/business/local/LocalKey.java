package com.cody.repository.business.local;

import com.cody.repository.framework.local.BaseLocalKey;

/**
 * Created by cody.yi on 2017/3/30.
 * 本地数据存储
 * 目前只提供sharePreference方式
 */
public interface LocalKey extends BaseLocalKey {
    String BASE = "local_";
    String APPTYPE = BASE + "type";
    String OPENID = BASE + "openid";
    String USERROLE = BASE + "userrole";
    String SHOPID = BASE + "shopid";
    String USERNAME = BASE + "username";
    String PICTUREURL = BASE + "pictureurl";
    String NICKNAME = BASE + "nickname";
    String MOBILEPHONE = BASE + "mobilephone";
    String COMPANYPHONE = BASE + "companyphone";
    String INTRODUCTION = BASE + "introduction";
    String GENDER = BASE + "gender";
    String RATING = BASE + "rating";//设计师独有的评分
    String CHECKSTATUS = BASE + "checkstatus";//设计师独有的头像审核
    String CACHENAME = BASE + "cachename";
    String CACHEPWD = BASE + "cachepwd";

    String IMID = BASE + "ImId";
    String PSD = BASE + "Psd";

    String ISNOTFIRSTLOGIN = BASE + "IsNotFirstLogin";
    String FREECHANCE = BASE + "FreeChance";
    String SHOPGUIDE = BASE + "Guide";
    String FIRSTLUNCH = BASE + "FirstLunch";
    String VERSION = BASE + "Version";
    String VERSIONCHANGED = BASE + "VersionChanged";
    String USERSTATUS = BASE + "UserStatus";
    String DESIGNERSTATUS = "designerStatus";
}
