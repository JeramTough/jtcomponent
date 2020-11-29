package test.util;

import com.jeramtough.jtcomponent.utils.ObjectsUtil;
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

    @Test
    public void test23() {
        String a = StringUtil.humpToLine(AbcdEfgG.class.getSimpleName());
        String a1 = StringUtil.humpToLine("dasTeTt");
        String a2 = StringUtil.humpToLine("n");
        String a3 = StringUtil.humpToLine("nnds");
        String b = StringUtil.lineToHump("abc_de_dds");
        String b1 = StringUtil.lineToHump("dfadas");
        String b2 = StringUtil.lineToHump("dafaSFESE3SF");
        L.debugs(a, a1, a2, a3, b, b1, b2);
    }

    public class AbcdEfgG {

        private int myName;
        private int age;
        private String isTest;

        public int getMyName() {
            return myName;
        }

        public void setMyName(int myName) {
            this.myName = myName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getIsTest() {
            return isTest;
        }

        public void setIsTest(String isTest) {
            this.isTest = isTest;
        }
    }
}
