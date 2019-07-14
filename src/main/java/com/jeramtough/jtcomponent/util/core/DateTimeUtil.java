package com.jeramtough.jtcomponent.util.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
     * @param date1 时间1
     * @param date2 时间2
     * @return 相差的秒数
     */
    public static int intervalTime(Date date1, Date date2) {
        long a = date1.getTime();
        long b = date2.getTime();
        long c = a - b;
        int second = (int) Double.valueOf(Double.toString(c / 1000D))
                .doubleValue();
        return second;
    }

}
