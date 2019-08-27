package test.task;

import com.jeramtough.jtcomponent.task.bean.no.PreTaskResult;
import com.jeramtough.jtcomponent.task.response.ResponseFactory;
import com.jeramtough.jtcomponent.task.response.ReturnResponse;
import com.jeramtough.jtcomponent.task.runner.ReturnRunner;
import com.jeramtough.jtlog.facade.L;

/**
 * Created on 2019-01-25 02:17
 * by @author JeramTough
 */
public class ReturnReponseTest {


    public static void main(String[] args) {
        new ReturnReponseTest();
    }

    ReturnReponseTest() {
        Service service = new ServiceImpl();
        ReturnResponse<String> returnResponse = service.doSomething();
        ReturnResponse<String> returnResponse1 = service.waitAndReturnNull();
        L.debugs(returnResponse.getReturn(), returnResponse.getTaskResult().getDetail());
        L.debugs(returnResponse1.getReturn(), returnResponse1.getTaskResult().getDetail());

    }


    public interface Service {

        ReturnResponse<String> doSomething();

        ReturnResponse<String> waitAndReturnNull();

    }

    public class ServiceImpl implements Service {

        @Override
        public ReturnResponse<String> doSomething() {
            return ResponseFactory.returnDoing(preTaskResult -> "德玛西亚");
        }

        @Override
        public ReturnResponse<String> waitAndReturnNull() {
            return ResponseFactory.returnDoing(new ReturnRunner<String>() {
                @Override
                public String doTask(PreTaskResult preTaskResult) {
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            });
        }
    }


    //*********************


}
