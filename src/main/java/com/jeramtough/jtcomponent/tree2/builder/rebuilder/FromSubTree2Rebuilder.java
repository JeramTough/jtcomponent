package com.jeramtough.jtcomponent.tree2.builder.rebuilder;

import com.jeramtough.jtcomponent.callback.CommonCallback;
import com.jeramtough.jtcomponent.tree2.core.DefaultTree2;
import com.jeramtough.jtcomponent.tree2.core.Tree2;
import com.jeramtough.jtcomponent.tree2.core.TreeNode2;
import com.jeramtough.jtcomponent.tree2.filter.TreeNode2Filter;
import com.jeramtough.jtcomponent.tree2.util.TreeNode2Utils;
import com.jeramtough.jtcomponent.utils.JtCollectionUtil;
import com.jeramtough.jtcomponent.utils.JtStrUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Created on 2025/7/17 下午10:34
 * by @author WeiBoWen
 * </pre>
 */
public class FromSubTree2Rebuilder<T> extends BaseTree2Rebuilder<T>
        implements Tree2Rebuilder<T> {

    private String subTreeNodeKey;
    private List<TreeNode2Filter> filterList = new ArrayList<>();


    public FromSubTree2Rebuilder(Tree2<T> tree2) {
        super(tree2);
    }


    public FromSubTree2Rebuilder<T> setSubTreeNodeKey(String subTreeNodeKey) {
        this.subTreeNodeKey = subTreeNodeKey;
        return this;
    }

    public FromSubTree2Rebuilder<T> setFilterList(List<TreeNode2Filter> filterList) {
        this.filterList = filterList;
        return this;
    }


    @Override
    public Tree2<T> rebuild() {

        if (JtStrUtil.isEmpty(subTreeNodeKey)) {
            throw new RuntimeException("请设置子树节点的key");
        }

        final TreeNode2<T> subTreeNode = getTree2().getTreeNode(subTreeNodeKey);
        if (subTreeNode == null) {
            throw new RuntimeException("请设置正确的子树节点的key,该key没有找到对应的子节点");
        }

        //计算耗时
        long startTime = System.currentTimeMillis();
        System.out.println(
                "开始使用subTreeNodeKey重构Tree2...,旧树共" + getTree2().getAllTreeNodeMap().size() + "个节点");

        //对过滤器进行排序
        filterList.sort(Comparator.comparingInt(TreeNode2Filter::getOrderNumber));

        List<TreeNode2<T>> treeNode2List = subTreeNode.getSubs();

        Map<String, TreeNode2<T>> allTreeNodeMap = TreeNode2Utils.toMapWithSubsParallel(
                treeNode2List, new CommonCallback<TreeNode2<T>>() {
                    @Override
                    public void callback(TreeNode2<T> tTreeNode2) {
                        int baseXs = subTreeNode.getLevel() + 1;
                        tTreeNode2.setLevel(tTreeNode2.getLevel() - baseXs);
                        List<String> paths = tTreeNode2.getPaths();
                        List<String> pewPaths = JtCollectionUtil.truncateList(paths, baseXs);
                        tTreeNode2.setPaths(pewPaths);
                    }
                });

        DefaultTree2<T> newTree2 = new DefaultTree2<>();
        List<TreeNode2<T>> newRootTreeNodeList = new ArrayList<>(treeNode2List);
        newTree2.setRootTreeNodeList(newRootTreeNodeList);
        newTree2.setAllTreeNodeMap(allTreeNodeMap);

        long hTime = (System.currentTimeMillis() - startTime);
        System.out.println(
                "使用subTreeNodeKey重构Tree2结束,新树共" + newTree2.getAllTreeNodeMap().size() + "个节点，耗时：" + hTime + "毫秒");

        return newTree2;

//       return super.rebuildByRootTreeNodeList(rootTreeNodeAdapterClass, getTree2(), valueList);
    }
}
