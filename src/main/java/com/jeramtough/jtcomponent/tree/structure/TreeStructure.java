package com.jeramtough.jtcomponent.tree.structure;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * Created on 2020/9/15 10:23
 * by @author WeiBoWen
 * </pre>
 */
public class TreeStructure implements Serializable {

    private int level = 0;

    private Object value;

    private List<TreeStructure> subTreeStructures;

    private TreeStructure parentTreeStructure;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public List<TreeStructure> getSubTreeStructures() {
        return subTreeStructures;
    }

    public void setSubTreeStructures(List<TreeStructure> subTreeStructures) {
        this.subTreeStructures = subTreeStructures;
    }

    public TreeStructure getParentTreeStructure() {
        return parentTreeStructure;
    }

    public void setParentTreeStructure(TreeStructure parentTreeStructure) {
        this.parentTreeStructure = parentTreeStructure;
    }
}
