package com.jeramtough.jtcomponent.tree2.adpater;

import com.jeramtough.jtcomponent.tree2.sort.TreeNode2SortMethod;

/**
 * 这是为了组装tree
 * <p>
 * 遍历节点List的适配器，用于适配数据结构和TreeNode差不多的数据，
 * 配合TreeProcessor可以处理节点为一个RootTree
 *
 * <code>
 * List&lt; BsReimburseItem &gt; reimburseItemList = bsReimburseItemService.list();
 * List&lt; OneTreeNodeAdapter&lt; String &gt;&gt; adapterList = reimburseItemList
 * .parallelStream()
 * .map(bsReimburseItem -&gt; new OneTreeNodeAdapter&lt; String &gt;() {
 * //@Override
 * public Object getKey() {
 * return bsReimburseItem.get科目编号();
 * }
 * <p>
 * //@Override
 * public Object getParentKey() {
 * return bsReimburseItem.get上级科目();
 * }
 * <p>
 * //@Override
 * public String getValue() {
 * return bsReimburseItem.get科目名称();
 * }
 * <p>
 * //@Override
 * public int getOrder() {
 * return bsReimburseItem.get科目序号().intValue();
 * }
 * })
 * .collect(Collectors.toList());
 * <p>
 * TreeProcessor treeProcessor = new DefaultTreeProcessor();
 * TreeNode rootTreeNode = treeProcessor.processing(adapterList);
 * </code>
 *
 * @see com.jeramtough.jtcomponent.tree.processor.TreeProcessor
 * <p>
 * Created on 2019/7/12 14:46
 * by @author WeiBoWen
 */
public interface OneTreeNode2Adapter<T> {

//    void setValue(T value);

    void setSource(Object source);

    T getValue();

    default String getCode() {
        return null;
    }

    String getKey();

    /**
     * 返回null代表是根节点！
     *
     * @return 父节点的key
     */
    String getParentKey();

    int getOrder();

}
