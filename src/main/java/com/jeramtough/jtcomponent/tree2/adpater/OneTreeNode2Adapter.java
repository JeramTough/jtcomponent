package com.jeramtough.jtcomponent.tree2.adpater;

import com.jeramtough.jtcomponent.tree2.sort.TreeNode2SortMethod;

/**
 * @see com.jeramtough.jtcomponent.tree.processor.TreeProcessor
 * <p>
 * Created on 2019/7/12 14:46
 * by @author WeiBoWen
 */
public interface OneTreeNode2Adapter<T> {

//    void setValue(T value);

    void setSource(Object source);

    T getValue();

    default String getCode() {
        return null;
    }

    String getKey();

    /**
     * 返回null代表是根节点！
     *
     * @return 父节点的key
     */
    String getParentKey();

    int getOrder();

}
