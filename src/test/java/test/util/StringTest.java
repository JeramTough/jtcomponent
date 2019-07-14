package test.util;

import com.jeramtough.jtcomponent.util.core.StringUtil;
import com.jeramtough.jtlog.facade.L;
import org.junit.jupiter.api.Test;

/**
 * Created on 2018-12-17 10:52
 * by @author JeramTough
 */
public class StringTest {

    @Test
    public void testEmpty() {
        L.debug(StringUtil.isEmptyOrSpaces(null));

        L.debug(StringUtil.isEmptyOrSpaces(""));

        L.debug(StringUtil.isEmptyOrSpaces("  s s"));

        L.debug(StringUtil.isEmptyOrSpaces("  "));

    }
}
