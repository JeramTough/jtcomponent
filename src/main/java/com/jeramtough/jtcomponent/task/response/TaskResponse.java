package com.jeramtough.jtcomponent.task.response;

import com.jeramtough.jtcomponent.task.bean.TaskResult;

/**
 * Created on 2019-01-25 02:31
 * by @author JeramTough
 */
public interface TaskResponse {

    TaskResult getTaskResult();

    boolean isSuccessful();

}
