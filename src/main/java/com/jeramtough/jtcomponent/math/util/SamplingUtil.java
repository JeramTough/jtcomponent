package com.jeramtough.jtcomponent.math.util;

import com.jeramtough.jtcomponent.tree.base.SortMethod;
import com.jeramtough.jtcomponent.tree.structure.DefaultTreeNode;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 抽样之样本模拟工具类
 * <p>
 * Created on 2019-08-08 03:23
 * by @author JeramTough
 */
public class SamplingUtil {

    public static TreeNode getStructure(int baseNumber, int times, boolean isPutback) {
        TreeNode baseTreeNode = new DefaultTreeNode();

        if (baseNumber<times){
            System.err.println("warn: times > baseNumber");
        }

        if (isPutback) {
            addBaseNumbers(baseNumber, baseTreeNode);

            List<TreeNode> currentTreeNodeList = new ArrayList<>();
            currentTreeNodeList.addAll(baseTreeNode.getSubs());

            for (int i = 0; i < times - 1; i++) {
                List<TreeNode> lastTreeNodeList = new ArrayList<>(currentTreeNodeList);
                currentTreeNodeList.clear();
                for (TreeNode treeNode : lastTreeNodeList) {
                    addBaseNumbers(baseNumber, treeNode);
                    currentTreeNodeList.addAll(treeNode.getSubs());
                }
            }
        }
        else {
            addBaseNumbers(baseNumber, baseTreeNode);

            List<TreeNode> currentTreeNodeList = new ArrayList<>();
            currentTreeNodeList.addAll(baseTreeNode.getSubs());

            for (int i = 0; i < times - 1; i++) {
                List<TreeNode> lastTreeNodeList = new ArrayList<>(currentTreeNodeList);
                currentTreeNodeList.clear();
                for (TreeNode indexTreeNode : lastTreeNodeList) {
                    addBaseNumbers(baseNumber, indexTreeNode);

                    TreeNode tempTreeNode= indexTreeNode;
                    while (!tempTreeNode.isRoot()) {
                        final int ownValue = ((int) tempTreeNode.getValue());
                        indexTreeNode.andPredicate((TreeNode treeNode1) -> {
                            int value = (int) treeNode1.getValue();
                            return ownValue != value;
                        });
                        tempTreeNode = tempTreeNode.getParent();
                    }

                    currentTreeNodeList.addAll(indexTreeNode.getSubsByFilters());


                }
            }
        }

        return baseTreeNode;
    }

    public static int[][] getMatrix(int baseNumber, int times, boolean isPutback) {
        TreeNode baseTreeNode = getStructure(baseNumber, times, isPutback);
        List<List<TreeNode>> allLevelNodeLists =
                baseTreeNode.getAllForLevel(SortMethod.ASCENDING);
        List<TreeNode> lastTreeNodeList = allLevelNodeLists.get(0);

        List<List<TreeNode>> allNodeLists = new ArrayList<>();
        for (TreeNode treeNode : lastTreeNodeList) {
            List<TreeNode> treeNodeList = new ArrayList<>();
            while (!treeNode.isRoot()) {
                treeNodeList.add(treeNode);
                treeNode = treeNode.getParent();
            }
            allNodeLists.add(treeNodeList);
        }

        Collections.reverse(allNodeLists);

        int[][] matrix = new int[allNodeLists.size()][allNodeLists.get(0).size()];
        for (int i = 0; i < allNodeLists.size(); i++) {
            List<TreeNode> treeNodeList = allNodeLists.get(i);
            for (int ii = 0; ii < treeNodeList.size(); ii++) {
                TreeNode treeNode = treeNodeList.get(ii);
                matrix[i][ii] = (int) treeNode.getValue();
            }
        }

        return matrix;

    }

    private static void addBaseNumbers(int baseNumber, TreeNode treeNode) {
        for (int i = 1; i <= baseNumber; i++) {
            treeNode.addSub(new DefaultTreeNode(i));
        }
    }

}
