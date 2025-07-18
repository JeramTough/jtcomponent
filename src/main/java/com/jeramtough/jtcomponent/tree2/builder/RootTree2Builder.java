package com.jeramtough.jtcomponent.tree2.builder;

import com.jeramtough.jtcomponent.tree2.adpater.FileRootTreeNode2Adapter;
import com.jeramtough.jtcomponent.tree2.adpater.RootTreeNode2Adapter;
import com.jeramtough.jtcomponent.tree2.core.DefaultTree2;
import com.jeramtough.jtcomponent.tree2.core.DefaultTreeNode2;
import com.jeramtough.jtcomponent.tree2.core.Tree2;
import com.jeramtough.jtcomponent.tree2.core.TreeNode2;
import com.jeramtough.jtcomponent.tree2.sort.TreeNode2Comparator;
import com.jeramtough.jtcomponent.tree2.sort.TreeNode2SortMethod;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <pre>
 *
 *     创建一个树，使用每一个子节点的源数据，类似数据库表里的每一个数据
 *
 *
 * Created on 2025/7/17 下午4:29
 * by @author WeiBoWen
 * </pre>
 */
public class RootTree2Builder<T> extends BaseTree2Builder<T> implements Tree2Builder<T> {

    private Boolean isRoot;

    private List<RootTreeNode2Adapter<T>> rootAdapterList = null;

    private TreeNode2SortMethod treeNode2SortMethod;

    public RootTree2Builder<T> setRoot(Boolean root) {
        isRoot = root;
        return this;
    }

    public RootTree2Builder<T> setRootAdapterList(
            List<RootTreeNode2Adapter<T>> rootAdapterList) {
        this.rootAdapterList = rootAdapterList;
        return this;
    }

    public RootTree2Builder<T> setTreeNode2SortMethod(
            TreeNode2SortMethod treeNode2SortMethod) {
        this.treeNode2SortMethod = treeNode2SortMethod;
        return this;
    }

    @Override
    public Tree2<T> build() {

        if (isRoot == null) {
            throw new NullPointerException("isRoot is null");
        }

        if (treeNode2SortMethod == null) {
            treeNode2SortMethod = TreeNode2SortMethod.ASCENDING;
        }

        if (rootAdapterList == null) {
            throw new NullPointerException("rootAdapterList is null");
        }

        return processing(isRoot, treeNode2SortMethod, rootAdapterList);
    }


    //******************************************

    private Tree2<T> processing(Boolean isRoot, TreeNode2SortMethod treeNode2SortMethod,
                                List<RootTreeNode2Adapter<T>> adapterList) {

        DefaultTree2<T> tree2 = new DefaultTree2<T>();

        LinkedList<RootTreeNode2Adapter<T>> tempTreeAdapterLinkedList = new LinkedList<>();
        List<RootTreeNode2Adapter<T>> rootAdapterList = new ArrayList<>();

        if (isRoot) {
            rootAdapterList = adapterList;
        }
        else {
            //算出传递的参数是否是最根级参数
            for (; ; ) {
                List<RootTreeNode2Adapter<T>> rootAdapterList2 = new ArrayList<>();
                for (RootTreeNode2Adapter<T> adapter : adapterList) {
                    T parentObject = adapter.getParent();
                    if (parentObject != null) {
                        RootTreeNode2Adapter<T> adapter2 =
                                adapter.getNewInstance(parentObject);
                        if (adapter2 != null) {
                            rootAdapterList2.add(adapter2);
                        }
                    }
                }

                if (rootAdapterList2.isEmpty()) {
                    break;
                }
                else {
                    rootAdapterList = rootAdapterList2;
                }

            }
        }


        //开始从根节点开始处理
        for (RootTreeNode2Adapter<T> rootTreeNode2Adapter : rootAdapterList) {

            TreeNode2<T> rootTreeNode = new DefaultTreeNode2<T>(rootTreeNode2Adapter.getKey(),
                    rootTreeNode2Adapter.getValue());
            tree2.getRootTreeNodeList().add(rootTreeNode);
            tree2.put(rootTreeNode);

            if (rootTreeNode2Adapter.hasSubs()) {
                List<T> subList = rootTreeNode2Adapter.getSubs();
                for (T t : subList) {
                    RootTreeNode2Adapter<T> newAdapter =
                            rootTreeNode2Adapter.getNewInstance(t);
                    tempTreeAdapterLinkedList.add(newAdapter);

                    TreeNode2<T> subTreeNode = new DefaultTreeNode2<T>(newAdapter.getKey(),
                            newAdapter.getValue());
                    rootTreeNode.addSubs(treeNode2SortMethod, subTreeNode);
                    tree2.put(subTreeNode);
                }

                RootTreeNode2Adapter<T> tempAdapter;
                while (!tempTreeAdapterLinkedList.isEmpty()) {
                    tempAdapter = tempTreeAdapterLinkedList.removeFirst();
                    TreeNode2<T> treeNode = tree2.getTreeNode(tempAdapter.getKey());

                    if (tempAdapter.hasSubs()) {
                        List<T> subList1 = tempAdapter.getSubs();

                        for (T t1 : subList1) {
                            RootTreeNode2Adapter<T> newAdapter =
                                    tempAdapter.getNewInstance(t1);
                            if (newAdapter.hasSubs()) {
                                tempTreeAdapterLinkedList.add(newAdapter);
                            }

                            TreeNode2<T> treeNode1 = new DefaultTreeNode2<T>(
                                    newAdapter.getKey(),
                                    newAdapter.getValue());
                            tree2.put(treeNode1);
                            treeNode.addSubs(treeNode2SortMethod, treeNode1);
                        }
                    }
                }

            }

            //排序
            TreeNode2Comparator comparator = new TreeNode2Comparator();
            tree2.getRootTreeNodeList().sort(comparator);
        }


        return tree2;
    }


}
