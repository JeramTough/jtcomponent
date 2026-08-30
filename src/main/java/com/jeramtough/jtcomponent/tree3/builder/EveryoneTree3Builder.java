package com.jeramtough.jtcomponent.tree3.builder;

import com.jeramtough.jtcomponent.tree3.adapter.OneTreeNode3Adapter;
import com.jeramtough.jtcomponent.tree3.core.DefaultTree3;
import com.jeramtough.jtcomponent.tree3.core.DefaultTreeNode3;
import com.jeramtough.jtcomponent.tree3.core.Tree3;
import com.jeramtough.jtcomponent.tree3.core.TreeNode3;
import com.jeramtough.jtcomponent.tree3.sort.TreeNode3Comparator;
import com.jeramtough.jtcomponent.tree3.sort.TreeNode3SortMethod;
import com.jeramtough.jtcomponent.utils.JtStrUtil;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     基于「扁平数据源（每条记录自带 parentKey）」构建整棵树。
 *
 *     相比 tree2 的 EveryoneTree2Builder：
 *     1. 先去重创建节点并注册，再统一建立父子关系；
 *     2. 通过 BFS 一次性归一化 level / paths / orderWithLevel，避免重复遍历。
 *
 * Created on 2025/7/17 下午4:29
 * by @author WeiBoWen
 * </pre>
 */
public class EveryoneTree3Builder<T> extends BaseTree3Builder<T> implements Tree3Builder<T> {

    private int noParentStrategy = Tree3Builder.NO_PARENT_STRATEGY_NODE;
    private List<OneTreeNode3Adapter<T>> adapterList;
    private TreeNode3SortMethod sortMethod = TreeNode3SortMethod.ASCENDING;

    /**
     * 找不到父节点时的处理策略，默认 {@link Tree3Builder#NO_PARENT_STRATEGY_NODE}（丢弃）。
     *
     * @param noParentStrategy 策略值
     * @return 当前实例
     */
    public EveryoneTree3Builder<T> setNoParentStrategy(int noParentStrategy) {
        this.noParentStrategy = noParentStrategy;
        return this;
    }

    /**
     * 设置扁平数据源（每条记录通过 OneTreeNode3Adapter 提供 key / parentKey 等信息）。
     *
     * @param adapterList 适配器列表
     * @return 当前实例
     */
    public EveryoneTree3Builder<T> setAdapterList(List<OneTreeNode3Adapter<T>> adapterList) {
        this.adapterList = adapterList;
        return this;
    }

    /**
     * 设置排序方式，默认 ASCENDING。
     *
     * @param sortMethod 排序方式
     * @return 当前实例
     */
    public EveryoneTree3Builder<T> setSortMethod(TreeNode3SortMethod sortMethod) {
        this.sortMethod = sortMethod;
        return this;
    }

    @Override
    public Tree3<T> build(boolean isShowDetailLog) {
        long start = System.currentTimeMillis();
        if (adapterList == null) {
            throw new NullPointerException("adapterList is null");
        }
        Tree3<T> tree = processing(adapterList, noParentStrategy, sortMethod);
        if (isShowDetailLog) {
            System.out.println("EveryoneTree3Builder 构建完成，节点数="
                    + tree.getAllIdKeyTreeNodeMap().size()
                    + "，耗时=" + (System.currentTimeMillis() - start) + "ms");
        }
        return tree;
    }

    private Tree3<T> processing(List<OneTreeNode3Adapter<T>> adapters, int noParentStrategy,
                                TreeNode3SortMethod sortMethod) {
        DefaultTree3<T> tree = new DefaultTree3<>();
        tree.setSortMethod(sortMethod);

        // 1. 创建全部节点并注册索引
        Map<String, DefaultTreeNode3<T>> nodeMap = new HashMap<>(adapters.size());
        for (OneTreeNode3Adapter<T> adapter : adapters) {
            DefaultTreeNode3<T> node =
                    new DefaultTreeNode3<>(adapter.getKey(), adapter.getValue());
            node.setCode(adapter.getCode());
            node.setOrder(adapter.getOrder());
            node.setParentKey(adapter.getParentKey());
            nodeMap.put(adapter.getKey(), node);
            tree.put(node);
        }

        // 2. 建立父子关系
        List<TreeNode3<T>> roots = new ArrayList<>();
        for (OneTreeNode3Adapter<T> adapter : adapters) {
            DefaultTreeNode3<T> node = nodeMap.get(adapter.getKey());
            String parentKey = adapter.getParentKey();
            if (JtStrUtil.isEmpty(parentKey)) {
                roots.add(node);
            }
            else {
                TreeNode3<T> parent = tree.getTreeNodeByIdKey(parentKey);
                if (parent == null) {
                    if (noParentStrategy == Tree3Builder.NO_PARENT_STRATEGY_INTO_ROOT_NODE) {
                        roots.add(node);
                    }
                }
                else {
                    ((DefaultTreeNode3<T>) parent).addSubRaw(node);
                }
            }
        }

        // 3. BFS 归一化 level / paths / orderWithLevel 并排序
        normalize(roots, sortMethod);

        tree.setRootTreeNodeList(roots);
        return tree;
    }

    private void normalize(List<TreeNode3<T>> roots, TreeNode3SortMethod sortMethod) {
        Deque<TreeNode3<T>> queue = new ArrayDeque<>();
        for (TreeNode3<T> root : roots) {
            root.setLevel(0);
            List<String> rootPaths = new ArrayList<>();
            rootPaths.add(root.getKey());
            root.setPaths(rootPaths);
            root.setOrderWithLevel(root.getOrder() == null ? 0 : root.getOrder());
            queue.add(root);
        }

        while (!queue.isEmpty()) {
            TreeNode3<T> node = queue.poll();
            for (TreeNode3<T> child : node.getSubs()) {
                child.setLevel(node.getLevel() + 1);
                List<String> childPaths = new ArrayList<>(node.getPaths());
                childPaths.add(child.getKey());
                child.setPaths(childPaths);
                child.setOrderWithLevel(
                        child.getLevel() * DefaultTreeNode3.ORDER_LEVEL_BASE
                                + (child.getOrder() == null ? 0 : child.getOrder()));
                queue.add(child);
            }
            if (node instanceof DefaultTreeNode3) {
                ((DefaultTreeNode3<T>) node).sortSubs(sortMethod);
            }
        }

        roots.sort(new TreeNode3Comparator(sortMethod));
    }
}
