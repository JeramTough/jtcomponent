package com.jeramtough.jtcomponent.task.exception;

/**
 * Created on 2019-01-27 19:31
 * by @author JeramTough
 */
public class HaventStartedException extends RuntimeException {

    public HaventStartedException() {
        super("Can't get the taskResult before invoking the start method");
    }
}
