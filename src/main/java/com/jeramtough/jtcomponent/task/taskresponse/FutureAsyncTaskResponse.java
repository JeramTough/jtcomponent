package com.jeramtough.jtcomponent.task.taskresponse;

import com.jeramtough.jtcomponent.task.bean.TaskResult;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created on 2019-01-25 21:20
 * by @author JeramTough
 */
public class FutureAsyncTaskResponse extends FutureTask<TaskResult>
        implements AsyncTaskResponse {

    public FutureAsyncTaskResponse(
            Callable<TaskResult> callable) {
        super(callable);
    }

    @Override
    public TaskResult waitingTaskResult() {
        try {
            return get();
        }
        catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }
}
