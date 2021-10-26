package com.jeramtough.jtcomponent.tree.expression.core;

import com.jeramtough.jtcomponent.tree.expression.TreeContext;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;

import java.util.List;

/**
 * <pre>
 * Created on 2021/8/22 上午1:27
 * by @author WeiBoWen
 * </pre>
 */
public interface TreeExpression {

    List<TreeNode> interpret(TreeContext treeContext);

    @Override
    String toString();

}
