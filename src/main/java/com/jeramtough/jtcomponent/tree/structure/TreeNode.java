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
     * 默认值是0
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
     * @see #getSubsByFilters()
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
     */
    List<TreeNode> getSubsByFilters();

    /**
     * 该节点被移除，如果你不想真正移除该节点，可以试试用子节点过滤器
     *
     * @see #andPredicate(Predicate)
     */
    void beMoved();

    Object getValue();

    void setValue(Object value);

    /**
     * 最小值是0
     */
    int getLevel();

    List<TreeNode> getAll();

    List<TreeNode> getAll(SortMethod sortMethod);

    /**
     * 该方法默认升序排序
     *
     * @return 以升序排序的TreeNode集合
     */
    List<List<TreeNode>> getAllForLevel();

    List<List<TreeNode>> getAllForLevel(SortMethod sortMethod);

    List<TreeNode> getBrothers();

    void foreach(NodeCaller nodeCaller);

    String getDetail();


}
