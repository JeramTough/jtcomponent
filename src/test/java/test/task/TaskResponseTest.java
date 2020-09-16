package test.task;

import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.jtcomponent.task.bean.PreTaskResult;
import com.jeramtough.jtcomponent.task.callback.TaskCallback;
import com.jeramtough.jtcomponent.task.response.TaskResponse;
import com.jeramtough.jtcomponent.task.response.ResponseFactory;
import com.jeramtough.jtlog.facade.L;

/**
 * Created on 2019-01-25 02:17
 * by @author JeramTough
 */
public class TaskResponseTest {

    public static void main(String[] args) {
        TaskResponseTest taskResponseTest = new TaskResponseTest();
    }

    TaskResponseTest() {
        Service service = new ServiceImpl();
        TaskResponse taskResponse = service.doTask();

        L.debug(taskResponse.getTaskResult().getDetail());

        service.doTaskWithCallback(new TaskCallback() {
            @Override
            public void onTaskStart() {
                L.arrive();
            }

            @Override
            public void onTaskRunning(PreTaskResult preTaskResult, int numerator,
                                      int denominator) {
                L.debug(denominator);
            }


            @Override
            public void onTaskCompleted(TaskResult taskResult) {
                L.debug(taskResult.getDetail());
            }
        });
    }


    public interface Service {

        TaskResponse doTask();

        TaskResponse doTaskWithCallback(TaskCallback taskCallback);

    }

    public class ServiceImpl implements TaskResponseTest.Service {

        @Override
        public TaskResponse doTask() {

            return ResponseFactory.doing(taskResult -> {
                L.arrive();
                return true;
            });

        }

        @Override
        public TaskResponse doTaskWithCallback(TaskCallback taskCallback) {
            return ResponseFactory.doing(taskCallback,
                    (taskResult, runningTaskCallback) -> {
                        L.debug("任务进行中。。。");
                        runningTaskCallback.onTaskRunning(taskResult, 2,50);
                        return false;
                    });

        }
    }


    //*********************


}
