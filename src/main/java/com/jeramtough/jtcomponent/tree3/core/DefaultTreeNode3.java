package com.jeramtough.jtcomponent.tree3.core;

import com.jeramtough.jtcomponent.tree3.adapter.ChildrenLoader3;
import com.jeramtough.jtcomponent.tree3.adapter.OneTreeNode3Adapter;
import com.jeramtough.jtcomponent.tree3.filter.TreeNode3Filter;
import com.jeramtough.jtcomponent.tree3.sort.TreeNode3Comparator;
import com.jeramtough.jtcomponent.tree3.sort.TreeNode3SortMethod;
import com.jeramtough.jtcomponent.tree3.util.TreeNode3Utils;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * <pre>
 *     默认树节点实现。
 *
 *     相比 tree2 的 DefaultTreeNode2：
 *     1. clone() 深拷贝子树结构，不再拍平。
 *     2. 支持懒加载：设置 ChildrenLoader3 后，首次访问子节点时才加载。
 *     3. orderWithLevel 的层级基数提取为常量，避免魔法数字。
 *
 * Created on 2025/7/10 下午4:07
 * by @author WeiBoWen
 * </pre>
 */
public class DefaultTreeNode3<T> implements TreeNode3<T> {

    private static final long serialVersionUID = 8429708234653250008L;

    /**
     * orderWithLevel = level * ORDER_LEVEL_BASE + order，用于同一层级内的稳定排序。
     */
    public static final int ORDER_LEVEL_BASE = 100;

    private String key;
    private T value;
    private String code;
    private List<TreeNode3<T>> children = new ArrayList<>();
    private String parentKey;
    private int level = 0;
    private Integer order = 0;
    private Integer orderWithLevel = 0;
    private List<String> paths = new ArrayList<>();

    // 懒加载相关；tree 为回引用，不参与序列化，避免循环引用
    private transient ChildrenLoader3<T> childrenLoader;
    private boolean childrenLoaded = true;
    private transient DefaultTree3<T> tree;

    public DefaultTreeNode3() {
    }

    public DefaultTreeNode3(String key, T value) {
        this.key = key;
        this.value = value;
        if (key != null) {
            this.paths.add(key);
        }
    }

    //////////////////////////////////////////
    // 基础 getter / setter
    //////////////////////////////////////////

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
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
    public Integer getOrder() {
        return this.order;
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
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
    public Integer getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String getParentKey() {
        return this.parentKey;
    }

    @Override
    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    @Override
    public List<String> getPaths() {
        return this.paths;
    }

    @Override
    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    @Override
    public TreeNode3<T> clone() {
        DefaultTreeNode3<T> copy = new DefaultTreeNode3<>();
        copy.setKey(this.key);
        copy.setValue(this.value);
        copy.setCode(this.code);
        copy.setOrder(this.order == null ? 0 : this.order);
        copy.setLevel(this.level);
        copy.setOrderWithLevel(this.orderWithLevel);
        copy.setParentKey(this.parentKey);
        copy.setPaths(this.paths == null ? new ArrayList<>() : new ArrayList<>(this.paths));
        copy.childrenLoader = this.childrenLoader;
        copy.childrenLoaded = this.childrenLoaded;

        List<TreeNode3<T>> copiedChildren = new ArrayList<>(this.children.size());
        for (TreeNode3<T> child : this.children) {
            copiedChildren.add(child.clone());
        }
        copy.children = copiedChildren;
        return copy;
    }

    //////////////////////////////////////////
    // 子节点访问
    //////////////////////////////////////////

    @Override
    public List<TreeNode3<T>> getSubs() {
        loadChildrenIfNeeded();
        return this.children;
    }

    @Override
    public List<TreeNode3<T>> getSubs(List<TreeNode3Filter> filterList) {
        loadChildrenIfNeeded();
        return TreeNode3Utils.doFilters(filterList, this.children);
    }

    @Override
    public boolean hasSubs() {
        loadChildrenIfNeeded();
        return !this.children.isEmpty();
    }

    @Override
    public int getSubsLength() {
        loadChildrenIfNeeded();
        return this.children.size();
    }

    @Override
    public List<TreeNode3<T>> getAllSubs() {
        List<TreeNode3<T>> result = new ArrayList<>();
        Deque<TreeNode3<T>> queue = new ArrayDeque<>();
        for (TreeNode3<T> child : getSubs()) {
            queue.add(child);
        }
        while (!queue.isEmpty()) {
            TreeNode3<T> node = queue.poll();
            result.add(node);
            for (TreeNode3<T> child : node.getSubs()) {
                queue.add(child);
            }
        }
        return result;
    }

    @Override
    public TreeNode3<T> addSubs(TreeNode3<T>... treeNodes) {
        return addSubs(TreeNode3SortMethod.ASCENDING, treeNodes);
    }

    @Override
    public TreeNode3<T> addSubs(TreeNode3SortMethod sortMethod, TreeNode3<T>... treeNodes) {
        if (treeNodes != null) {
            for (TreeNode3<T> treeNode : treeNodes) {
                addChildInternal(treeNode);
            }
        }
        sortChildren(sortMethod);
        return this;
    }

    //////////////////////////////////////////
    // 懒加载
    //////////////////////////////////////////

    @Override
    public boolean isChildrenLoaded() {
        return this.childrenLoaded;
    }

    @Override
    public void setChildrenLoader(ChildrenLoader3<T> loader) {
        this.childrenLoader = loader;
        if (loader != null) {
            this.childrenLoaded = false;
        }
    }

    @Override
    public ChildrenLoader3<T> getChildrenLoader() {
        return this.childrenLoader;
    }

    @Override
    public List<TreeNode3<T>> loadChildren() {
        if (childrenLoaded) {
            return this.children;
        }
        if (childrenLoader == null) {
            childrenLoaded = true;
            return this.children;
        }

        List<OneTreeNode3Adapter<T>> adapters = childrenLoader.loadChildren(this);
        if (adapters != null) {
            for (OneTreeNode3Adapter<T> adapter : adapters) {
                DefaultTreeNode3<T> child =
                        new DefaultTreeNode3<>(adapter.getKey(), adapter.getValue());
                child.setCode(adapter.getCode());
                child.setOrder(adapter.getOrder());
                // 子节点继承同一个加载器，保证整棵子树都能逐层懒加载
                child.setChildrenLoader(this.childrenLoader);
                addChildInternal(child);
            }
        }
        childrenLoaded = true;
        sortChildren(resolveSortMethod());
        return this.children;
    }

    //////////////////////////////////////////
    // 搜索
    //////////////////////////////////////////

    @Override
    public List<TreeNode3<T>> searchDescendants(Predicate<TreeNode3<T>> predicate) {
        List<TreeNode3<T>> result = new ArrayList<>();
        for (TreeNode3<T> descendant : getAllSubs()) {
            if (predicate == null || predicate.test(descendant)) {
                result.add(descendant);
            }
        }
        return result;
    }

    //////////////////////////////////////////
    // 内部方法
    //////////////////////////////////////////

    private void loadChildrenIfNeeded() {
        if (!childrenLoaded) {
            loadChildren();
        }
    }

    /**
     * 仅建立父子关系，不计算 level/paths/orderWithLevel，也不触发注册。
     * 供扁平数据源构建器使用，后续统一做 BFS 归一化。
     *
     * @param child 要添加的子节点
     */
    public void addSubRaw(TreeNode3<T> child) {
        Objects.requireNonNull(child);
        this.children.add(child);
        child.setParentKey(this.key);
        if (child instanceof DefaultTreeNode3) {
            ((DefaultTreeNode3<T>) child).setTree(this.tree);
        }
    }

    /**
     * 对当前节点的直接子节点排序。
     *
     * @param sortMethod 排序方式
     */
    public void sortSubs(TreeNode3SortMethod sortMethod) {
        sortChildren(sortMethod);
    }

    private void addChildInternal(TreeNode3<T> child) {
        Objects.requireNonNull(child);
        this.children.add(child);
        child.setParentKey(this.key);
        child.setLevel(this.level + 1);

        List<String> childPaths = this.paths == null ? new ArrayList<>() : new ArrayList<>(this.paths);
        childPaths.add(child.getKey());
        child.setPaths(childPaths);

        int orderWithLevel = child.getLevel() * ORDER_LEVEL_BASE
                + (child.getOrder() == null ? 0 : child.getOrder());
        child.setOrderWithLevel(orderWithLevel);

        if (child instanceof DefaultTreeNode3) {
            ((DefaultTreeNode3<T>) child).setTree(this.tree);
        }
        if (this.tree != null) {
            this.tree.put(child);
        }
    }

    private void sortChildren(TreeNode3SortMethod sortMethod) {
        this.children.sort(new TreeNode3Comparator(sortMethod));
    }

    private TreeNode3SortMethod resolveSortMethod() {
        if (tree != null && tree.getSortMethod() != null) {
            return tree.getSortMethod();
        }
        return TreeNode3SortMethod.ASCENDING;
    }

    void setTree(DefaultTree3<T> tree) {
        this.tree = tree;
    }

    DefaultTree3<T> getTree() {
        return this.tree;
    }
}
