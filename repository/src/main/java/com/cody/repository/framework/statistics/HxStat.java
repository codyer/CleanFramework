/*
 * @Title HxStat.java
 * @Copyright Copyright 2016 Cody All Rights Reserved.
 * @author Cody
 * @date 2016-2-22 下午3:51:46
 * @version 1.0
 */
package com.cody.repository.framework.statistics;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.cody.xf.utils.DeviceUtil;
import com.cody.xf.utils.NetUtil;

import java.util.HashMap;

/**
 * 数据统计类
 */
public class HxStat {
    private static final String TAG = "HxStat";
    /**
     * 是否被初始化
     */
    private static boolean isInited = false;
    /**
     * 当前进入页面信息
     */
    private static StatActivityPageInfo mCurrentActivityPage;
    /**
     * 页面停留的最小有效时间
     */
    private static int PAGE_STAY_MIN_TIME = 0;
    /**
     * 页面可接受的最小挂起时间,超过则作为新页面
     */
    private static int PAGE_PAUSE_MIN_TIME = 0;

    /**
     * 保存当前activity里的fragment统计信息
     */
    private static HashMap<String, StatPageInfo> mHashMap = new HashMap<>();

    /**
     * 上报基本信息源
     */
    private static StatConstDataSource mConstDataSource;
    /**
     * 实现上报的API
     */
    private static StatDataControl mDataControl;
    /**
     * 最后一次上报PVUV的来源页面标志refurl
     */
    private static String mLastRefurl;
    private static Context mContext;
    /*******************************************************************************
     *	Overrides From Base
     *******************************************************************************/

    /*******************************************************************************
     *	Public/Protected Methods
     *******************************************************************************/
    /**
     * Constructor
     */
    public HxStat() {
    }

    /**
     * 设置采集的基本数据来源，比如设备信息，位置信息等
     */
    public static void setConstDataSource(StatConstDataSource data) {
        mConstDataSource = data;
    }

    /**
     * 设置实现网络上报的对象
     */
    public static void setDataControl(StatDataControl control) {
        mDataControl = control;
    }

    /**
     * 初始化静态变量
     */
    public static void install(Context context) {
        mContext = context;
        StatConstData.initData(context);
        PVManager.install();
        initActivityStatData();
        initFragmentStatData();
        mLastRefurl = null;
        isInited = true;
    }

    /**
     * 初始化静态变量-针对activity类型的页面信息
     */
    private static void initActivityStatData() {
        mCurrentActivityPage = new StatActivityPageInfo();
    }

    /**
     * 初始化静态变量-针对fragment类型的页面信息
     */
    private static void initFragmentStatData() {
//		mCurrentFragmentPageInfo = new StatFragmentPageInfo();
//		mPreviousFragmentPageInfo = new StatFragmentPageInfo();
        mHashMap.clear();
    }

    /**
     * 销毁.就是reset静态变量
     */
    public static void destroy() {
        destroyActivityStatData();
        destroyFragmentStatData();
        mLastRefurl = null;
        isInited = false;
    }

    public static boolean isInited() {
        return isInited;
    }

    /**
     * 针对activity类型的页面信息
     */
    private static void destroyActivityStatData() {
        initActivityStatData();
    }

    /**
     * 销毁-针对fragment类型的页面信息
     */
    private static void destroyFragmentStatData() {
        initFragmentStatData();
    }

    /**
     * 直接上报PVUV
     *
     * @param pageName 页面名
     * @param refUrl   来源
     * @param params1  参数
     * @param params2  参数
     */
    public static void reportPvUvDirectly(String pageName, String refUrl, HashMap<String, String> params1, HashMap<String, String> params2) {
        reportPvuv(pageName, refUrl, params1, params2);
    }

    /**
     * activity resume
     *
     * @param activity activity对象
     */
    public static void onResume(Activity activity) {
        if (!isInited || activity == null) return;
        String pageObject = activity + "";
        //对象包名+类名
        String pageName = activity.getClass().getName();
        //数据统计resume
        HashMap<String, String> map = UserDataManager.checkShouldStat(pageName, true);
        HashMap<String, String> params1 = null;
        HashMap<String, String> params2 = null;
        boolean needReport = map != null;
        if (needReport) {
            params1 = new HashMap<>();
            params2 = new HashMap<>();
            //post参数
            if (map.get(StatKey.Parameter.id) != null) {
                params2.put(StatKey.Parameter.id, map.get(StatKey.Parameter.id));
                if (map.get(StatKey.Parameter.zid) != null) {
                    params2.put(StatKey.Parameter.zid, map.get(StatKey.Parameter.zid));
                }
            } else {
//                if (map.get(StatKey.Parameter.page) != null) {
//                    params2.put(StatKey.Parameter.page, map.get(StatKey.Parameter.page));
//                }
//                if (map.get(StatKey.Parameter.p_channel) != null) {
//                    params2.put(StatKey.Parameter.p_channel, map.get(StatKey.Parameter.p_channel));
//                }
//                if (map.get(StatKey.Parameter.p_type) != null) {
//                    params2.put(StatKey.Parameter.p_type, map.get(StatKey.Parameter.p_type));
//                }
                if (map.get(StatKey.Parameter.p_title) != null) {
                    params2.put(StatKey.Parameter.p_title, map.get(StatKey.Parameter.p_title));
                }
            }
            if (activity instanceof IStatDynamic) {
                ((IStatDynamic) activity).onStatResume(params1, params2);
            }
        }
        if (mCurrentActivityPage.mPageObject == null || !mCurrentActivityPage.mPageObject.equals(pageObject)) {//表示进入新页面
            //activity切换时,检查前一activity里是否有fragment需要上报停留时间
            String fragmentPageName = reportFragmentAccessTimeWhenExchangeActivity();

            if (mCurrentActivityPage.mPageObject != null) {
                //前一页面上报停留时间
//				checkAndReportActivityAccessTime();
                //上一个页面的名字就是当前页面来源页r_url
                mCurrentActivityPage.mRefUrl = (fragmentPageName == null ? mCurrentActivityPage.mPageName : fragmentPageName);
            } else {
                mCurrentActivityPage.mRefUrl = null;
            }
            //记录新页面信息
            mCurrentActivityPage.needReport = needReport;
            mCurrentActivityPage.mPageObject = pageObject;
            mCurrentActivityPage.mPageName = pageName;
            mCurrentActivityPage.mMillTimeStart = System.currentTimeMillis();
            mCurrentActivityPage.mMillTimePause = 0L;
            mCurrentActivityPage.params1 = params1;
            mCurrentActivityPage.params2 = params2;

            if (needReport) {
                reportPvuv(mCurrentActivityPage.mPageName, mCurrentActivityPage.mRefUrl, mCurrentActivityPage.params1, mCurrentActivityPage.params2);
            }
        } else {
            //页面未切换,仅仅重新resume
            long time = (int) ((System.currentTimeMillis() - mCurrentActivityPage.mMillTimePause) / 1000);
            if (time >= PAGE_PAUSE_MIN_TIME) {
                //页面被pause的时间足够长,则作为新页面
                if (needReport) {
                    //上报pause前的停留时间
//					checkAndReportActivityAccessTime();
                    reportPvuv(mCurrentActivityPage.mPageName, mCurrentActivityPage.mRefUrl, mCurrentActivityPage.params1, mCurrentActivityPage.params2);
                }
                //更新信息
                mCurrentActivityPage.mMillTimeStart = System.currentTimeMillis();
                mCurrentActivityPage.mMillTimePause = 0L;
            }
        }
    }

    /**
     * activity pause时调用
     *
     * @param activity 页面对象
     */
    public static void onPause(Activity activity) {
        if (!isInited || activity == null) return;
        String pageObject = activity + "";
        if (mCurrentActivityPage.mPageObject.equals(pageObject)) {
            mCurrentActivityPage.mMillTimePause = System.currentTimeMillis();
            //上报pause前的停留时间
            checkAndReportActivityAccessTime();
        } else {
            //应该不会走到这里来
        }
    }


    /**
     * fragment resume
     *
     * @param fragment fragment对象
     */
    public static void onResume(Fragment fragment) {
        if (!isInited || fragment == null) return;
        String pageObject = fragment + "";
        //对象包名+类名
        String pageName = fragment.getClass().getName();
        HashMap<String, String> map = UserDataManager.checkShouldStat(pageName, false);
        HashMap<String, String> params1 = null;
        HashMap<String, String> params2 = null;
        boolean needReport = map != null;
        if (needReport) {
            params1 = new HashMap<>();
            params2 = new HashMap<>();
            //post参数
            if (map.get(StatKey.Parameter.id) != null) {
                params2.put(StatKey.Parameter.id, map.get(StatKey.Parameter.id));
                if (map.get(StatKey.Parameter.zid) != null) {
                    params2.put(StatKey.Parameter.zid, map.get(StatKey.Parameter.zid));
                }
            } else {
                if (map.get(StatKey.Parameter.p_title) != null) {
                    params2.put(StatKey.Parameter.p_title, map.get(StatKey.Parameter.p_title));
                }
            }
            if (fragment instanceof IStatDynamic) {
                ((IStatDynamic) fragment).onStatResume(params1, params2);
            }
        }
        StatPageInfo info = mHashMap.get(pageObject);
        if (info == null) {
            info = new StatPageInfo();
            mHashMap.put(pageObject, info);
        }
        info.mRefUrl = info.mPageName;
        info.mPageName = pageName;
        info.mPageObject = pageObject;
        info.params1 = params1;
        info.params2 = params2;
        info.needReport = needReport;
        info.isResume = true;
        if (fragment.getUserVisibleHint()) {
            if (needReport) {
                reportPvuv(pageName, info.mRefUrl, info.params1, info.params2);
            }
            info.isVisibleToUser = true;
//			if (info.mTimePause != 0L) {
            //挂起恢复
            int time = (int) ((System.currentTimeMillis() - info.mTimePause) / 1000);
            if (time >= PAGE_PAUSE_MIN_TIME) {
                info.mTimeVisibleStart = System.currentTimeMillis();
                info.mTimePause = 0;
            } else {
                if (info.mTimeVisibleStart == 0) {
                    info.mTimeVisibleStart = System.currentTimeMillis();
                }
            }
//			} else {
//				//第一次resume
//			}
        }
    }

    /**
     * fragment pause
     *
     * @param fragment
     */
    public static void onPause(Fragment fragment) {
        if (!isInited || fragment == null) return;
        String pageObject = fragment + "";
        StatPageInfo info = mHashMap.get(pageObject);
        if (info == null) {
            return;
        }
        info.isResume = false;
        info.mTimePause = System.currentTimeMillis();

        if (info.isVisibleToUser) {
            int time = (int) ((System.currentTimeMillis() - info.mTimeVisibleStart) / 1000);
            if (time >= PAGE_STAY_MIN_TIME && info.needReport) {
                reportAccessTime(info.mPageName, info.params1, info.params2, System.currentTimeMillis() - info.mTimeVisibleStart);
            }
        }
    }

    /**
     * fragment 切换为可见
     *
     * @param fragment
     * @param isVisible
     */
    public static void setUserVisibleHint(Fragment fragment, boolean isVisible) {
        if (!isInited || fragment == null) return;
        String pageObject = fragment + "";
        String pageName = fragment.getClass().getName();
        StatPageInfo info = mHashMap.get(pageObject);
        if (info == null) {
            if (!isVisible) {
                return;
            }
            //user visible hint为true,可能早于onResume,所以需要添加,不过仍然会onResume,所以赋值仍然可以在onResume里做
            info = new StatPageInfo();
            mHashMap.put(pageObject, info);
        }
        info.mRefUrl = info.mPageName;
        info.mPageName = pageName;
        info.mPageObject = pageObject;
        if (isVisible) {
            //转为可见
            if (info.needReport && info.isResume) {
                reportPvuv(pageName, info.mRefUrl, info.params1, info.params2);
            }
            info.isVisibleToUser = true;
            info.mTimeVisibleStart = System.currentTimeMillis();
        } else {
            //转为不可见
            info.isVisibleToUser = false;
            if (info.mTimeVisibleStart != 0) {
                int time = (int) ((System.currentTimeMillis() - info.mTimeVisibleStart) / 1000);
                if (time >= PAGE_STAY_MIN_TIME && info.needReport) {
//					reportPvuv(fragment, null, info.params1, info.params2);
                    reportAccessTime(info.mPageName, info.params1, info.params2, System.currentTimeMillis() - info.mTimeVisibleStart);
                }
                info.mTimeVisibleStart = 0;
            }
        }
    }

    /**
     * activity切换时如果有fragment,则需要上报fragment的停留时间
     */
    private static String reportFragmentAccessTimeWhenExchangeActivity() {
        String pageName = null;
        for (String fragmentInfo : mHashMap.keySet()) {
            StatPageInfo info = mHashMap.get(fragmentInfo);
            if (info == null) {
                continue;
            }
            if (info.isVisibleToUser) {
                int time = (int) ((System.currentTimeMillis() - info.mTimeVisibleStart) / 1000);
                if (time >= PAGE_STAY_MIN_TIME && info.needReport) {
//					reportAccessTime(info.mPageObject, info.mPageName, info.params1,info.params2,System.currentTimeMillis() - info.mTimeVisibleStart);
                }
                //用于activity中获取来源页面,可能是从fagment跳转到了activity
                if (pageName == null) {
                    pageName = info.mPageName;
                }
            }
        }
        mHashMap.clear();
        return pageName;
    }

    public static void setStatParams(HashMap<String, String> map) {
        if (mConstDataSource != null) {
            if (mConstDataSource.userId() != null) {
                //guid,如果登录,则为用户ID
                map.put("u0gid", mConstDataSource.userId());
            } else {
                //guid",如果为登录,则为设备号
                map.put("u0gid", mConstDataSource.deviceId());
            }
            map.put("l0country", StatKey.checkValue(mConstDataSource.country()));
            map.put("l0province", StatKey.checkValue(mConstDataSource.province()));
            map.put("l0city", StatKey.checkValue(mConstDataSource.city()));
            //用户选择城市
            map.put("u0city", StatKey.checkValue(mConstDataSource.userCity()));
            map.put("l0dist", StatKey.checkValue(mConstDataSource.district()));
            map.put("app0b", StatKey.checkValue(mConstDataSource.channel()));
        }
        map.put("l0gps", StatKey.checkValue(mConstDataSource.gps()));
        map.put("l0ip", StatKey.checkValue(NetUtil.getIPAddress(mContext)));
        if (mCurrentActivityPage != null) {
            map.put("CurrentActivityPage", (mCurrentActivityPage.mRefUrl + "->" + mCurrentActivityPage.mPageName).replace("com.redstar.mainapp.", ""));
        }
    }

    /**
     * activity内的点击事件上报
     *
     * @param pageName 对象包名+类名
     * @param params1  上报参数列表,url参数
     * @param params2  上报参数列表,post参数
     */
    public static void statClick(String pageName, HashMap<String, String> params1, HashMap<String, String> params2) {
        if (pageName == null) {
            return;
        }
        reportClickAction(pageName, params1, params2);
    }

    /**
     * activity内的曝光率上报
     *
     * @param pageName 对象包名+类名
     * @param params1  上报参数列表,url参数
     * @param params2  上报参数列表,post参数
     */
    public static void statExpo(String pageName, HashMap<String, String> params1, HashMap<String, String> params2) {
        if (pageName == null) {
            return;
        }
        reportExpo(pageName, params1, params2);
    }

    /**
     * 停留时间上报
     *
     * @param pageName 对象包名+类名
     * @param params1  上报参数列表,url参数
     * @param params2  上报参数列表,post参数
     */
    public static void statTime(String pageName, HashMap<String, String> params1, HashMap<String, String> params2, long time) {
        if (pageName == null) {
            return;
        }
        reportAccessTime(pageName, params1, params2, time);
    }

    /**
     * activity内的深度S点 屏数
     *
     * @param pageName 对象包名+类名
     * @param params1  上报参数列表,url参数
     * @param params2  上报参数列表,post参数
     */
    public static void statSHeight(String pageName, HashMap<String, String> params1, HashMap<String, String> params2) {
        if (pageName == null) {
            return;
        }
        reportSHeightExpo(pageName, params1, params2);
    }
    /*******************************************************************************
     *	Private Methods
     *******************************************************************************/
    /**
     * 检查并上报activity页面的访问时间
     */
    private static void checkAndReportActivityAccessTime() {
        int time = (int) ((mCurrentActivityPage.mMillTimePause - mCurrentActivityPage.mMillTimeStart) / 1000);
        if (mCurrentActivityPage.needReport && time >= PAGE_STAY_MIN_TIME) {
            reportAccessTime(mCurrentActivityPage.mPageName, mCurrentActivityPage.params1, mCurrentActivityPage.params2
                    , mCurrentActivityPage.mMillTimePause - mCurrentActivityPage.mMillTimeStart);
        }
    }

    /**
     * 上报PVUV
     *
     * @param pageName 页面包名+类名
     * @param refUrl   来源
     * @param params1  参数
     * @param params2  参数
     */
    private static void reportPvuv(String pageName, String refUrl, HashMap<String, String> params1, HashMap<String, String> params2) {
        if (pageName == null) {
            return;
        }
        if (params1 == null) {
            params1 = new HashMap<>();
        }
        if (params2 == null) {
            params2 = new HashMap<>();
        }
        params2.put(StatKey.Parameter.p_url, pageName);
        if (!TextUtils.isEmpty(mLastRefurl)) {
            params2.put(StatKey.Parameter.r_url, StatKey.checkValue(mLastRefurl));
        }
        mLastRefurl = pageName;
        setPvUvParams(params1, params2);
        if (mDataControl != null) {
            mDataControl.reportStatPvUvData(params1, params2);
        }
        UserDataManager.mLastPauseActivityCLassName = pageName;
    }


    /**
     * 上报访问时间
     *
     * @param pageName 来源
     * @param params1  参数
     * @param params2  参数
     * @param time     停留时间
     */
    private static void reportAccessTime(String pageName, HashMap<String, String> params1, HashMap<String, String> params2, long time) {
        if (time < 0) {
            time = 0;
        }
        if (params1 == null) {
            params1 = new HashMap<>();
        }
        if (params2 == null) {
            params2 = new HashMap<>();
        }
        params2.put(StatKey.Parameter.p_url, pageName);
        params2.put(StatKey.Parameter.p_stay_time, StatKey.checkValue(time + ""));
        setAccessTimeParams(params1, params2);
        HashMap<String, String> temp = new HashMap<>();
        temp.putAll(params2);
        //如果z点的id存在,则是用z点的id
        if (temp.containsKey(StatKey.Parameter.zid)) {
            temp.put(StatKey.Parameter.id, temp.get(StatKey.Parameter.zid));
            temp.remove(StatKey.Parameter.zid);
        }
        if (mDataControl != null) {
            mDataControl.reportStatZData(params1, temp);
        }
    }


    /**
     * 上报点击事件
     *
     * @param pageName 页面包名+类名
     * @param params1  参数
     * @param params2  参数
     */
    private static void reportClickAction(String pageName, HashMap<String, String> params1, HashMap<String, String> params2) {
        if (pageName == null) {
            return;
        }
        if (params1 == null) {
            params1 = new HashMap<>();
        }
        if (params2 == null) {
            params2 = new HashMap<>();
        }
        params2.put(StatKey.Parameter.p_url, pageName);
        setClickActionParams(params1, params2);

        if (mDataControl != null) {
            mDataControl.reportStatFData(params1, params2);
        }
    }

    /**
     * 上报曝光率事件
     *
     * @param pageName 页面包名+类名
     * @param params1  参数
     * @param params2  参数
     */
    private static void reportExpo(String pageName, HashMap<String, String> params1, HashMap<String, String> params2) {
        if (pageName == null) {
            return;
        }
        if (params1 == null) {
            params1 = new HashMap<>();
        }
        if (params2 == null) {
            params2 = new HashMap<>();
        }
        params2.put(StatKey.Parameter.p_url, pageName);
        setExpoParams(params1, params2);

        if (mDataControl != null) {
            mDataControl.reportStatIData(params1, params2);
        }
    }

    /**
     * 上报S点
     *
     * @param pageName 页面包名+类名
     * @param params1  参数
     * @param params2  参数
     */
    private static void reportSHeightExpo(String pageName, HashMap<String, String> params1, HashMap<String, String> params2) {
        if (pageName == null) {
            return;
        }
        if (params1 == null) {
            params1 = new HashMap<>();
        }
        if (params2 == null) {
            params2 = new HashMap<>();
        }
        params2.put(StatKey.Parameter.p_url, pageName);
        setSHeightParams(params1, params2);

        if (mDataControl != null) {
            mDataControl.reportStatSData(params1, params2);
        }
    }

    /**
     * 获取PVUV参数
     *
     * @param params1 参数
     * @param params2 参数
     */
    private static void setPvUvParams(HashMap<String, String> params1, HashMap<String, String> params2) {
        //url参数
        params1.put(StatKey.Parameter.service, "android.pvuv");
        setCommonParams(params1, params2);
    }

    /**
     * 获取PVUV参数
     *
     * @param params1 参数
     * @param params2 参数
     */
    private static void setAccessTimeParams(HashMap<String, String> params1, HashMap<String, String> params2) {
        //url参数
        params1.put(StatKey.Parameter.service, "android.staytime");
        setCommonParams(params1, params2);
    }

    /**
     * 获取点击操作参数
     *
     * @param params1 参数
     * @param params2 参数
     */
    private static void setClickActionParams(HashMap<String, String> params1, HashMap<String, String> params2) {
        //url参数
        params1.put(StatKey.Parameter.service, "android.click");
        setCommonParams(params1, params2);
    }

    /**
     * 获取曝光率参数
     *
     * @param params1 参数
     * @param params2 参数
     */
    private static void setExpoParams(HashMap<String, String> params1, HashMap<String, String> params2) {
        //url参数
        params1.put(StatKey.Parameter.service, "android.expo");
        setCommonParams(params1, params2);
    }

    /**
     * 获取浏览深度参数
     *
     * @param params1 参数
     * @param params2 参数
     */
    private static void setSHeightParams(HashMap<String, String> params1, HashMap<String, String> params2) {
        //url参数
        params1.put(StatKey.Parameter.service, "android.s");
        setCommonParams(params1, params2);
    }

    /**
     * 获取通用参数
     *
     * @param params1 参数
     * @param params2 参数
     */
    private static void setCommonParams(HashMap<String, String> params1, HashMap<String, String> params2) {
        //url参数
        long timestamp = System.currentTimeMillis();
        params1.put(StatKey.Parameter.ts, String.valueOf(timestamp));
        //post参数
        params2.put(StatKey.Parameter.d_browser, "app");
        //埋点SDK版本号
        params2.put(StatKey.Parameter.version, "1.0");

        if (mConstDataSource != null) {
            if (mConstDataSource.userId() != null) {
                //guid,如果登录,则为用户ID
                params2.put(StatKey.Parameter.u_gid, mConstDataSource.userId());
            } else {
                //guid,如果为登录,则为设备号
                params2.put(StatKey.Parameter.u_gid, mConstDataSource.deviceId());
            }
//            params2.put(StatKey.Parameter.p_domain, mConstDataSource.domain());
            params2.put(StatKey.Parameter.u_id, StatKey.checkValue(mConstDataSource.userId()));
            params2.put(StatKey.Parameter.l_country, StatKey.checkValue(mConstDataSource.country()));
            params2.put(StatKey.Parameter.l_province, StatKey.checkValue(mConstDataSource.province()));
            params2.put(StatKey.Parameter.l_city, StatKey.checkValue(mConstDataSource.city()));
            //用户选择城市
            params2.put(StatKey.Parameter.u_city, StatKey.checkValue(mConstDataSource.userCity()));
            params2.put(StatKey.Parameter.l_dist, StatKey.checkValue(mConstDataSource.district()));
            params2.put(StatKey.Parameter.app_b, StatKey.checkValue(mConstDataSource.channel()));
        }
        params2.put(StatKey.Parameter.d_os_version, StatKey.checkValue(StatConstData.osVersion));
        params2.put(StatKey.Parameter.d_os, StatKey.OS_TYPE_ANDROID);
        params2.put(StatKey.Parameter.d_prixel_x, StatKey.checkValue(String.valueOf(DeviceUtil.getScreenWidth())));
        params2.put(StatKey.Parameter.d_prixel_y, StatKey.checkValue(String.valueOf(DeviceUtil.getScreenHeight())));
        params2.put(StatKey.Parameter.d_model, StatKey.checkValue(StatConstData.deviceName));
        params2.put(StatKey.Parameter.d_mark, StatKey.checkValue(StatConstData.deviceBrand));
        params2.put(StatKey.Parameter.u_mid, UserDataManager.getDeviceUtdid());
        params2.put(StatKey.Parameter.app_v, StatKey.checkValue(StatConstData.appVersionName));
        params2.put(StatKey.Parameter.u_idfa, StatKey.checkValue(StatConstData.deviceImei));
        params2.put(StatKey.Parameter.l_gps, StatKey.checkValue(mConstDataSource.gps()));
        params2.put(StatKey.Parameter.l_ip, StatKey.checkValue(NetUtil.getIPAddress(mContext)));
    }

    /*******************************************************************************
     *	Internal Class,Interface
     *******************************************************************************/

    private static class StatActivityPageInfo {
        /**
         * 页面对象
         */
        private String mPageObject;
        /**
         * 页面包名+类名
         */
        private String mPageName;
        /**
         * 页面进入时间
         */
        private long mMillTimeStart = 0L;
        /**
         * 页面挂起时间
         */
        private long mMillTimePause = 0L;
        /**
         * 是否为统计页面
         */
        private boolean needReport = true;
        /**
         * 前一页面信息
         */
        private String mRefUrl;
        /**
         * Description
         */
        private HashMap<String, String> params1;
        private HashMap<String, String> params2;
    }

    private static class StatPageInfo {
        /**
         * 页面对象
         */
        private String mPageObject;
        /**
         * page name
         */
        private String mPageName;
        /**
         * fragment状态,resume/pause
         */
        private boolean isResume;
        /**
         * fragment的user visible hint
         */
        private boolean isVisibleToUser;
        /**
         * user visible hint为true时的开始时间
         */
        private long mTimeVisibleStart;
        /**
         * fragment 挂起时间
         */
        private long mTimePause;
        /**
         * 是否为统计页面
         */
        private boolean needReport = true;
        /**
         * 前一页面信息
         */
        private String mRefUrl;
        private HashMap<String, String> params1;
        private HashMap<String, String> params2;
    }
}
