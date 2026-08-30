package com.jeramtough.jtcomponent.tree3.builder.mapbuilder;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     树转 Map 的结果对象。
 *
 * Created on 2025/7/17 下午9:33
 * by @author WeiBoWen
 * </pre>
 */
public class Tree3Map {

    private List<Map<String, Object>> rootMapList;
    private Map<String, Map<String, Object>> keyNodeMap;

    /**
     * @return 根节点的嵌套 Map 列表（每个 Map 含 children 字段）
     */
    public List<Map<String, Object>> getRootMapList() {
        return rootMapList;
    }

    public void setRootMapList(List<Map<String, Object>> rootMapList) {
        this.rootMapList = rootMapList;
    }

    /**
     * @return key {@code ->} 节点 Map 的扁平索引（不含嵌套 children）
     */
    public Map<String, Map<String, Object>> getKeyNodeMap() {
        return keyNodeMap;
    }

    public void setKeyNodeMap(Map<String, Map<String, Object>> keyNodeMap) {
        this.keyNodeMap = keyNodeMap;
    }
}
