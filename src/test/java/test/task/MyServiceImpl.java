package test.task;

import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.jtcomponent.task.callback.TaskCallback;
import com.jeramtough.jtcomponent.task.response.TaskResponse;
import com.jeramtough.jtcomponent.task.response.TaskResponseBuilder;
import com.jeramtough.jtlog.facade.L;

/**
 * Created on 2019-01-25 02:17
 * by @author JeramTough
 */
public class MyServiceImpl {

    public static void main(String[] args) {
        MyServiceImpl myService1 = new MyServiceImpl();
        TaskResponse taskResponse = myService1.doTask();

        L.debug(taskResponse.getTaskResult().getDetail());

        myService1.doTaskWithCallback(new TaskCallback() {
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

    public TaskResponse doTask() {

        return TaskResponseBuilder.doing(taskResult -> {
            L.arrive();
            return true;
        });

    }

    public TaskResponse doTaskWithCallback(TaskCallback taskCallback) {

        return TaskResponseBuilder.doing(taskCallback,
                (taskResult, runningTaskCallback) -> {
                    L.debug("任务进行中。。。");
                    runningTaskCallback.onTaskRunning(taskResult, 50);
                    return false;
                });

    }


    //*********************


}
