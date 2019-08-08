package com.jeramtough.jtcomponent.math.util;

/**
 * Created on 2019-08-08 03:26
 * by @author JeramTough
 */
public class MatrixUtil {

    public static String printMatrix(int[][] matrix) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int ii = 0; ii < matrix[0].length; ii++) {
            stringBuilder.append(" ").append(ii+1).append(" ");
        }
        stringBuilder.append("\n");
        for (int ii = 0; ii < matrix[0].length; ii++) {
            stringBuilder.append("---");
        }
        stringBuilder.append("\n");

        for (int i = 0; i < matrix.length; i++) {
            for (int ii = 0; ii < matrix[i].length; ii++) {
                int value=matrix[i][ii];
                stringBuilder.append(" ").append(value).append(" ");
            }
            stringBuilder.append(" 【").append(i+1).append("】").append("\n");
        }
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

}
