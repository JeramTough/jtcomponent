package com.jeramtough.jtcomponent.task.callback;

import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.jtcomponent.task.bean.PreTaskResult;

/**
 * Created on 2019-01-24 22:18
 * by @author JeramTough
 */
public interface TaskCallback extends RunningTaskCallback {


    /**
     * Calling when the task is preparing to start .
     */
    void onTaskStart();

    /**
     * Calling when the task is running.
     *
     * @param preTaskResult {@link PreTaskResult}
     * @param percent       percent of task
     */
    @Override
    void onTaskRunning(
            PreTaskResult preTaskResult,
            int percent);

    /**
     * Calling while the task is completed.
     *
     * @param taskResult TaskResult bean
     */
    void onTaskCompleted(TaskResult taskResult);
}
