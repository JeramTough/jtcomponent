package com.jeramtough.jtcomponent.tree.expression.core;

import com.jeramtough.jtcomponent.tree.expression.TreeContext;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;

import java.util.List;

/**
 * <pre>
 * Created on 2021/8/22 上午1:31
 * by @author WeiBoWen
 * </pre>
 */
public class ValueExpression implements TreeExpression {

    public static final String EMPTY="empty";
    public static final String NULL="null";

    private final int level;
    private final String value;

    public ValueExpression(int level, String value) {
        this.level = level;
        this.value = value;
    }


    @Override
    public List<TreeNode> interpret(TreeContext treeContext) {
        return treeContext.lookup(this);
    }

    public int getLevel() {
        return level;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return level+":"+value;
    }
}
