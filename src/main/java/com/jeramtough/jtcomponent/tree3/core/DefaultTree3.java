package com.jeramtough.jtcomponent.tree3.core;

import com.jeramtough.jtcomponent.callback.CommonCallback;
import com.jeramtough.jtcomponent.tree3.builder.mapbuilder.DefaultTree3MapBuilder;
import com.jeramtough.jtcomponent.tree3.filter.TreeNode3Filter;
import com.jeramtough.jtcomponent.tree3.sort.TreeNode3Comparator;
import com.jeramtough.jtcomponent.tree3.sort.TreeNode3SortMethod;
import com.jeramtough.jtcomponent.tree3.util.TreeNode3Utils;
import com.jeramtough.jtcomponent.utils.IdUtil;
import com.jeramtough.jtcomponent.utils.JtStrUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;

/**
 * <pre>
 *     默认树实现。
 *
 *     相比 tree2 的 DefaultTree2：
 *     1. 使用 HashMap 索引（构建为单线程，读取更快）。
 *     2. getAll 每次基于索引快照排序，不缓存以避免脏数据。
 *     3. 提供 search 按条件搜索。
 *
 * Created on 2025/7/17 上午1:09
 * by @author WeiBoWen
 * </pre>
 */
public class DefaultTree3<T> implements Tree3<T> {

    private static final long serialVersionUID = 7453032997579760511L;

    private Map<String, TreeNode3<T>> allIdKeyTreeNodeMap = new HashMap<>();
    private Map<String, TreeNode3<T>> allCodeKeyTreeNodeMap = new HashMap<>();
    private List<TreeNode3<T>> rootTreeNodeList = new ArrayList<>();
    private TreeNode3SortMethod sortMethod = TreeNode3SortMethod.ASCENDING;

    public DefaultTree3() {
    }

    @Override
    public void setAllIdKeyTreeNodeMap(Map<String, TreeNode3<T>> allIdKeyTreeNodeMap) {
        this.allIdKeyTreeNodeMap = allIdKeyTreeNodeMap;
    }

    @Override
    public void setAllCodeKeyTreeNodeMap(Map<String, TreeNode3<T>> allCodeKeyTreeNodeMap) {
        this.allCodeKeyTreeNodeMap = allCodeKeyTreeNodeMap;
    }

    public void setRootTreeNodeList(List<TreeNode3<T>> rootTreeNodeList) {
        this.rootTreeNodeList = rootTreeNodeList;
    }

    public void setSortMethod(TreeNode3SortMethod sortMethod) {
        this.sortMethod = sortMethod == null ? TreeNode3SortMethod.ASCENDING : sortMethod;
    }

    @Override
    public List<TreeNode3<T>> getRootTreeNodeList() {
        return this.rootTreeNodeList;
    }

    @Override
    public List<TreeNode3<T>> getRootTreeNodeList(List<TreeNode3Filter> filterList) {
        return TreeNode3Utils.doFilters(filterList, this.rootTreeNodeList);
    }

    @Override
    public void put(TreeNode3<T> treeNode) {
        if (treeNode == null) {
            return;
        }
        if (treeNode instanceof DefaultTreeNode3) {
            ((DefaultTreeNode3<T>) treeNode).setTree(this);
        }
        if (!JtStrUtil.isEmpty(treeNode.getKey())) {
            allIdKeyTreeNodeMap.put(treeNode.getKey(), treeNode);
        }
        if (!JtStrUtil.isEmpty(treeNode.getCode())) {
            allCodeKeyTreeNodeMap.put(treeNode.getCode(), treeNode);
        }
    }

    @Override
    public TreeNode3<T> getTreeNodeByIdKey(String key) {
        return allIdKeyTreeNodeMap.get(key);
    }

    @Override
    public TreeNode3<T> getTreeNodeByCodeKey(String key) {
        return allCodeKeyTreeNodeMap.get(key);
    }

    @Override
    public Map<String, TreeNode3<T>> getAllIdKeyTreeNodeMap() {
        return this.allIdKeyTreeNodeMap;
    }

    @Override
    public Map<String, TreeNode3<T>> getAllCodeKeyTreeNodeMap() {
        return this.allCodeKeyTreeNodeMap;
    }

    @Override
    public List<TreeNode3<T>> getAll() {
        return getAll(this.sortMethod);
    }

    @Override
    public List<TreeNode3<T>> getAll(TreeNode3SortMethod sortMethod) {
        List<TreeNode3<T>> nodes = new ArrayList<>(allIdKeyTreeNodeMap.values());
        nodes.sort(new TreeNode3Comparator(sortMethod));
        return nodes;
    }

    @Override
    public List<List<TreeNode3<T>>> getAllForLevel() {
        return getAllForLevel(this.sortMethod);
    }

    @Override
    public List<List<TreeNode3<T>>> getAllForLevel(TreeNode3SortMethod sortMethod) {
        if (sortMethod == null) {
            sortMethod = TreeNode3SortMethod.ASCENDING;
        }
        Comparator<Integer> levelComparator = sortMethod == TreeNode3SortMethod.DESCENDING
                ? Comparator.reverseOrder()
                : Comparator.naturalOrder();

        Map<Integer, List<TreeNode3<T>>> grouped = new TreeMap<>(levelComparator);
        for (TreeNode3<T> node : getAll(sortMethod)) {
            Integer level = node.getLevel() == null ? 0 : node.getLevel();
            List<TreeNode3<T>> list = grouped.get(level);
            if (list == null) {
                list = new ArrayList<>();
                grouped.put(level, list);
            }
            list.add(node);
        }
        return new ArrayList<>(grouped.values());
    }

    @Override
    public List<TreeNode3<T>> search(Predicate<TreeNode3<T>> predicate) {
        List<TreeNode3<T>> result = new ArrayList<>();
        for (TreeNode3<T> node : getAll()) {
            if (predicate == null || predicate.test(node)) {
                result.add(node);
            }
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> toTreeNodeMapList() {
        return toTreeNodeMapList(null);
    }

    @Override
    public List<Map<String, Object>> toTreeNodeMapList(
            CommonCallback<Map<String, Object>> commonCallback) {
        return new DefaultTree3MapBuilder()
                .setTree3(this)
                .setCommonCallback(commonCallback)
                .build()
                .getRootMapList();
    }

    @Override
    public TreeNode3SortMethod getSortMethod() {
        return this.sortMethod;
    }
}
