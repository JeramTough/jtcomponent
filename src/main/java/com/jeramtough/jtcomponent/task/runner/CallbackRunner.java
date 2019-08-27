package com.jeramtough.jtcomponent.task.runner;

import com.jeramtough.jtcomponent.task.bean.no.PreTaskResult;
import com.jeramtough.jtcomponent.task.callback.RunningTaskCallback;

/**
 *
 * 用户业务实现类需实现的接口，带回调接口
 *
 * Created on 2019-01-27 02:05
 * by @author JeramTough
 */
public interface CallbackRunner {

    boolean doTask(PreTaskResult preTaskResult, RunningTaskCallback runningTaskCallback);

}
