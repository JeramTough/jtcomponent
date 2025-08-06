package com.jeramtough.jtcomponent.tree2.core;

import com.jeramtough.jtcomponent.callback.CommonCallback;
import com.jeramtough.jtcomponent.tree2.filter.TreeNode2Filter;
import com.jeramtough.jtcomponent.tree2.sort.TreeNode2SortMethod;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *
 *     通过Tree2Builder创建的树对象
 *
 * Created on 2025/7/17 上午1:03
 * by @author WeiBoWen
 * </pre>
 */
public interface Tree2<T> extends Serializable {

    void setAllIdKeyTreeNodeMap(
            Map<String, TreeNode2<T>> allIdKeyTreeNodeMap);

    void setCodeKeyTreeNodeMap(
            Map<String, TreeNode2<T>> allIdKeyTreeNodeMap);

    List<TreeNode2<T>> getRootTreeNodeList();

    /**
     * 仅仅是过滤子节点，孙节点不进行过滤
     * @param filterList 过滤器
     * @return 过滤后的子节点
     */
    List<TreeNode2<T>> getRootTreeNodeList(List<TreeNode2Filter> filterList);

    void put(TreeNode2<T> treeNode);

    TreeNode2<T> getTreeNodeByIdKey(String key);
    TreeNode2<T> getTreeNodeByCodeKey(String key);

    Map<String, TreeNode2<T>> getAllIdKeyTreeNodeMap();
    Map<String, TreeNode2<T>> getAllCodeKeyTreeNodeMap();

    /**
     * @return 全部节点的集合类
     */
    List<TreeNode2<T>> getAll();

    /**
     * @param sortMethod 排序方法
     * @return 排序后
     */
    List<TreeNode2<T>> getAll(TreeNode2SortMethod sortMethod);

    /**
     * 该方法默认升序排序
     *
     * @return 以升序排序的TreeNode集合
     */
    List<List<TreeNode2<T>>> getAllForLevel();

    /**
     * @param sortMethod 排序方法
     * @return 排序后
     */
    List<List<TreeNode2<T>>> getAllForLevel(TreeNode2SortMethod sortMethod);

    List<Map<String, Object>> toTreeNodeMapList();

    List<Map<String, Object>> toTreeNodeMapList(
            CommonCallback<Map<String, Object>> commonCallback);

}
