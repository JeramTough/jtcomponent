package com.jeramtough.jtcomponent.utils;

import java.math.BigDecimal;
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


    public static BigDecimal calculateSimilarity(List<String> list1, List<String> list2) {
        int totalDistance = 0;

        for (String str1 : list1) {
            int minDistance = Integer.MAX_VALUE;
            for (String str2 : list2) {
                int distance = calculateLevenshteinDistance(str1, str2);
                minDistance = Math.min(minDistance, distance);
            }
            totalDistance += minDistance;
        }

        int maxLength = Math.max(list1.size(), list2.size());
        double similarity = 1 - ((double) totalDistance / maxLength);

        return new BigDecimal(similarity);
    }

    public static int calculateLevenshteinDistance(String str1, String str2) {
        int[][] distanceMatrix = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 0; i <= str1.length(); i++) {
            distanceMatrix[i][0] = i;
        }

        for (int j = 0; j <= str2.length(); j++) {
            distanceMatrix[0][j] = j;
        }

        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                int cost = (str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1;

                distanceMatrix[i][j] = Math.min(
                        Math.min(distanceMatrix[i - 1][j] + 1, distanceMatrix[i][j - 1] + 1),
                        distanceMatrix[i - 1][j - 1] + cost
                );
            }
        }

        return distanceMatrix[str1.length()][str2.length()];
    }

    /**
     * 得到两个字符串的相似度系数
     *
     * @param str1 str1
     * @param str2 str2
     * @return 相似度
     */
    public static BigDecimal getStringSimilarity(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();

        // 创建二维数组来存储距离矩阵
        int[][] distanceMatrix = new int[len1 + 1][len2 + 1];

        // 初始化第一行和第一列
        for (int i = 0; i <= len1; i++) {
            distanceMatrix[i][0] = i;
        }
        for (int j = 0; j <= len2; j++) {
            distanceMatrix[0][j] = j;
        }

        // 计算距离矩阵
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                int cost = (str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1;
                distanceMatrix[i][j] = Math.min(
                        Math.min(distanceMatrix[i - 1][j] + 1, distanceMatrix[i][j - 1] + 1),
                        distanceMatrix[i - 1][j - 1] + cost);
            }
        }

        // 计算相似度
        int maxLen = Math.max(len1, len2);
        double similarity = 1.0 - (double) distanceMatrix[len1][len2] / maxLen;
        BigDecimal bigDecimal = new BigDecimal(similarity);
        return bigDecimal;
    }

}
