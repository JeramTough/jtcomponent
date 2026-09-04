package com.jeramtough.jtcomponent.tree3.builder.mapbuilder;

import com.jeramtough.jtcomponent.callback.CommonCallback;
import com.jeramtough.jtcomponent.tree3.core.Tree3;

import java.util.Map;

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
     * 设置要转换的树对象。
     *
     * @param tree3 树对象
     * @return 当前实例
     */
    Tree3MapBuilder setTree3(Tree3<?> tree3);

    /**
     * 设置节点回调，可在每个节点 Map 中追加自定义字段。
     *
     * @param commonCallback 自定义字段回调
     * @return 当前实例
     */
    Tree3MapBuilder setCommonCallback(CommonCallback<Map<String, Object>> commonCallback);

    /**
     * 构建并返回 Tree3Map 结果。
     *
     * @return Tree3Map 结果
     */
    Tree3Map build();
}
