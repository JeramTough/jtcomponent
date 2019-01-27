package com.jeramtough.jtcomponent.task.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2018-12-28 12:46
 * by @author JeramTough
 */
public class TaskResult extends com.jeramtough.jtcomponent.task.bean.no.TaskResult {


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