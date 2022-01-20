package test.tree;

import com.jeramtough.jtcomponent.callback.CommonCallback;
import com.jeramtough.jtcomponent.task.bean.PreTaskResult;
import com.jeramtough.jtcomponent.tree.base.SortMethod;
import com.jeramtough.jtcomponent.tree.structure.DefaultTreeNode;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;
import com.jeramtough.jtcomponent.tree.util.TreeNodeUtils;
import com.jeramtough.jtlog.facade.L;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
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

        treeNode.andFilter(new Predicate<TreeNode>() {
            @Override
            public boolean test(TreeNode treeNode) {
                return ((int) treeNode.getValue()) != 3;
            }
        }).andFilter(new Predicate<TreeNode>() {
            @Override
            public boolean test(TreeNode treeNode) {
                return ((int) treeNode.getValue()) != 2;
            }
        });

        List<TreeNode> treeNodeList = treeNode.getSubsByFilters();
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

    @Test
    public void treeNodeMapTest() {
        TreeNode treeNode = new DefaultTreeNode(1);
        PreTaskResult preTaskResult = new PreTaskResult();
        preTaskResult.setMessage("sdfsfdsfdsfsdf");

        TreeNode treeNode7 = new DefaultTreeNode(preTaskResult);

        treeNode.addSubs(treeNode7);

        Map<String, Object> treeMap = TreeNodeUtils.toTreeMap(treeNode,
                new CommonCallback<Map<String, Object>>() {
                    @Override
                    public void callback(Map<String, Object> stringObjectMap) {
                        L.debug(stringObjectMap.size());
                    }
                });
        L.debug(treeMap.size());
    }

    @Test
    public void treeNodeMapTest2() {
        TreeNode rootTreeNode = new DefaultTreeNode();
        rootTreeNode.setValue("传到前端的bean类");

        TreeNode treeNode1 = new DefaultTreeNode("传到前端的bean类1");
        TreeNode treeNode2 = new DefaultTreeNode("传到前端的bean类2");
        TreeNode treeNode3 = new DefaultTreeNode("传到前端的bean类3");

        rootTreeNode.addSubs(treeNode1, treeNode2, treeNode3);

        Map<String, Object> resultMap = TreeNodeUtils.toTreeMap(rootTreeNode);
        L.debug(resultMap);
    }

    @Test
    public void cloneTest() {
        TreeNode treeNode = new DefaultTreeNode("root");
        TreeNode treeNode4 = new DefaultTreeNode(4);
        TreeNode treeNode5 = new DefaultTreeNode(5);
        TreeNode treeNode6 = new DefaultTreeNode(6);
        TreeNode treeNode7 = new DefaultTreeNode(7);

        treeNode.addSubs( treeNode4);
        treeNode4.addSub(treeNode5);
        treeNode5.addSubs(treeNode6,treeNode7);

        L.debug(treeNode.getDetail());
        L.p("//////////////////");
        TreeNode newTreeNode1 = (TreeNode) treeNode.clone();
        L.debug(newTreeNode1.getDetail());
        L.p("//////////////////");
        TreeNode newTreeNode2 = (TreeNode) treeNode5.clone();
        L.debug(newTreeNode2.getParent().getDetail());
        L.p("//////////////////");
    }
}
