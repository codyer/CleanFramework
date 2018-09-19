package com.cody.xf.utils;

import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.cody.xf.XFoundation;

/**
 * Created by cody.yi on 2018/7/10.
 * 定位
 */
public class LocationUtil {

    private static LocationUtil sInstance;
    private final AMapLocationClient mLocationClient;
    private final MapLocationListener mLocationListener;
    private AMapLocation mAMapLocation;

    public static void install() {
        sInstance = new LocationUtil();
        if (getInstance().mLocationClient != null) {
            getInstance().mLocationClient.startLocation();
        }
    }

    public static void uninstall() {
        if (getInstance().mLocationClient != null && getInstance().mLocationListener != null) {
            getInstance().mLocationClient.unRegisterLocationListener(getInstance().mLocationListener);
            getInstance().mLocationClient.stopLocation();
            getInstance().mLocationClient.onDestroy();
        }
        sInstance = null;
    }

    public static AMapLocation getLocation() {
        return getInstance().mAMapLocation;
    }

    private static LocationUtil getInstance() {
        if (sInstance == null) {
            throw new NullPointerException("You should call ActivityUtil.install() in you application first!");
        } else {
            return sInstance;
        }
    }

    private LocationUtil() {
        mLocationClient = new AMapLocationClient(XFoundation.getContext());
        AMapLocationClientOption option = new AMapLocationClientOption();
        //可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        option.setGpsFirst(false);
        //可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        option.setHttpTimeOut(30000);
        //可选，设置定位间隔。默认为2秒
        option.setInterval(2000);
        //可选，设置是否返回逆地理地址信息。默认是true
        option.setNeedAddress(true);
        //可选，设置是否单次定位。默认是false
        option.setOnceLocation(true);
        //可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        option.setOnceLocationLatest(false);
        //可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);
        //可选，设置是否使用传感器。默认是false
        option.setSensorEnable(false);
        //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        option.setWifiScan(true);
        //可选，设置是否使用缓存定位，默认为true
        option.setLocationCacheEnable(true);
        //设置定位参数
        mLocationClient.setLocationOption(option);
        // 设置定位监听
        mLocationListener = new MapLocationListener();
        mLocationClient.setLocationListener(mLocationListener);
    }

    class MapLocationListener implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation location) {
            if (location != null) {
                StringBuilder sb = new StringBuilder();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.getErrorCode() == 0) {
                    sb.append("定位成功" + "\n");
                    sb.append("定位类型: ").append(location.getLocationType()).append("\n");
                    sb.append("经    度    : ").append(location.getLongitude()).append("\n");
                    sb.append("纬    度    : ").append(location.getLatitude()).append("\n");
                    sb.append("精    度    : ").append(location.getAccuracy()).append("米").append("\n");
                    sb.append("提供者    : ").append(location.getProvider()).append("\n");
                    sb.append("速    度    : ").append(location.getSpeed()).append("米/秒").append("\n");
                    sb.append("角    度    : ").append(location.getBearing()).append("\n");
                    // 获取当前提供定位服务的卫星个数
                    sb.append("星    数    : ").append(location.getSatellites()).append("\n");
                    sb.append("国    家    : ").append(location.getCountry()).append("\n");
                    sb.append("省            : ").append(location.getProvince()).append("\n");
                    sb.append("市            : ").append(location.getCity()).append("\n");
                    sb.append("城市编码 : ").append(location.getCityCode()).append("\n");
                    sb.append("区            : ").append(location.getDistrict()).append("\n");
                    sb.append("区域 码   : ").append(location.getAdCode()).append("\n");
                    sb.append("地    址    : ").append(location.getAddress()).append("\n");
                    sb.append("兴趣点    : ").append(location.getPoiName()).append("\n");
                    //定位完成的时间

                    sb.append("定位时间: ").append(DateUtil.getFullTimeString(location.getTime()));
                    //存储信息
                    //此cityCode为行政区域编码，精确到区县，为与后台一一对应截取了后两位
                    String adCode = location.getAdCode();
                    if (!TextUtils.isEmpty(adCode) && adCode.length() > 5) {
                        adCode = adCode.substring(0, 4);
                        adCode += "00";
                    }
                    location.setCityCode(adCode);
                } else {
                    //定位失败
                    sb.append("定位失败" + "\n");
                    sb.append("错误码:").append(location.getErrorCode()).append("\n");
                    sb.append("错误信息:").append(location.getErrorInfo()).append("\n");
                    sb.append("错误描述:").append(location.getLocationDetail()).append("\n");
                }
                //定位之后的回调时间
                sb.append("回调时间: ").append(DateUtil.getFullTimeString(System.currentTimeMillis()));
                //解析定位结果，
                String result = sb.toString();
                LogUtil.i(result);
                mAMapLocation = location;
            }
        }
    }
}
