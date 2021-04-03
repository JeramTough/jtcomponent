package com.jeramtough.jtcomponent.exception;

/**
 * <pre>
 * Created on 2021/3/17 14:24
 * by @author WeiBoWen
 * </pre>
 */
public class UndoneException extends Exception {

    public UndoneException() {
        this("该功能代码未完成！");
    }

    public UndoneException(String message) {
        super(message);
    }
}
