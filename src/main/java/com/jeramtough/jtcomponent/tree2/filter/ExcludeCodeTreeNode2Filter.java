package com.jeramtough.jtcomponent.tree2.filter;

import com.jeramtough.jtcomponent.tree2.core.TreeNode2;
import com.jeramtough.jtcomponent.utils.JtStrUtil;
import com.sun.istack.internal.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * <pre>
 * Created on 2025/7/19 上午12:15
 * by @author WeiBoWen
 * </pre>
 */
public class ExcludeCodeTreeNode2Filter implements TreeNode2Filter {

    private String excludeCode;
    /**
     * 排除的codes，多个用逗号分隔
     */
    private String excludeCodes = null;

    private String excludeCodeRegexp = null;

    //黑名单
    private Set<String> blackCodeList = new HashSet<>();
    //黑名单code正则表达式对象
    private Pattern blackCodeRegexp = null;

    public ExcludeCodeTreeNode2Filter(@Nullable String excludeCode,
                                      @Nullable String excludeCodes,
                                      @Nullable String excludeCodeRegexp) {
        this.excludeCode = excludeCode;
        this.excludeCodes = excludeCodes;
        this.excludeCodeRegexp = excludeCodeRegexp;

        this.init();
    }

    protected void init() {
        if (!JtStrUtil.isEmpty(excludeCode)) {
            this.blackCodeList.add(excludeCode);
        }
        if (!JtStrUtil.isEmpty(excludeCodes)) {
            this.blackCodeList.addAll(JtStrUtil.splitByComma(excludeCodes));
        }
        if (!JtStrUtil.isEmpty(excludeCodeRegexp)) {
            this.blackCodeRegexp = Pattern.compile(excludeCodeRegexp);
        }
    }

    @Override
    public int getOrderNumber() {
        return 0;
    }

    @Override
    public <T> boolean accept(TreeNode2<T> treeNode) {

        String code = treeNode.getCode();

        if (JtStrUtil.isEmpty(code)) {
            return true;
        }

        if (!blackCodeList.isEmpty() && blackCodeList.contains(code)) {
            return false;
        }

        if (blackCodeRegexp != null) {
            return !blackCodeRegexp.matcher(code).matches();
        }

        return true;
    }

}
