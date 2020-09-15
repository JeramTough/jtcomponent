package test.tree;

import com.jeramtough.jtcomponent.tree.base.SortMethod;
import com.jeramtough.jtcomponent.tree.structure.DefaultTreeNode;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;
import com.jeramtough.jtcomponent.tree.structure.TreeStructure;
import com.jeramtough.jtcomponent.tree.util.TreeNodeUtils;
import com.jeramtough.jtlog.facade.L;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created on 2019-08-08 12:31
 * by @author JeramTough
 */
public class TreeNodeTest {

    @Test
    public void filterTest() {
        TreeNode treeNode = new DefaultTreeNode(1);
        TreeNode treeNode2 = new DefaultTreeNode(2);
        TreeNode treeNode3 = new DefaultTreeNode(3);
        TreeNode treeNode4 = new DefaultTreeNode(4);

        treeNode.addSubs(treeNode2, treeNode3, treeNode4);

        treeNode.andPredicate(new Predicate<TreeNode>() {
            @Override
            public boolean test(TreeNode treeNode) {
                return ((int) treeNode.getValue()) != 3;
            }
        }).andPredicate(new Predicate<TreeNode>() {
            @Override
            public boolean test(TreeNode treeNode) {
                return ((int) treeNode.getValue()) != 2;
            }
        });

        List<TreeNode> treeNodeList = treeNode.getSubsByFilters();
    }

    @Test
    public void treeStructureTest() {
        TreeNode treeNode = new DefaultTreeNode(1);
        TreeNode treeNode2 = new DefaultTreeNode(2);
        TreeNode treeNode3 = new DefaultTreeNode(3);
        TreeNode treeNode4 = new DefaultTreeNode(4);
        TreeNode treeNode5 = new DefaultTreeNode(5);
        TreeNode treeNode6 = new DefaultTreeNode(6);

        treeNode.addSubs(treeNode2, treeNode3, treeNode4);
        treeNode5.addSub(treeNode6);
        treeNode4.addSub(treeNode5);

        TreeStructure treeStructure = TreeNodeUtils.toTreeStructure(treeNode);
        L.debug(treeStructure.getLevel());
    }

    @Test
    public void treeStructureForLevelTest() {
        TreeNode treeNode = new DefaultTreeNode(1);
        TreeNode treeNode2 = new DefaultTreeNode(2);
        TreeNode treeNode3 = new DefaultTreeNode(3);
        TreeNode treeNode4 = new DefaultTreeNode(4);
        TreeNode treeNode5 = new DefaultTreeNode(5);
        TreeNode treeNode6 = new DefaultTreeNode(6);

        treeNode.addSubs(treeNode2, treeNode3, treeNode4);
        treeNode5.addSub(treeNode6);
        treeNode4.addSub(treeNode5);

        List<List<TreeNode>> treeNodesForLevel = treeNode.getAllForLevel(SortMethod.DESCENDING);
        List<List<TreeStructure>> treeStructuresForLevel = TreeNodeUtils.toTreeStructuresForLevel(
                treeNodesForLevel);
        L.debug(treeStructuresForLevel.size());
    }

    @Test
    public void treeStructureSortTest() {
        TreeNode treeNode = new DefaultTreeNode(1);
        TreeNode treeNode2 = new DefaultTreeNode(2);
        treeNode2.setOrder(3);
        TreeNode treeNode3 = new DefaultTreeNode(3);
        treeNode3.setOrder(1);
        TreeNode treeNode4 = new DefaultTreeNode(4);
        treeNode4.setOrder(7);
        TreeNode treeNode5 = new DefaultTreeNode(5);
        treeNode5.setOrder(1);
        TreeNode treeNode6 = new DefaultTreeNode(6);

        treeNode.addSubs(treeNode2, treeNode3, treeNode4);
        treeNode5.addSub(treeNode6);
        treeNode4.addSub(treeNode5);

        List<List<TreeNode>> treeNodesForLevel = treeNode.getAllForLevel(SortMethod.ASCENDING);
        L.debug(treeNodesForLevel.size());
    }
}
