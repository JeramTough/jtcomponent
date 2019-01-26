package com.jeramtough.jtcomponent.task.runnable;

import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.jtcomponent.task.callback.RunningTaskCallback;
import com.jeramtough.jtcomponent.task.callback.TaskCallback;

/**
 * Created on 2019-01-27 02:05
 * by @author JeramTough
 */
public abstract class TaskRunnableWithCallback extends BaseTaskRunnable {

    private TaskCallback taskCallback;

    public TaskRunnableWithCallback(
            TaskCallback taskCallback) {
        super();
        this.taskCallback = taskCallback;
    }

    @Override
    public TaskResult call() {
        TaskResult taskResult = super.call();
        taskCallback.onTaskCompleted(taskResult);
        return taskResult;
    }

    @Override
    protected boolean doAsyncSomething() {
        taskCallback.onTaskStart();
        return doAsyncSomething(getTaskResult(), taskCallback);
    }

    public abstract boolean doAsyncSomething(
            com.jeramtough.jtcomponent.task.bean.no.TaskResult taskResult,
            RunningTaskCallback taskCallback);

}
