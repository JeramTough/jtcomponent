package com.jeramtough.jtcomponent.tree2.filter;

import com.jeramtough.jtcomponent.tree2.core.TreeNode2;

/**
 * <pre>
 * Created on 2024/11/26 下午8:14
 * by @author WeiBoWen
 * </pre>
 */
public interface TreeNode2Filter {

    int getOrderNumber();

    <T> boolean accept(TreeNode2<T> treeNode);
}
