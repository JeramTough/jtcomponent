package com.jeramtough.jtcomponent.tree3.builder.mapbuilder;

/**
 * <pre>
 * Created on 2026/9/4 14:31
 * by @author WeiBoWen
 * </pre>
 */
public class Tree3MapBuilderFactory {

    public static Tree3MapBuilder getTree3MapBuilder(
            Tree3MapBuilder.Type tree3MapBuilderType) {
        switch (tree3MapBuilderType) {
            case TREE:
                return new DefaultTree3MapBuilder();
            case LIST:
                return new ListTree3MapBuilder();
            default:
                throw new IllegalArgumentException("not support type");
        }
    }

}
