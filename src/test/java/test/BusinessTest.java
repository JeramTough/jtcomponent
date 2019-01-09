package test;

import com.jeramtough.jtcomponent.business.bean.BusinessResult;
import com.jeramtough.jtcomponent.business.core.BusinessResponse;
import com.jeramtough.jtcomponent.business.core.DefaultBusinessResponse;
import org.junit.jupiter.api.Test;

/**
 * Created on 2019-01-09 11:14
 * by @author JeramTough
 */
public class BusinessTest {

    @Test
    public void test() {
        BusinessResponse businessResponse = new ServiceImpl().getBusinessResponse();
    }

    public class ServiceImpl {


        BusinessResponse getBusinessResponse() {
            BusinessResult businessResult = new BusinessResult(true);
            return new DefaultBusinessResponse(businessResult);
        }
    }
}
