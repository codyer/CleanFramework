/*
 * @Title StatKey.java
 * @Copyright Copyright 2016 Redstar Macalline All Rights Reserved.
 * @author SongZheng
 * @date 2016-2-24 下午1:11:09
 * @version 1.0
 */
package com.cody.repository.framework.statistics;

import android.text.TextUtils;

/**
 * @author SongZheng
 * @Description 数据统计用到的Key
 * @date 2016-2-24 下午1:11:09
 */
public class StatKey {

    /*******************************************************************************
     *	Global Const/Variables
     *******************************************************************************/
    /**
     * 参数字段名
     */
    public static class Parameter {
        public static String zid = "zid";
        public static String id = "id";
        public static String ts = "ts";
        public static String d_browser = "d_browser";
        public static String service = "service";
        public static String u_id = "u_id";
        public static String u_mid = "u_mid";
//        public static String page = "page";
//        public static String p_channel = "p_channel";
//        public static String p_domain = "p_domain";
//        public static String p_type = "p_type";
        public static String p_id = "p_id";
        public static String p_title = "p_title";
        public static String d_os = "d_os";
        public static String d_os_version = "d_os_version";
        public static String version = "version";
        public static String p_url = "p_url";
        public static String r_url = "r_url";
        public static String p_action_total = "p_action_total";
        public static String u_gid = "u_guid";
        public static String l_country = "l_country";
        public static String l_province = "l_province";
        public static String l_city = "l_city";
        public static String u_city = "u_city";
        public static String l_dist = "l_dist";
        public static String l_ip = "l_ip";
        public static String l_gps = "l_gps";
        public static String r_from = "r_from";
        public static String r_keyword= "r_keyword";
        public static String app_b = "app_b";
        public static String d_prixel_x = "d_prixel_x";
        public static String d_prixel_y = "d_prixel_y";
        public static String d_model = "d_model";
        public static String d_mark = "d_mark";
        public static String app_v = "app_v";
//        public static String p_item = "p_item";
        public static String p_action_id = "p_action_id";
        public static String p_action = "p_action";
        public static String p_action_pos = "p_action_pos";
        public static String p_stay_time = "p_stay_time";
        public static String p_live_time = "p_live_time";
        public static String u_idfa = "u_idfa";
        public static String u_phone = "u_phone";
        public static String p_scene_nb = "p_scene_nb";
        public static String default_value = "-99999";
    }

    /**
     * 操作系统类型
     */
    public static final String OS_TYPE_ANDROID = "Android";

    public static String checkValue(String value) {
        return TextUtils.isEmpty(value) ? Parameter.default_value : value;
    }

    /*******************************************************************************
     *	Unused Codes
     *******************************************************************************/
}
