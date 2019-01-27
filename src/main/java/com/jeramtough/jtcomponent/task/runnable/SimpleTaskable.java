package com.jeramtough.jtcomponent.task.runnable;

import com.jeramtough.jtcomponent.task.bean.TaskResult;

/**
 * Created on 2019-01-27 16:49
 * by @author JeramTough
 */
public abstract class SimpleTaskable extends BaseTaskable {

    @Override
    public TaskResult doTask() {
        super.doTask();
        getTaskResult().setSuccessful(doTask(getTaskResult()));
        getTaskResult().setTimeConsuming(System.currentTimeMillis() - getStartTaskTime());
        return getTaskResult();
    }

    public abstract boolean doTask(
            com.jeramtough.jtcomponent.task.bean.no.TaskResult taskResult);
}
