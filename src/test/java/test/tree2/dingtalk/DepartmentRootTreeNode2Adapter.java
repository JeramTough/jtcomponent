package test.tree2.dingtalk;

import com.jeramtough.jtcomponent.tree2.adpater.RootTreeNode2Adapter;
import test.tree2.dingtalk.net.DingTalkHttpClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/7/12 14:26
 * by @author WeiBoWen
 */
public class DepartmentRootTreeNode2Adapter implements RootTreeNode2Adapter<Long> {

    private DingTalkHttpClient dingTalkHttpClient;
    private Long id;

    public DepartmentRootTreeNode2Adapter(DingTalkHttpClient dingTalkHttpClient, Long aLong) {
        this.id = aLong;
        this.dingTalkHttpClient = dingTalkHttpClient;
    }

    @Override
    public String getKey() {
        return id.toString();
    }

    @Override
    public Long getParent() {
        return null;
    }

    @Override
    public List<Long> getSubs() {
        List<Long> ids = new ArrayList<>();
        while (ids.size() == 0) {
            ids = dingTalkHttpClient.getSubDepartmentIdList(getValue());
        }
        return ids;
    }

    @Override
    public boolean hasSubs() {
        List<Long> ids = dingTalkHttpClient.getSubDepartmentIdList(getValue());
        return ids.size() > 0;
    }

    @Override
    public RootTreeNode2Adapter<Long> getNewInstance(Long value) {
        return new DepartmentRootTreeNode2Adapter(dingTalkHttpClient, value);
    }

    /*@Override
    public void setSource(Object source) {
        this.id = (Long) source;
    }
*/
   /* @Override
    public void setValue(Long value) {
        this.id = value;
    }
*/
    @Override
    public Long getValue() {
        return id;
    }

}
