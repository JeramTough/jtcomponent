package com.jeramtough.jtcomponent.tree3.builder.mapbuilder;

import com.jeramtough.jtcomponent.tree3.core.Tree3;

/**
 * <pre>
 *     树转 Map 构建器：将 Tree3 转为嵌套 Map 结构（适用于 JSON 序列化）。
 *
 * Created on 2025/7/17 下午4:36
 * by @author WeiBoWen
 * </pre>
 */
public interface Tree3MapBuilder {

    /**
     * 构建并返回 Tree3Map 结果。
     *
     * @return Tree3Map 结果
     */
    Tree3Map build();
}
