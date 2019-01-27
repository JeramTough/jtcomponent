package com.jeramtough.jtcomponent.task.runnable;

import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.jtcomponent.task.callback.RunningTaskCallback;
import com.jeramtough.jtcomponent.task.callback.TaskCallback;

/**
 * Created on 2019-01-27 02:05
 * by @author JeramTough
 */
public abstract class TaskableWithCallback extends BaseTaskable {

    private TaskCallback taskCallback;

    public TaskableWithCallback(
            TaskCallback taskCallback) {
        super();
        this.taskCallback = taskCallback;
    }

    @Override
    public TaskResult doTask() {

        super.doTask();
        if (taskCallback != null) {
            taskCallback.onTaskStart();
        }
        getTaskResult().setSuccessful(doTask(getTaskResult(), taskCallback));

        getTaskResult().setTimeConsuming(System.currentTimeMillis() - getStartTaskTime());

        if (taskCallback != null) {
            taskCallback.onTaskCompleted(getTaskResult());
        }
        return getTaskResult();
    }

    public abstract boolean doTask(
            com.jeramtough.jtcomponent.task.bean.no.TaskResult taskResult,
            RunningTaskCallback taskCallback);

}
