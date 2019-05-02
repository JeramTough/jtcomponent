package com.jeramtough.jtcomponent.task.runner;

import com.jeramtough.jtcomponent.task.bean.no.TaskResult;
import com.jeramtough.jtcomponent.task.callback.RunningTaskCallback;

/**
 * Created on 2019-01-27 02:05
 * by @author JeramTough
 */
public interface CallbackRunner {


    boolean doTask(TaskResult taskResult, RunningTaskCallback runningTaskCallback);

}
