package com.jeramtough.jtcomponent.math.util;

/**
 * Created on 2019-08-08 03:26
 * by @author JeramTough
 */
public class MatrixUtil {

    public static String printMatrix(int[][] matrix, int... signNumbers) {
        StringBuilder stringBuilder = new StringBuilder();

        String abc = "   ";
        String ab = " （";
        String bc = "） ";

        for (int ii = 0; ii < matrix[0].length; ii++) {
            stringBuilder.append(abc).append(ii + 1).append(abc);
        }
        stringBuilder.append("\n");
        for (int ii = 0; ii < matrix[0].length; ii++) {
            stringBuilder.append("-----------");
        }
        stringBuilder.append("\n");

        for (int i = 0; i < matrix.length; i++) {
            for (int ii = 0; ii < matrix[i].length; ii++) {
                int value = matrix[i][ii];
                boolean isSign = false;
                for (int signNumber : signNumbers) {
                    if (signNumber == value) {
                        isSign = true;
                        break;
                    }
                }
                if (isSign) {
                    stringBuilder.append(ab).append(value).append(bc);
                }
                else {
                    stringBuilder.append(abc).append(value).append(abc);
                }
            }
            stringBuilder.append(" 【").append(i + 1).append("】").append("\n");
        }
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

}
