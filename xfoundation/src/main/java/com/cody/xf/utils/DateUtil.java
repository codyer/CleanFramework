package com.cody.xf.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Cody.yi on 2016.8.16.
 * 时间格式化工具类
 */
public class DateUtil {

    /**
     * 获取日期
     * @param time 时间
     * @return eg：2016年6月5日
     */
    public static String getDateString(long time){
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        return df.format(new Date(time));
    }
    public static Date stringdata2date(String dateStr) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * 获取时间
     * @param time 时间
     * @return eg：2016-6-5 11:20
     */
    public static String getTimeString(long time){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
        return df.format(new Date(time));
    }

    /**
     * 获取时间
     * @param time 时间
     * @return eg：2016-6-5 11:20
     */
    public static String getFullTimeString(long time){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return df.format(new Date(time));
    }

    /**
     * 获取时间
     * @param time 时间
     * @return eg：2016/6/5 11:20
     */
    public static String getTimeSlashString(long time){
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.US);
        return df.format(new Date(time));
    }


    /**
     * 获取时间
     * @param time 时间
     * @return eg：／7月14日 12:00／
     */
    public static String getSpecialTimeString(long time){
        SimpleDateFormat df = new SimpleDateFormat("/ MM月dd日 HH:mm /", Locale.CHINA);
        return df.format(new Date(time));
    }


    public static Date string2date(String dateStr) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String Data2String(Date date)
    {
        return new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static Date[] string2date(String dateStr[]) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date[] dates = new Date[dateStr.length];
        for (int i = 0; i < dateStr.length; i++) {
            Date date = null;
            try {
                date = sdf.parse(dateStr[i]);
                dates[i] = date;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dates;
    }

}
