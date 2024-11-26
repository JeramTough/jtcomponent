package com.jeramtough.jtcomponent.tree.util;

import com.jeramtough.jtcomponent.callback.CommonCallback;
import com.jeramtough.jtcomponent.tree.adapter.AssemblyOneTreeNodeAdapter;
import com.jeramtough.jtcomponent.tree.base.SortMethod;
import com.jeramtough.jtcomponent.tree.filter.TreeNodeFilter;
import com.jeramtough.jtcomponent.tree.foreach.NodeCaller;
import com.jeramtough.jtcomponent.tree.processor.DefaultTreeProcessor;
import com.jeramtough.jtcomponent.tree.processor.TreeProcessor;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;
import com.jeramtough.jtcomponent.utils.ObjectsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import static javafx.scene.input.KeyCode.L;

/**
 * Created on 2019/6/18 10:28
 * by @author WeiBoWen
 */
public class TreeNodeUtils {

    public static List<TreeNode> getAll(TreeNode rootTreeNode) {
        return getAll(rootTreeNode, SortMethod.ASCENDING, true);
    }

    public static List<TreeNode> getAll(TreeNode rootTreeNode, SortMethod sortMethod) {
        return getAll(rootTreeNode, sortMethod, true);
    }

    public static List<TreeNode> getAll(TreeNode rootTreeNode, SortMethod sortMethod,
                                        boolean containRoot) {
        List<TreeNode> sortedTreeNodes = new ArrayList<>();
        Deque<TreeNode> tempTreeNodes = new ArrayDeque<>();

        if (containRoot) {
            sortedTreeNodes.add(rootTreeNode);
        }

        // 是否从末尾插入决定最终排序
        boolean addToEnd = (sortMethod != SortMethod.DESCENDING);

        // 添加根节点的子节点到队列
        if (rootTreeNode.hasSubs()) {
            List<TreeNode> rootSubs = rootTreeNode.getSubsByFilters();
            if (addToEnd) {
                sortedTreeNodes.addAll(rootSubs);
            }
            else {
                sortedTreeNodes.addAll(0, rootSubs);
            }

            // 将子节点加入队列进行广度优先遍历
            for (TreeNode node : rootSubs) {
                if (node.hasSubs()) {
                    tempTreeNodes.add(node);
                }
            }
        }

        while (!tempTreeNodes.isEmpty()) {
            TreeNode currentNode = tempTreeNodes.removeFirst();
            List<TreeNode> subs = currentNode.getSubsByFilters();

            // 根据排序方法决定插入方式
            if (addToEnd) {
                sortedTreeNodes.addAll(subs);
            }
            else {
                sortedTreeNodes.addAll(0, subs);
            }

            // 继续遍历子节点
            for (TreeNode node : subs) {
                if (node.hasSubs()) {
                    tempTreeNodes.add(node);
                }
            }
        }

        return sortedTreeNodes;
    }


    public static List<TreeNode> getAllForTree(TreeNode rootTreeNode, SortMethod sortMethod,
                                               boolean containRoot) {

        List<TreeNode> sortedTreeNodes = new ArrayList<>();

        if (containRoot) {
            sortedTreeNodes.add(rootTreeNode);
        }

        if (rootTreeNode.hasSubs()) {
            List<TreeNode> treeNodes = rootTreeNode.getSubsByFilters();
            for (TreeNode treeNode : treeNodes) {
                addChildNode(sortedTreeNodes, treeNode);
            }
        }

        if (sortMethod == SortMethod.DESCENDING) {
            Collections.reverse(sortedTreeNodes);
        }

        return sortedTreeNodes;
    }

    public static List<List<TreeNode>> getAllForLevel(TreeNode rootTreeNode,
                                                      SortMethod sortMethod) {
        List<TreeNode> sortTreeNodes = getAll(rootTreeNode, sortMethod, true);
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

       /* if (sortMethod == SortMethod.DESCENDING) {
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


    /**
     * 并行过滤整个子树的所有子节点。
     *
     * @param rootTreeNode 被过滤的根节点
     * @param filterList   过滤器
     * @return 过滤后的所有子节点
     */
    public static List<TreeNode> filterAllSubs(TreeNode rootTreeNode,
                                               List<TreeNodeFilter> filterList) {
        Queue<TreeNode> result = new ConcurrentLinkedQueue<>(); // 使用线程安全的集合
        boolean accepted = true;

        if (!rootTreeNode.isRoot()) {
            // 当前节点通过所有过滤器时加入结果
            for (TreeNodeFilter filter : filterList) {
                try {
                    if (!filter.accept(rootTreeNode)) {
                        accepted = false;
                        break;
                    }
                }
                catch (Exception e) {
                    System.err.println("过滤器异常！" + e.getMessage());
                    throw new IllegalStateException(e);
                }

            }
            if (accepted) {
                result.add(rootTreeNode);
            }
        }


        // 递归过滤子节点，使用并行流
        if (rootTreeNode.hasSubs()) {
            rootTreeNode.getSubs().parallelStream() // 并行流处理子节点
                        .map(sub -> filterAllSubs(sub, filterList)) // 递归过滤
                        .forEach(result::addAll); // 合并结果
        }

        return new ArrayList<>(result); // 转换为 ArrayList 返回
    }

    /**
     * 并行过滤整个子树的所有子节点。
     *
     * @param rootTreeNode 被过滤的根节点
     * @param filterList   过滤器
     * @return 过滤后的新的树形结构
     */
    public static TreeNode filterAllSubs2(TreeNode rootTreeNode,
                                          List<TreeNodeFilter> filterList) {

        List<TreeNode> treeNodeList = filterAllSubs(rootTreeNode,
                filterList);
        TreeNode rootTreeNode2 = assemblyTreeNode(treeNodeList);
        return rootTreeNode2;
    }

    /**
     * 还原rootTreeNode
     *
     * @param treeNodeList treeNodeList
     * @return treeNodeList
     */
    public static TreeNode assemblyTreeNode(List<TreeNode> treeNodeList) {

        List<AssemblyOneTreeNodeAdapter> adapterList = treeNodeList.parallelStream().map(
                AssemblyOneTreeNodeAdapter::new).collect(Collectors.toList());

        TreeProcessor treeProcessor = new DefaultTreeProcessor();
        TreeNode rootTreeNode = treeProcessor.processing(adapterList);
        return rootTreeNode;
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
            if (!nodeMap.containsKey("level")) {
                nodeMap.put("level", treeNode.getLevel());
            }
            if (!nodeMap.containsKey("order")) {
                nodeMap.put("order", treeNode.getOrder());
            }
            if (!nodeMap.containsKey("hasChildren")) {
                nodeMap.put("hasChildren", treeNode.hasSubs());
            }
            if (!nodeMap.containsKey("children")) {
                nodeMap.put("children", new ArrayList<HashMap<String, Object>>());
            }

            treeNodesMap.put(treeNode, nodeMap);
            return true;
        });

        //第二次遍历，添加上子节点
        beTreeNode.foreach(treeNode -> {
            //取出与TreeNode对应的Map集合
            Map<String, Object> thisTreeMap = treeNodesMap.get(treeNode);

            //设置子节点
            List<TreeNode> subTreeNodes = treeNode.getSubs();
            List<Map<String, Object>> childrenMapList = (List<Map<String, Object>>) treeNodesMap.get(
                    treeNode).get("children");

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


    private static void addChildNode(List<TreeNode> sortedTreeNodes, TreeNode treeNode) {

        sortedTreeNodes.add(treeNode);

        if (treeNode.hasSubs()) {
            List<TreeNode> treeNodes = treeNode.getSubsByFilters();
            for (TreeNode node : treeNodes) {
                addChildNode(sortedTreeNodes, node);
            }
        }
    }
}
