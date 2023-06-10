package com.jeramtough.jtcomponent.tree.comparator;

import com.jeramtough.jtcomponent.tree.structure.TreeNode;

import java.util.Comparator;

/**
 * <pre>
 * Created on 2020/9/15 13:57
 * by @author WeiBoWen
 * </pre>
 */
public class TreeNodeComparator implements Comparator<TreeNode> {
    @Override
    public int compare(TreeNode o1, TreeNode o2) {
        return o1.getOrder() - o2.getOrder();
    }
}
