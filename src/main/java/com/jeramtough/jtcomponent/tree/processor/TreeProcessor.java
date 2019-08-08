package com.jeramtough.jtcomponent.tree.processor;

import com.jeramtough.jtcomponent.tree.adapter.TreeNodeAdapter;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;

/**
 * Created on 2019/7/12 8:24
 * by @author WeiBoWen
 */
public interface TreeProcessor {

    /**
     * @param root                是否作为根节点处理，否者的话将会向上寻找父节点,
     *                            直到父节点为null为止才开始处理
     * @param baseTreeNodeAdapter {@link TreeNodeAdapter}
     * @return {@link TreeNode}
     */
    TreeNode processing(boolean root, TreeNodeAdapter baseTreeNodeAdapter);

}
