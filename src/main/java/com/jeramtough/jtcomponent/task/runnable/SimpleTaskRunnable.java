package com.jeramtough.jtcomponent.task.runnable;

/**
 * Created on 2019-01-27 02:02
 * by @author JeramTough
 */
public abstract class SimpleTaskRunnable extends BaseTaskRunnable {

    @Override
    protected boolean doAsyncSomething() {
        return doAsyncSomething(getTaskResult());
    }

    public abstract boolean doAsyncSomething(
            com.jeramtough.jtcomponent.task.bean.no.TaskResult taskResult);
}
