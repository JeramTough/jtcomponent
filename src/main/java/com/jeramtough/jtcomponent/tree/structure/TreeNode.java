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

    List<TreeNode> getSubs();

    boolean hasSubs();

    TreeNode getParent();

    void setParent(TreeNode parentTreeNode);

    TreeNode addSub(TreeNode treeNode);

    void addSubs(TreeNode... treeNodes);

    /**
     * 添加子节点过滤器
     *
     * @see #getSubsWithFilters()
     */
    void andPredicate(Predicate<TreeNode> filter);

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
    List<TreeNode> getSubsWithFilters();

    /**
     * 该节点被移除，如果你不想真正移除该节点，可以试试用子节点过滤器
     *
     * @see #andPredicate(Predicate)
     */
    void beMoved();

    Object getValue();

    void setValue(Object value);

    int getLevel();

    void setLevel(int level);

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
