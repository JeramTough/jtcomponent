package com.jeramtough.jtcomponent.task.response;

/**
 * Created on 2019-01-25 23:55
 * by @author JeramTough
 */
public abstract class SimpleTaskResponse extends BaseTaskResponse {


    public SimpleTaskResponse() {
        super();
        getTaskResult().setSuccessful(doSomething(getTaskResult()));
    }

}
