package com.jeramtough.jtcomponent.utils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    private static final Pattern LINE_PATTERN = Pattern.compile("_(\\w)");
    private static final Pattern HUMP_PATTERN = Pattern.compile("[A-Z]");

    public static String addOrDeleteWords(String originalText, boolean isAdded, int start,
                                          String words) {
        originalText = (originalText == null ? "" : originalText);
        String textHead = originalText.substring(0, start);
        String textBoot = originalText.substring(start, originalText.length());
        if (isAdded) {
            textHead = textHead + words;
        }
        else {
            if (textHead.length() >= words.length()) {
                textHead = textHead.substring(0, textHead.length() - words.length());
            }
        }
        originalText = textHead + textBoot;
        return originalText;
    }

    /**
     * return true if the str is empty or the str just is comprised of spaces.
     *
     * @param str `
     * @return `
     */
    public static boolean isEmpty(String str) {

        //first pass
        if (str == null || str.length() == 0) {
            return true;
        }

        //second pass
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }


    /**
     * 下划线字符串转驼峰字符串
     *
     * @param str 要转换的字符串
     * @return 转换后的字符串
     */
    public static String lineToHump(String str) {
        Objects.requireNonNull(str);

        if (!str.contains("_")) {
            return str;
        }

        str = str.toLowerCase();
        Matcher matcher = LINE_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    /**
     * 转驼峰字符串转下划线字符串
     *
     * @param str 要转换的字符串
     * @return 转换后的字符串
     */
    public static String humpToLine(String str) {
        Objects.requireNonNull(str);

        if (!WordUtil.isContainsUpperCase(str)) {
            return str;
        }

        Matcher matcher = HUMP_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);

        String result = sb.toString();

        if (str.length() > 1) {
            if (Character.isUpperCase(str.charAt(0))) {
                if (result.length() > 0) {
                    result = result.substring(1);
                }
                return result;
            }
        }

        return result;


    }
}
