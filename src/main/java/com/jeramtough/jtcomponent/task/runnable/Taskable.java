package com.jeramtough.jtcomponent.task.runnable;

import com.jeramtough.jtcomponent.task.bean.TaskResult;

import java.util.concurrent.Callable;

/**
 * Created on 2019-01-27 16:43
 * by @author JeramTough
 */
public interface Taskable extends Callable<TaskResult> {
}
