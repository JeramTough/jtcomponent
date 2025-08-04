package com.jeramtough.jtcomponent.tree2.filter;

import com.jeramtough.jtcomponent.tree2.core.TreeNode2;

/**
 * <pre>
 * Created on 2024/11/26 下午2:33
 * by @author WeiBoWen
 * </pre>
 */
public class MaxLevelTreeNode2Filter implements TreeNode2Filter {

    private final int maxLevel;

    public MaxLevelTreeNode2Filter(int maxLevel) {
        this.maxLevel = maxLevel;
    }


    @Override
    public int getOrderNumber() {
        return 0;
    }

    @Override
    public <T> boolean accept(TreeNode2<T> treeNode2) {
        //最大取的层级
        return treeNode2.getLevel() <= maxLevel;
    }
}
