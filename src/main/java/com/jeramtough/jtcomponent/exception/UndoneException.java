package com.jeramtough.jtcomponent.exception;

/**
 * 应该用undone异常的地方，代码没运行到就不管
 *
 * <pre>
 * Created on 2021/3/17 14:24
 * by @author WeiBoWen
 * </pre>
 */
public class UndoneException extends RuntimeException {

    public UndoneException() {
        this("该功能代码未完成！");
    }

    public UndoneException(String message) {
        super(message);
    }
}
