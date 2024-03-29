package com.jeramtough.jtcomponent.task.bean;

import java.io.Serializable;

/**
 * 任务进行完毕，已经出现结果的TaskResult对象
 * <p>
 * Created on 2018-12-28 12:46
 * by @author JeramTough
 */
public class TaskResult extends PreTaskResult implements Serializable {


    @Override
    public void setSuccessful(Boolean successful) {
        super.setSuccessful(successful);
    }

    @Override
    public Boolean isSuccessful() {
        return super.isSuccessful();
    }

    @Override
    public Boolean getSuccessful() {
        return super.getSuccessful();
    }

    @Override
    public long getTimeConsuming() {
        return super.getTimeConsuming();
    }

    @Override
    public void setTimeConsuming(long timeConsuming) {
        super.setTimeConsuming(timeConsuming);
    }

}