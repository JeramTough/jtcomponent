package com.jeramtough.jtcomponent.tree.expression.interpret;

import com.jeramtough.jtcomponent.tree.expression.core.TreeExpression;

/**
 * <pre>
 * Created on 2021/8/22 上午2:44
 * by @author WeiBoWen
 * </pre>
 */
public interface ExpressionInterpreter {

    TreeExpression interpret(String expression);

}
