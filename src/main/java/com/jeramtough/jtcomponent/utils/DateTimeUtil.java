package com.jeramtough.jtcomponent.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 11718
 */
public class DateTimeUtil {

    /**
     * 返回当前时间
     *
     * @param format DateFormat
     * @return 返回当前时间
     */
    public static String getCurrentDateTime(DateFormat format) {
        Date date = new Date();
        String time = format.format(date);
        return time;
    }

    /**
     * @return 返回当前时间
     */
    public static String getCurrentDateTime() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        return time;
    }

    public static String getCurrentDate() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(date);
        return time;
    }

    /**
     * compute date(日期时间的相加减)
     *
     * @param startDate 开始时间
     * @param operation 加或者减
     * @param second    秒
     * @return 最终时间
     */
    public static String dateTimeOperation(Date startDate, String operation,
                                           int second) {
        long t = startDate.getTime();

        if ("+".equals(operation)) {
            t += second * 1000;
        }
        else if ("-".equals(operation)) {
            t -= second * 1000;
        }
        Date date = new Date(t);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        return time;
    }

    public static String getFormattedDate(Date date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        return time;
    }

    /**
     * Date类型，就使用equals(), before(), after()来计算 long类型，就使用==, 小于号, 大于号来计算
     *
     * @param startDate 时间1
     * @param endDate   时间2
     * @return 相差的秒数
     */
    public static int intervalTime(Date startDate, Date endDate) {
        long a = startDate.getTime();
        long b = endDate.getTime();
        long c = b - a;
        int second = (int) Double.valueOf(Double.toString(c / 1000D))
                                 .doubleValue();
        return second;
    }

    /**
     * @param date
     * @param day  想要获取的日期与传入日期的差值 比如想要获取传入日期前四天的日期 day=-4即可
     * @return
     */
    public static Date getSomeDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * 日期差天数、小时、分钟、秒数组
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long[] getDisTime(Date startDate, Date endDate) {
        long timesDis = Math.abs(startDate.getTime() - endDate.getTime());
        long day = timesDis / (1000 * 60 * 60 * 24);
        long hour = timesDis / (1000 * 60 * 60) - day * 24;
        long min = timesDis / (1000 * 60) - day * 24 * 60 - hour * 60;
        long sec = timesDis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60;
        return new long[]{day, hour, min, sec};
    }

    /**
     * 日期差天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getDisDay(Date startDate, Date endDate) {
        long[] dis = getDisTime(startDate, endDate);
        long day = dis[0];
        if (dis[1] > 0 || dis[2] > 0 || dis[3] > 0) {
            day += 1;
        }
        return day;
    }

    /**
     * 日期差文字描述
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static String getDistanceTimeStr(Date startDate, Date endDate) {
        long[] dis = getDisTime(startDate, endDate);
        return new StringBuilder().append(dis[0]).append("天").append(dis[1]).append("小时").append(dis[2]).append(
                "分钟").append(dis[3]).append("秒").toString();
    }

    public static String getDistanceTimeStr(Date startDate, Date endDate, String type) {
        long[] dis = getDisTime(startDate, endDate);

        switch (type) {
            case "天":
                long dayNumber = dis[0];
                if (dayNumber == 0) {
                    return "今天";
                }
                else if (dayNumber == 1) {
                    return "昨天";
                }
                else if (dayNumber == 2) {
                    return "前天";
                }
                else {
                    return dayNumber + "天前";
                }
            default:
                return new StringBuilder().append(dis[0]).append("天").append(dis[1]).append("小时").append(
                        dis[2]).append(
                        "分钟").append(dis[3]).append("秒").toString();
        }

    }

}
