package com.jeramtough.jtcomponent.task.runnable;

import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.jtcomponent.task.callback.RunningTaskCallback;
import com.jeramtough.jtcomponent.task.callback.TaskCallback;
import com.jeramtough.jtcomponent.task.runner.CallbackRunner;

/**
 * Created on 2019-01-27 02:05
 * by @author JeramTough
 */
public class CallbackTaskable extends BaseTaskable {

    private CallbackRunner callbackRunner;
    private TaskCallback taskCallback;

    public CallbackTaskable(
            CallbackRunner callbackRunner,
            TaskCallback taskCallback) {
        super();
        this.callbackRunner = callbackRunner;
        this.taskCallback = taskCallback;
    }


    @Override
    public TaskResult doTask() throws Exception {
        TaskResult taskResult = getTaskResult();
        taskCallback.onTaskStart();
        try {
            boolean isSuccessful = callbackRunner.doTask(taskResult, taskCallback);
            taskResult.setSuccessful(isSuccessful);
            taskResult.setTimeConsuming(System.currentTimeMillis() - getStartTaskTime());
            taskCallback.onTaskCompleted(taskResult);
            return taskResult;
        }
        catch (Exception e) {
            e.printStackTrace();
            taskResult.setSuccessful(false);
            taskResult.setMessage(e.getMessage());
            taskResult.setTimeConsuming(System.currentTimeMillis() - getStartTaskTime());
            taskCallback.onTaskCompleted(taskResult);
            return taskResult;
        }

    }
}
