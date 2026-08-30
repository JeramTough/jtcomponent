package com.jeramtough.jtcomponent.tree3.rebuilder;

import com.jeramtough.jtcomponent.tree3.builder.Tree3Builder;
import com.jeramtough.jtcomponent.tree3.core.Tree3;
import com.jeramtough.jtcomponent.tree3.core.TreeNode3;
import com.jeramtough.jtcomponent.utils.JtStrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     取子树重构器：以指定节点（或其子节点）作为新树的根，重建新树。
 *
 *     subTreeNodeKey 为空时，直接以原树的根节点为根；
 *     否则以该节点 key 的「下一级子节点」作为新树的根。
 *
 * Created on 2025/7/17 下午10:34
 * by @author WeiBoWen
 * </pre>
 */
public class FromSubTree3Rebuilder<T> extends BaseTree3Rebuilder<T> implements Tree3Rebuilder<T> {

    private String subTreeNodeKey;

    public FromSubTree3Rebuilder(Tree3<T> tree) {
        super(tree);
    }

    /**
     * 设置子树根节点的 key。为空时直接以原树根节点为根。
     *
     * @param subTreeNodeKey 子树根节点的 key
     * @return 当前实例
     */
    public FromSubTree3Rebuilder<T> setSubTreeNodeKey(String subTreeNodeKey) {
        this.subTreeNodeKey = subTreeNodeKey;
        return this;
    }

    @Override
    public Tree3<T> rebuild() {
        List<TreeNode3<T>> selectedRoots;
        if (JtStrUtil.isEmpty(subTreeNodeKey)) {
            selectedRoots = new ArrayList<>(getTree().getRootTreeNodeList());
        }
        else {
            TreeNode3<T> subNode = getTree().getTreeNodeByIdKey(subTreeNodeKey);
            if (subNode == null) {
                throw new RuntimeException("未找到子节点 key: " + subTreeNodeKey);
            }
            selectedRoots = new ArrayList<>(subNode.getSubs());
        }

        // 深拷贝选中子树，避免修改原树；拷贝后根节点父节点置空
        List<TreeNode3<T>> clonedRoots = new ArrayList<>(selectedRoots.size());
        for (TreeNode3<T> node : selectedRoots) {
            TreeNode3<T> clone = node.clone();
            clone.setParentKey(null);
            clonedRoots.add(clone);
        }

        // 收集根及其全部后代
        List<TreeNode3<T>> all = new ArrayList<>();
        for (TreeNode3<T> root : clonedRoots) {
            all.add(root);
            all.addAll(root.getAllSubs());
        }

        return rebuildByEveryOneTreeNodeList(all,
                Tree3Builder.NO_PARENT_STRATEGY_NODE,
                getTree().getSortMethod());
    }
}
