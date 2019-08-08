package test.math;

import com.jeramtough.jtcomponent.math.util.SamplingUtil;
import com.jeramtough.jtcomponent.math.util.MatrixUtil;
import org.junit.jupiter.api.Test;

/**
 * Created on 2019-08-08 00:41
 * by @author JeramTough
 */
public class MathTest {

    @org.junit.jupiter.api.Test
    public void test1() {
        int[][] matrix = SamplingUtil.getMatrix(4, 4,true);
        MatrixUtil.printMatrix(matrix);
    }

    @Test
    public void test2() {
        int[][] matrix = SamplingUtil.getMatrix(4, 5,false);
        MatrixUtil.printMatrix(matrix);
    }



}
