package com.jeramtough.jtcomponent.task.runner;

import com.jeramtough.jtcomponent.task.bean.PreTaskResult;

/**
 * <pre>
 * 用户业务实现类需实现的接口，有返回值
 *
 * Created on 2019-08-27 22:00
 * by @author JeramTough
 * </pre>
 */
public interface ReturnRunner<T> {

    /**
     * @param preTaskResult {@link PreTaskResult}
     * @return T
     */
    T doTask(PreTaskResult preTaskResult);

}
