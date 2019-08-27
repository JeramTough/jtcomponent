package test.math;

import com.jeramtough.jtcomponent.math.util.SamplingUtil;
import com.jeramtough.jtcomponent.math.util.MatrixUtil;
import com.jeramtough.jtlog.facade.L;
import org.junit.jupiter.api.Test;

/**
 * Created on 2019-08-08 00:41
 * by @author JeramTough
 */
public class MathTest {

    @org.junit.jupiter.api.Test
    public void test1() {
        int baseNumber = 4;
        int times = 2;
        boolean isPutback = true;
        boolean isIgnoredOrder = false;
        System.out.println("size: " +
                SamplingUtil.countSampleSize(baseNumber, times, isPutback));
        int[][] matrix = SamplingUtil.getMatrix(baseNumber, times, isPutback, isIgnoredOrder);
        MatrixUtil.printMatrix(matrix);
    }

    @Test
    public void test2() {
        int baseNumber = 3;
        int times = 2;
        boolean isPutback = false;
        boolean isIgnoredOrder = false;
        System.out.println("size: " +
                SamplingUtil.countSampleSize(baseNumber, times, isPutback));
        int[][] matrix = SamplingUtil.getMatrix(baseNumber, times, isPutback, isIgnoredOrder);
        MatrixUtil.printMatrix(matrix);
    }

    @org.junit.jupiter.api.Test
    public void test3() {
        int baseNumber = 3;
        int times = 2;
        boolean isPutBack = true;
        boolean isIgnoredOrder = true;
        System.out.println("size: " +
                SamplingUtil.countSampleSize(baseNumber, times, isPutBack));
        int[][] matrix = SamplingUtil.getMatrix(baseNumber, times, isPutBack, isIgnoredOrder);
        MatrixUtil.printMatrix(matrix);
    }

    @Test
    public void test4() {
        int baseNumber = 5;
        int times = 3;
        boolean isPutBack = true;
        boolean isIgnoredOrder = false;
        System.out.println("size: " +
                SamplingUtil.countSampleSize(baseNumber, times, isPutBack));
        int[][] matrix = SamplingUtil.getMatrix(baseNumber, times, isPutBack, isIgnoredOrder);
        MatrixUtil.printMatrix(matrix,1,2);
    }


    @Test
    public void test5() {
        int[][] matrix;

       /* matrix = SamplingUtil.getMatrix(4, 4, false, false);
        MatrixUtil.printMatrix(matrix);
        matrix = SamplingUtil.getMatrix(4, 4, false, true);
        MatrixUtil.printMatrix(matrix);
        matrix = SamplingUtil.getMatrix(4, 3, false, false);
        MatrixUtil.printMatrix(matrix);
        matrix = SamplingUtil.getMatrix(4, 3, false, true);
        MatrixUtil.printMatrix(matrix);*/
        matrix = SamplingUtil.getMatrix(3, 3, true, false);
        MatrixUtil.printMatrix(matrix);
        matrix = SamplingUtil.getMatrix(3, 3, true, true);
        MatrixUtil.printMatrix(matrix);
        matrix = SamplingUtil.getMatrix(3, 2, true, false);
        MatrixUtil.printMatrix(matrix);
        matrix = SamplingUtil.getMatrix(3, 2, true, true);
        MatrixUtil.printMatrix(matrix);
        matrix = SamplingUtil.getMatrix(3, 1, true, false);
        MatrixUtil.printMatrix(matrix);
        matrix = SamplingUtil.getMatrix(3, 1, true, true);
        MatrixUtil.printMatrix(matrix);

        matrix = SamplingUtil.getMatrix(3, 3, false, false);
        MatrixUtil.printMatrix(matrix);
        matrix = SamplingUtil.getMatrix(3, 3, false, true);
        MatrixUtil.printMatrix(matrix);
        matrix = SamplingUtil.getMatrix(3, 2, false, false);
        MatrixUtil.printMatrix(matrix);
        matrix = SamplingUtil.getMatrix(3, 2, false, true);
        MatrixUtil.printMatrix(matrix);
        matrix = SamplingUtil.getMatrix(3, 1, false, false);
        MatrixUtil.printMatrix(matrix);
        matrix = SamplingUtil.getMatrix(3, 1, false, true);
        MatrixUtil.printMatrix(matrix);
    }

    @Test
    public void test6() {
        int baseNumber = 3;
        for (int i = baseNumber; i > 0; i--) {
            int[][] matrix1 = SamplingUtil.getMatrix(baseNumber, i, false, false);
            int[][] matrix3 = SamplingUtil.getMatrix(baseNumber, i, true, false);
            int[][] matrix4 = SamplingUtil.getMatrix(baseNumber, i, true, true);
//            MatrixUtil.printMatrix(matrix);
            int[][] matrix2 = SamplingUtil.getMatrix(baseNumber, i, false, true);
//            MatrixUtil.printMatrix(matrix);
            L.debugs(baseNumber, i, matrix3.length, matrix4.length, matrix1.length,
                    matrix2.length);
        }
    }

}
