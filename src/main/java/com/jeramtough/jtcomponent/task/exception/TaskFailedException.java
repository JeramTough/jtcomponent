package com.jeramtough.jtcomponent.task.exception;

import com.jeramtough.jtcomponent.task.response.TaskResponse;

/**
 * <pre>
 * Created on 2022/2/7 下午5:17
 * by @author WeiBoWen
 * </pre>
 */
public class TaskFailedException extends Exception {

    private final TaskResponse taskResponse;


    public TaskFailedException(
            TaskResponse taskResponse) {
        this.taskResponse = taskResponse;
    }

    public TaskResponse getTaskResponse() {
        return taskResponse;
    }
}
