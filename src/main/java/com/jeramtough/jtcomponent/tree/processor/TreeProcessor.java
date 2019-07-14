package com.jeramtough.jtcomponent.tree.processor;

import com.jeramtough.jtcomponent.tree.adapter.TreeNodeAdapter;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;

/**
 * Created on 2019/7/12 8:24
 * by @author WeiBoWen
 */
public interface TreeProcessor {

    TreeNode processing(boolean root, TreeNodeAdapter baseTreeNodeAdapter);

}
