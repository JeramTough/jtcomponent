package com.jeramtough.jtcomponent.tree.adapter;

import java.util.Objects;

/**
 * Created on 2019/7/11 15:23
 * by @author WeiBoWen
 */
public abstract class BaseTreeNodeAdapter<T> implements TreeNodeAdapter<T> {

    private T t;

    public BaseTreeNodeAdapter(T t) {
        Objects.requireNonNull(t);
        this.t = t;
    }


    @Override
    public T get() {
        return t;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseTreeNodeAdapter<?> that = (BaseTreeNodeAdapter<?>) o;
        return Objects.equals(t, that.t);
    }

    @Override
    public int hashCode() {
        return Objects.hash(t);
    }
}
