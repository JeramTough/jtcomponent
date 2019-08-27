package test.util;

import com.jeramtough.jtcomponent.utils.StringUtil;
import com.jeramtough.jtlog.facade.L;
import org.junit.jupiter.api.Test;

/**
 * Created on 2018-12-17 10:52
 * by @author JeramTough
 */
public class StringTest {

    @Test
    public void testEmpty() {
        L.debug(StringUtil.isEmpty(null));

        L.debug(StringUtil.isEmpty(""));

        L.debug(StringUtil.isEmpty("  s s"));

        L.debug(StringUtil.isEmpty("  "));

    }
}
