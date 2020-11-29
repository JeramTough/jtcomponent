package com.jeramtough.jtcomponent.tree.adapter;

/**
 * 遍历节点List的适配器，用于适配数据结构和TreeNode差不多的数据，
 * 配合TreeProcessor可以处理节点为一个RootTree
 * @see com.jeramtough.jtcomponent.tree.processor.TreeProcessor
 * <p>
 * Created on 2019/7/12 14:46
 * by @author WeiBoWen
 */
public interface OneTreeNodeAdapter<T> {

    Object getKey();

    Object getParentKey();

    T getValue();

    int getOrder();
}
