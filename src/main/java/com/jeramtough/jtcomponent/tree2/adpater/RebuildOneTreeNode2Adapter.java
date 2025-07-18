package com.jeramtough.jtcomponent.tree2.adpater;

import com.jeramtough.jtcomponent.tree.adapter.OneTreeNodeAdapter;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;
import com.jeramtough.jtcomponent.tree2.core.TreeNode2;

/**
 * <pre>
 * Created on 2024/11/26 下午8:28
 * by @author WeiBoWen
 * </pre>
 */
public class RebuildOneTreeNode2Adapter<T> implements OneTreeNode2Adapter<T> {

    private TreeNode2<T> treeNode2;

    public RebuildOneTreeNode2Adapter() {
    }

    @Override
    public void setSource(Object source) {
        treeNode2 = (TreeNode2<T>) source;
    }


    @Override
    public String getKey() {
        return treeNode2.getKey();
    }

    @Override
    public String getParentKey() {
        return treeNode2.getParentKey();
    }


    @Override
    public T getValue() {
        return treeNode2.getValue();
    }

    @Override
    public int getOrder() {
        return treeNode2.getOrder();
    }
}
