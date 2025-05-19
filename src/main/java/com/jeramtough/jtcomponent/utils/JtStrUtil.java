package com.jeramtough.jtcomponent.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JtStrUtil {

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
        return splitByComma(str, String.class);
    }

    /**
     * 用中英文逗号分割字符串
     *
     * @param str       被分割的字符串
     * @param dataClass 任意
     * @param <T>       任意
     * @return 分割后的数组
     */
    public static <T> List<T> splitByComma(String str, Class<T> dataClass) {
        if (isEmpty(str)) {
            return new ArrayList<>();
        }

        String splitChar;
        if (str.contains(",")) {
            splitChar = ",";
        }
        else if (str.contains("，")) {
            splitChar = "，";
        }
        else if (str.contains("|")) {
            splitChar = "|";
        }
        else {
            List<T> list = new ArrayList<>();
            list.add(ObjectsUtil.transferToLongIntStr(str, dataClass));
            return list;
        }

        String[] split = str.split(splitChar);
        List<T> list = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            String str2 = split[i];
            list.add(ObjectsUtil.transferToLongIntStr(str2, dataClass));
        }
        return list;
    }

    /**
     * 用英文逗号拼接字串符
     *
     * @param strList 被拼接的字符串集合
     * @return 拼接后的字符串
     */
    public static String appendByComma(List<Object> strList) {
        if (strList == null || strList.isEmpty()) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        for (Object s : strList) {
            if (s != null) {
                result.append(s.toString()).append(",");
            }
        }
        String str = result.toString();
        if (!str.isEmpty()) {
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

    /**
     * 截断字符串
     *
     * @param str   字符串
     * @param start 开始位置
     * @param end   结束位置
     * @return 新的截断后的字符串
     */
    public static String sub(String str, int start, int end) {
        return sub(str, start, end, false);
    }

    /**
     * 截断字符串
     *
     * @param str           字符串
     * @param start         开始位置
     * @param end           结束位置
     * @param isAddEllipsis 是否添加省略号
     * @return 新的截断后的字符串
     */
    public static String sub(String str, int start, int end, boolean isAddEllipsis) {
        String newStr = str.substring(start, end + 1 > str.length() ? str.length() - 1 : end);

        if (isAddEllipsis) {
            if (str.length() > newStr.length()) {
                newStr = newStr + "...";
            }
        }

        return newStr;

    }


}
