package com.jeramtough.jtcomponent.tree.util;

import com.jeramtough.jtcomponent.tree.base.SortMethod;
import com.jeramtough.jtcomponent.tree.foreach.NodeCaller;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;

import java.util.*;

/**
 * Created on 2019/6/18 10:28
 * by @author WeiBoWen
 */
public class TreeNodeUtils {

    public static List<TreeNode> getAll(TreeNode rootTreeNode) {
        return getAll(rootTreeNode, SortMethod.DESCENDING);
    }

    public static List<TreeNode> getAll(TreeNode rootTreeNode,
                                        SortMethod sortMethod) {

        List<TreeNode> sortedTreeNodes = new ArrayList<>();
        LinkedList<TreeNode> tempTreeNodes = new LinkedList<>();

        sortedTreeNodes.add(rootTreeNode);

        if (rootTreeNode.hasSubs()) {
            List<TreeNode> treeNodes = rootTreeNode.getSubs();
            for (TreeNode TreeNode : treeNodes) {
                if (TreeNode.hasSubs()) {
                    tempTreeNodes.add(TreeNode);
//                    System.out.println("添加有子结构:" + TreeNode.getValue());
                }
                /*else {
                    System.out.println("添加没有子结构:" + TreeNode.getValue());
                }*/
                sortedTreeNodes.add(TreeNode);
            }

            TreeNode tempTreeNode;
            while (!tempTreeNodes.isEmpty()) {
                tempTreeNode = tempTreeNodes.removeFirst();
                treeNodes = tempTreeNode.getSubs();
                for (TreeNode treeNode2 : treeNodes) {
                    if (treeNode2.hasSubs()) {
                        tempTreeNodes.add(treeNode2);
//                        System.out.println("添加有子结构:" + treeNode2.getValue());
                    }
                    else {
//                        System.out.println("添加没有子结构:" + treeNode2.getValue());
                    }
                    sortedTreeNodes.add(treeNode2);
                }
            }

            if (sortMethod == SortMethod.ASCENDING) {
                Collections.reverse(sortedTreeNodes);
            }
        }

        return sortedTreeNodes;
    }

    public static List<List<TreeNode>> getAllForLevel(TreeNode rootTreeNode,
                                                      SortMethod sortMethod) {
        List<TreeNode> sortTreeNodes = getAll(rootTreeNode, sortMethod);
        Map<Integer, List<TreeNode>> integerListMap = new HashMap<>();

        for (TreeNode TreeNode : sortTreeNodes) {
            List<TreeNode> treeStructures1 = integerListMap.get(TreeNode.getLevel());
            if (treeStructures1 == null) {
                treeStructures1 = new ArrayList<>();
            }
            treeStructures1.add(TreeNode);
            integerListMap.put(TreeNode.getLevel(), treeStructures1);
        }

        List<List<TreeNode>> allTreeStructures = new ArrayList<>();
        for (int i = 0; i < integerListMap.size(); i++) {
            allTreeStructures.add(integerListMap.get(i));
        }

        if (sortMethod == SortMethod.ASCENDING) {
            Collections.reverse(allTreeStructures);
        }
        return allTreeStructures;
    }

    public static void foreach(TreeNode rootTreeNode, NodeCaller nodeCaller) {
        LinkedList<TreeNode> tempTreeNodes = new LinkedList<>();

        nodeCaller.called(rootTreeNode);

        if (rootTreeNode.hasSubs()) {
            List<TreeNode> treeNodes = rootTreeNode.getSubs();
            for (TreeNode treeNode : treeNodes) {
                if (treeNode.hasSubs()) {
                    tempTreeNodes.add(treeNode);
                }

                boolean isContinued = nodeCaller.called(treeNode);
                if (!isContinued) {
                    return;
                }
            }

            TreeNode tempTreeNode;
            while (!tempTreeNodes.isEmpty()) {
                tempTreeNode = tempTreeNodes.removeFirst();
                treeNodes = tempTreeNode.getSubs();
                for (TreeNode treeNode2 : treeNodes) {
                    if (treeNode2.hasSubs()) {
                        tempTreeNodes.add(treeNode2);
                    }

                    boolean isContinued = nodeCaller.called(treeNode2);
                    if (!isContinued) {
                        return;
                    }
                }
            }
        }
    }

}
