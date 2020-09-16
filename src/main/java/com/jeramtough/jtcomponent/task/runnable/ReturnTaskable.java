package com.jeramtough.jtcomponent.task.runnable;

import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.jtcomponent.task.runner.ReturnRunner;

/**
 * Created on 2019-08-27 22:02
 * by @author JeramTough
 */
public class ReturnTaskable<T> extends BaseTaskable {

    private ReturnRunner<T> returnRunner;
    private T returnT;

    public ReturnTaskable(ReturnRunner<T> returnRunner) {
        this.returnRunner = returnRunner;
    }

    @Override
    public TaskResult doTask() throws Exception {
        TaskResult taskResult = getTaskResult();
        returnT = returnRunner.doTask(taskResult);
        boolean isSuccessful = false;
        if (returnT != null) {
            isSuccessful = true;
        }
        taskResult.setSuccessful(isSuccessful);
        taskResult.setTimeConsuming(System.currentTimeMillis() - getStartTaskTime());
        return taskResult;
    }

    public T getReturnT() {
        return returnT;
    }
}
