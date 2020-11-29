package test.util;

import com.jeramtough.jtcomponent.utils.ObjectsUtil;
import com.jeramtough.jtcomponent.utils.StringUtil;
import com.jeramtough.jtcomponent.utils.WordUtil;
import com.jeramtough.jtlog.facade.L;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

/**
 * <pre>
 * Created on 2020/10/20 15:27
 * by @author WeiBoWen
 * </pre>
 */
public class ObjectTest {

    @Test
    public void test2() {
        List<Field> fieldList = ObjectsUtil.getFieldsIfIncludeGetSet(A.class);
        L.debug(fieldList.size());
    }

    @Test
    public void test1() {
        List<Field> fieldList = ObjectsUtil.getFieldsIfIncludeGetSet(B.class);
        L.debug(fieldList.size());
    }



    public class A {
        private String a;
        private int b;
        private Character c;
        private WordUtil wordUtil;
        private boolean isTest;
        public boolean d;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

        public Character getC() {
            return c;
        }

        public void setC(Character c) {
            this.c = c;
        }

        public WordUtil getWordUtil() {
            return wordUtil;
        }

        public void setWordUtil(WordUtil wordUtil) {
            this.wordUtil = wordUtil;
        }

        public boolean isTest() {
            return isTest;
        }

        public void setTest(boolean test) {
            isTest = test;
        }

        public boolean isD() {
            return d;
        }

        public void setD(boolean d) {
            this.d = d;
        }
    }

    public class B {

        private boolean isTest;

        public boolean isTest() {
            return isTest;
        }

        public void setTest(boolean test) {
            isTest = test;
        }

    }


}
