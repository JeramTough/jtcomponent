package com.jeramtough.jtcomponent.tree3.builder.mapbuilder;

import com.jeramtough.jtcomponent.tree3.core.TreeNode3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     默认的树转List平铺结构的 Map 构建器。
 *
 * Created on 2025/7/17 下午8:50
 * by @author WeiBoWen
 * </pre>
 */
public class ListTree3MapBuilder extends BaseTree3MapBuilder implements Tree3MapBuilder {

    @Override
    public Tree3Map build() {
        if (tree3 == null) {
            throw new NullPointerException("tree3 is null");
        }

        List<Map<String, Object>> rootMapList = new ArrayList<>();
        Map<String, Map<String, Object>> keyNodeMap = new HashMap<>();

        for (TreeNode3<?> node : tree3.getAll()) {
            Map<String, Object> nodeMap = toNodeMap(tree3, node);
            nodeMap.remove("children");
            keyNodeMap.put(node.getKey(), nodeMap);
            if (node.getLevel() == 0) {
                rootMapList.add(nodeMap);
            }
        }

        Tree3Map result = new Tree3Map();
        result.setRootMapList(rootMapList);
        result.setKeyNodeMap(keyNodeMap);
        return result;
    }
}
