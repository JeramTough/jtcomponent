package com.jeramtough.jtcomponent.tree.base;

/**
 * Created on 2019-07-14 23:20
 * by @author JeramTough
 */
public enum SortMethod {

    /**
     * 以层级数Index作为单位，升序
     *   o
     *  ooo
     * ooooo
     */
    ASCENDING,

    /**
     * 以层级数Index作为单位，降序
     * ooooo
     *  ooo
     *   o
     *
     */
    DESCENDING;
}
