package test.tree;

import com.jeramtough.jtcomponent.tree.expression.TreeContext;
import com.jeramtough.jtcomponent.tree.expression.core.TreeExpression;
import com.jeramtough.jtcomponent.tree.expression.interpret.DefaultExpressionInterpreter;
import com.jeramtough.jtcomponent.tree.expression.interpret.ExpressionInterpreter;
import com.jeramtough.jtcomponent.tree.structure.DefaultTreeNode;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;
import com.jeramtough.jtlog.facade.L;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * <pre>
 * Created on 2021/8/22 上午2:47
 * by @author WeiBoWen
 * </pre>
 */
public class ExpressionTest {
    @Test
    public void test2() {
        TreeNode rootTreeNode = new DefaultTreeNode(0);

        TreeNode treeNode = new DefaultTreeNode(1);
        TreeNode treeNode2 = new DefaultTreeNode(2);
        TreeNode treeNode3 = new DefaultTreeNode(3);
        TreeNode treeNode4 = new DefaultTreeNode(4);
        TreeNode treeNode5 = new DefaultTreeNode(5);
        TreeNode treeNode6 = new DefaultTreeNode(6);
        TreeNode treeNode7 = new DefaultTreeNode(7);

        treeNode.setExpression("aaa");
        treeNode2.setExpression(null);
        treeNode3.setExpression("bbb");
        treeNode4.setExpression("");
        treeNode5.setExpression("ccc");
        treeNode6.setExpression("ddd");
        treeNode7.setExpression("eee");

        rootTreeNode.addSubs(treeNode, treeNode2);

        treeNode.addSubs(treeNode3);
        treeNode2.addSubs(treeNode4);
        treeNode3.addSubs(treeNode5);
        treeNode4.addSubs(treeNode6, treeNode7);

        TreeContext context = new TreeContext(rootTreeNode);

        ExpressionInterpreter interpreter = new DefaultExpressionInterpreter();
        TreeExpression treeExpression = interpreter.interpret("aaa||null,bbb||empty,ccc||ddd");

        List<TreeNode> treeNodeList = treeExpression.interpret(context);
        L.debug(treeNodeList.size());
    }

    @Test
    public void test1 (){
        TreeNode rootTreeNode = new DefaultTreeNode(0);

        TreeNode treeNode = new DefaultTreeNode(1,"1");
        TreeNode treeNode2 = new DefaultTreeNode(2,"2");
        TreeNode treeNode3 = new DefaultTreeNode(3,"3");
        TreeNode treeNode4 = new DefaultTreeNode(4,"4");
        TreeNode treeNode5 = new DefaultTreeNode(5,"5");
        TreeNode treeNode6 = new DefaultTreeNode(6,"6");
        TreeNode treeNode7 = new DefaultTreeNode(7,"7");

        treeNode.setExpression("aaa");
        treeNode2.setExpression(null);
        treeNode3.setExpression("bbb");
        treeNode4.setExpression("");
        treeNode5.setExpression("ccc");
        treeNode6.setExpression("ddd");
        treeNode7.setExpression("eee");

        rootTreeNode.addSubs(treeNode, treeNode2);

        treeNode.addSubs(treeNode3);
        treeNode2.addSubs(treeNode4);
        treeNode3.addSubs(treeNode5);
        treeNode4.addSubs(treeNode6, treeNode7);

        List<TreeNode> treeNodeList = rootTreeNode.query("aaa||null,bbb||empty,ccc||ddd");
        L.debug(treeNodeList.size());
    }

}
