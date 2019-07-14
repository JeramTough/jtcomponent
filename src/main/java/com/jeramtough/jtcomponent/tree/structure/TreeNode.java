package com.jeramtough.jtcomponent.tree.structure;

import com.jeramtough.jtcomponent.tree.base.SortMethod;
import com.jeramtough.jtcomponent.tree.foreach.NodeCaller;

import java.util.List;

/**
 * Created on 2019/7/11 15:25
 * by @author WeiBoWen
 */
public interface TreeNode {

    boolean isRoot();

    List<TreeNode> getSubs();

    boolean hasSubs();

    TreeNode getParent();

    void setParent(TreeNode parentTreeNode);

    void addSub(TreeNode treeNode);

    Object getValue();

    void setValue(Object value);

    int getLevel();

    void setLevel(int level);

    List<TreeNode> getAll();

    List<TreeNode> getAll(SortMethod sortMethod);

    List<List<TreeNode>> getAllForLevel(SortMethod sortMethod);

    List<TreeNode> getBrothers();

    void foreach(NodeCaller nodeCaller);

    String getDetail();
}
