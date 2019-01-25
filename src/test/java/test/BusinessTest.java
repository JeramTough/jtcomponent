package test;

import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.jtcomponent.task.callback.TaskCallback;
import com.jeramtough.jtcomponent.task.taskresponse.TaskResponse;
import com.jeramtough.jtlog.facade.L;

/**
 * Created on 2019-01-09 11:14
 * by @author JeramTough
 */
public class BusinessTest {

    private static MyServiceImpl myService = new MyServiceImpl();

    public static void main(String[] args) {
//        test1();
        test2();
    }

    public static void test1() {
        TaskResponse taskResponse = myService.doSimplestTask();
        L.debug(taskResponse.getTaskResult().getDetail());
    }

    public static void test2() {

        TaskResponse taskResponse = myService.doSimplestTask(new TaskCallback() {
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
//        L.debug(taskResponse.getTaskResult().getDetail());

    }


}
