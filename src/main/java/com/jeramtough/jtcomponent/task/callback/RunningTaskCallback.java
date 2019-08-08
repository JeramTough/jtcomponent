package com.jeramtough.jtcomponent.task.callback;

/**
 * Created on 2019-01-26 00:58
 * by @author JeramTough
 */
public interface RunningTaskCallback {

    /**
     * Calling when the task is running.
     *
     * @param taskResult {@link com.jeramtough.jtcomponent.task.bean.no.TaskResult}
     * @param percent percent of task
     */
    void onTaskRunning(
            com.jeramtough.jtcomponent.task.bean.no.TaskResult taskResult,
            int percent);

}
