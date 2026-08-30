package com.jeramtough.jtcomponent.tree3.filter;

import com.jeramtough.jtcomponent.tree3.core.TreeNode3;
import com.jeramtough.jtcomponent.utils.JtStrUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * <pre>
 *     code 黑名单过滤器：排除 code 命中黑名单的节点。
 *
 * Created on 2025/7/19 上午12:15
 * by @author WeiBoWen
 * </pre>
 */
public class ExcludeCodeTreeNode3Filter implements TreeNode3Filter {

    private final String excludeCode;
    private final String excludeCodes;
    private final String excludeCodeRegexp;

    private final Set<String> blackCodeList = new HashSet<>();
    private final Pattern blackCodeRegexp;

    public ExcludeCodeTreeNode3Filter(String excludeCode, String excludeCodes,
                                      String excludeCodeRegexp) {
        this.excludeCode = excludeCode;
        this.excludeCodes = excludeCodes;
        this.excludeCodeRegexp = excludeCodeRegexp;

        if (!JtStrUtil.isEmpty(excludeCode)) {
            this.blackCodeList.add(excludeCode);
        }
        if (!JtStrUtil.isEmpty(excludeCodes)) {
            this.blackCodeList.addAll(JtStrUtil.splitByComma(excludeCodes));
        }
        this.blackCodeRegexp =
                JtStrUtil.isEmpty(excludeCodeRegexp) ? null : Pattern.compile(excludeCodeRegexp);
    }

    @Override
    public int getOrderNumber() {
        return 0;
    }

    @Override
    public <T> boolean accept(TreeNode3<T> treeNode) {
        String nodeCode = treeNode.getCode();
        if (JtStrUtil.isEmpty(nodeCode)) {
            return true;
        }

        if (!blackCodeList.isEmpty() && blackCodeList.contains(nodeCode)) {
            return false;
        }
        if (blackCodeRegexp != null && blackCodeRegexp.matcher(nodeCode).matches()) {
            return false;
        }
        return true;
    }
}
