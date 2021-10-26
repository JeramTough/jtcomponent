package com.jeramtough.jtcomponent.tree.expression;

import com.jeramtough.jtcomponent.tree.base.SortMethod;
import com.jeramtough.jtcomponent.tree.expression.core.ValueExpression;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;
import com.jeramtough.jtcomponent.tree.util.TreeNodeUtils;
import com.jeramtough.jtcomponent.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <pre>
 * Created on 2021/8/22 上午1:27
 * by @author WeiBoWen
 * </pre>
 */
public class TreeContext {

    private final List<List<TreeNode>> allForLevel;

    public TreeContext(TreeNode rootTreeNode) {
        allForLevel = TreeNodeUtils.getAllForLevel(rootTreeNode, SortMethod.ASCENDING);
    }

    public List<TreeNode> lookup(ValueExpression valueExpression) {
        if (valueExpression.getLevel() < allForLevel.size()) {
            List<TreeNode> treeNodeList = allForLevel.get(valueExpression.getLevel());
            treeNodeList = treeNodeList
                    .parallelStream()
                    .filter(treeNode -> {

                        //真实的值，根据实际情况
                        String value = treeNode.getExpression();

                        //用户填写的表达式
                        String expression = valueExpression.getValue();
                        Objects.requireNonNull(expression);

                        if (expression.equalsIgnoreCase(value)) {
                            return true;
                        }
                        //如果表达式是empty，真实值是“”
                        else if (expression.equalsIgnoreCase(
                                ValueExpression.EMPTY) && StringUtil.isEmpty(
                                value)) {
                            return true;
                        }
                        //如果表达式是empty，真实值是“”
                        else if (expression.equalsIgnoreCase(
                                ValueExpression.NULL) && value == null) {
                            return true;
                        }
                        else {
                            return false;
                        }
                    })
                    .collect(Collectors.toList());
            return treeNodeList;
        }
        else {
            return new ArrayList<>();
        }
    }
}
