package com.jeramtough.jtcomponent.math.util;

/**
 * <pre>
 * Created on 2024/9/14 上午1:29
 * by @author WeiBoWen
 * </pre>
 */
public class JtMathFactionUtil {

    /**
     * 计算逻辑斯蒂函数的值
     *
     * @param t  当前时间点
     * @param L  最终值
     * @param k  增长率
     * @param t0 中心时间点
     * @return 函数值
     */
    public static double logistic(double t, double L, double k, double t0) {
        double result = L / (1 + Math.exp(-k * (t - t0)));
        // 四舍五入保留三位小数
        return Math.round(result * 1000.0) / 1000.0;
    }

}
