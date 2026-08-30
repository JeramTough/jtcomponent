package com.jeramtough.jtcomponent.tree3.filter;

import com.jeramtough.jtcomponent.tree3.core.TreeNode3;

/**
 * <pre>
 *     按最大层级过滤：保留 level &lt;= maxLevel 的节点。
 *
 * Created on 2024/11/26 下午2:33
 * by @author WeiBoWen
 * </pre>
 */
public class MaxLevelTreeNode3Filter implements TreeNode3Filter {

    private final int maxLevel;

    public MaxLevelTreeNode3Filter(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    @Override
    public int getOrderNumber() {
        return 0;
    }

    @Override
    public <T> boolean accept(TreeNode3<T> treeNode) {
        Integer level = treeNode.getLevel();
        return level != null && level <= maxLevel;
    }
}
