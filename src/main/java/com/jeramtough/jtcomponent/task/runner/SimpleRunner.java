package com.jeramtough.jtcomponent.task.runner;


import com.jeramtough.jtcomponent.task.bean.PreTaskResult;

/**
 *
 * 用户业务实现类需实现的接口
 *
 * Created on 2019-04-25 15:03
 * by @author JeramTough
 */
public interface SimpleRunner {

    boolean doTask(PreTaskResult preTaskResult);

}
