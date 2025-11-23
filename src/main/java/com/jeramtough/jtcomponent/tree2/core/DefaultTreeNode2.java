package com.jeramtough.jtcomponent.tree2.core;

import com.jeramtough.jtcomponent.tree2.filter.TreeNode2Filter;
import com.jeramtough.jtcomponent.tree2.sort.TreeNode2Comparator;
import com.jeramtough.jtcomponent.tree2.sort.TreeNode2SortMethod;
import com.jeramtough.jtcomponent.tree2.util.TreeNode2Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 * Created on 2025/7/10 下午4:07
 * by @author WeiBoWen
 * </pre>
 */
public class DefaultTreeNode2<T> implements TreeNode2<T> {

    private static final long serialVersionUID = 8429708234653250007L;

    private String key;
    private T value;
    private String code;
    private List<TreeNode2<T>> subTreeNodes = new ArrayList<>();
    private String parentTreeNodeKey;
    private int level = 0;
    private Integer order = 0;
    private Integer orderWithLevel = 0;
    private List<String> paths = new ArrayList<>();

    public DefaultTreeNode2() {
    }

    public DefaultTreeNode2(String key, T value) {
        this.key = key;
        this.value = value;

        paths.add(key);
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setSubTreeNodes(List<TreeNode2<T>> subTreeNodes) {
        this.subTreeNodes = subTreeNodes;
    }

    @Override
    public TreeNode2<T> clone() {
        DefaultTreeNode2<T> newTreeNode2 = new DefaultTreeNode2<T>(getKey(), getValue());

        newTreeNode2.setKey(getKey());
        newTreeNode2.setCode(getCode());
        newTreeNode2.setOrder(getOrder());
        newTreeNode2.setLevel(getLevel());
        newTreeNode2.setPaths(getPaths());
        newTreeNode2.setValue(getValue());
        newTreeNode2.setSubTreeNodes(getAllSubs());

        return newTreeNode2;
    }

    @Override
    public Integer getOrder() {
        return this.order;
    }

    @Override
    public Integer getOrderWithLevel() {
        return this.orderWithLevel;
    }

    @Override
    public void setOrderWithLevel(Integer orderWithLevel) {
        this.orderWithLevel = orderWithLevel;
    }

    @Override
    public List<String> getPaths() {
        return this.paths;
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public List<TreeNode2<T>> getSubs() {
        return this.getSubs(new ArrayList<>());
    }

    @Override
    public List<TreeNode2<T>> getSubs(List<TreeNode2Filter> filterList) {
        //进行过滤
        List<TreeNode2<T>> treeNode2List = TreeNode2Utils.doFilters(filterList,
                this.subTreeNodes);
        return treeNode2List;
    }

    @Override
    public String getParentKey() {
        return this.parentTreeNodeKey;
    }

    @Override
    public void setParentKey(String parentKey) {
        this.parentTreeNodeKey = parentKey;
    }

    @Override
    public T getValue() {
        return this.value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public Integer getLevel() {
        return this.level;
    }


    ////////////////////////////////////
    //////////////////////////////////
    ///一下方法都是操作方法/////////////
    //////////////////////////////////

    @Override
    public boolean hasSubs() {
        return !subTreeNodes.isEmpty();
    }

    @Override
    public List<TreeNode2<T>> getAllSubs() {
        List<TreeNode2<T>> allSubs = new ArrayList<>();
        if (hasSubs()) {
            for (TreeNode2<T> treeNode : subTreeNodes) {
                allSubs.add(treeNode);
                allSubs.addAll(treeNode.getAllSubs());
            }
            return allSubs;
        }
        else {
            return Collections.emptyList();
        }
    }


    @SafeVarargs
    @Override
    public final TreeNode2<T> addSubs(TreeNode2<T>... treeNodes) {
        return addSubs(TreeNode2SortMethod.ASCENDING, treeNodes);
    }

    @Override
    public TreeNode2<T> addSubs(TreeNode2SortMethod treeNode2SortMethod,
                                TreeNode2<T>... treeNodes) {
        for (TreeNode2<T> treeNode : treeNodes) {
            addSubButDontSort(treeNode);
        }
        //排序
        TreeNode2Comparator comparator = new TreeNode2Comparator(treeNode2SortMethod);
        subTreeNodes.sort(comparator);
        return this;
    }


    //**********************************

    /**
     * 添加子节点，但是不排序
     */
    private void addSubButDontSort(TreeNode2<T> treeNode) {
        Objects.requireNonNull(treeNode);
        subTreeNodes.add(treeNode);
        treeNode.setParentKey(this.getKey());

        treeNode.setLevel(this.level + 1);
        List<String> subPaths = new ArrayList<>(this.paths);
        subPaths.add(treeNode.getKey());
        treeNode.setPaths(subPaths);

        int orderWithLevel = (treeNode.getLevel() * 100 + treeNode.getOrder());
        treeNode.setOrderWithLevel(orderWithLevel);
    }

}
