package test;

import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.jtcomponent.task.callback.TaskCallback;
import com.jeramtough.jtcomponent.task.response.FutureTaskResponse;
import com.jeramtough.jtcomponent.task.response.TaskResponse;
import com.jeramtough.jtcomponent.task.response.TaskResponseBuilder;
import com.jeramtough.jtcomponent.task.runner.SimpleRunner;
import com.jeramtough.jtlog.facade.L;

/**
 * Created on 2019-01-25 02:17
 * by @author JeramTough
 */
public class MyServiceImpl2 {

    public static void main(String[] args) {
        MyServiceImpl2 myService1 = new MyServiceImpl2();

        FutureTaskResponse futureTaskResponse = myService1.doAsyncTask();
        L.debug(futureTaskResponse.isDone());
        L.debug(futureTaskResponse.waitingTaskResult().toString());

        myService1.doAsyncTaskWithCallback(new TaskCallback() {
            @Override
            public void onTaskStart() {
                L.arrive();
            }

            @Override
            public void onTaskRunning(TaskResult taskResult, int percent) {
                L.debug(percent);
            }

            @Override
            public void onTaskCompleted(TaskResult taskResult) {
                L.debug(taskResult.getDetail());
            }
        });
    }

    public FutureTaskResponse doAsyncTask() {

        return TaskResponseBuilder.asyncDoing(taskResult -> {
            L.debug("异步任务进行中。。。");
            return true;
        });

    }

    public FutureTaskResponse doAsyncTaskWithCallback(TaskCallback taskCallback) {

        return TaskResponseBuilder.asyncDoing(taskCallback,
                (taskResult, runningTaskCallback) -> {
                    L.debug("任务进行中。。。");
                    runningTaskCallback.onTaskRunning(taskResult, 50);
                    try {
                        Thread.sleep(200);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return false;
                });

    }


    //*********************


}
