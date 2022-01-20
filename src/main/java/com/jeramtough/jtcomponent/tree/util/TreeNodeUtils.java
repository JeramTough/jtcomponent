package com.jeramtough.jtcomponent.tree.util;

import com.jeramtough.jtcomponent.callback.CommonCallback;
import com.jeramtough.jtcomponent.tree.base.SortMethod;
import com.jeramtough.jtcomponent.tree.foreach.NodeCaller;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;
import com.jeramtough.jtcomponent.utils.ObjectsUtil;

import java.util.*;

/**
 * Created on 2019/6/18 10:28
 * by @author WeiBoWen
 */
public class TreeNodeUtils {

    public static List<TreeNode> getAll(TreeNode rootTreeNode) {
        return getAll(rootTreeNode, SortMethod.ASCENDING);
    }

    public static List<TreeNode> getAll(TreeNode rootTreeNode,
                                        SortMethod sortMethod) {

        List<TreeNode> sortedTreeNodes = new ArrayList<>();
        LinkedList<TreeNode> tempTreeNodes = new LinkedList<>();

        sortedTreeNodes.add(rootTreeNode);

        if (rootTreeNode.hasSubs()) {
            List<TreeNode> treeNodes = rootTreeNode.getSubsByFilters();
            for (TreeNode treeNode : treeNodes) {
                if (treeNode.hasSubs()) {
                    tempTreeNodes.add(treeNode);
//                    System.out.println("添加有子结构:" + TreeNode.getValue());
                }
                /*else {
                    System.out.println("添加没有子结构:" + TreeNode.getValue());
                }*/
                sortedTreeNodes.add(treeNode);
            }

            TreeNode tempTreeNode;
            while (!tempTreeNodes.isEmpty()) {
                tempTreeNode = tempTreeNodes.removeFirst();
                treeNodes = tempTreeNode.getSubsByFilters();
                for (TreeNode treeNode2 : treeNodes) {
                    sortedTreeNodes.add(treeNode2);
                    if (treeNode2.hasSubs()) {
                        tempTreeNodes.add(treeNode2);
                    }
                }
            }

            if (sortMethod == SortMethod.DESCENDING) {
                Collections.reverse(sortedTreeNodes);
            }
        }

        return sortedTreeNodes;
    }

    public static List<List<TreeNode>> getAllForLevel(TreeNode rootTreeNode,
                                                      SortMethod sortMethod) {
        List<TreeNode> sortTreeNodes = getAll(rootTreeNode, sortMethod);
        Map<Integer, List<TreeNode>> integerListMap = new HashMap<>(16);

        for (TreeNode treeNode : sortTreeNodes) {
            List<TreeNode> treeStructures1 = integerListMap.get(treeNode.getLevel());
            if (treeStructures1 == null) {
                treeStructures1 = new ArrayList<>();
            }
            treeStructures1.add(treeNode);
            integerListMap.put(treeNode.getLevel(), treeStructures1);
        }

        List<List<TreeNode>> allTreeStructures = new ArrayList<>();

        //键值对的键进行排序
        Set<Integer> sortSet = new TreeSet<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (SortMethod.DESCENDING == sortMethod) {
                    return o2.compareTo(o1);//降序排列
                }
                else {
                    return o1.compareTo(o2);
                }
            }
        });

        sortSet.addAll(integerListMap.keySet());

        for (Integer i : sortSet) {
            allTreeStructures.add(integerListMap.get(i));
        }

        /*if (sortMethod == SortMethod.DESCENDING) {
            Collections.reverse(allTreeStructures);
        }*/
        return allTreeStructures;
    }

    public static void foreach(TreeNode rootTreeNode, NodeCaller nodeCaller) {
        LinkedList<TreeNode> tempTreeNodes = new LinkedList<>();

        nodeCaller.called(rootTreeNode);

        if (rootTreeNode.hasSubs()) {
            List<TreeNode> treeNodes = rootTreeNode.getSubsByFilters();
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
                treeNodes = tempTreeNode.getSubsByFilters();
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


    public static Map<String, Object> toTreeMap(TreeNode beTreeNode) {
        return parseTreeNodeMap(beTreeNode, null).get(beTreeNode);
    }

    public static Map<String, Object> toTreeMap(TreeNode beTreeNode,
                                                CommonCallback<Map<String, Object>> commonCallback) {
        return parseTreeNodeMap(beTreeNode, commonCallback).get(beTreeNode);
    }


    //*********************

    private static Map<TreeNode, Map<String, Object>> parseTreeNodeMap(TreeNode beTreeNode,
                                                                       CommonCallback<Map<String, Object>> commonCallback) {
        //以TreeNode和相应的Map集合的映射关系
        Map<TreeNode, Map<String, Object>> treeNodesMap = new HashMap<>(16);

        //第一次遍历，先组成相应的映射关系
        beTreeNode.foreach(treeNode -> {
            Map<String, Object> nodeMap = null;
            try {
                Object value = treeNode.getValue();
                if (value == null) {
                    nodeMap = new HashMap<>(1);
                }
                else if (ObjectsUtil.isPrimaryType(value)) {
                    nodeMap = new HashMap<>(16);
                    nodeMap.put("value", value);
                }
                else {
                    nodeMap = ObjectsUtil.getMapFromObject(treeNode.getValue());
                }
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Objects.requireNonNull(nodeMap);
            nodeMap.put("level", treeNode.getLevel());
            nodeMap.put("order", treeNode.getOrder());
            nodeMap.put("hasChildren", treeNode.hasSubs());
            nodeMap.put("children", new ArrayList<HashMap<String, Object>>());
            treeNodesMap.put(treeNode, nodeMap);
            return true;
        });

        //第二次遍历，添加上子节点
        beTreeNode.foreach(treeNode -> {
            //取出与TreeNode对应的Map集合
            Map<String, Object> thisTreeMap = treeNodesMap.get(treeNode);

            //设置子节点
            List<TreeNode> subTreeNodes = treeNode.getSubs();
            List<Map<String, Object>> childrenMapList =
                    (List<Map<String, Object>>) treeNodesMap.get(treeNode).get("children");

            //如果有子节点则添加，没有则移除children属性
            if (subTreeNodes.size() > 0) {
                for (TreeNode subTreeNode : subTreeNodes) {
                    childrenMapList.add(treeNodesMap.get(subTreeNode));
                }
                thisTreeMap.put("children", childrenMapList);
            }
            else {
                thisTreeMap.remove("children");
            }


            if (commonCallback != null) {
                commonCallback.callback(thisTreeMap);
            }
            return true;
        });

        return treeNodesMap;
    }
}
