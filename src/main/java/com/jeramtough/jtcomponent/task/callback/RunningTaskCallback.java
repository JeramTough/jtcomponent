package com.jeramtough.jtcomponent.task.callback;

import com.jeramtough.jtcomponent.task.bean.no.PreTaskResult;

/**
 * Created on 2019-01-26 00:58
 * by @author JeramTough
 */
public interface RunningTaskCallback {

    /**
     * Calling when the task is running.
     *
     * @param preTaskResult {@link PreTaskResult}
     * @param percent percent of task
     */
    void onTaskRunning(
            PreTaskResult preTaskResult,
            int percent);

}
