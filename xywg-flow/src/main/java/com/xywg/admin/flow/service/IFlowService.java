package com.xywg.admin.flow.service;

import java.util.List;
import java.util.Map;

/**
 * @author 严鹏
 * @date 2019/7/17
 */
public abstract class IFlowService {

    public abstract List<Map<String,Object>> getAllUser();
    public abstract List<Map<String,Object>> getAllDept();
    public abstract List<Map<String,Object>> getAllRole();

    public abstract Object insert1(String displayName, String lists);

    public abstract Object xiugai(String displayName, String lists,String id);

    public abstract Object delete(String id);
}
