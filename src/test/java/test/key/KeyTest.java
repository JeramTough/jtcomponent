package test.key;

import com.jeramtough.jtcomponent.key.component.GroupKey;
import com.jeramtough.jtlog.facade.L;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * Created on 2019-08-08 21:44
 * by @author JeramTough
 */
public class KeyTest {

    @Test
    public void test2() {
        GroupKey groupKey1=new GroupKey(Arrays.asList(1,2,3));
        GroupKey groupKey2=new GroupKey(Arrays.asList(2,1,3));
        L.debug(groupKey1.equals(groupKey2));
        L.debug(groupKey1.hashCode()==groupKey2.hashCode());
    }
}
