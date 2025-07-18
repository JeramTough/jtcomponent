package com.jeramtough.jtcomponent.tree2.core;

import com.jeramtough.jtcomponent.tree2.filter.TreeNode2Filter;
import com.jeramtough.jtcomponent.tree2.sort.TreeNode2Comparator;
import com.jeramtough.jtcomponent.tree2.sort.TreeNode2SortMethod;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 2019/7/11 15:25
 * by @author WeiBoWen
 */
public interface TreeNode2<T> extends Cloneable, Serializable {

    String getKey();

    String getCode();

    Object clone();

    /**
     * 返回排序顺序，0最大，1次之
     *
     * @return 默认值是0
     */
    Integer getOrder();

    List<String> getPaths();

    void setOrder(int order);

    List<TreeNode2<T>> getSubs();

    /**
     * 仅仅是过滤子节点，孙节点不进行过滤
     */
    List<TreeNode2<T>> getSubs(List<TreeNode2Filter> filterList);

    String getParentKey();

    void setParentKey(String parentKey);

    /**
     * 返回附带的值对象
     *
     * @return 附带的值对象
     */
    T getValue();

    /**
     * 设置值对象
     *
     * @param value 值对象
     */
    void setValue(T value);

    /**
     * 最小值是0
     *
     * @return 层级
     */
    Integer getLevel();

    void setLevel(int level);

    void setPaths(List<String> paths);

    void setKey(String key);

    ////////////////////////////////////
    //////////////////////////////////
    ///一下方法都是操作方法/////////////
    //////////////////////////////////

    boolean hasSubs();

    /**
     * 得到除了本身以外的所有子节点，子节点倒序排列
     *
     * @return 子节点
     */
    List<TreeNode2<T>> getAllSubs();

    TreeNode2<T> addSubs(TreeNode2<T>... treeNodes);

    TreeNode2<T> addSubs(TreeNode2SortMethod treeNode2SortMethod, TreeNode2<T>... treeNodes);


}
