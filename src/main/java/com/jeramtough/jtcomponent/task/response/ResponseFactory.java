package com.jeramtough.jtcomponent.task.response;

import com.jeramtough.jtcomponent.task.callback.TaskCallback;
import com.jeramtough.jtcomponent.task.runnable.CallbackTaskable;
import com.jeramtough.jtcomponent.task.runnable.ReturnTaskable;
import com.jeramtough.jtcomponent.task.runnable.SimpleTaskable;
import com.jeramtough.jtcomponent.task.runnable.Taskable;
import com.jeramtough.jtcomponent.task.runner.CallbackRunner;
import com.jeramtough.jtcomponent.task.runner.ReturnRunner;
import com.jeramtough.jtcomponent.task.runner.SimpleRunner;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/**
 * Created on 2019-03-14 14:41
 * by @author JeramTough
 */
public class ResponseFactory {

    public static TaskResponse doing(SimpleRunner simpleRunner) {
        Taskable taskable = new SimpleTaskable(simpleRunner);
        return doing(taskable);
    }

    public static TaskResponse doing(TaskCallback taskCallback,
                                     CallbackRunner callbackRunner) {
        Taskable taskable = new CallbackTaskable(callbackRunner, taskCallback);
        return doing(taskable);
    }

    public static <T> ReturnResponse<T> returnDoing(ReturnRunner<T> returnRunner) {
        DefaultReturnResponse<T> defaultReturnResponse =
                new DefaultReturnResponse(new ReturnTaskable(returnRunner));
        defaultReturnResponse.start();
        return defaultReturnResponse;
    }

    public static FutureTaskResponse asyncDoing(SimpleRunner simpleRunner) {
        Taskable taskable = new SimpleTaskable(simpleRunner);
        return asyncDoing(taskable);
    }

    public static FutureTaskResponse asyncDoing(TaskCallback taskCallback,
                                                CallbackRunner callbackRunner) {
        Taskable taskable = new CallbackTaskable(callbackRunner, taskCallback);
        return asyncDoing(taskable);
    }

    public static FutureTaskResponse asyncDoing(
            ThreadFactory threadFactory, TaskCallback taskCallback,
            CallbackRunner callbackRunner) {
        Taskable taskable = new CallbackTaskable(callbackRunner, taskCallback);
        return asyncDoing(threadFactory, taskable);
    }


    public static FutureTaskResponse asyncDoing(Executor executor, TaskCallback taskCallback,
                                                CallbackRunner callbackRunner) {
        Taskable taskable = new CallbackTaskable(callbackRunner, taskCallback);
        return asyncDoing(executor, taskable);
    }

    //****************************

    private static TaskResponse doing(Taskable taskable) {
        TaskResponse taskResponse = new DefaultTaskResponse(taskable).start();
        return taskResponse;
    }

    private static FutureTaskResponse asyncDoing(Taskable taskable) {
        DefultFutureTaskResponse defultFutureTaskResponse =
                new DefultFutureTaskResponse(taskable).start();
        return defultFutureTaskResponse;
    }

    private static FutureTaskResponse asyncDoing(
            ThreadFactory threadFactory, Taskable taskable) {
        DefultFutureTaskResponse defultFutureTaskResponse =
                new DefultFutureTaskResponse(taskable).start(threadFactory);
        return defultFutureTaskResponse;
    }

    private static FutureTaskResponse asyncDoing(Executor executor, Taskable taskable) {
        DefultFutureTaskResponse defultFutureTaskResponse =
                new DefultFutureTaskResponse(taskable).start(executor);
        return defultFutureTaskResponse;
    }
}
