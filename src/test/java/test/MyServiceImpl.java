package test;

import com.jeramtough.jtcomponent.task.bean.no.TaskResult;
import com.jeramtough.jtcomponent.task.callback.RunningTaskCallback;
import com.jeramtough.jtcomponent.task.callback.TaskCallback;
import com.jeramtough.jtcomponent.task.response.SimpleTaskResponse;
import com.jeramtough.jtcomponent.task.response.TaskResponse;
import com.jeramtough.jtcomponent.task.response.TaskResponseWithCallback;
import com.jeramtough.jtlog.facade.L;

/**
 * Created on 2019-01-25 02:17
 * by @author JeramTough
 */
public class MyServiceImpl {

    public TaskResponse doSynchTask() {
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

    public TaskResponse doSynchTask(TaskCallback taskCallback) {

        return new TaskResponseWithCallback(taskCallback) {

            @Override
            public boolean doSomething(TaskResult taskResult,
                                       RunningTaskCallback taskCallback) {
                try {
                    Thread.sleep(1000);
                    taskCallback.onTaskRunning(taskResult, 50);
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
        };

    }


    //*********************


}
