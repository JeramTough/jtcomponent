package com.jeramtough.jtcomponent.task.runnable;

import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.jtcomponent.task.runner.SimpleRunner;

/**
 * Created on 2019-01-27 16:49
 * by @author JeramTough
 */
public class SimpleTaskable extends BaseTaskable {

    private SimpleRunner simpleRunner;

    public SimpleTaskable(SimpleRunner simpleRunner) {
        super();
        this.simpleRunner = simpleRunner;
    }

    @Override
    public TaskResult doTask() {
        TaskResult taskResult = getTaskResult();
        boolean isSuccessful = simpleRunner.doTask(taskResult);
        taskResult.setSuccessful(isSuccessful);
        taskResult.setTimeConsuming(System.currentTimeMillis() - getStartTaskTime());
        return taskResult;
    }

}
