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
 *     默认的树转树形结构的 Map 构建器。
 *
 * Created on 2025/7/17 下午8:50
 * by @author WeiBoWen
 * </pre>
 */
public class DefaultTree3MapBuilder extends BaseTree3MapBuilder implements Tree3MapBuilder {



    @Override
    public Tree3Map build() {
        if (tree3 == null) {
            throw new NullPointerException("tree3 is null");
        }

        List<Map<String, Object>> rootMapList = new ArrayList<>();
        Map<String, Map<String, Object>> keyNodeMap = new HashMap<>();

        for (TreeNode3<?> root : tree3.getRootTreeNodeList()) {
            Map<String, Object> rootMap = toNodeMap(tree3, root);
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
            Map<String, Object> childMap = toNodeMap(tree3, child);
            children.add(childMap);
            buildChildren(child, childMap, keyNodeMap);
        }
    }

}
