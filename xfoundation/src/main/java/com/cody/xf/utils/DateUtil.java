package com.cody.xf.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Cody.yi on 2016.8.16.
 * 时间格式化工具类
 */
public class DateUtil {

    public static String getDateString() {
        SimpleDateFormat df = new SimpleDateFormat("_yyyy_MM_dd_", Locale.CHINA);
        return df.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 判断今天,明天,后天
     *
     * @param time 时间
     * @return eg：
     */
    public static String getDateFrom(long time, boolean isTopDate) {
        try {
            Calendar aCalendar = Calendar.getInstance();
            aCalendar.setTimeInMillis(time);
            int day = aCalendar.get(Calendar.DAY_OF_YEAR);
            int aYear = aCalendar.get(Calendar.YEAR);

            aCalendar.setTimeInMillis(System.currentTimeMillis());
            int now = aCalendar.get(Calendar.DAY_OF_YEAR);
            int bYear = aCalendar.get(Calendar.YEAR);
            if (aYear == bYear) {
                int diff = day - now;
                if (diff < 0) {
                    return "报名已截止";
                } else if (diff == 0) {
                    return "今天报名截止";
                } else if (diff == 1) {
                    return "明天报名截止";
                } else if (diff == 2) {
                    return "后天报名截止";
                } else {
                    if (isTopDate)
                        return DateUtil.getDateString(time, "yyyy年MM月dd日");
                    else
                        return DateUtil.getDateString(time, "MM月dd日") + "报名截止";
                }
            } else if (aYear > bYear) {
                return DateUtil.getDateString(time, "MM月dd日") + "报名截止";
            } else {
                return "报名已截止";
            }
        } catch (Exception e) {
            return "";
        }
    }

    public static String getDateBefore(String time) {
        if (TextUtils.isEmpty(time)) {
            return time;
        }
        long longTime = 0;
        try {
            longTime = Long.parseLong(time);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return getDateBefore(longTime);
    }

    /**
     * 判断今天,昨天
     *
     * @param time 时间
     * @return eg：
     */
    public static String getDateFromat(long time) {
        try {
            Calendar aCalendar = Calendar.getInstance();
            aCalendar.setTimeInMillis(time);
            int day = aCalendar.get(Calendar.DAY_OF_YEAR);

            aCalendar.setTimeInMillis(System.currentTimeMillis());
            int now = aCalendar.get(Calendar.DAY_OF_YEAR);
            int diff = day - now;
            if (diff == -1) {
                return "昨天";
            } else if (diff == 0) {
                return "今天";
            } else {
                return DateUtil.getDateString(time, "yyyy/M/d");
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取日期
     *
     * @param time 时间
     * @return eg：2016年6月5日
     */
    public static String getDateString(long time, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.CHINA);
        return df.format(new Date(time));
    }

    /*2017-09-06 16:55:35*/
    public static long stringToLong(String time) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String longToString(long time, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(new Date(time));
    }

    /**
     * 获取时间
     *
     * @param time 时间
     * @return eg：2016-6-5 11:20
     */
    public static String getTimetoString(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        return df.format(new Date(time));
    }

    public static String getSpecialTimeString(long time, String dateType) {
        SimpleDateFormat df = new SimpleDateFormat(dateType, Locale.CHINA);
        return df.format(new Date(time));
    }

    /**
     * @param dateStr   日期格式
     * @param inFormat  传入的日期格式
     * @param outFormat 返回的日期格式
     * @return
     */
    public static String string2String(String dateStr, String inFormat, String outFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(outFormat, Locale.CHINA);
        return sdf.format(string2date(dateStr, inFormat));
    }

    public static Date string2date(String dateStr, String dateType) {

        SimpleDateFormat sdf = new SimpleDateFormat(dateType);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    // string类型转换为long类型
    // strTime要转换的String类型的时间
    public static long stringToLong(String strTime, String dateType)
            throws ParseException {
        Date date = string2date(strTime, dateType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            return date.getTime();
        }
    }

    /**
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     * @mss 要转换的毫秒数
     * @author fy.zhang
     */
    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        String day = "";
        String hour = "";
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        if (days != 0L) {
            day = days + "天";
        }
        if (hours != 0L) {
            hour = hours + "时";
        }
        return day + hour + minutes + "分"
                + seconds + "秒";
    }

    /**
     * @return 该毫秒数转换为 * days * hours * minutes 后的格式
     * @mss 要转换的秒数
     * @author fy.zhang
     */
    public static String formatDuring2(long mss) {
        if (mss < 60) {
            return "不足一分钟";
        }
        long days = mss / (60 * 60 * 24);
        String day = "";
        String hour = "";
        long hours = (mss % (60 * 60 * 24)) / (60 * 60);
        long minutes = (mss % (60 * 60)) / (60);
        if (days != 0L) {
            day = days + "天";
        }
        if (hours != 0L) {
            hour = hours + "小时";
        }
        return day + hour + minutes + "分";
    }

    public static String data2String(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String get2DayFrom(long date) {
        //所在时区时8，系统初始时间是1970-01-01 80:00:00，注意是从八点开始，计算的时候要加回去
        int offSet = Calendar.getInstance().getTimeZone().getRawOffset();
        long today = (System.currentTimeMillis() + offSet) / 86400000;
        long start = (date + offSet) / 86400000;
        long intervalTime = start - today;
        //-2:前天,-1：昨天,0：今天,1：明天,2：后天
        String strDes = "";
        if (intervalTime == 0) {
            strDes = "今天 " + getDateString(date, "HH:mm");//今天
        } else if (intervalTime == -1) {
            strDes = "昨天 " + getDateString(date, "HH:mm");//昨天
        } else {
            strDes = getDateString(date, "MM月dd日");
        }
        return strDes;
    }

    public static String get2DayFrom2(long date) {
        //所在时区时8，系统初始时间是1970-01-01 80:00:00，注意是从八点开始，计算的时候要加回去
        int offSet = Calendar.getInstance().getTimeZone().getRawOffset();
        long today = (System.currentTimeMillis() + offSet) / 86400000;
        long start = (date + offSet) / 86400000;
        long intervalTime = start - today;
        //-2:前天,-1：昨天,0：今天,1：明天,2：后天
        String strDes = "";
        if (intervalTime == 0) {
            strDes = "今天 ";//今天
        } else if (intervalTime == -1) {
            strDes = "昨天 ";//昨天
        } else {
            strDes = getDateString(date, "MM月dd日");
        }
        return strDes;
    }

    public static String utc2Local(String utcTime, String localTimePatten) {
        return utc2Local(utcTime, "yyyy-MM-dd'T'HH:mm:ss.sss'Z'", localTimePatten);
    }

    /**
     * 函数功能描述:UTC时间转本地时间格式
     *
     * @param utcTime         UTC时间
     * @param utcTimePatten   UTC时间格式
     * @param localTimePatten 本地时间格式
     * @return 本地时间格式的时间
     * eg:utc2Local("2018-01-06T02:02:23.765Z", "yyyy-MM-dd'T'HH:mm:ss.sss'Z'", "yyyy-MM-dd HH:mm:ss.SSS")
     */
    public static String utc2Local(String utcTime, String utcTimePatten, String localTimePatten) {
        if (TextUtils.isEmpty(utcTime)) {
            return utcTime;
        }
        SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePatten);
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));//时区定义并进行时间获取
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (Exception e) {
            e.printStackTrace();
            return utcTime;
        }
        SimpleDateFormat localFormater = new SimpleDateFormat(localTimePatten);
        localFormater.setTimeZone(TimeZone.getDefault());
        String localTime = localFormater.format(gpsUTCDate.getTime());
        return localTime;
    }


    /**
     * 当前时间之前的时间显示格式
     *
     * @param time 时间
     * @return eg：
     */
    public static String getDateBefore(long time) {
        try {
            Calendar aCalendar = Calendar.getInstance();
            aCalendar.setTimeInMillis(time);
            int day = aCalendar.get(Calendar.DAY_OF_YEAR);
            int otherYear = aCalendar.get(Calendar.YEAR);

            aCalendar.setTimeInMillis(System.currentTimeMillis());
            int now = aCalendar.get(Calendar.DAY_OF_YEAR);
            int nowYear = aCalendar.get(Calendar.YEAR);
            if (otherYear == nowYear) {
                int diff = day - now;
                if (diff == 0) {
                    return DateUtil.getHHmm(time);
                } else if (diff == -1) {
                    return "昨天";
                } else if (diff == -2) {
                    return "前天";
                } else {
                    return DateUtil.getMMdd(time);
                }
            } else {
                return DateUtil.getMMdd(time);
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 距离现在多久
     *
     * @param time 时间
     * @return eg：3天后
     */
    public static String getDateFrom(long time) {
        try {
            Calendar aCalendar = Calendar.getInstance();
            aCalendar.setTimeInMillis(time);
            int day = aCalendar.get(Calendar.DAY_OF_YEAR);

            aCalendar.setTimeInMillis(System.currentTimeMillis());
            int now = aCalendar.get(Calendar.DAY_OF_YEAR);
            if (now > day) {
                return now - day + "天前";
            } else if (now < day) {
                return (day - now) + "天后";
            } else {
                return "今天";
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 通用的时间格式
     *
     * @param time 时间
     * @return eg：
     */
    public static String commonFormatTime(long time) {
        try {
            Calendar aCalendar = Calendar.getInstance();
            aCalendar.setTimeInMillis(time);
            int day = aCalendar.get(Calendar.DAY_OF_YEAR);
            int otherYear = aCalendar.get(Calendar.YEAR);

            aCalendar.setTimeInMillis(System.currentTimeMillis());
            int now = aCalendar.get(Calendar.DAY_OF_YEAR);
            int nowYear = aCalendar.get(Calendar.YEAR);
            if (otherYear == nowYear) {
                int diff = day - now;
                if (diff == 0) {
                    return DateUtil.getHHmm(time);
                } else if (diff == -1) {
                    return "昨天" + DateUtil.getHHmm(time);
                } else if (diff == -2) {
                    return "前天" + DateUtil.getHHmm(time);
                } else {
                    return DateUtil.getSpecialTimeString(time);
                }
            } else {
                return DateUtil.getYYYYMMddHHmm(time);
            }
        } catch (Exception e) {
            return "";
        }
    }

    public static String get3DayFrom(long date) {
        //所在时区时8，系统初始时间是1970-01-01 80:00:00，注意是从八点开始，计算的时候要加回去
        int offSet = Calendar.getInstance().getTimeZone().getRawOffset();
        long today = (System.currentTimeMillis() + offSet) / 86400000;
        long start = (date + offSet) / 86400000;
        long intervalTime = start - today;
        //-2:前天,-1：昨天,0：今天,1：明天,2：后天
        String strDes = "";
        if (intervalTime == 0) {
            strDes = "今天";//今天
        } else if (intervalTime == 1) {
            strDes = "明天";//昨天
        } else if (intervalTime == 2) {
            strDes = "后天";//直接显示时间
        } else {
            strDes = getTimeYMDString(date);
        }
        return strDes;
    }

    /**
     * 获取日期
     *
     * @param time 时间
     * @return eg：2016.06.05
     */
    public static String getDateDotString(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
        return df.format(new Date(time));
    }

    /**
     * 获取日期
     *
     * @param time 时间
     * @return eg：2016年6月5日
     */
    public static String getDateString(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return df.format(new Date(time));
    }

    /**
     * 获取时分 19:20
     *
     * @param time 时间
     * @return eg：2016年6月5日
     */
    public static String getHHmm(long time) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm", Locale.CHINA);
        return df.format(new Date(time));
    }

    /**
     * 获取月日 12月20日
     *
     * @param time 时间
     * @return eg：2016年6月5日
     */
    public static String getMMdd(long time) {
        SimpleDateFormat df = new SimpleDateFormat("MM月dd日", Locale.CHINA);
        return df.format(new Date(time));
    }

    /**
     * 获取年月日时分
     *
     * @param time 时间
     * @return eg：12月20日 19:20
     */
    public static String getYYYYMMddHHmm(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.CHINA);
        return df.format(new Date(time));
    }

    public static Date stringdata2date(String dateStr) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            date = new Date(System.currentTimeMillis());
            e.printStackTrace();
        }

        return date;
    }

    /**
     * 获取时间
     *
     * @param time 时间
     * @return eg：2016-6-5 11:20
     */
    public static String getTimeString(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return df.format(new Date(time));
    }

    /**
     * 获取年份
     *
     * @param time 时间
     * @return eg：2016-6-5 11:20
     */
    public static String getYear(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy", Locale.CHINA);
        return df.format(new Date(time));
    }

    /**
     * 获取时间
     *
     * @param time 时间
     * @return eg：2016-6-5 11:20
     */
    public static String getTimeYMDString(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        return df.format(new Date(time));
    }

    /**
     * 获取时间
     *
     * @param time 时间
     * @return eg：2016-6-5 11:20
     */
    public static String getTimeYMDString(String time) {
        String format = "";
        try {
            long timestamp = Long.parseLong(time);
            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
            format = df.format(new Date(timestamp));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return format;
    }

    /**
     * 获取时间
     *
     * @param time   时间
     * @param format 时间格式 例如："yyyy年MM月dd日"
     * @return eg：2016-6-5 11:20
     */
    public static String getTimeString(String time, String format) {
        try {
            long timestamp = Long.parseLong(time);
            SimpleDateFormat df = new SimpleDateFormat(format, Locale.CHINA);
            format = df.format(new Date(timestamp));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return format;
    }

    public static long getLongTime(String time) {
        long l = 0;
        try {
            l = Long.parseLong(time);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return l;
    }

    /**
     * 判断给定时间是否小于今天
     *
     * @param when
     * @return
     */
    public static boolean isAfter(long when) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(when);
        int thenDay = instance.get(Calendar.DAY_OF_YEAR);
        int thenYear = instance.get(Calendar.YEAR);
        instance.setTimeInMillis(System.currentTimeMillis());
        int currentDay = instance.get(Calendar.DAY_OF_YEAR);
        int currentYear = instance.get(Calendar.YEAR);
        return currentYear > thenYear || currentYear == thenYear && currentDay > thenDay;
    }

    /**
     * 获取时间
     *
     * @param time 时间
     * @return eg：2016-6-5 11:20
     */
    public static String getFullTimeString(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return df.format(new Date(time));
    }

    /**
     * 获取时间
     *
     * @param time 时间
     * @return eg：2016/6/5 11:20
     */
    public static String getTimeSlashString(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.CHINA);
        return df.format(new Date(time));
    }


    /**
     * 获取时间
     *
     * @param time 时间
     * @return eg：／7月14日 12:00／
     */
    public static String getSpecialTimeString(long time) {
        SimpleDateFormat df = new SimpleDateFormat("MM月dd日 HH:mm", Locale.CHINA);
        return df.format(new Date(time));
    }


    public static Date string2date(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            date = new Date(System.currentTimeMillis());
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 时间格式回去的：yyyy-MM-dd转成其他
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static String dataFormat(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        String result = dateStr;
        try {
            date = sdf.parse(dateStr);
            result = new SimpleDateFormat(format).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String Data2String(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String data2String(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
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

    /**
     * 是否距离当前时间太接近
     */
    public static boolean isNearly(String time) {
        if (StringUtil.isEmpty(time)) return true;
        return string2date(time).before(new Date(System.currentTimeMillis()));
    }

    /**
     * 是否今天之前的日期
     * 比较方式:param(time) < 当天
     * 单位:天
     * 需要日期字符串格式:yyyy-MM-dd
     */
    public static boolean isBeforeToday(String time) {
        if (StringUtil.isEmpty(time)) return true;
        return string2date(time).before(string2date(getDateString(System.currentTimeMillis())));
    }

    /**
     * 是否今天
     * 比较方式:param(time) == 当天
     * 单位:天
     * 需要日期字符串格式:yyyy-MM-dd
     */
    public static boolean isAlikeDay(String time) {
        return string2date(time).equals(string2date(getDateString(System.currentTimeMillis())));
    }

    /**
     * 时间戳格式转换
     */
    static String dayNames[] = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public static String getNewChatTime(long timesamp) {
        String result = "";
        Calendar todayCalendar = Calendar.getInstance();
        Calendar otherCalendar = Calendar.getInstance();
        otherCalendar.setTimeInMillis(timesamp);

        String timeFormat = "M月d日 HH:mm";
        String yearTimeFormat = "yyyy年M月d日 HH:mm";
        String am_pm = "";
        int hour = otherCalendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour < 6) {
            am_pm = "凌晨";
        } else if (hour >= 6 && hour < 12) {
            am_pm = "早上";
        } else if (hour == 12) {
            am_pm = "中午";
        } else if (hour > 12 && hour < 18) {
            am_pm = "下午";
        } else if (hour >= 18) {
            am_pm = "晚上";
        }
        timeFormat = "M月d日 " + am_pm + "HH:mm";
        yearTimeFormat = "yyyy年M月d日 " + am_pm + "HH:mm";

        boolean yearTemp = todayCalendar.get(Calendar.YEAR) == otherCalendar.get(Calendar.YEAR);
        if (yearTemp) {
            int todayMonth = todayCalendar.get(Calendar.MONTH);
            int otherMonth = otherCalendar.get(Calendar.MONTH);
            if (todayMonth == otherMonth) {//表示是同一个月
                int temp = todayCalendar.get(Calendar.DATE) - otherCalendar.get(Calendar.DATE);
                switch (temp) {
                    case 0:
                        result = getHourAndMin(timesamp);
                        break;
                    case 1:
                        result = "昨天 " + getHourAndMin(timesamp);
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        int dayOfMonth = otherCalendar.get(Calendar.WEEK_OF_MONTH);
                        int todayOfMonth = todayCalendar.get(Calendar.WEEK_OF_MONTH);
                        if (dayOfMonth == todayOfMonth) {//表示是同一周
                            int dayOfWeek = otherCalendar.get(Calendar.DAY_OF_WEEK);
                            if (dayOfWeek != 1) {//判断当前是不是星期日     如想显示为：周日 12:09 可去掉此判断
                                result = dayNames[otherCalendar.get(Calendar.DAY_OF_WEEK) - 1] + getHourAndMin
                                        (timesamp);
                            } else {
                                result = getTime(timesamp, timeFormat);
                            }
                        } else {
                            result = getTime(timesamp, timeFormat);
                        }
                        break;
                    default:
                        result = getTime(timesamp, timeFormat);
                        break;
                }
            } else {
                result = getTime(timesamp, timeFormat);
            }
        } else {
            result = getYearTime(timesamp, yearTimeFormat);
        }
        return result;
    }

    /**
     * 当天的显示时间格式
     *
     * @param time
     * @return
     */
    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    /**
     * 不同一周的显示时间格式
     *
     * @param time
     * @param timeFormat
     * @return
     */
    public static String getTime(long time, String timeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        return format.format(new Date(time));
    }

    /**
     * 不同年的显示时间格式
     *
     * @param time
     * @param yearTimeFormat
     * @return
     */
    public static String getYearTime(long time, String yearTimeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(yearTimeFormat);
        return format.format(new Date(time));
    }

    /***
     * 是否是同一年，月，日
     * @param targetTime
     * @param compareTime
     * @param field  {@link Calendar#MONTH} , {@link Calendar#YEAR} and {@link Calendar#DAY_OF_YEAR}
     * @return
     */
    public static boolean isSameDate(Date targetTime, Date compareTime, int field) {
        Calendar tarCalendar = Calendar.getInstance();
        tarCalendar.setTime(targetTime);
        int tarTime = tarCalendar.get(field);

        Calendar comCalendar = Calendar.getInstance();
        comCalendar.setTime(compareTime);
        int comTime = comCalendar.get(field);
        return tarTime == comTime;
    }

    /***
     * 商家学院时间戳转换
     * @param endTime
     * @return
     */
    public static String getShortTime(long endTime) {
        Date endDate = new Date(endTime);
        Date curDate = new Date();
        String result = "";
        long durTime = curDate.getTime() - endDate.getTime();

        if (durTime < 0) {
            result = DateUtil.getTime(endTime, "yyyy.MM.dd");
        } else if (durTime / (60 * 60 * 1000) < 1) {
            //小于一小时显示分钟
            result = Math.max(1, durTime / (60 * 1000)) + "分钟前发布";
        } else if (durTime / (60 * 60 * 1000) < 24) {
            //小于一天显示小时
            result = durTime / (60 * 60 * 1000) + "小时前发布";
        } else if (durTime / (24 * 60 * 60 * 1000) < 30) {
            //小于30天显示天数
            result = durTime / (24 * 60 * 60 * 1000) + "天前发布";
        } else {
            result = DateUtil.getTime(endTime, "yyyy.MM.dd");
        }

        return result;
    }
}
