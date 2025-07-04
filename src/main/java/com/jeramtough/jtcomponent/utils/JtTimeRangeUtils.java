package com.jeramtough.jtcomponent.utils;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * <pre>
 * Created on 2025/7/4 下午1:05
 * by @author WeiBoWen
 * </pre>
 */
public class JtTimeRangeUtils {

    /*public static void main(String[] args) {
        {
            Date[] range = TimeRangeUtils.calculateTimeRange("WEEK", 1);
            System.out.println("WEEK开始时间：" + DateUtil.format(range[0], "yyyy-MM-dd HH:mm:ss"));
            System.out.println("结束时间：" + DateUtil.format(range[1], "yyyy-MM-dd HH:mm:ss"));
        }
        {
            Date[] range = TimeRangeUtils.calculateTimeRange("DAY", 1);
            System.out.println("DAY开始时间：" + DateUtil.format(range[0], "yyyy-MM-dd HH:mm:ss"));
            System.out.println("结束时间：" + DateUtil.format(range[1], "yyyy-MM-dd HH:mm:ss"));
        }
        {
            Date[] range = TimeRangeUtils.calculateTimeRange("MONTH", 1);
            System.out.println("MONTH开始时间：" + DateUtil.format(range[0], "yyyy-MM-dd HH:mm:ss"));
            System.out.println("结束时间：" + DateUtil.format(range[1], "yyyy-MM-dd HH:mm:ss"));
        }
        {
            Date[] range = TimeRangeUtils.calculateTimeRange("YEAR", 1);
            System.out.println("YEAR开始时间：" + DateUtil.format(range[0], "yyyy-MM-dd HH:mm:ss"));
            System.out.println("结束时间：" + DateUtil.format(range[1], "yyyy-MM-dd HH:mm:ss"));
        }

    }*/

    // 中国时区
    private static final ZoneId DEFAULT_ZONE = ZoneId.of("Asia/Shanghai");

    public static Date[] calculateTimeRange(String timeType, int timeNumber) {
        if (timeNumber > 0) {
            timeNumber = timeNumber - 1;
        }
        LocalDateTime now = LocalDateTime.now(DEFAULT_ZONE);

        LocalDateTime startDateTime;
        // 结束时间为当天 23:59:59.999
        LocalDateTime endDateTime = roundToDayEnd(now);

        switch (timeType.toUpperCase()) {
            case "YEAR":
                startDateTime = now.minusYears(timeNumber);
                startDateTime = roundToYearStart(startDateTime);
                break;

            case "MONTH":
                startDateTime = now.minusMonths(timeNumber);
                startDateTime = roundToMonthStart(startDateTime);
                break;

            case "WEEK":
                startDateTime = now.minusWeeks(timeNumber);
                startDateTime = roundToMondayStart(startDateTime);
                break;

            case "DAY":
            default:
                startDateTime = now.minusDays(timeNumber);
                startDateTime = roundToDayStart(startDateTime);
                break;
        }

        Date startDate = Date.from(startDateTime.atZone(DEFAULT_ZONE).toInstant());
        Date endDate = Date.from(endDateTime.atZone(DEFAULT_ZONE).toInstant());

        return new Date[]{startDate, endDate};
    }

    // 对齐到当天 00:00:00
    private static LocalDateTime roundToDayStart(LocalDateTime dt) {
        return dt.toLocalDate().atStartOfDay();
    }

    // 对齐到当天 23:59:59.999
    private static LocalDateTime roundToDayEnd(LocalDateTime dt) {
        return dt.toLocalDate().atTime(23, 59, 59, 999_999_999);
    }

    // 对齐到当月第一天 00:00:00
    private static LocalDateTime roundToMonthStart(LocalDateTime dt) {
        return dt.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
    }

    // 对齐到当年第一天 00:00:00
    private static LocalDateTime roundToYearStart(LocalDateTime dt) {
        return dt.with(TemporalAdjusters.firstDayOfYear()).with(LocalTime.MIN);
    }

    // 对齐到周一 00:00:00（中国标准）
    private static LocalDateTime roundToMondayStart(LocalDateTime dt) {
        return dt.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).with(LocalTime.MIN);
    }

}
