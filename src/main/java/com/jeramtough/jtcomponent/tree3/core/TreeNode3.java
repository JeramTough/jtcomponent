package com.jeramtough.jtcomponent.tree3.core;

import com.jeramtough.jtcomponent.tree3.adapter.ChildrenLoader3;
import com.jeramtough.jtcomponent.tree3.filter.TreeNode3Filter;
import com.jeramtough.jtcomponent.tree3.sort.TreeNode3SortMethod;

import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;

/**
 * <pre>
 *     树节点。相比 tree2 的 TreeNode2，核心差异：
 *
 *     1. clone() 语义修正为「深拷贝子树结构」，不再拍平。
 *     2. 支持懒加载：通过 ChildrenLoader3 在需要时才加载下一级子节点。
 *     3. 提供 searchDescendants 按条件在后代中搜索。
 *
 * Created on 2025/7/10 下午4:07
 * by @author WeiBoWen
 * </pre>
 */
public interface TreeNode3<T> extends Cloneable, Serializable {

    /**
     * @return 节点唯一标识（同层内不可重复）
     */
    String getKey();

    void setKey(String key);

    /**
     * @return 节点的业务编码，用于按 code 查找，可为 null
     */
    String getCode();

    /**
     * @return 节点承载的业务对象
     */
    T getValue();

    void setValue(T value);

    /**
     * 排序顺序，数值越小越靠前。
     */
    Integer getOrder();

    void setOrder(int order);

    /**
     * @return 排序权重（含层级），值 = level * 100 + order，用于全局排序
     */
    Integer getOrderWithLevel();

    void setOrderWithLevel(Integer orderWithLevel);

    /**
     * 层级，根节点为 0。
     */
    Integer getLevel();

    void setLevel(int level);

    /**
     * @return 父节点的 key，根节点为 null
     */
    String getParentKey();

    void setParentKey(String parentKey);

    /**
     * 从根节点到当前节点的 key 路径。
     */
    List<String> getPaths();

    void setPaths(List<String> paths);

    /**
     * 深拷贝当前节点（含子树结构）。
     */
    TreeNode3<T> clone();

    /**
     * 直接子节点。
     * 懒加载模式下，若子节点尚未加载，会触发 ChildrenLoader3 加载并缓存。
     */
    List<TreeNode3<T>> getSubs();

    /**
     * 仅过滤直接子节点，不递归过滤孙节点。
     */
    List<TreeNode3<T>> getSubs(List<TreeNode3Filter> filterList);

    boolean hasSubs();

    /**
     * 返回除自身以外的全部后代节点（BFS 遍历，懒加载模式下会逐层触发加载）。
     */
    List<TreeNode3<T>> getAllSubs();

    /**
     * 添加子节点（升序），自动设置 level / paths / orderWithLevel 并注册到树索引。
     */
    TreeNode3<T> addSubs(TreeNode3<T>... treeNodes);

    /**
     * 添加子节点，按指定排序方式。
     */
    TreeNode3<T> addSubs(TreeNode3SortMethod sortMethod, TreeNode3<T>... treeNodes);

    //////////////////////////////////////////
    // 懒加载能力
    //////////////////////////////////////////

    /**
     * 直接子节点是否已经加载（懒加载模式下，未加载前为 false）。
     */
    boolean isChildrenLoaded();

    void setChildrenLoader(ChildrenLoader3<T> loader);

    /**
     * @return 当前节点的懒加载器，全量加载模式下为 null
     */
    ChildrenLoader3<T> getChildrenLoader();

    /**
     * 触发加载下一级子节点（幂等：已加载则直接返回）。
     *
     * @return 直接子节点
     */
    List<TreeNode3<T>> loadChildren();

    //////////////////////////////////////////
    // 搜索能力
    //////////////////////////////////////////

    /**
     * 在后代节点中按条件搜索。
     */
    List<TreeNode3<T>> searchDescendants(Predicate<TreeNode3<T>> predicate);
}
