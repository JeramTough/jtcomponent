package com.jeramtough.jtcomponent.tree3.rebuilder;

import com.jeramtough.jtcomponent.tree3.adapter.OneTreeNode3Adapter;
import com.jeramtough.jtcomponent.tree3.adapter.RebuildOneTreeNode3Adapter;
import com.jeramtough.jtcomponent.tree3.builder.EveryoneTree3Builder;
import com.jeramtough.jtcomponent.tree3.core.Tree3;
import com.jeramtough.jtcomponent.tree3.core.TreeNode3;
import com.jeramtough.jtcomponent.tree3.sort.TreeNode3SortMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     重构器基类：提供「由扁平节点集合重建新树」的模板方法。
 *
 * Created on 2025/7/17 下午10:35
 * by @author WeiBoWen
 * </pre>
 */
public abstract class BaseTree3Rebuilder<T> implements Tree3Rebuilder<T> {

    private final Tree3<T> tree;

    protected BaseTree3Rebuilder(Tree3<T> tree) {
        this.tree = tree;
    }

    protected Tree3<T> getTree() {
        return this.tree;
    }

    protected Tree3<T> rebuildByEveryOneTreeNodeList(List<TreeNode3<T>> nodes,
                                                     int noParentStrategy,
                                                     TreeNode3SortMethod sortMethod) {
        List<OneTreeNode3Adapter<T>> adapters = new ArrayList<>(nodes.size());
        for (TreeNode3<T> node : nodes) {
            RebuildOneTreeNode3Adapter<T> adapter = new RebuildOneTreeNode3Adapter<>();
            adapter.setSource(node);
            adapters.add(adapter);
        }
        return new EveryoneTree3Builder<T>()
                .setNoParentStrategy(noParentStrategy)
                .setAdapterList(adapters)
                .setSortMethod(sortMethod)
                .build();
    }
}
