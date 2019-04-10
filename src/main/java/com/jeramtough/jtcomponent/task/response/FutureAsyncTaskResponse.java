package com.jeramtough.jtcomponent.task.response;

import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.jtcomponent.task.exception.DidntStartException;
import com.jeramtough.jtcomponent.task.runnable.Taskable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;

/**
 * Created on 2019-01-25 21:20
 * by @author JeramTough
 */
public class FutureAsyncTaskResponse extends FutureTask<TaskResult>
        implements AsyncTaskResponse {

    private boolean isStarted = false;

    protected FutureAsyncTaskResponse(Taskable taskable) {
        super(taskable);
    }

    @Override
    public TaskResult waitingTaskResult() {
        if (!isStarted) {
            throw new DidntStartException();
        }
        else {
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

    public FutureAsyncTaskResponse start() {
        new Thread(this).start();
        isStarted = true;

        return this;
    }

    public FutureAsyncTaskResponse start(ThreadFactory threadFactory) {
        threadFactory.newThread(this).start();
        isStarted = true;

        return this;
    }

    public FutureAsyncTaskResponse start(Executor executor) {
        executor.execute(this);
        isStarted = true;

        return this;
    }

}
