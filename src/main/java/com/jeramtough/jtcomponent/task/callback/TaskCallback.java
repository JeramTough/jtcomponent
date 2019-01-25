package com.jeramtough.jtcomponent.task.callback;

import com.jeramtough.jtcomponent.task.bean.TaskResult;

/**
 * Created on 2019-01-24 22:18
 * by @author JeramTough
 */
public abstract class TaskCallback implements RunningTaskCallback {


    /**
     * Calling when the task is preparing to start .
     */
    public abstract void onTaskStart();

    @Override
    public void onTaskRunning(com.jeramtough.jtcomponent.task.bean.no.TaskResult taskResult,
                              int percent) {
        com.jeramtough.jtcomponent.task.bean.TaskResult taskResult1 = (TaskResult) taskResult;
        onTaskRunning(taskResult1, percent);
    }

    /**
     * Calling when the task is running.
     */
    public abstract void onTaskRunning(
            com.jeramtough.jtcomponent.task.bean.TaskResult taskResult,
            int percent);


    /**
     * Calling while the task completed.
     *
     * @param taskResult TaskResult bean
     */
    public abstract void onTaskCompleted(TaskResult taskResult);
}
