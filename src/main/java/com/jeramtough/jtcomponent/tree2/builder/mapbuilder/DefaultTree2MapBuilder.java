package com.jeramtough.jtcomponent.tree2.builder.mapbuilder;

import com.jeramtough.jtcomponent.callback.CommonCallback;
import com.jeramtough.jtcomponent.tree2.core.Tree2;
import com.jeramtough.jtcomponent.tree2.core.Tree2Map;
import com.jeramtough.jtcomponent.tree2.core.TreeNode2;
import com.jeramtough.jtcomponent.utils.JtBeanUtil;
import com.jeramtough.jtcomponent.utils.ObjectsUtil;

import java.util.*;

/**
 * <pre>
 * Created on 2025/7/17 下午8:50
 * by @author WeiBoWen
 * </pre>
 */
public class DefaultTree2MapBuilder implements Tree2MapBuilder {

    private Tree2<?> tree2 = null;
    private CommonCallback<Map<String, Object>> commonCallback;

    public DefaultTree2MapBuilder setTree2(Tree2<?> tree2) {
        this.tree2 = tree2;
        return this;
    }

    public DefaultTree2MapBuilder setCommonCallback(
            CommonCallback<Map<String, Object>> commonCallback) {
        this.commonCallback = commonCallback;
        return this;
    }


    @Override
    public Tree2Map build() {

        if (tree2 == null) {
            throw new NullPointerException("tree2 is null");
        }

        List<? extends TreeNode2<?>> rootTreeNodeList = tree2.getRootTreeNodeList();
        Map<String, ? extends TreeNode2<?>> allTreeNodeMap = tree2.getAllIdKeyTreeNodeMap();

        return processing(allTreeNodeMap, rootTreeNodeList, commonCallback);

    }

    //******************************************

    private Tree2Map processing(Map<String, ? extends TreeNode2<?>> treeNodeMap,
                                List<? extends TreeNode2<?>> rootTreeNodeList,
                                CommonCallback<Map<String, Object>> commonCallback) {

        List<Map<String, Object>> rootMapList = new ArrayList<>();
        Map<String, Map<String, Object>> keyNodeMap = new HashMap<>();


        LinkedList<Map<String, Object>> tempNodeMapLinkedList = new LinkedList<>();


        //开始从根节点开始处理
        for (TreeNode2<?> rootTreeNode : rootTreeNodeList) {

            Map<String, Object> rootNodeMap = toNodeMap(rootTreeNode, commonCallback);
            List<Map<String, Object>> children = (List<Map<String, Object>>) rootNodeMap.get(
                    "children");

            rootMapList.add(rootNodeMap);
            keyNodeMap.put(rootTreeNode.getKey(), rootNodeMap);

            if (rootTreeNode.hasSubs()) {
                List<? extends TreeNode2<?>> subList = rootTreeNode.getSubs();
                for (TreeNode2<?> t : subList) {
                    Map<String, Object> newNodeMap = toNodeMap(t, commonCallback);
                    tempNodeMapLinkedList.add(newNodeMap);

                    children.add(newNodeMap);
                    keyNodeMap.put(t.getKey(), newNodeMap);

                }

                Map<String, Object> tempNodeMap;
                while (!tempNodeMapLinkedList.isEmpty()) {
                    tempNodeMap = tempNodeMapLinkedList.removeFirst();
                    String treeNodeKey = (String) tempNodeMap.get("treeNodeKey");
                    Objects.requireNonNull(treeNodeKey);
                    TreeNode2<?> tempTreeNode = treeNodeMap.get(treeNodeKey);
                    Objects.requireNonNull(tempTreeNode);

                    if (tempTreeNode.hasSubs()) {
                        List<Map<String, Object>> subList1 = (List<Map<String, Object>>) tempNodeMap.get(
                                "children");
                        List<? extends TreeNode2<?>> subs = tempTreeNode.getSubs();
                        for (TreeNode2<?> t1 : subs) {

                            Map<String, Object> newSubNodeMap = toNodeMap(t1, commonCallback);
                            subList1.add(newSubNodeMap);

                            if (t1.hasSubs()) {
                                tempNodeMapLinkedList.add(newSubNodeMap);
                            }
                            keyNodeMap.put(t1.getKey(), newSubNodeMap);
                        }
                    }
                }

            }

        }

        Tree2Map tree2Map = new Tree2Map();
        tree2Map.setRootMapList(rootMapList);
        tree2Map.setKeyNodeMap(keyNodeMap);
        return tree2Map;
    }


    private Map<String, Object> toNodeMap(TreeNode2<?> treeNode,
                                          CommonCallback<Map<String, Object>> callback) {
        Map<String, Object> nodeMap = null;
        Object value = treeNode.getValue();
        if (value == null) {
            nodeMap = new HashMap<>(1);
        }
        else if (ObjectsUtil.isPrimaryType(value)) {
            nodeMap = new HashMap<>(16);
            nodeMap.put("value", value);
        }
        else {
            nodeMap = JtBeanUtil.beanToMap(treeNode.getValue());
        }
        Objects.requireNonNull(nodeMap);
        nodeMap.put("treeNodeKey", treeNode.getKey());

        if (!nodeMap.containsKey("level")) {
            nodeMap.put("level", treeNode.getLevel());
        }
        if (!nodeMap.containsKey("order")) {
            nodeMap.put("order", treeNode.getOrder());
        }
        if (!nodeMap.containsKey("orderWithLevel")) {
            nodeMap.put("orderWithLevel", treeNode.getOrderWithLevel());
        }
        if (!nodeMap.containsKey("hasSubs")) {
            nodeMap.put("hasSubs", treeNode.hasSubs());
        }
        if (!nodeMap.containsKey("children")) {
            nodeMap.put("children", new ArrayList<Map<String, Object>>());
        }

        callback(nodeMap, callback);

        return nodeMap;
    }

    private void callback(Map<String, Object> nodeMap,
                          CommonCallback<Map<String, Object>> callback) {
        if (callback != null) {
            callback.callback(nodeMap);
        }
    }

}
