/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cody.repository.business.interaction.constant;


import android.os.Environment;

import com.cody.repository.KeyConstant;

/**
 * create by chy
 * apk接口相关常量统一配置
 */
public class Constant {
    public final static String UPLOAD_FILE_NAME = "file";
    public final static String UPLOAD_FILES_NAME = "files";
    public static final String APP_ID = KeyConstant.APP_ID;
    public static final String APP_SECRET = KeyConstant.APP_SECRET;
//    android 正式环境
//    appid:253F904C
//    secret:8a943e16a085e4d713d265f551420bac0b32af7e

    public static final Boolean HTTP_DEBUG = false;
    public static final int EVENT_BEGIN = 0X100;

    public static final class Paths {
        public static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
        public static final String IMAGE_LOADER_CACHE_PATH = BASE_PATH + "/longguo/Images/";
        public static final String PATH_LONGGUO_CACHE = BASE_PATH + "/longguo/crash/";
        public static final String PATH_LONGGUO = BASE_PATH + "/longguo/";
    }

    public static final int PAGE_LAZY_LOAD_DELAY_TIME_MS = 200;

    //导购手册名称
    public static final String SHOP_GUIDE_NAME = "guide.pdf";

    //intent 中传递数值
    public static final String KEY_TYPE = "type";
    public static final int MANAGER_ENTER = 0x3;

    /**
     * 用户角色
     */
    public interface Role {
        String SALE = "HX000001"; //导购员角色
        //TODO 修改对应的值
        String SHOP_MANAGER = "1";//店长角色
//        String MANAGER = "HX000004";//经理人角色
//        String BRAND = "HX000007";//品牌商角色
//        String DESIGNER_SERVICE = "HX000008";//设计师客服
    }

    public interface MsgType {
        String USER_ENTER = "10015";//角色入驻
        String CHANGE_PHONE = "10016";//更改手机号
        String LOAST_PWD = "10017";//找回密码
        String CHANGE_PWD = "10018";//修改密码
        String ADD_PHONE = "10019";//手机号验证 经纪人修改经济公司手机号
    }

    /**
     * 任务和订单区分
     */
    public interface Type {
        int TASK = 0;
        int ORDER = 1;
        int ORDER_UNDER_WAY = 1;//订单及进行中
        int ORDER_FINISHED = 2;//订单已完成
        int ORDER_FINISHED_DETAIL = 3;//订单已完成的详情页
    }

    /**
     * 同一个页面多个请求时，请求type
     */
    public interface RequestType {
        int TYPE_1 = 1;
        int TYPE_2 = 2;
        int TYPE_3 = 3;
        int TYPE_4 = 4;
        int TYPE_5 = 5;
        int TYPE_6 = 6;
        int TYPE_7 = 7;
        int TYPE_8 = 8;
        int TYPE_9 = 9;
        int TYPE_10 = 10;
    }

    /**
     * 返回码
     */
    public interface ResultCode {
        int CODE_1 = 1;
        int CODE_2 = 2;
        int CODE_3 = 3;
        int CODE_4 = 4;
        int CODE_5 = 5;
        int CODE_6 = 6;
        int CODE_7 = 7;
        int CODE_8 = 8;
        int CODE_9 = 9;
        int CODE_10 = 10;
    }

    /**
     * 正则
     */
    public interface Regex {
        String identity = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
        String age = "^\\d{1,100}$";
        String email = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    }

    public interface Designer {
        String designerInfo = "designInfo";
    }

    /**
     * 极光推送注册
     */
    public interface PushRegist {
        int JPUSH = 1;
    }

    public interface StrikeChannel {
        String PUBLIC = "public";
        String SELLER = "seller";
        String DESIGNER = "designer";
        String AGENT = "agent";
    }

    public interface StrikeType {
        int P = 0;
        int F = 1;
    }

    public static final String STRIKE_DOMAIN = "bshop.com";
    public static final String STRIKE_SERVICE = "b.android.pvuv";

    public static final String KEY_TOKEN = "x-auth-token";

    public interface Status {
        int hide = 3;
        int online = 1;
        int leave = 2;
    }
}
