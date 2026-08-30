package com.jeramtough.jtcomponent.tree3.core;

import com.jeramtough.jtcomponent.callback.CommonCallback;
import com.jeramtough.jtcomponent.tree3.filter.TreeNode3Filter;
import com.jeramtough.jtcomponent.tree3.sort.TreeNode3SortMethod;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * <pre>
 *     通过 Tree3Builder 创建的树对象。
 *
 *     相比 tree2，重点增强了两块能力：
 *     1. 节点重构（rebuilder 包）：过滤、取子树、按层级截断等。
 *     2. 节点搜索：除按 key/code 的 O(1) 查找外，新增按条件（Predicate）搜索。
 *
 * Created on 2025/7/17 上午1:03
 * by @author WeiBoWen
 * </pre>
 */
public interface Tree3<T> extends Serializable {

    /**
     * 设置 id-key 索引（反序列化或外部重建时使用）。
     */
    void setAllIdKeyTreeNodeMap(Map<String, TreeNode3<T>> allIdKeyTreeNodeMap);

    /**
     * 设置 code-key 索引（反序列化或外部重建时使用）。
     */
    void setAllCodeKeyTreeNodeMap(Map<String, TreeNode3<T>> allCodeKeyTreeNodeMap);

    /**
     * @return 根节点集合
     */
    List<TreeNode3<T>> getRootTreeNodeList();

    /**
     * 仅过滤根节点，不递归过滤子节点。
     *
     * @param filterList 过滤器集合
     * @return 过滤后的根节点
     */
    List<TreeNode3<T>> getRootTreeNodeList(List<TreeNode3Filter> filterList);

    /**
     * 将节点注册到树的索引（key / code）中。
     */
    void put(TreeNode3<T> treeNode);

    /**
     * 按 key 快速查找节点（O(1)）。
     */
    TreeNode3<T> getTreeNodeByIdKey(String key);

    /**
     * 按 code 快速查找节点（O(1)）。
     */
    TreeNode3<T> getTreeNodeByCodeKey(String key);

    /**
     * @return id-key 索引（key -> node），包含全部已注册节点
     */
    Map<String, TreeNode3<T>> getAllIdKeyTreeNodeMap();

    /**
     * @return code-key 索引（code -> node），仅包含设置了 code 的节点
     */
    Map<String, TreeNode3<T>> getAllCodeKeyTreeNodeMap();

    /**
     * @return 全部节点，默认升序
     */
    List<TreeNode3<T>> getAll();

    /**
     * @param sortMethod 排序方式
     * @return 排序后的全部节点
     */
    List<TreeNode3<T>> getAll(TreeNode3SortMethod sortMethod);

    /**
     * 按层级分组返回，默认升序。
     */
    List<List<TreeNode3<T>>> getAllForLevel();

    /**
     * 按层级分组返回。
     */
    List<List<TreeNode3<T>>> getAllForLevel(TreeNode3SortMethod sortMethod);

    /**
     * 按条件搜索全部匹配节点。
     *
     * @param predicate 匹配条件
     * @return 匹配的节点集合
     */
    List<TreeNode3<T>> search(Predicate<TreeNode3<T>> predicate);

    /**
     * 将树转为带 children 的嵌套 Map 列表（适用于 JSON 序列化）。
     */
    List<Map<String, Object>> toTreeNodeMapList();

    /**
     * 将树转为带 children 的嵌套 Map 列表，每个节点回调 commonCallback 允许自定义字段。
     */
    List<Map<String, Object>> toTreeNodeMapList(
            CommonCallback<Map<String, Object>> commonCallback);

    /**
     * @return 树的全局排序方式
     */
    TreeNode3SortMethod getSortMethod();
}
