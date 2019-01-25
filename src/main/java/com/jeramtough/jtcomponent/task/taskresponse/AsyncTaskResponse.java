package com.jeramtough.jtcomponent.task.taskresponse;

import com.jeramtough.jtcomponent.task.bean.TaskResult;

/**
 * Created on 2019-01-25 02:08
 * by @author JeramTough
 */
public interface AsyncTaskResponse {


    /**
     * Current thread will be blocked until the business completes
     *
     * @return TaskResult Bean
     */
    TaskResult waitingTaskResult();
}
