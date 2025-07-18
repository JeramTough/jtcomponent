package com.jeramtough.jtcomponent.tree.structure;

import com.jeramtough.jtcomponent.tree.base.SortMethod;
import com.jeramtough.jtcomponent.tree.foreach.NodeCaller;

import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created on 2019/7/11 15:25
 * by @author WeiBoWen
 */
public interface TreeNode extends Cloneable, Serializable {

    String getKey();
    boolean isRoot();

    Object clone();

    /**
     * 返回排序顺序，0最大，1次之
     *
     * @return 默认值是0
     */
    int getOrder();

    List<String> getPaths();

    /**
     * 设置表达式，用于检索
     *
     * @param expression 表达式
     * @see #query(String)
     */
    void setExpression(String expression);

    /**
     * 返回表达式
     *
     * @return 表达式
     */
    String getExpression();

    /**
     * 语法：3234||2312,null||cccc,empty||121||323
     * 检索第一级子节点表达式为3234或者2312，二级子节点为null或者cccc。。。。
     *
     * @param expression 检索表达式
     * @return 搜索到的子节点
     */
    List<TreeNode> query(String expression);

    TreeNode findAllByKey(String key);

    void setOrder(int order);

    List<TreeNode> getSubs();

    /**
     * 得到除了本身以外的所有子节点，子节点倒序排列
     *
     * @return 子节点
     */
    List<TreeNode> getAllSubs();

    boolean hasSubs();

    TreeNode getParent();

    TreeNode addSub(TreeNode treeNode);

    TreeNode addSubs(TreeNode... treeNodes);


    /**
     * 添加子节点过滤器
     *
     * @param filter 过滤器表达式
     * @return 返回TreeNode对象可以继续过滤
     * @see #getSubsByFilters() ，得到过滤后的List节点
     */
    TreeNode andFilter(Predicate<TreeNode> filter);


    /**
     * 得到过滤后的List子节点，使用上过滤器
     *
     * @return 过滤后的集合
     * @see #andFilter(Predicate) 关于如何添加子节点过滤器
     */
    List<TreeNode> getSubsByFilters();

    /**
     * 得到过滤后的这个节点，这个节点的子节点将会被移除掉。
     * 这个节点下的子节点的子节点。。。一样会被过滤
     * <p>
     * 如果想保留原来的节点，请使用克隆后的节点进行过滤
     *
     * @see #clone()
     */
    void doFilters();

    /**
     * 该节点被移除，如果你不想真正移除该节点，可以试试用子节点过滤器
     *
     * @see #andFilter(Predicate)
     */
    void beMoved();

    /**
     * 删除该节点的所有子节点，子节点List为新new出来的;
     */
    void moveSubs();

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
     *
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
     * getValue brothers of node
     *
     * @return the list
     */
    List<TreeNode> getBrothers();

    /**
     * 回调器返回true则继续执行，返回false则终止执行
     *
     * @param nodeCaller 回调器
     */
    void foreach(NodeCaller nodeCaller);

    /**
     * getValue all node for detail
     *
     * @return the detail of string
     */
    String getDetail();


}
