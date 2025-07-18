package test.tree2.dingtalk.net;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/7/11 16:41
 * by @author WeiBoWen
 */
public class MyDingTalkHttpClient implements DingTalkHttpClient {

    private static boolean isFirst = true;

    @Override
    public List<Long> getSubDepartmentIdList(Long departmentId) {
        List<Long> ids = new ArrayList<>();
        int length = 0;

        if (isFirst) {
            length = 3;
            isFirst = false;
        }
        else {
            length = (int) (Math.random() * 4);
        }

        for (int i = 0; i < length; i++) {
            long id = (long) (1 + Math.random() * 10000);
            ids.add(id);
        }
        return ids;
    }
}
