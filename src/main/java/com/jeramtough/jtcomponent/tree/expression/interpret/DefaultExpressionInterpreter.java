package com.jeramtough.jtcomponent.tree.expression.interpret;

import com.jeramtough.jtcomponent.tree.expression.core.OrExpression;
import com.jeramtough.jtcomponent.tree.expression.core.TreeExpression;
import com.jeramtough.jtcomponent.tree.expression.core.ValueExpression;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 * Created on 2021/8/22 上午2:45
 * by @author WeiBoWen
 * </pre>
 */
public class DefaultExpressionInterpreter implements ExpressionInterpreter {
    @Override
    public TreeExpression interpret(String expression) {
        Objects.requireNonNull(expression);

        List<ValueExpression> valueExpressionList = new ArrayList<>();

        String[] levels = expression.split(",");
        for (int i = 0; i < levels.length; i++) {
            //这里要加1，因为0层是root节点
            int level = i+1;
            String exp = levels[i];
            String[] values = exp.split("\\|\\|");

            for (String value : values) {
                ValueExpression valueExpression = new ValueExpression(level, value);
                valueExpressionList.add(valueExpression);
            }
        }

        OrExpression orExpression = new OrExpression(valueExpressionList);
        return orExpression;
    }
}
