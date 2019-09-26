package com.jeramtough.jtcomponent.task.callback;

import com.jeramtough.jtcomponent.task.bean.PreTaskResult;

/**
 * Created on 2019-01-26 00:58
 * by @author JeramTough
 */
public interface RunningTaskCallback {

    /**
     * Calling when the task is running.
     *
     * @param preTaskResult {@link PreTaskResult}
     * @param numerator     percent of task
     * @param denominator   totality of task
     */
    void onTaskRunning(
            PreTaskResult preTaskResult,
            int numerator, int denominator);

}
