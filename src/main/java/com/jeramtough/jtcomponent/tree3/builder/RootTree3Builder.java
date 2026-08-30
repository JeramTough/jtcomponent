package com.jeramtough.jtcomponent.tree3.builder;

import com.jeramtough.jtcomponent.tree3.adapter.RootTreeNode3Adapter;
import com.jeramtough.jtcomponent.tree3.core.DefaultTree3;
import com.jeramtough.jtcomponent.tree3.core.DefaultTreeNode3;
import com.jeramtough.jtcomponent.tree3.core.Tree3;
import com.jeramtough.jtcomponent.tree3.core.TreeNode3;
import com.jeramtough.jtcomponent.tree3.sort.TreeNode3Comparator;
import com.jeramtough.jtcomponent.tree3.sort.TreeNode3SortMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     基于「递归根节点适配器」构建整棵树。
 *
 * Created on 2025/7/17 下午4:29
 * by @author WeiBoWen
 * </pre>
 */
public class RootTree3Builder<T> extends BaseTree3Builder<T> implements Tree3Builder<T> {

    private boolean isRoot;
    private List<RootTreeNode3Adapter<T>> rootAdapterList;
    private TreeNode3SortMethod sortMethod = TreeNode3SortMethod.ASCENDING;

    /**
     * 是否直接以传入的 adapterList 作为根节点（true = 直接用，false = 自动向上追溯真正的根）。
     *
     * @param isRoot 是否直接作为根节点
     * @return 当前实例
     */
    public RootTree3Builder<T> setRoot(boolean isRoot) {
        this.isRoot = isRoot;
        return this;
    }

    /**
     * 设置递归根节点适配器列表。
     *
     * @param rootAdapterList 根节点适配器列表
     * @return 当前实例
     */
    public RootTree3Builder<T> setRootAdapterList(List<RootTreeNode3Adapter<T>> rootAdapterList) {
        this.rootAdapterList = rootAdapterList;
        return this;
    }

    /**
     * 设置排序方式，默认 ASCENDING。
     *
     * @param sortMethod 排序方式
     * @return 当前实例
     */
    public RootTree3Builder<T> setSortMethod(TreeNode3SortMethod sortMethod) {
        this.sortMethod = sortMethod;
        return this;
    }

    @Override
    public Tree3<T> build(boolean isShowDetailLog) {
        long start = System.currentTimeMillis();
        if (rootAdapterList == null) {
            throw new NullPointerException("rootAdapterList is null");
        }
        Tree3<T> tree = processing(isRoot, sortMethod, rootAdapterList);
        if (isShowDetailLog) {
            System.out.println("RootTree3Builder 构建完成，节点数="
                    + tree.getAllIdKeyTreeNodeMap().size()
                    + "，耗时=" + (System.currentTimeMillis() - start) + "ms");
        }
        return tree;
    }

    private Tree3<T> processing(boolean isRoot, TreeNode3SortMethod sortMethod,
                                List<RootTreeNode3Adapter<T>> adapterList) {
        DefaultTree3<T> tree = new DefaultTree3<>();
        tree.setSortMethod(sortMethod);

        List<RootTreeNode3Adapter<T>> rootAdapters =
                isRoot ? adapterList : resolveRootAdapters(adapterList);

        List<TreeNode3<T>> roots = new ArrayList<>();
        for (RootTreeNode3Adapter<T> rootAdapter : rootAdapters) {
            DefaultTreeNode3<T> root =
                    new DefaultTreeNode3<>(rootAdapter.getKey(), rootAdapter.getValue());
            roots.add(root);
            tree.put(root);
            buildChildren(root, rootAdapter, tree, sortMethod);
        }

        roots.sort(new TreeNode3Comparator(sortMethod));
        tree.setRootTreeNodeList(roots);
        return tree;
    }

    private void buildChildren(DefaultTreeNode3<T> parentNode,
                               RootTreeNode3Adapter<T> parentAdapter,
                               DefaultTree3<T> tree,
                               TreeNode3SortMethod sortMethod) {
        if (!parentAdapter.hasSubs()) {
            return;
        }
        List<T> subs = parentAdapter.getSubs();
        for (T subValue : subs) {
            RootTreeNode3Adapter<T> subAdapter = parentAdapter.getNewInstance(subValue);
            DefaultTreeNode3<T> child =
                    new DefaultTreeNode3<>(subAdapter.getKey(), subAdapter.getValue());
            parentNode.addSubs(sortMethod, child);
            buildChildren(child, subAdapter, tree, sortMethod);
        }
    }

    private List<RootTreeNode3Adapter<T>> resolveRootAdapters(
            List<RootTreeNode3Adapter<T>> adapterList) {
        List<RootTreeNode3Adapter<T>> current = new ArrayList<>(adapterList);
        while (true) {
            List<RootTreeNode3Adapter<T>> upper = new ArrayList<>();
            for (RootTreeNode3Adapter<T> adapter : current) {
                T parentValue = adapter.getParent();
                if (parentValue != null) {
                    RootTreeNode3Adapter<T> parentAdapter = adapter.getNewInstance(parentValue);
                    if (parentAdapter != null) {
                        upper.add(parentAdapter);
                    }
                }
            }
            if (upper.isEmpty()) {
                break;
            }
            current = upper;
        }
        return current;
    }
}
