package com.jeramtough.jtcomponent.task.response;

import com.jeramtough.jtcomponent.task.runnable.Taskable;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/**
 * Created on 2019-03-14 14:41
 * by @author JeramTough
 */
public class TaskResponseBuilder {


    public static TaskResponse doing(Taskable taskable) {
        TaskResponse taskResponse = new DefaultTaskResponse(taskable).start();
        return taskResponse;
    }

    public static AsyncTaskResponse asyncDoing(Taskable taskable) {
        FutureAsyncTaskResponse futureAsyncTaskResponse =
                new FutureAsyncTaskResponse(taskable).start();
        return futureAsyncTaskResponse;
    }

    public static AsyncTaskResponse asyncDoing(Taskable taskable,
                                               ThreadFactory threadFactory) {
        FutureAsyncTaskResponse futureAsyncTaskResponse =
                new FutureAsyncTaskResponse(taskable).start(threadFactory);
        return futureAsyncTaskResponse;
    }

    public static AsyncTaskResponse asyncDoing(Taskable taskable,
                                               Executor executor) {
        FutureAsyncTaskResponse futureAsyncTaskResponse =
                new FutureAsyncTaskResponse(taskable).start(executor);
        return futureAsyncTaskResponse;
    }
}
