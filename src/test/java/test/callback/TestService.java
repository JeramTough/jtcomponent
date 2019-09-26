package test.callback;

import com.jeramtough.jtcomponent.callback.CommonCallback;

/**
 * <pre>
 * Created on 2019-08-31 22:15
 * by @author JeramTough
 * </pre>
 */
public interface TestService {

    void doSomething(CommonCallback<String> callback);

}
