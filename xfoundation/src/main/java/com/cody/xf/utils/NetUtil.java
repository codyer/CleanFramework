package com.cody.xf.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2016/3/9.
 *
 * @author Liuliwei
 * @description 判断网络状态
 */
public class NetUtil {
    //判断是否ip地址
    public static boolean isIP(String address)
    {
        if(address == null || address.length() < 7 || address.length() > 15 || "".equals(address))
        {
            return false;
        }
        String regex = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(address);
        return mat.find();
    }

    // 判断网络连接状态
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo net = connectivityManager.getActiveNetworkInfo();
            if (net != null) {
                return net.isAvailable();
            }
        }
        return false;
    }

    /**
     * @param context
     * @return
     * @description 判断wifi网络
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiNet = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiNet != null && isNetworkConnected(context)) {
                return wifiNet.isAvailable();
            }
        }
        return false;
    }

    // 判断是否手机网络
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobileNet = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobileNet != null && isNetworkConnected(context)) {
                return mobileNet.isAvailable();
            }
        }
        return false;
    }

    // 获取连接类型
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo net = connectivityManager.getActiveNetworkInfo();
            if (net != null && net.isAvailable()) {
                return net.getType();
            }
        }
        return -1;
    }

    public static String getIPAddress(Context context) {
        if (context == null)return null;
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }
}
