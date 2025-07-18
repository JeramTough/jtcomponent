package com.jeramtough.jtcomponent.tree2.sort;

import com.jeramtough.jtcomponent.tree.structure.TreeNode;
import com.jeramtough.jtcomponent.tree2.core.TreeNode2;

import java.util.Comparator;

/**
 * <pre>
 * Created on 2020/9/15 13:57
 * by @author WeiBoWen
 * </pre>
 */
public class TreeNode2Comparator implements Comparator<TreeNode2> {

    private TreeNode2SortMethod sortMethod = TreeNode2SortMethod.ASCENDING;

    public TreeNode2Comparator() {
    }

    public TreeNode2Comparator(TreeNode2SortMethod sortMethod) {
        this.sortMethod = sortMethod;
    }

    @Override
    public int compare(TreeNode2 o1, TreeNode2 o2) {
        Integer order1 = o1.getOrder();
        if (order1 == null) {
            order1 = 0;
        }
        if (o1.getLevel() != null) {
            order1 = order1 + o1.getLevel() * 100;
        }
        Integer order2 = o2.getOrder();
        if (order2 == null) {
            order2 = 0;
        }
        if (o2.getLevel() != null) {
            order2 = order2 + o2.getLevel() * 100;
        }

        Long id1 = 0L;
        Long id2 = 0L;
        try {
            id1 = Long.parseLong(o1.getKey());
            id2 = Long.parseLong(o2.getKey());
        }
        catch (Exception e) {

        }
        //在order相同的情况下，比较id
        if (order1.equals(order2)) {
            if (sortMethod == TreeNode2SortMethod.ASCENDING) {
//                return id2.compareTo(id1);
                return id1.compareTo(id2);
            }
            else if (sortMethod == TreeNode2SortMethod.DESCENDING) {
//                return id1.compareTo(id2);
                return id2.compareTo(id1);
            }
            else {
                return 0;
            }
        }
        else {
            if (sortMethod == TreeNode2SortMethod.ASCENDING) {
                return order1 - order2;
            }
            else if (sortMethod == TreeNode2SortMethod.DESCENDING) {
                return order2 - order1;
            }
            else {
                return 0;
            }
        }


    }
}
