package com.jeramtough.jtcomponent.task.callback;

import com.jeramtough.jtcomponent.task.bean.TaskResult;

/**
 * Created on 2019-01-26 00:58
 * by @author JeramTough
 */
public interface RunningTaskCallback {

    /**
     * Calling when the task is running.
     */
    void onTaskRunning(
            com.jeramtough.jtcomponent.task.bean.no.TaskResult taskResult,
            int percent);

}
