package test.tree.component.dingtalk;

import com.jeramtough.jtcomponent.tree.adapter.BaseTreeNodeAdapter;
import test.tree.component.dingtalk.net.DingTalkHttpClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/7/12 14:26
 * by @author WeiBoWen
 */
public class DepartmentTreeNodeAdapter extends BaseTreeNodeAdapter<Long> {

    private DingTalkHttpClient dingTalkHttpClient;

    public DepartmentTreeNodeAdapter(
            DingTalkHttpClient dingTalkHttpClient, Long aLong) {
        super(aLong);
        this.dingTalkHttpClient = dingTalkHttpClient;
    }

    @Override
    public Long getParent() {
        return null;
    }

    @Override
    public List<Long> getSubs() {
        List<Long> ids = new ArrayList<>();
        while (ids.size() == 0) {
            ids = dingTalkHttpClient.getSubDepartmentIdList(get());
        }
        return ids;
    }

    @Override
    public boolean hasSubs() {
        List<Long> ids = dingTalkHttpClient.getSubDepartmentIdList(get());
        return ids.size() > 0;
    }

    @Override
    public BaseTreeNodeAdapter<Long> getNewInstance(Long aLong) {
        return new DepartmentTreeNodeAdapter(dingTalkHttpClient, aLong);
    }
}
