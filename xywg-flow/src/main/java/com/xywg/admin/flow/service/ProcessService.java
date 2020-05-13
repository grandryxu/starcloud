package com.xywg.admin.flow.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.flow.entity.Order;
import com.xywg.admin.flow.entity.Process;

import java.util.List;
import java.util.Map;

/**
 * @author 严鹏
 * @date 2019/3/31
 */
public interface ProcessService {

    /**
     * 创建流程
     * @param process 流程本体
     * @return 1-成功 0-失败
     * @throws Exception 见异常信息
     */
    int insert(Process process,Integer userId,Integer deptId,List<Integer> roleIds,String projectId) throws Exception;
    int update(Process process,Integer userId,Integer deptId,List<Integer> roleIds) throws Exception;

    /**
     * 发起一道流程
     * @param processId 流程id
     * @return 发起的申请
     * @throws Exception 见异常信息
     */
    Order start(String processId,Integer userId,Integer deptId,List<Integer> roleIds) throws Exception;

    List<Process> getAll();

    Process getOne(Process process,Integer userId,Integer deptId,List<Integer> roleIds);
    Process getOneMax(Process process,Integer userId,Integer deptId,List<Integer> roleIds);

    List<Map<String,Object>> getOneDetail(String processId);

    void delete1(String id);
	List<Process> getAllNotByType();

    List<Process> selectList(Map<String, Object> map, Page<Process> page);
}
