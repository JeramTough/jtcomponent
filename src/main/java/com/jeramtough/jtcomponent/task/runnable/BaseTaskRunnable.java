package com.jeramtough.jtcomponent.task.runnable;

import com.jeramtough.jtcomponent.task.bean.TaskResult;

import java.util.concurrent.Callable;

/**
 * Created on 2019-01-27 01:35
 * by @author JeramTough
 */
public abstract class BaseTaskRunnable implements Callable<TaskResult> {

    private TaskResult taskResult;

    public BaseTaskRunnable() {
        taskResult = new TaskResult();
    }

    public TaskResult getTaskResult() {
        return taskResult;
    }

    public void setTaskResult(TaskResult taskResult) {
        this.taskResult = taskResult;
    }

    @Override
    public TaskResult call() {
        boolean isSuccessful = doAsyncSomething();
        taskResult.setSuccessful(isSuccessful);
        return taskResult;
    }


    protected abstract boolean doAsyncSomething();

}
