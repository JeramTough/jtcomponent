package com.jeramtough.jtcomponent.utils;

import java.math.BigDecimal;
import java.util.List;

/**
 * <pre>
 * Created on 2023/7/9 下午7:26
 * by @author WeiBoWen
 * </pre>
 */
public class JtStrSimilarityUtil {


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

    public static BigDecimal calculateSimilarity(List<String> keywords, String text) {
        // 将关键词和文本转换为向量表示
        double[] keywordVector = new double[keywords.size()];
        double[] textVector = new double[keywords.size()];

        for (int i = 0; i < keywords.size(); i++) {
            String keyword = keywords.get(i);
            keywordVector[i] = countOccurrences(keyword, text);
            textVector[i] = countOccurrences(keyword, text);
        }

        // 计算向量的余弦相似度
        double dotProduct = dotProduct(keywordVector, textVector);
        double magnitude = magnitude(keywordVector) * magnitude(textVector);

        if (magnitude != 0) {
            return BigDecimal.valueOf(dotProduct / magnitude);
        }
        else {
            return BigDecimal.ZERO;
        }
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


    //**********************

    private static int countOccurrences(String keyword, String text) {
        // 使用简单的计数方法，统计关键词在文本中出现的次数
        int count = 0;
        int index = 0;

        while ((index = text.indexOf(keyword, index)) != -1) {
            count++;
            index += keyword.length();
        }

        return count;
    }

    private static double dotProduct(double[] vector1, double[] vector2) {
        // 计算两个向量的点积
        double product = 0.0;

        for (int i = 0; i < vector1.length; i++) {
            product += vector1[i] * vector2[i];
        }

        return product;
    }

    private static double magnitude(double[] vector) {
        // 计算向量的模长
        double sum = 0.0;

        for (double value : vector) {
            sum += Math.pow(value, 2);
        }

        return Math.sqrt(sum);
    }

}
