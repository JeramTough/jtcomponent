package com.jeramtough.jtcomponent.task.response;

import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.jtcomponent.task.runnable.BaseTaskRunnable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created on 2019-01-25 21:20
 * by @author JeramTough
 */
public class FutureAsyncTaskResponse extends FutureTask<TaskResult>
        implements AsyncTaskResponse {

    public FutureAsyncTaskResponse(BaseTaskRunnable baseTaskRunnable) {
        super(baseTaskRunnable);

    }

    @Override
    public TaskResult waitingTaskResult() {
        try {
            return get();
        }
        catch (InterruptedException | ExecutionException e) {
            TaskResult taskResult = new TaskResult();
            taskResult.setSuccessful(false);
            taskResult.setMessage(e.getMessage());
            return taskResult;
        }
    }

}
