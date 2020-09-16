package test.util;

import com.jeramtough.jtcomponent.utils.CompressorUtil;
import com.jeramtough.jtcomponent.io.Directory;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * Created on 2018-12-17 09:30
 * by @author JeramTough
 */
public class ZipTest {

    @Test
    public void abc(){
        Directory directory=new Directory("E:\\Codes\\IdeaCodes\\jtutil\\src\\test" +
                "\\resources\\abc");
        File file=new File("E:\\Codes\\IdeaCodes\\jtutil\\src\\test\\resources\\ggg.zip");

        CompressorUtil.compress(directory.listFiles(),file.getAbsolutePath());
    }

}
