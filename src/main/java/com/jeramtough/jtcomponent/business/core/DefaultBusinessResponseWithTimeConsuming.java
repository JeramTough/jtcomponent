package com.jeramtough.jtcomponent.business.core;

import com.jeramtough.jtcomponent.business.bean.BusinessResult;

import java.util.Objects;

/**
 * Created on 2019-01-09 11:29
 * by @author JeramTough
 */
public class DefaultBusinessResponseWithTimeConsuming extends DefaultBusinessResponse
        implements BusinessResponseWithTimeConsuming {

    private Long startTime;
    private Long stopTime;

    public DefaultBusinessResponseWithTimeConsuming(
            BusinessResult businessResult) {
        super(businessResult);
    }

    @Override
    public void startCountingTime() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void stopCountingTime() {
        stopTime = System.currentTimeMillis();
    }

    @Override
    public long getTimeConsuming() {
        return Objects.requireNonNull(stopTime) - Objects.requireNonNull(startTime);
    }
}
