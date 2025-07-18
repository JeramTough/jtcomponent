package com.jeramtough.jtcomponent.tree2.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Created on 2025/7/17 下午9:33
 * by @author WeiBoWen
 * </pre>
 */
public class Tree2Map {

    private List<Map<String, Object>> rootMapList;
    private Map<String, Map<String, Object>> keyNodeMap;

    public List<Map<String, Object>> getRootMapList() {
        return rootMapList;
    }

    public void setRootMapList(
            List<Map<String, Object>> rootMapList) {
        this.rootMapList = rootMapList;
    }

    public Map<String, Map<String, Object>> getKeyNodeMap() {
        return keyNodeMap;
    }

    public void setKeyNodeMap(
            Map<String, Map<String, Object>> keyNodeMap) {
        this.keyNodeMap = keyNodeMap;
    }
}
