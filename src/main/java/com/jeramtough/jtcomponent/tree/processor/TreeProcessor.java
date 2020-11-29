package com.jeramtough.jtcomponent.tree.processor;

import com.jeramtough.jtcomponent.tree.adapter.OneTreeNodeAdapter;
import com.jeramtough.jtcomponent.tree.adapter.RootTreeNodeAdapter;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;

import java.util.List;

/**
 * Created on 2019/7/12 8:24
 * by @author WeiBoWen
 */
public interface TreeProcessor {

    /**
     * @param root                    是否作为根节点处理，否者的话将会向上寻找父节点,
     *                                直到父节点为null为止才开始处理
     * @param baseRootTreeNodeAdapter {@link RootTreeNodeAdapter}
     * @return {@link TreeNode}
     */
    TreeNode processing(boolean root, RootTreeNodeAdapter baseRootTreeNodeAdapter);

    /**
     * 处理List(OneTreeNodeAdapter一个TreeNode的适配器)集合成为TreeNode
     *
     * @param oneTreeNodeAdapterList {@link OneTreeNodeAdapter}
     * @return {@link TreeNode}
     * @param <T> t
     */
    <T> TreeNode processing(List<? extends OneTreeNodeAdapter<T>> oneTreeNodeAdapterList);

}
