package com.jeramtough.jtcomponent.business.core;

/**
 * Created on 2019-01-09 11:25
 * by @author JeramTough
 */
public interface BusinessResponseWithTimeConsuming extends BusinessResponse {

    void startCountingTime();

    void stopCountingTime();

    long getTimeConsuming();

}
