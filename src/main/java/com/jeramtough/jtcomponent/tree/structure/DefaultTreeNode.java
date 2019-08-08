package com.jeramtough.jtcomponent.tree.structure;

import com.jeramtough.jtcomponent.tree.base.SortMethod;
import com.jeramtough.jtcomponent.tree.foreach.NodeCaller;
import com.jeramtough.jtcomponent.tree.util.TreeNodeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created on 2019/7/11 15:44
 * by @author WeiBoWen
 */
public class DefaultTreeNode implements TreeNode {

    private Object value;
    private List<TreeNode> subTreeNodes;
    private TreeNode parentTreeNode;
    private int level = 0;
    private Predicate<TreeNode> subFilters;

    public DefaultTreeNode() {
        subTreeNodes = new ArrayList<>();
    }

    public DefaultTreeNode(Object value) {
        this();
        this.value = value;
    }

    @Override
    public boolean isRoot() {
        return parentTreeNode == null;
    }

    @Override
    public boolean hasSubs() {
        return getSubs().size() > 0;
    }

    @Override
    public List<TreeNode> getSubs() {
        return subTreeNodes;
    }


    @Override
    public TreeNode getParent() {
        return parentTreeNode;
    }

    @Override
    public void setParent(TreeNode parentTreeNode) {
        this.parentTreeNode = parentTreeNode;
    }

    @Override
    public TreeNode addSub(TreeNode treeNode) {
        getSubs().add(treeNode);
        treeNode.setParent(this);
        treeNode.setLevel(this.level + 1);
        return this;
    }

    @Override
    public void addSubs(TreeNode... treeNodes) {
        for (TreeNode treeNode : treeNodes) {
            addSub(treeNode);
        }
    }

    @Override
    public TreeNode andPredicate(Predicate<TreeNode> filter) {
        if (subFilters == null) {
            subFilters = filter;
        }
        else {
            subFilters=subFilters.and(filter);
        }
        return this;
    }

    @Override
    public Predicate<TreeNode> getSubFilters() {
        return this.subFilters;
    }


    @Override
    public List<TreeNode> getSubsByFilters() {
        if (subFilters == null) {
            return getSubs();
        }
        else {
            List<TreeNode> treeNodeList =
                    getSubs().stream().filter(subFilters).collect(Collectors.toList());

            return treeNodeList;
        }
    }

    @Override
    public void beMoved() {
        if (!isRoot()) {
            parentTreeNode.getSubs().remove(this);
        }
        else {
            throw new IllegalStateException("The root node can't be removed");
        }
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public List<TreeNode> getAll() {
        return TreeNodeUtils.getAll(this);
    }

    @Override
    public List<TreeNode> getAll(SortMethod sortMethod) {
        return TreeNodeUtils.getAll(this, sortMethod);
    }

    @Override
    public List<List<TreeNode>> getAllForLevel() {
        return getAllForLevel(SortMethod.DESCENDING);
    }

    @Override
    public List<List<TreeNode>> getAllForLevel(SortMethod sortMethod) {
        return TreeNodeUtils.getAllForLevel(this, sortMethod);
    }

    @Override
    public List<TreeNode> getBrothers() {
        List<TreeNode> brothers = new ArrayList<>();

        if (this.getParent() != null) {
            this.getParent().foreach(new NodeCaller() {
                @Override
                public boolean called(TreeNode treeNode) {

                    if (treeNode.getLevel() > DefaultTreeNode.this.level) {
                        return false;
                    }
                    else {
                        if (treeNode.getLevel() == DefaultTreeNode.this.level &&
                                treeNode != DefaultTreeNode.this) {
                            brothers.add(treeNode);
                        }
                        return true;
                    }

                }
            });
        }
        return brothers;
    }

    @Override
    public void foreach(NodeCaller nodeCaller) {
        TreeNodeUtils.foreach(this, nodeCaller);
    }


    @Override
    public String toString() {
        return "DefaultTreeNode{" +
                "value=" + value +
                ", level=" + level +
                '}';
    }

    @Override
    public String getDetail() {
        StringBuilder stringBuilder = new StringBuilder();

        foreach(new NodeCaller() {
            @Override
            public boolean called(TreeNode treeNode) {
                stringBuilder.append(treeNode.toString()).append("\n");
                return true;
            }
        });

        return stringBuilder.toString();
    }
}
