package test.util;

import com.jeramtough.jtcomponent.utils.ClassUtil;
import com.jeramtough.jtlog.facade.L;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * <pre>
 * Created on 2020/9/16 9:59
 * by @author WeiBoWen
 * </pre>
 */
public class ClassTest {

    @Test
    public void test2() {
        List<Class<?>> list = ClassUtil.getClassesByPagename("com.jeramtough.jtcomponent.utils");
        L.debug(list.size());
    }


}
