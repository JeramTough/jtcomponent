package com.jeramtough.jtcomponent.tree2.filter;

import com.jeramtough.jtcomponent.tree2.core.TreeNode2;
import com.jeramtough.jtcomponent.utils.JtStrUtil;
import com.sun.istack.internal.Nullable;
import sun.dc.pr.PRError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * <pre>
 * Created on 2025/7/19 上午12:15
 * by @author WeiBoWen
 * </pre>
 */
public class CodeTreeNode2Filter implements TreeNode2Filter {

    private String code = null;

    private String codes = null;

    private String codeRegexp = null;

    //白名单codeList
    private Set<String> whiteCodeList = new HashSet<>();
    //白名单code正则表达式对象
    private Pattern whiteCodeRegexp = null;


    public CodeTreeNode2Filter(@Nullable String code, @Nullable String codes,
                               @Nullable String codeRegexp) {
        this.code = code;
        this.codes = codes;
        this.codeRegexp = codeRegexp;

        init();
    }

    protected void init() {
        if (!JtStrUtil.isEmpty(code)) {
            this.whiteCodeList.add(code);
        }
        if (!JtStrUtil.isEmpty(codes)) {
            this.whiteCodeList.addAll(JtStrUtil.splitByComma(codes));
        }
        if (!JtStrUtil.isEmpty(codeRegexp)) {
            this.whiteCodeRegexp = Pattern.compile(codeRegexp);
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

        if (!whiteCodeList.isEmpty() && whiteCodeList.contains(code)) {
            return true;
        }

        if (whiteCodeRegexp != null) {
            return whiteCodeRegexp.matcher(code).matches();
        }

        return true;
    }

}
