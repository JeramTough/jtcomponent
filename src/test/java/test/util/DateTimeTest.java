package test.util;

import com.jeramtough.jtcomponent.utils.DateTimeUtil;
import com.jeramtough.jtlog.facade.L;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <pre>
 * Created on 2020/9/30 10:28
 * by @author WeiBoWen
 * </pre>
 */
public class DateTimeTest {

    @Test
    public void test2() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 =format.parse("2020-12-28 10:00:00");
            Date date2 =format.parse("2020-12-30 23:00:00");
            String result=DateTimeUtil.getDistanceTimeStr(date1,date2);
            String result1=DateTimeUtil.getDistanceTimeStr(date1,date2,"天");
            L.debugs(result,result1);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
