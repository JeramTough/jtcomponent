package com.jeramtough.jtcomponent.task.response;

import com.jeramtough.jtcomponent.task.runnable.ReturnTaskable;

/**
 * Created on 2019-08-27 18:04
 * by @author JeramTough
 */
public class DefaultReturnResponse<T> extends DefaultTaskResponse
        implements ReturnResponse<T> {

    private T returnT;
    private ReturnTaskable<T> returnTaskable;

    protected DefaultReturnResponse(ReturnTaskable<T> returnTaskable) {
        super(returnTaskable);
        this.returnTaskable = returnTaskable;
    }

    @Override
    public DefaultTaskResponse start() {
        DefaultTaskResponse defaultTaskResponse = super.start();
        returnT = returnTaskable.getReturnT();
        return defaultTaskResponse;
    }

    @Override
    public T getReturn() {
        return returnT;
    }
}
