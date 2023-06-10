package com.jeramtough.jtcomponent.tree.foreach;


import com.jeramtough.jtcomponent.tree.structure.TreeNode;

/**
 * Created on 2019/7/12 16:49
 * by @author WeiBoWen
 */
public interface NodeCaller {


    /**
     * @param treeNode {@link TreeNode}
     * @return return true is continual
     */
    boolean called(TreeNode treeNode);
}
