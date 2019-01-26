package test;

import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.jtcomponent.task.callback.TaskCallback;
import com.jeramtough.jtcomponent.task.response.AsyncTaskResponse;
import com.jeramtough.jtcomponent.task.response.TaskResponse;
import com.jeramtough.jtlog.facade.L;

/**
 * Created on 2019-01-09 11:14
 * by @author JeramTough
 */
public class BusinessTest {

    private static MyServiceImpl myService = new MyServiceImpl();
    private static MyServiceImpl1 myService1 = new MyServiceImpl1();

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();
    }

    public static void test1() {
        TaskResponse taskResponse = myService.doSynchTask();
        L.debug(taskResponse.getTaskResult().getDetail());
    }

    public static void test2() {

        TaskResponse taskResponse = myService.doSynchTask(new TaskCallback() {
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


    public static void test3() {
        AsyncTaskResponse asyncTaskResponse = myService1.doAsyncTask();
        L.debug("异步线程运行中。。。");
        L.debug(asyncTaskResponse.waitingTaskResult().getDetail());
    }

    public static void test4() {
        myService1.doAsyncTask(new TaskCallback() {
            @Override
            public void onTaskStart() {
                L.debug("start async task");
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
        L.debug("异步线程运行中。。。");
    }
}
