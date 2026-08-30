package com.jeramtough.jtcomponent.tree3.rebuilder;

import com.jeramtough.jtcomponent.tree3.builder.Tree3Builder;
import com.jeramtough.jtcomponent.tree3.core.Tree3;
import com.jeramtough.jtcomponent.tree3.core.TreeNode3;
import com.jeramtough.jtcomponent.tree3.filter.TreeNode3Filter;
import com.jeramtough.jtcomponent.tree3.sort.TreeNode3SortMethod;
import com.jeramtough.jtcomponent.tree3.util.TreeNode3Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     过滤重构器：先对全部节点做过滤，再由保留下来的节点重建新树。
 *
 * Created on 2025/7/17 下午10:34
 * by @author WeiBoWen
 * </pre>
 */
public class FilterTree3Rebuilder<T> extends BaseTree3Rebuilder<T> implements Tree3Rebuilder<T> {

    private int noParentStrategy = Tree3Builder.NO_PARENT_STRATEGY_NODE;
    private TreeNode3SortMethod sortMethod = TreeNode3SortMethod.ASCENDING;
    private List<TreeNode3Filter> filterList = new ArrayList<>();
    private boolean isShowDetailLog = false;

    public FilterTree3Rebuilder(Tree3<T> tree) {
        super(tree);
    }

    /**
     * 设置是否打印每个过滤器的耗时日志，默认 false。
     *
     * @param isShowDetailLog 是否打印详细日志
     * @return 当前实例
     */
    public FilterTree3Rebuilder<T> setIsShowDetailLog(boolean isShowDetailLog) {
        this.isShowDetailLog = isShowDetailLog;
        return this;
    }

    /**
     * 找不到父节点时的处理策略，默认 {@link Tree3Builder#NO_PARENT_STRATEGY_NODE}（丢弃）。
     *
     * @param noParentStrategy 策略值
     * @return 当前实例
     */
    public FilterTree3Rebuilder<T> setNoParentStrategy(int noParentStrategy) {
        this.noParentStrategy = noParentStrategy;
        return this;
    }

    /**
     * 设置排序方式，默认 ASCENDING。
     *
     * @param sortMethod 排序方式
     * @return 当前实例
     */
    public FilterTree3Rebuilder<T> setSortMethod(TreeNode3SortMethod sortMethod) {
        this.sortMethod = sortMethod;
        return this;
    }

    /**
     * 设置过滤器列表，多个过滤器之间为 AND 语义。
     *
     * @param filterList 过滤器列表
     * @return 当前实例
     */
    public FilterTree3Rebuilder<T> setFilterList(List<TreeNode3Filter> filterList) {
        this.filterList = filterList;
        return this;
    }

    @Override
    public Tree3<T> rebuild() {
        List<TreeNode3<T>> nodes =
                new ArrayList<>(getTree().getAllIdKeyTreeNodeMap().values());
        List<TreeNode3<T>> filtered = TreeNode3Utils.doFilters(filterList, nodes, isShowDetailLog);
        return rebuildByEveryOneTreeNodeList(filtered, noParentStrategy, sortMethod);
    }
}
