package com.jeramtough.jtcomponent.task.response;

import com.jeramtough.jtcomponent.task.bean.no.TaskResult;
import com.jeramtough.jtcomponent.task.callback.RunningTaskCallback;
import com.jeramtough.jtcomponent.task.callback.TaskCallback;

/**
 * Created on 2019-01-25 22:00
 * by @author JeramTough
 */
public abstract class TaskResponseWithCallback extends BaseTaskResponse {

    private TaskCallback taskCallback;

    public TaskResponseWithCallback(TaskCallback taskCallback) {
        super();
        this.taskCallback = taskCallback;

        if (taskCallback != null) {
            taskCallback.onTaskStart();
        }

        getTaskResult().setSuccessful(doSomething(getTaskResult(), taskCallback));

        if (taskCallback != null) {
            taskCallback.onTaskCompleted(getTaskResult());
        }
    }

    @Override
    @Deprecated
    protected boolean doSomething(TaskResult taskResult) {
        return false;
    }

    public abstract boolean doSomething(
            com.jeramtough.jtcomponent.task.bean.no.TaskResult taskResult,
            RunningTaskCallback taskCallback);
}
