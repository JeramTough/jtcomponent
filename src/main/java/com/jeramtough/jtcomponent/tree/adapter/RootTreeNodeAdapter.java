package com.jeramtough.jtcomponent.tree.adapter;

import java.util.List;

/**
 *
 * 遍历一个根节点的适配器
 *
 * Created on 2019/7/12 14:46
 * by @author WeiBoWen
 */
public interface RootTreeNodeAdapter<T> {

    T getParent();

    List<T> getSubs();

    boolean hasSubs();

    RootTreeNodeAdapter<T> getNewInstance(T t);

    T get();

}
