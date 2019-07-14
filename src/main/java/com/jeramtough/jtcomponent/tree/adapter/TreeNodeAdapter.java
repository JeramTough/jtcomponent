package com.jeramtough.jtcomponent.tree.adapter;

import java.util.List;

/**
 * Created on 2019/7/12 14:46
 * by @author WeiBoWen
 */
public interface TreeNodeAdapter<T> {

    T getParent();

    List<T> getSubs();

    boolean hasSubs();

    TreeNodeAdapter<T> getNewInstance(T t);

    T get();

}
