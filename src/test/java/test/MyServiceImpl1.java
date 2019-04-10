package test;

import com.jeramtough.jtcomponent.task.bean.no.TaskResult;
import com.jeramtough.jtcomponent.task.callback.RunningTaskCallback;
import com.jeramtough.jtcomponent.task.callback.TaskCallback;
import com.jeramtough.jtcomponent.task.response.*;
import com.jeramtough.jtcomponent.task.runnable.SimpleTaskable;
import com.jeramtough.jtcomponent.task.runnable.Taskable;
import com.jeramtough.jtcomponent.task.runnable.TaskableWithCallback;
import com.jeramtough.jtlog.facade.L;

/**
 * Created on 2019-01-25 02:17
 * by @author JeramTough
 */
public class MyServiceImpl1 {

    public AsyncTaskResponse doAsyncTask() {

        return TaskResponseBuilder.asyncDoing(new SimpleTaskable() {
            @Override
            public boolean doTask(TaskResult taskResult) {
                L.debug("start async task");

                try {
                    Thread.sleep(3000);
                    L.debug("doing async something");
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    public AsyncTaskResponse doAsyncTask(TaskCallback taskCallback) {
        return TaskResponseBuilder.asyncDoing(new TaskableWithCallback(taskCallback) {
            @Override
            public boolean doTask(TaskResult taskResult, RunningTaskCallback taskCallback) {
                try {
                    Thread.sleep(1000);
                    taskCallback.onTaskRunning(taskResult, 50);
                    Thread.sleep(1000);
                    throw new Exception("throw a test exception");
                }
                catch (Exception e) {
                    e.printStackTrace();
                    taskResult.setMessage("task fail");
                    taskResult.putPayload("dsfsa", 87);
                    taskResult.putPayload("ssdad", 'd');
                    return false;
                }
            }
        });
    }


    //*********************


}
