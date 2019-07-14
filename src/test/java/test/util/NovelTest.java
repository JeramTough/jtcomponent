package test.util;

import com.jeramtough.jtcomponent.util.core.NovelUtil;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * Created on 2018-12-17 10:51
 * by @author JeramTough
 */
public class NovelTest {

    @Test
    public void test() {
        NovelUtil.formal(new File("C:\\Users\\11718\\Desktop\\New folder\\恐怖广播.txt"), new
                File("C:\\Users\\11718\\Desktop\\New folder\\恐怖广播OK.txt"));
    }
}
