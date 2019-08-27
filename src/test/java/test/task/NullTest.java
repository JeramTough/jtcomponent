package test.task;

import com.jeramtough.jtcomponent.task.response.TaskResponse;
import com.jeramtough.jtcomponent.task.response.ResponseFactory;
import com.jeramtough.jtlog.facade.L;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

/**
 * Created on 2019-06-22 12:47
 * by @author JeramTough
 */
public class NullTest {

    @Test
    public void test(){
        TaskResponse taskResponse= ResponseFactory.doing((taskResult)->{
            A[] a=new A[1];
            taskResult.putPayload("a",a);
            return true;
        });
        Object a= taskResponse.getTaskResult().getSerializablePayload("a");
        L.debug(a);
    }

    public class A implements Serializable{

    }

}
