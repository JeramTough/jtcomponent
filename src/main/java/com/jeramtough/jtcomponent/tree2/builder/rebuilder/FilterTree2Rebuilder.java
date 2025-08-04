package com.jeramtough.jtcomponent.tree2.builder.rebuilder;

import com.jeramtough.jtcomponent.tree2.adpater.OneTreeNode2Adapter;
import com.jeramtough.jtcomponent.tree2.adpater.RebuildOneTreeNode2Adapter;
import com.jeramtough.jtcomponent.tree2.builder.Tree2Builder;
import com.jeramtough.jtcomponent.tree2.core.Tree2;
import com.jeramtough.jtcomponent.tree2.core.TreeNode2;
import com.jeramtough.jtcomponent.tree2.filter.TreeNode2Filter;
import com.jeramtough.jtcomponent.tree2.sort.TreeNode2SortMethod;
import com.jeramtough.jtcomponent.tree2.util.TreeNode2Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * Created on 2025/7/17 下午10:34
 * by @author WeiBoWen
 * </pre>
 */
public class FilterTree2Rebuilder<T> extends BaseTree2Rebuilder<T>
        implements Tree2Rebuilder<T> {

    private Integer noParentStrategy;
    private TreeNode2SortMethod sortMethod;
    private List<TreeNode2Filter> filterList = new ArrayList<>();

    public FilterTree2Rebuilder(Tree2<T> tree2) {
        super(tree2);
    }

    public List<TreeNode2Filter> getFilterList() {
        return filterList;
    }

    public FilterTree2Rebuilder<T> setFilterList(List<TreeNode2Filter> filterList) {
        this.filterList = filterList;
        return this;
    }

    public FilterTree2Rebuilder<T> setNoParentStrategy(Integer noParentStrategy) {
        this.noParentStrategy = noParentStrategy;
        return this;
    }

    public FilterTree2Rebuilder<T> setSortMethod(TreeNode2SortMethod sortMethod) {
        this.sortMethod = sortMethod;
        return this;
    }

    @Override
    public Tree2<T> rebuild() {

        //过滤器默认策略为没有父节点直接抛弃
        if (noParentStrategy == null) {
            noParentStrategy = Tree2Builder.NO_PARENT_STRATEGY_NODE;
        }
        if (sortMethod == null) {
            sortMethod = TreeNode2SortMethod.ASCENDING;
        }

        System.out.println(
                "进行Tree2重构,共" + filterList.size() + "个过滤器，数据源共" + getTree2().getAllTreeNodeMap().size() + "个数据");


        List<TreeNode2<T>> treeNode2List = new ArrayList<>(
                getTree2().getAllTreeNodeMap().values());


        //进行过滤
       List<TreeNode2<T>> newTreeNode2List = TreeNode2Utils.doFilters(filterList,
               treeNode2List);

        Tree2<T> tree2 = super.rebuildByEveryOneTreeNodeList(newTreeNode2List, noParentStrategy,
                sortMethod);
        return tree2;
    }
}
