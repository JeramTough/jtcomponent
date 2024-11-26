package com.jeramtough.jtcomponent.tree.structure;

import java.util.List;

/**
 * <pre>
 * Created on 2020/9/15 14:27
 * by @author WeiBoWen
 * </pre>
 */
public interface TreeNodeAble extends TreeNode {

    void setParent(TreeNode parentTreeNode);

    void setLevel(int level);

    void setPaths(List<String> paths);

    void setKey(String key);
}
