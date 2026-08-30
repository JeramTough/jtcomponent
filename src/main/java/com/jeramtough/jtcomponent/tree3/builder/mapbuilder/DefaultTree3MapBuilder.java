package com.jeramtough.jtcomponent.tree3.builder.mapbuilder;

import com.jeramtough.jtcomponent.callback.CommonCallback;
import com.jeramtough.jtcomponent.tree3.core.Tree3;
import com.jeramtough.jtcomponent.tree3.core.TreeNode3;
import com.jeramtough.jtcomponent.utils.JtBeanUtil;
import com.jeramtough.jtcomponent.utils.ObjectsUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     默认的树转 Map 构建器。
 *
 * Created on 2025/7/17 下午8:50
 * by @author WeiBoWen
 * </pre>
 */
public class DefaultTree3MapBuilder implements Tree3MapBuilder {

    private Tree3<?> tree3;
    private CommonCallback<Map<String, Object>> commonCallback;

    /**
     * 设置要转换的树对象。
     */
    public DefaultTree3MapBuilder setTree3(Tree3<?> tree3) {
        this.tree3 = tree3;
        return this;
    }

    /**
     * 设置节点回调，可在每个节点 Map 中追加自定义字段。
     */
    public DefaultTree3MapBuilder setCommonCallback(
            CommonCallback<Map<String, Object>> commonCallback) {
        this.commonCallback = commonCallback;
        return this;
    }

    @Override
    public Tree3Map build() {
        if (tree3 == null) {
            throw new NullPointerException("tree3 is null");
        }

        List<Map<String, Object>> rootMapList = new ArrayList<>();
        Map<String, Map<String, Object>> keyNodeMap = new HashMap<>();

        for (TreeNode3<?> root : tree3.getRootTreeNodeList()) {
            Map<String, Object> rootMap = toNodeMap(root);
            rootMapList.add(rootMap);
            buildChildren(root, rootMap, keyNodeMap);
        }

        Tree3Map result = new Tree3Map();
        result.setRootMapList(rootMapList);
        result.setKeyNodeMap(keyNodeMap);
        return result;
    }

    @SuppressWarnings("unchecked")
    private void buildChildren(TreeNode3<?> node, Map<String, Object> nodeMap,
                               Map<String, Map<String, Object>> keyNodeMap) {
        keyNodeMap.put(node.getKey(), nodeMap);
        List<Map<String, Object>> children = (List<Map<String, Object>>) nodeMap.get(
                "children");
        for (TreeNode3<?> child : node.getSubs()) {
            Map<String, Object> childMap = toNodeMap(child);
            children.add(childMap);
            buildChildren(child, childMap, keyNodeMap);
        }
    }

    private Map<String, Object> toNodeMap(TreeNode3<?> node) {
        Map<String, Object> nodeMap;
        Object value = node.getValue();
        if (value == null) {
            nodeMap = new HashMap<>(1);
        }
        else if (ObjectsUtil.isPrimaryType(value)) {
            nodeMap = new HashMap<>(16);
            nodeMap.put("value", value);
        }
        else {
            nodeMap = JtBeanUtil.beanToMap(value);
        }
        if (nodeMap == null) {
            nodeMap = new HashMap<>();
        }

        nodeMap.put("treeNodeKey", node.getKey());
        if (!nodeMap.containsKey("level")) {
            nodeMap.put("level", node.getLevel());
        }
        if (!nodeMap.containsKey("order")) {
            nodeMap.put("order", node.getOrder());
        }
        if (!nodeMap.containsKey("orderWithLevel")) {
            nodeMap.put("orderWithLevel", node.getOrderWithLevel());
        }
        if (!nodeMap.containsKey("hasSubs")) {
            nodeMap.put("hasSubs", node.hasSubs());
        }
        if (!nodeMap.containsKey("children")) {
            nodeMap.put("children", new ArrayList<Map<String, Object>>());
        }
        if (!nodeMap.containsKey("childrenSize")) {
            nodeMap.put("childrenSize", node.getSubsLength());
        }

        if (commonCallback != null) {
            commonCallback.callback(nodeMap);
        }
        return nodeMap;
    }
}
