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

public abstract class BaseTree3MapBuilder implements Tree3MapBuilder {

    protected Tree3<?> tree3;
    protected CommonCallback<Map<String, Object>> commonCallback;

    /**
     * 设置要转换的树对象。
     *
     * @param tree3 树对象
     * @return 当前实例
     */
    @Override
    public Tree3MapBuilder setTree3(Tree3<?> tree3) {
        this.tree3 = tree3;
        return this;
    }

    /**
     * 设置节点回调，可在每个节点 Map 中追加自定义字段。
     *
     * @param commonCallback 自定义字段回调
     * @return 当前实例
     */
    @Override
    public Tree3MapBuilder setCommonCallback(
            CommonCallback<Map<String, Object>> commonCallback) {
        this.commonCallback = commonCallback;
        return this;
    }


    protected Map<String, Object> toNodeMap(Tree3<?> tree3, TreeNode3<?> node) {
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

        if (!nodeMap.containsKey("pathNames")) {
            nodeMap.put("pathNames", node.getPathNames());
        }

        if (!nodeMap.containsKey("paths")) {
            nodeMap.put("paths", node.getPaths());
        }

        if (commonCallback != null) {
            commonCallback.callback(nodeMap);
        }
        return nodeMap;
    }
}
