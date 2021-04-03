package com.jeramtough.jtcomponent.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/**
 * <pre>
 * Created on 2020-08-31 23:06
 * by @author WeiBoWen
 * </pre>
 */
public class Base64Util {

    /**
     * 将字符串转成base64编码格式的字符串
     * 严格地说，属于编码格式，而非加密算法
     *
     * @param str 字符串
     * @return base64格式的字符串
     */
    public static String toBase64Str(String str) {
        return toBase64Str(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 将data转成String，而且用上BASE64算法
     * 严格地说，属于编码格式，而非加密算法
     *
     * @param data 字节码数组
     * @return 字节码数据的base64格式
     */
    public static String toBase64Str(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * 将base64编码后的字符串解码为byte[]形式
     *
     * @param base64String base64格式的字符串
     * @return base64字符串的字节码数组
     */
    public static byte[] decodeBytesFromBase64(String base64String) {
        try {
            return Base64.getDecoder().decode(base64String);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解码被base64编码后的字符串
     *
     * @param base64Str base64编码后的字符串
     * @return 解码后的字符串
     */
    public static String decodeStringFromBase64(String base64Str) {
        return new String(Objects.requireNonNull(decodeBytesFromBase64(base64Str)));
    }

}
