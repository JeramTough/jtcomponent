package com.jeramtough.jtcomponent.tree.adapter;

/**
 *这是为了组装tree
 *
 * 遍历节点List的适配器，用于适配数据结构和TreeNode差不多的数据，
 * 配合TreeProcessor可以处理节点为一个RootTree
 *
 *<code>
 * List&lt; BsReimburseItem &gt; reimburseItemList = bsReimburseItemService.list();
 *         List&lt; OneTreeNodeAdapter&lt; String &gt;&gt; adapterList = reimburseItemList
 *                 .parallelStream()
 *                 .map(bsReimburseItem -&gt; new OneTreeNodeAdapter&lt; String &gt;() {
 *                     //@Override
 *                     public Object getKey() {
 *                         return bsReimburseItem.get科目编号();
 *                     }
 *
 *                     //@Override
 *                     public Object getParentKey() {
 *                         return bsReimburseItem.get上级科目();
 *                     }
 *
 *                     //@Override
 *                     public String getValue() {
 *                         return bsReimburseItem.get科目名称();
 *                     }
 *
 *                     //@Override
 *                     public int getOrder() {
 *                         return bsReimburseItem.get科目序号().intValue();
 *                     }
 *                 })
 *                 .collect(Collectors.toList());
 *
 *         TreeProcessor treeProcessor = new DefaultTreeProcessor();
 *         TreeNode rootTreeNode = treeProcessor.processing(adapterList);
 *</code>
 * @see com.jeramtough.jtcomponent.tree.processor.TreeProcessor
 * <p>
 * Created on 2019/7/12 14:46
 * by @author WeiBoWen
 */
public interface OneTreeNodeAdapter<T> {

    Object getKey();

    Object getParentKey();

    T getValue();

    int getOrder();
}
