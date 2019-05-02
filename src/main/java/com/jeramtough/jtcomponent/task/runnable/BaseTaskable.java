package com.jeramtough.jtcomponent.task.runnable;

import com.jeramtough.jtcomponent.task.bean.TaskResult;

/**
 * Created on 2019-01-27 16:48
 * by @author JeramTough
 */
public abstract class BaseTaskable implements Taskable {

    private TaskResult taskResult;
    private long startTaskTime;

    public BaseTaskable() {
        taskResult = new TaskResult();
    }


    @Override
    public TaskResult call() {
        startTaskTime = System.currentTimeMillis();
        try {
            TaskResult taskResult = doTask();
            return taskResult;
        }
        catch (Exception e) {
            e.printStackTrace();
            taskResult = new TaskResult();
            taskResult.setSuccessful(false);
            taskResult.setMessage(e.getMessage());
            return taskResult;
        }
    }

    public abstract TaskResult doTask() throws Exception;

    public TaskResult getTaskResult() {
        return taskResult;
    }

    public void setTaskResult(TaskResult taskResult) {
        this.taskResult = taskResult;
    }

    public long getStartTaskTime() {
        return startTaskTime;
    }
}
