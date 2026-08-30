package com.jeramtough.jtcomponent.tree3.builder;

import com.jeramtough.jtcomponent.tree3.core.Tree3;

/**
 * <pre>
 *     构建器基类：提供 build() 无参默认实现（不打印日志）。
 *
 * Created on 2025/7/17 下午5:34
 * by @author WeiBoWen
 * </pre>
 */
public abstract class BaseTree3Builder<T> implements Tree3Builder<T> {

    @Override
    public Tree3<T> build() {
        return build(false);
    }
}
