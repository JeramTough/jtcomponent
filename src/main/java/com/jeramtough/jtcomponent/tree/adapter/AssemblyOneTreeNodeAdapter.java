package com.jeramtough.jtcomponent.tree.adapter;

import com.jeramtough.jtcomponent.tree.structure.TreeNode;

import java.util.Objects;

/**
 * <pre>
 * Created on 2024/11/26 下午8:28
 * by @author WeiBoWen
 * </pre>
 */
public class AssemblyOneTreeNodeAdapter implements OneTreeNodeAdapter<Object> {

    private final TreeNode treeNode;

    public AssemblyOneTreeNodeAdapter(TreeNode treeNode) {
        this.treeNode = treeNode;
    }

    @Override
    public Object getKey() {
        String key = treeNode.getKey();
        return key;
    }

    @Override
    public Object getParentKey() {
        TreeNode parent = treeNode.getParent();
        if (parent == null) {
            return null;
        }
        else {
            return parent.getKey();
        }
    }

    @Override
    public Object getValue() {
        return treeNode.getValue();
    }

    @Override
    public int getOrder() {
        return treeNode.getOrder();
    }
}
