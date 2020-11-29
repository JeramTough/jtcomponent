package com.jeramtough.jtcomponent.tree.structure;

import com.jeramtough.jtcomponent.tree.base.SortMethod;
import com.jeramtough.jtcomponent.tree.foreach.NodeCaller;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created on 2019/7/11 15:25
 * by @author WeiBoWen
 */
public interface TreeNode {

    boolean isRoot();

    /**
     * 返回排序顺序，0最大，1次之
     *
     * @return 默认值是0
     */
    int getOrder();

    void setOrder(int order);

    List<TreeNode> getSubs();

    boolean hasSubs();

    TreeNode getParent();

    TreeNode addSub(TreeNode treeNode);

    TreeNode addSubs(TreeNode... treeNodes);

    /**
     * 添加子节点过滤器
     *
     * @param filter 过滤器表达式
     * @see #getSubsByFilters()
     * @return 过滤后的TreeNode对象
     */
    TreeNode andPredicate(Predicate<TreeNode> filter);

    /**
     * 返回完整版的子节点过滤器
     *
     * @return 返回完整版的子节点过滤器
     */
    Predicate<TreeNode> getSubFilters();

    /**
     * 得到所有子节点并使用上所有过滤器
     *
     * @see #andPredicate(Predicate) 关于如何添加子节点过滤器
     * @return 过滤后的集合
     */
    List<TreeNode> getSubsByFilters();

    /**
     * 该节点被移除，如果你不想真正移除该节点，可以试试用子节点过滤器
     *
     * @see #andPredicate(Predicate)
     */
    void beMoved();

    /**
     * 返回附带的值对象
     *
     * @return 附带的值对象
     */
    Object getValue();

    /**
     * 设置值对象
     *
     * @param value 值对象
     */
    void setValue(Object value);

    /**
     * 最小值是0
     * @return 层级
     */
    int getLevel();

    /**
     * @return 全部节点的集合类
     */
    List<TreeNode> getAll();

    /**
     * @param sortMethod 排序方法
     * @return 排序后
     */
    List<TreeNode> getAll(SortMethod sortMethod);

    /**
     * 该方法默认升序排序
     *
     * @return 以升序排序的TreeNode集合
     */
    List<List<TreeNode>> getAllForLevel();

    /**
     * @param sortMethod 排序方法
     * @return 排序后
     */
    List<List<TreeNode>> getAllForLevel(SortMethod sortMethod);

    /**
     * get brothers of node
     * @return the list
     */
    List<TreeNode> getBrothers();

    /**
     * 回调器返回true则继续执行，返回false则终止执行
     * @param nodeCaller 回调器
     */
    void foreach(NodeCaller nodeCaller);

    /**
     * get all node for detail
     *
     * @return the detail of string
     */
    String getDetail();


}
