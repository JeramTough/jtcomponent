package com.jeramtough.jtcomponent.tree2.core;

import com.jeramtough.jtcomponent.callback.CommonCallback;
import com.jeramtough.jtcomponent.tree2.builder.mapbuilder.DefaultTree2MapBuilder;
import com.jeramtough.jtcomponent.tree2.sort.TreeNode2Comparator;
import com.jeramtough.jtcomponent.tree2.sort.TreeNode2SortMethod;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 * Created on 2025/7/17 上午1:09
 * by @author WeiBoWen
 * </pre>
 */
public class DefaultTree2<T> implements Tree2<T> {

    private static final long serialVersionUID = 7453032997579760510L;

    private Map<String, TreeNode2<T>> allTreeNodeMap = new ConcurrentHashMap<>();
    private List<TreeNode2<T>> rootTreeNodeList = new ArrayList<>();

    public void setRootTreeNodeList(List<TreeNode2<T>> rootTreeNodeList) {
        this.rootTreeNodeList = rootTreeNodeList;
    }

    @Override
    public void setAllTreeNodeMap(
            Map<String, TreeNode2<T>> allTreeNodeMap) {
        this.allTreeNodeMap = allTreeNodeMap;
    }

    @Override
    public List<TreeNode2<T>> getRootTreeNodeList() {
        return rootTreeNodeList;
    }

    @Override
    public void put(TreeNode2<T> treeNode) {
        allTreeNodeMap.put(treeNode.getKey(), treeNode);
    }

    @Override
    public TreeNode2<T> getTreeNode(String key) {
        return allTreeNodeMap.get(key);
    }

    @Override
    public Map<String, TreeNode2<T>> getAllTreeNodeMap() {
        return allTreeNodeMap;
    }

    @Override
    public List<TreeNode2<T>> getAll() {
        return getAll(TreeNode2SortMethod.ASCENDING);
    }

    @Override
    public List<TreeNode2<T>> getAll(TreeNode2SortMethod sortMethod) {
        TreeNode2Comparator treeNode2Comparator = new TreeNode2Comparator(sortMethod);
        List<TreeNode2<T>> treeNode2List = new ArrayList<>(allTreeNodeMap.values());
        treeNode2List.sort(treeNode2Comparator);
        return treeNode2List;
    }

    @Override
    public List<List<TreeNode2<T>>> getAllForLevel() {
        return this.getAllForLevel(TreeNode2SortMethod.ASCENDING);
    }

    @Override
    public List<List<TreeNode2<T>>> getAllForLevel(TreeNode2SortMethod sortMethod) {

        List<TreeNode2<T>> sortTreeNodes = getAll(sortMethod);
        Map<Integer, List<TreeNode2<T>>> integerListMap = new HashMap<>(16);

        for (TreeNode2<T> treeNode : sortTreeNodes) {
            List<TreeNode2<T>> treeStructures1 = integerListMap.get(treeNode.getLevel());
            if (treeStructures1 == null) {
                treeStructures1 = new ArrayList<>();
            }
            treeStructures1.add(treeNode);
            integerListMap.put(treeNode.getLevel(), treeStructures1);
        }

        List<List<TreeNode2<T>>> allTreeStructures = new ArrayList<>();

        //键值对的键进行排序
        Set<Integer> sortSet = new TreeSet<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (TreeNode2SortMethod.DESCENDING == sortMethod) {
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

    @Override
    public List<Map<String, Object>> toTreeNodeMapList() {
        return toTreeNodeMapList(null);
    }

    @Override
    public List<Map<String, Object>> toTreeNodeMapList(
            CommonCallback<Map<String, Object>> commonCallback) {

        DefaultTree2MapBuilder tree2MapBuilder = new DefaultTree2MapBuilder();
        tree2MapBuilder.setTree2(this);
        tree2MapBuilder.setCommonCallback(commonCallback);
        Tree2Map tree2Map = tree2MapBuilder.build();
        return tree2Map.getRootMapList();
    }

}
