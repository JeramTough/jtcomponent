package com.jeramtough.jtcomponent.tree3.adapter;

import com.jeramtough.jtcomponent.tree3.core.TreeNode3;

/**
 * <pre>
 *     重构专用适配器：把一个已存在的 TreeNode3 包装成扁平适配器，
 *     供 EveryoneTree3Builder 重新构建新树（如过滤、取子树后）。
 *
 * Created on 2024/11/26 下午8:28
 * by @author WeiBoWen
 * </pre>
 */
public class RebuildOneTreeNode3Adapter<T> implements OneTreeNode3Adapter<T> {

    private TreeNode3<T> treeNode;

    /**
     * @param source 必须是 TreeNode3 实例
     */
    @Override
    public void setSource(Object source) {
        this.treeNode = (TreeNode3<T>) source;
    }

    @Override
    public String getKey() {
        return treeNode.getKey();
    }

    @Override
    public String getParentKey() {
        return treeNode.getParentKey();
    }

    @Override
    public T getValue() {
        return treeNode.getValue();
    }

    @Override
    public int getOrder() {
        return treeNode.getOrder() == null ? 0 : treeNode.getOrder();
    }

    @Override
    public String getCode() {
        return treeNode.getCode();
    }
}
