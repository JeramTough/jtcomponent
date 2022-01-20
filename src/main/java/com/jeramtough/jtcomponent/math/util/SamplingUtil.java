package com.jeramtough.jtcomponent.math.util;

import com.jeramtough.jtcomponent.key.component.GroupKey;
import com.jeramtough.jtcomponent.tree.base.SortMethod;
import com.jeramtough.jtcomponent.tree.structure.DefaultTreeNode;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;

import java.util.*;

/**
 * 抽样之样本模拟工具类
 * <p>
 * Created on 2019-08-08 03:23
 * by @author JeramTough
 */
public class SamplingUtil {

    public static TreeNode getStructure(int baseNumber, int times, boolean isPutBack) {
        TreeNode baseTreeNode = new DefaultTreeNode();

        if (!isPutBack && baseNumber < times) {
            System.err.println("warn: baseNumber < times");
        }

        if (isPutBack) {
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

                    TreeNode tempTreeNode = indexTreeNode;
                    while (!tempTreeNode.isRoot()) {
                        final int ownValue = ((int) tempTreeNode.getValue());
                        indexTreeNode.andFilter((TreeNode treeNode1) -> {
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

    public static int[][] getMatrix(int baseNumber, int times, boolean isPutBack,
                                    boolean isIgnoredOrder) {
        TreeNode baseTreeNode = getStructure(baseNumber, times, isPutBack);
        List<List<TreeNode>> allLevelNodeLists =
                baseTreeNode.getAllForLevel(SortMethod.DESCENDING);
        List<TreeNode> lastTreeNodeList = allLevelNodeLists.get(0);

        List<List<TreeNode>> allNodeLists;
        if (isIgnoredOrder) {
            Map<GroupKey, List<TreeNode>> allNodeListMap = new HashMap<>(16);
            for (TreeNode treeNode : lastTreeNodeList) {
                List<TreeNode> treeNodeList = new ArrayList<>();
                GroupKey groupKey = new GroupKey();
                while (!treeNode.isRoot()) {
                    int key = ((int) treeNode.getValue());
                    groupKey.append(key);

                    treeNodeList.add(treeNode);
                    treeNode = treeNode.getParent();
                }
                allNodeListMap.put(groupKey, treeNodeList);
            }

            allNodeLists = new ArrayList<>(allNodeListMap.size());
            allNodeListMap.forEach(
                    (integer, treeNodes) -> allNodeLists.add(treeNodes));

        }
        else {
            allNodeLists = new ArrayList<>();
            for (TreeNode treeNode : lastTreeNodeList) {
                List<TreeNode> treeNodeList = new ArrayList<>();
                while (!treeNode.isRoot()) {
                    treeNodeList.add(treeNode);
                    treeNode = treeNode.getParent();
                }
                allNodeLists.add(treeNodeList);
            }

            Collections.reverse(allNodeLists);
        }


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

    public static int countSampleSize(int baseNumber, int times,
                                      boolean isPutBack) {

        int size;

        //case 1:放回抽样
        if (isPutBack) {
            //size=baseNumber^times
            size = (int) Math.pow(baseNumber, times);
        }
        //case 2:不放回抽样
        else {
            //不放回抽样次数不能大于基数
            if (times > baseNumber) {
                times = baseNumber;
            }
            size = 1;
            for (int i = baseNumber; i > (baseNumber - times); i--) {
                size = size * i;
            }
        }


        return size;
    }

    //******************************

    private static void addBaseNumbers(int baseNumber, TreeNode treeNode) {
        for (int i = 1; i <= baseNumber; i++) {
            treeNode.addSub(new DefaultTreeNode(i));
        }
    }

}
