package com.jeramtough.jtcomponent.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Deprecated: 使用JtStrUtil
 */
@Deprecated
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

    public static String lineToHump(String str, boolean firstCharUpper) {
        if (firstCharUpper) {
            str = lineToHump(str);
            String firstChar = str.charAt(0) + "";
            firstChar = firstChar.toUpperCase();
            String newStr = firstChar + str.substring(1);
            return newStr;

        }
        else {
            return lineToHump(str);
        }
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


    /**
     * 用中英文逗号分割字符串
     *
     * @param str 被分割的字符串
     * @return 分割后的数组
     */
    public static List<String> splitByComma(String str) {
        List<String> lis = new ArrayList<>();
        if (str.contains(",")) {
            String[] split = str.split(",");
            for (int i = 0; i < split.length; i++) {
                String url = split[i];
                lis.add(url);
            }
        }
        else if (str.contains("，")) {
            String[] split = str.split("，");
            for (int i = 0; i < split.length; i++) {
                String url = split[i];
                lis.add(url);
            }
        }
        else {
            String[] split = str.split(",");
            for (int i = 0; i < split.length; i++) {
                String url = split[i];
                lis.add(url);
            }
        }
        return lis;
    }

    /**
     * 用英文逗号拼接字串符
     *
     * @param strList 被拼接的字符串集合
     * @return 拼接后的字符串
     */
    public static String appendByComma(List<String> strList) {
        if (strList.size() == 0) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        for (String s : strList) {
            if (s != null) {
                result.append(s).append(",");
            }
        }
        String str = result.toString();
        if (str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**
     * 首字母大写
     *
     * @param str 字符串参数
     * @return 修改后字符串
     */
    public static String firstCharUpperCase(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalStateException("参数异常！");
        }

        String firstChar = str.charAt(0) + "";
        firstChar = firstChar.toUpperCase();
        String newStr = firstChar + str.substring(1);
        return newStr;

    }

    /**
     * 首字母小写
     *
     * @param str 字符串参数
     * @return 修改后字符串
     */
    public static String firstCharLowerCase(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalStateException("参数异常！");
        }

        String firstChar = str.charAt(0) + "";
        firstChar = firstChar.toLowerCase();
        String newStr = firstChar + str.substring(1);
        return newStr;

    }
}
