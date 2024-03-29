package com.jeramtough.jtcomponent.utils;

/**
 * <pre>
 * Created on 2020/10/14 18:11
 * by @author WeiBoWen
 * </pre>
 */
public class LocationUtil {

    /**
     * 赤道半径
     */
    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * Description : 通过经纬度获取距离(单位：米)
     * Group :
     *
     * @param origin      出发点
     * @param destination 目的地
     * @return double 距离(单位：米)
     */
    public static double getDistance(String origin, String destination) {
        if (origin == null) {
            System.err.println("出发点 经纬度不可以为空！");
            return 0;
        }
        if (destination == null) {
            System.err.println("目的地 经纬度不可以为空！");
            return 0;
        }
        String[] temp = origin.split(",");
        String[] temp2 = destination.split(",");

        double radLat1 = rad(Double.parseDouble(temp[1]));
        double radLat2 = rad(Double.parseDouble(temp2[1]));
        double a = radLat1 - radLat2;
        double b = rad(Double.parseDouble(temp[0])) - rad(Double.parseDouble(temp2[0]));
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        // 保留两位小数
        s = Math.round(s * 100d) / 100d;
        s = s * 1000;
        return s;
    }

    /**
     * Description : 通过经纬度获取距离(单位：米)
     * Group :
     *
     * @param originLon      出发点经度
     * @param originLat      出发点纬度
     * @param destinationLon 目的地经度
     * @param destinationLat 目的地纬度
     * @return double 距离(单位：米)
     */
    public static double getDistance(String originLon, String originLat, String destinationLon, String destinationLat) {
        if (StringUtil.isEmpty(originLon)) {
            System.err.println("出发点 经度不可以为空！");
            return 0;
        }
        if (StringUtil.isEmpty(originLat)) {
            System.err.println("出发点 纬度不可以为空！");
            return 0;
        }
        if (StringUtil.isEmpty(destinationLon)) {
            System.err.println("目的地 经度不可以为空！");
            return 0;
        }
        if (StringUtil.isEmpty(destinationLat)) {
            System.err.println("目的地 纬度不可以为空！");
            return 0;
        }

        double radLat1 = rad(Double.parseDouble(originLat));
        double radLat2 = rad(Double.parseDouble(destinationLat));
        double a = radLat1 - radLat2;
        double b = rad(Double.parseDouble(originLon)) - rad(Double.parseDouble(destinationLon));
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        // 保留两位小数
        s = Math.round(s * 100d) / 100d;
        s = s * 1000;
        return s;
    }

}
