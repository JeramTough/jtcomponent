package com.jeramtough.jtcomponent.tree.filter;

import com.jeramtough.jtcomponent.tree.structure.TreeNode;

/**
 * <pre>
 * Created on 2024/11/26 下午8:14
 * by @author WeiBoWen
 * </pre>
 */
@FunctionalInterface
public interface TreeNodeFilter {
    boolean accept(TreeNode treeNode);
}
