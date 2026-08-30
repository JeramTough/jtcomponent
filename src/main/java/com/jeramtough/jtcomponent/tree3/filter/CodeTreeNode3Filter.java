package com.jeramtough.jtcomponent.tree3.filter;

import com.jeramtough.jtcomponent.tree3.core.TreeNode3;
import com.jeramtough.jtcomponent.utils.JtStrUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * <pre>
 *     code 白名单过滤器：仅保留 code 命中白名单的节点。
 *     未配置任何白名单规则时，全部放行。
 *
 * Created on 2025/7/19 上午12:15
 * by @author WeiBoWen
 * </pre>
 */
public class CodeTreeNode3Filter implements TreeNode3Filter {

    private final String code;
    private final String codes;
    private final String codeRegexp;

    private final Set<String> whiteCodeList = new HashSet<>();
    private final Pattern whiteCodeRegexp;

    public CodeTreeNode3Filter(String code, String codes, String codeRegexp) {
        this.code = code;
        this.codes = codes;
        this.codeRegexp = codeRegexp;

        if (!JtStrUtil.isEmpty(code)) {
            this.whiteCodeList.add(code);
        }
        if (!JtStrUtil.isEmpty(codes)) {
            this.whiteCodeList.addAll(JtStrUtil.splitByComma(codes));
        }
        this.whiteCodeRegexp = JtStrUtil.isEmpty(codeRegexp) ? null : Pattern.compile(codeRegexp);
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

        boolean hasRule = !whiteCodeList.isEmpty() || whiteCodeRegexp != null;
        if (!hasRule) {
            return true;
        }

        if (!whiteCodeList.isEmpty() && whiteCodeList.contains(nodeCode)) {
            return true;
        }
        if (whiteCodeRegexp != null && whiteCodeRegexp.matcher(nodeCode).matches()) {
            return true;
        }
        return false;
    }
}
