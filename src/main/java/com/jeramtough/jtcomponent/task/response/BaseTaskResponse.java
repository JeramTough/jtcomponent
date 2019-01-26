package com.jeramtough.jtcomponent.task.response;


/**
 * Created on 2019-01-25 23:55
 * by @author JeramTough
 */
public abstract class BaseTaskResponse implements TaskResponse {

    private com.jeramtough.jtcomponent.task.bean.TaskResult taskResult;

    public BaseTaskResponse() {
        taskResult = new com.jeramtough.jtcomponent.task.bean.TaskResult();
    }

    @Override
    public com.jeramtough.jtcomponent.task.bean.TaskResult getTaskResult() {
        return taskResult;
    }

    public void setTaskResult(com.jeramtough.jtcomponent.task.bean.TaskResult taskResult) {
        this.taskResult = taskResult;
    }

    protected abstract boolean doSomething(
            com.jeramtough.jtcomponent.task.bean.no.TaskResult taskResult);
}
