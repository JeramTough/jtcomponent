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

    void setAllTreeNodeMap(
            Map<String, TreeNode2<T>> allTreeNodeMap);

    List<TreeNode2<T>> getRootTreeNodeList();

    void put(TreeNode2<T> treeNode);

    TreeNode2<T> getTreeNode(String key);

    Map<String, TreeNode2<T>> getAllTreeNodeMap();

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
