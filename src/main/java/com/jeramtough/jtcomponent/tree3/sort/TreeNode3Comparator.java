package com.jeramtough.jtcomponent.tree3.sort;

import com.jeramtough.jtcomponent.tree3.core.TreeNode3;

import java.util.Comparator;

/**
 * <pre>
 *     节点比较器。相比 tree2 的 TreeNode2Comparator：
 *     不再在每次比较时 Long.parseLong(key)，改为 orderWithLevel 优先、key 自然序兜底。
 *
 * Created on 2020/9/15 13:57
 * by @author WeiBoWen
 * </pre>
 */
public class TreeNode3Comparator implements Comparator<TreeNode3<?>> {

    private final TreeNode3SortMethod sortMethod;

    public TreeNode3Comparator() {
        this(TreeNode3SortMethod.ASCENDING);
    }

    public TreeNode3Comparator(TreeNode3SortMethod sortMethod) {
        this.sortMethod = sortMethod == null ? TreeNode3SortMethod.ASCENDING : sortMethod;
    }

    @Override
    public int compare(TreeNode3<?> o1, TreeNode3<?> o2) {
        int c = Integer.compare(orderWithLevel(o1), orderWithLevel(o2));
        if (c != 0) {
            return sortMethod == TreeNode3SortMethod.DESCENDING ? -c : c;
        }

        String key1 = o1.getKey() == null ? "" : o1.getKey();
        String key2 = o2.getKey() == null ? "" : o2.getKey();
        int kc = key1.compareTo(key2);
        return sortMethod == TreeNode3SortMethod.DESCENDING ? -kc : kc;
    }

    private int orderWithLevel(TreeNode3<?> node) {
        Integer v = node.getOrderWithLevel();
        return v == null ? 0 : v;
    }
}
