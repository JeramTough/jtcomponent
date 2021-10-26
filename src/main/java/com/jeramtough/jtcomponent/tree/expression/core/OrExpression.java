package com.jeramtough.jtcomponent.tree.expression.core;

import com.jeramtough.jtcomponent.tree.expression.TreeContext;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * Created on 2021/8/22 上午1:34
 * by @author WeiBoWen
 * </pre>
 */
public class OrExpression implements TreeExpression {

    private final List<ValueExpression> valueExpressionList;

    public OrExpression(ValueExpression... valueExpressions) {
        this.valueExpressionList = Arrays.asList(valueExpressions);
    }

    public OrExpression(List<ValueExpression> valueExpressionList) {
        this.valueExpressionList = valueExpressionList;
    }

    @Override
    public List<TreeNode> interpret(TreeContext treeContext) {
        List<TreeNode> treeNodeList = new ArrayList<>();

        valueExpressionList
                .parallelStream()
                .forEach(valueExpression -> {
                    List<TreeNode> list = treeContext.lookup(valueExpression);
                    treeNodeList.addAll(list);
                });
        return treeNodeList;
    }
}
