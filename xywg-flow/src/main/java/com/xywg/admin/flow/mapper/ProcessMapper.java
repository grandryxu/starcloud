package com.xywg.admin.flow.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.flow.entity.Process;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 严鹏
 * @date 2019/3/31
 */
@Mapper
public interface ProcessMapper{

    int findCountByProcessName(Process process);

    Boolean isMaxVersionByProcessId(@Param("processId") String processId);

    List<Process> getAll(Map<String, Object> params);

    Process getOne(Process process);

    int insert(Process process);

    Process selectById(String processId);

    void delete(String id);

    Process getOneMax(Process process);

    List<Process> selectList(@Param("map")Map<String, Object> map, Page<Process> page);
}
