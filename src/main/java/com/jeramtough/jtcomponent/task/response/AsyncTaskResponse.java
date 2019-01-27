package com.jeramtough.jtcomponent.task.response;

import com.jeramtough.jtcomponent.task.bean.TaskResult;

import java.util.concurrent.RunnableFuture;

/**
 * Created on 2019-01-25 02:08
 * by @author JeramTough
 */
public interface AsyncTaskResponse extends RunnableFuture<TaskResult> {

    /**
     * Current thread will be blocked until the task would be completed
     *
     * @return TaskResult Bean
     */
    TaskResult waitingTaskResult();
}
