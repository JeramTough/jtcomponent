package com.jeramtough.jtcomponent.tree3.rebuilder;

import com.jeramtough.jtcomponent.tree3.builder.Tree3Builder;
import com.jeramtough.jtcomponent.tree3.core.Tree3;
import com.jeramtough.jtcomponent.tree3.core.TreeNode3;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * <pre>
 *     按最大保留层级重构器：只保留 level &lt;= maxRetainSubNodeLevel 的节点。
 *
 *     相比 tree2 的 MaxRetainSubTree2Rebuilder（原实现从未向下遍历子节点，
 *     结果只保留了根节点），这里通过 BFS 逐层下钻，正确截断到指定层级。
 *
 * Created on 2025/7/17 下午10:34
 * by @author WeiBoWen
 * </pre>
 */
public class MaxRetainSubTree3Rebuilder<T> extends BaseTree3Rebuilder<T>
        implements Tree3Rebuilder<T> {

    private int maxRetainSubNodeLevel;

    public MaxRetainSubTree3Rebuilder(Tree3<T> tree) {
        super(tree);
    }

    /**
     * 设置最大保留子节点层级（从 0 开始，0 表示只保留根节点）。
     *
     * @param maxRetainSubNodeLevel 最大保留层级
     * @return 当前实例
     */
    public MaxRetainSubTree3Rebuilder<T> setMaxRetainSubNodeLevel(int maxRetainSubNodeLevel) {
        if (maxRetainSubNodeLevel < 0) {
            throw new IllegalArgumentException("maxRetainSubNodeLevel 不能小于 0");
        }
        this.maxRetainSubNodeLevel = maxRetainSubNodeLevel;
        return this;
    }

    @Override
    public Tree3<T> rebuild() {
        List<TreeNode3<T>> retained = new ArrayList<>();
        Deque<TreeNode3<T>> queue = new ArrayDeque<>();

        for (TreeNode3<T> root : getTree().getRootTreeNodeList()) {
            queue.add(root);
        }

        while (!queue.isEmpty()) {
            TreeNode3<T> node = queue.poll();
            int level = node.getLevel() == null ? 0 : node.getLevel();
            if (level > maxRetainSubNodeLevel) {
                continue;
            }
            retained.add(node);
            if (level < maxRetainSubNodeLevel) {
                for (TreeNode3<T> child : node.getSubs()) {
                    queue.add(child);
                }
            }
        }

        return rebuildByEveryOneTreeNodeList(retained,
                Tree3Builder.NO_PARENT_STRATEGY_NODE,
                getTree().getSortMethod());
    }
}
