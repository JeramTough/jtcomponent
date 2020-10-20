package com.jeramtough.jtcomponent.tree.adapter;

/**
 * 遍历节点List的适配器
 * <p>
 * Created on 2019/7/12 14:46
 * by @author WeiBoWen
 */
public interface OnceTreeNodeAdapter<T> {

    Object getKey();

    Object getParentKey();

    T getValue();

    int getOrder();
}
