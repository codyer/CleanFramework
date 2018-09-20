/**
 * @file UserDataManager
 * @copyright (c) 2016 Macalline All Rights Reserved.
 * @author Cody
 * @date 2016/3/8
 */
package com.cody.repository.framework.statistics;


import android.text.TextUtils;

import com.mcxiaoke.packer.helper.PackerNg;
import com.cody.repository.framework.Repository;
import com.cody.repository.framework.local.BaseLocalKey;
import com.cody.xf.XFoundation;
import com.cody.xf.utils.LocationUtil;
import com.ut.device.UTDevice;

import java.util.HashMap;

/**
 * @author Cody
 * @description TODO
 * @date 2016/3/8
 */
public class UserDataManager {
    /**
     * ali utdid
     */
    public static String mAliUtdid = "";
    /**
     * 数据统计页面列表
     */
    private static HashMap<String, HashMap<String, String>> mHashMapStatActivityPageList = new HashMap<>();
    /**
     * 数据统计页面列表
     */
    private static HashMap<String, HashMap<String, String>> mHashMapStatFragmentPageList = new HashMap<>();
    /**
     * 打包渠道ID
     */
    private static String channel = "";
    /**
     * 记录最后pause的activity,用于埋点
     */
    public static String mLastPauseActivityCLassName;

    /**
     * 获得区
     */
    public static String getDistrict() {
        if (LocationUtil.getLocation() != null) {
            return LocationUtil.getLocation().getDistrict();
        }
        return "";
    }

    /***
     * 获取gps
     * @return
     */
    public static String getGps() {
        if (LocationUtil.getLocation() == null) return "";
        return LocationUtil.getLocation().getLongitude() + "," + LocationUtil.getLocation().getLatitude();
    }


    /**
     * 获得市
     */
    public static String getCity() {
        if (LocationUtil.getLocation() != null) {
            return LocationUtil.getLocation().getCity();
        }
        return "";
    }

    /**
     * 获得国家
     */
    public static String getCountry() {
        if (LocationUtil.getLocation() != null) {
            return LocationUtil.getLocation().getCountry();
        }
        return "";
    }

    /**
     * 获得省
     */
    public static String getProvince() {
        if (LocationUtil.getLocation() != null) {
            return LocationUtil.getLocation().getProvince();
        }
        return "";
    }

    /**
     * 获取打包渠道
     */
    public static String getChannel() {
        if (!TextUtils.isEmpty(channel))
            return channel;
        try {
            channel = PackerNg.getChannel(XFoundation.getContext());
            if (TextUtils.isEmpty(channel)) {
                //设置默认为10004
                channel = "30015";
            }
          /*  PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channel = applicationInfo.metaData.getInt("HXAPP_CHANNEL")+"";
                    }
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            channel = "defalut_channel";
        }
        return channel;
    }

    /**
     * @param @return
     * @return String
     * @Description 采用阿里方式生成的设备唯一标识
     * @author Cody
     */
    public static String getDeviceUtdid() {
        if (TextUtils.isEmpty(mAliUtdid)) {
            String utdid = Repository.getLocalValue(BaseLocalKey.DEVICE_ALI_UTD_ID);
            if (TextUtils.isEmpty(utdid)) {
                utdid = UTDevice.getUtdid(XFoundation.getApp());
                if (!TextUtils.isEmpty(utdid)) {
                    Repository.setLocalValue(BaseLocalKey.DEVICE_ALI_UTD_ID, utdid);
                }
            }
            UserDataManager.mAliUtdid = utdid;
        }
        return mAliUtdid;
    }

    /**
     * @param cls
     * @param isActivity
     * @return String
     * Description 检查该页面是否需要进行数据统计
     * @author Cody
     */
    public static HashMap<String, String> checkShouldStat(String cls, boolean isActivity) {
        HashMap<String, String> paramer = null;
        if (cls == null) {
            return null;
        }
        if (isActivity) {
            paramer = mHashMapStatActivityPageList.get(cls);
        } else {
            paramer = mHashMapStatFragmentPageList.get(cls);
        }
        return paramer;
    }

    public static void addStatFragmentPageItem(String cls, HashMap<String, String> paramer) {
        if (cls == null || paramer == null) {
            return;
        }
        mHashMapStatFragmentPageList.put(cls, paramer);
    }

    public static void addStatActivityPageItem(String cls, HashMap<String, String> paramer) {
        if (cls == null || paramer == null) {
            return;
        }
        mHashMapStatActivityPageList.put(cls, paramer);
    }
}
