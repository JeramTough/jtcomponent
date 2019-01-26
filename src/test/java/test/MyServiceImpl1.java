package test;

import com.jeramtough.jtcomponent.task.bean.no.TaskResult;
import com.jeramtough.jtcomponent.task.callback.RunningTaskCallback;
import com.jeramtough.jtcomponent.task.callback.TaskCallback;
import com.jeramtough.jtcomponent.task.runnable.BaseTaskRunnable;
import com.jeramtough.jtcomponent.task.response.*;
import com.jeramtough.jtcomponent.task.runnable.SimpleTaskRunnable;
import com.jeramtough.jtcomponent.task.runnable.TaskRunnableWithCallback;
import com.jeramtough.jtlog.facade.L;

/**
 * Created on 2019-01-25 02:17
 * by @author JeramTough
 */
public class MyServiceImpl1 {

    public AsyncTaskResponse doAsyncTask() {
        FutureAsyncTaskResponse asyncTaskResponse = new FutureAsyncTaskResponse(
                new SimpleTaskRunnable() {
                    @Override
                    public boolean doAsyncSomething(TaskResult taskResult) {
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
        Thread thread = new Thread(asyncTaskResponse);
        thread.start();
        return asyncTaskResponse;
    }

    public AsyncTaskResponse doAsyncTask(TaskCallback taskCallback) {
        FutureAsyncTaskResponse asyncTaskResponse = new FutureAsyncTaskResponse(
                new TaskRunnableWithCallback(taskCallback) {

                    @Override
                    public boolean doAsyncSomething(TaskResult taskResult,
                                                    RunningTaskCallback taskCallback) {
                        try {
                            Thread.sleep(1000);
                            taskCallback.onTaskRunning(taskResult, 50);
                            Thread.sleep(1000);
                            throw new Exception("throw a test exception");
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            taskResult.setMessage("task fail");
                            taskResult.putPayload("dsfsa",87);
                            taskResult.putPayload("ssdad",'d');
                            return false;
                        }

                    }
                });
        Thread thread = new Thread(asyncTaskResponse);
        thread.start();
        return asyncTaskResponse;
    }


    //*********************


}
