package com.jeramtough.jtcomponent.task.response;


import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.jtcomponent.task.exception.HaventStartedException;
import com.jeramtough.jtcomponent.task.runnable.Taskable;

/**
 * Created on 2019-01-25 23:55
 * by @author JeramTough
 */
public class DefaultTaskResponse implements TaskResponse {

    private Taskable taskable;
    private TaskResult taskResult;


    public DefaultTaskResponse(Taskable taskable) {
        this.taskable = taskable;
    }

    public DefaultTaskResponse start() {
        try {
            taskResult = taskable.doTask();
        }
        catch (Exception e) {
            e.printStackTrace();
            taskResult = new TaskResult();
            taskResult.setSuccessful(false);
            taskResult.setMessage(e.getMessage());
        }
        return this;
    }

    @Override
    public TaskResult getTaskResult() {
        if (taskResult == null) {
            throw new HaventStartedException();
        }
        return taskResult;
    }
}
