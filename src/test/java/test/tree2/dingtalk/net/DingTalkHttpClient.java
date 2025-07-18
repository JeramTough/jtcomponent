package test.tree2.dingtalk.net;

import java.util.List;

/**
 * 钉钉网络通信客户端，负责调用钉钉的API
 * Created on 2019/6/11 15:03
 * by @author JeramTough
 */
public interface DingTalkHttpClient {

    /**
     * 获取子部门Id列表
     *
     * @param departmentId 1为根部门
     */
    List<Long> getSubDepartmentIdList(Long departmentId);

}

