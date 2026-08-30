package com.jeramtough.jtcomponent.tree3.builder;

import com.jeramtough.jtcomponent.tree3.adapter.ChildrenLoader3;
import com.jeramtough.jtcomponent.tree3.adapter.OneTreeNode3Adapter;
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
 *     懒加载树构建器：只构建根节点，子节点通过 ChildrenLoader3 在首次访问时按需加载。
 *
 *     典型用法（栏目树，选中节点才加载下一级）：
 *     <code>
 *         Tree3&lt;Channel&gt; tree = new LazyTree3Builder&lt;Channel&gt;()
 *                 .setRootAdapterList(rootAdapters)
 *                 .setChildrenLoader(parent -&gt; channelService.listByParentId(parent.getKey()))
 *                 .build();
 *     </code>
 *
 * Created on 2026/8/30
 * by @author WeiBoWen
 * </pre>
 */
public class LazyTree3Builder<T> extends BaseTree3Builder<T> implements Tree3Builder<T> {

    private List<OneTreeNode3Adapter<T>> rootAdapterList;
    private ChildrenLoader3<T> childrenLoader;
    private TreeNode3SortMethod sortMethod = TreeNode3SortMethod.ASCENDING;

    /**
     * 设置根节点适配器列表（只需提供根节点数据）。
     */
    public LazyTree3Builder<T> setRootAdapterList(List<OneTreeNode3Adapter<T>> rootAdapterList) {
        this.rootAdapterList = rootAdapterList;
        return this;
    }

    /**
     * 设置子节点懒加载器：首次访问节点的子节点时触发加载。
     */
    public LazyTree3Builder<T> setChildrenLoader(ChildrenLoader3<T> childrenLoader) {
        this.childrenLoader = childrenLoader;
        return this;
    }

    /**
     * 设置排序方式，默认 ASCENDING。
     */
    public LazyTree3Builder<T> setSortMethod(TreeNode3SortMethod sortMethod) {
        this.sortMethod = sortMethod;
        return this;
    }

    @Override
    public Tree3<T> build(boolean isShowDetailLog) {
        long start = System.currentTimeMillis();
        if (rootAdapterList == null) {
            throw new NullPointerException("rootAdapterList is null");
        }
        if (childrenLoader == null) {
            throw new NullPointerException("childrenLoader is null");
        }

        DefaultTree3<T> tree = new DefaultTree3<>();
        tree.setSortMethod(sortMethod);

        List<TreeNode3<T>> roots = new ArrayList<>(rootAdapterList.size());
        for (OneTreeNode3Adapter<T> adapter : rootAdapterList) {
            DefaultTreeNode3<T> root =
                    new DefaultTreeNode3<>(adapter.getKey(), adapter.getValue());
            root.setCode(adapter.getCode());
            root.setOrder(adapter.getOrder());
            root.setOrderWithLevel(root.getOrder() == null ? 0 : root.getOrder());
            root.setChildrenLoader(childrenLoader);
            tree.put(root);
            roots.add(root);
        }

        roots.sort(new TreeNode3Comparator(sortMethod));
        tree.setRootTreeNodeList(roots);

        if (isShowDetailLog) {
            System.out.println("LazyTree3Builder 构建完成（仅根节点），根节点数=" + roots.size()
                    + "，耗时=" + (System.currentTimeMillis() - start) + "ms");
        }
        return tree;
    }
}
