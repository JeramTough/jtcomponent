package test.tree;

import com.jeramtough.jtcomponent.tree.structure.DefaultTreeNode;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;
import com.jeramtough.jtlog.facade.L;
import org.junit.jupiter.api.Test;

/**
 * <pre>
 * Created on 2022/1/20 上午10:59
 * by @author WeiBoWen
 * </pre>
 */
public class TreeFiltersTest {

    @Test
    public void test1() {
        TreeNode treeNode = new DefaultTreeNode(1);
        TreeNode treeNode2 = new DefaultTreeNode(2);
        TreeNode treeNode3 = new DefaultTreeNode(3);
        TreeNode treeNode4 = new DefaultTreeNode(4);
        TreeNode treeNode5 = new DefaultTreeNode(5);
        TreeNode treeNode6 = new DefaultTreeNode(6);
        TreeNode treeNode7 = new DefaultTreeNode(7);

        treeNode.addSubs(treeNode2, treeNode3, treeNode4);
        treeNode4.addSub(treeNode5);
        treeNode5.addSubs(treeNode6, treeNode7);

        //过滤掉不等于2，也不等于3\6的节点
        treeNode
                .andFilter
                        (treeNode1 -> ((int) treeNode1.getValue()) != 3)
                .andFilter
                        (treeNode1 -> ((int) treeNode1.getValue()) != 6)
                .andFilter
                        (treeNode12 -> ((int) treeNode12.getValue()) != 2);

        treeNode.doFilters();
        L.debug(treeNode.getDetail());
    }
}
