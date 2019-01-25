package test;

import com.jeramtough.jtcomponent.task.bean.no.TaskResult;
import com.jeramtough.jtcomponent.task.callback.RunningTaskCallback;
import com.jeramtough.jtcomponent.task.callback.TaskCallback;
import com.jeramtough.jtcomponent.task.taskresponse.AsyncTaskResponse;
import com.jeramtough.jtcomponent.task.taskresponse.CallbackTaskResponse;
import com.jeramtough.jtcomponent.task.taskresponse.FutureAsyncTaskResponse;
import com.jeramtough.jtcomponent.task.taskresponse.SimpleTaskResponse;
import com.jeramtough.jtcomponent.task.taskresponse.TaskResponse;
import com.jeramtough.jtlog.facade.L;

/**
 * Created on 2019-01-25 02:17
 * by @author JeramTough
 */
public class MyServiceImpl {

    public TaskResponse doSimplestTask() {
        return new SimpleTaskResponse() {
            @Override
            protected boolean doSomething(TaskResult taskResult) {
                L.debug("start task");
                try {
                    Thread.sleep(3000);
                    L.debug("doing something");
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
        };
    }

    public TaskResponse doSimplestTask(TaskCallback taskCallback) {

        return new CallbackTaskResponse(taskCallback) {

            @Override
            public boolean doSomething(TaskResult taskResult,
                                       RunningTaskCallback taskCallback) {
                L.debug("start task");
                try {
                    Thread.sleep(1000);
                    taskCallback.onTaskRunning(taskResult, 50);
                    L.debug("doing something");
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
        };

    }

    public FutureAsyncTaskResponse futurnTaskResponse() {
        return null;
    }


    public AsyncTaskResponse doSynchronousTask() {

        AsyncTaskResponse asyncTaskResponse = null;

        return asyncTaskResponse;

    }

    //*********************


}
