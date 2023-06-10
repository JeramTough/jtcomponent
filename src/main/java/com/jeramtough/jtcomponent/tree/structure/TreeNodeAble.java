package com.jeramtough.jtcomponent.tree.structure;

/**
 * <pre>
 * Created on 2020/9/15 14:27
 * by @author WeiBoWen
 * </pre>
 */
public interface TreeNodeAble extends TreeNode {

    void setParent(TreeNode parentTreeNode);

    void setLevel(int level);

}
