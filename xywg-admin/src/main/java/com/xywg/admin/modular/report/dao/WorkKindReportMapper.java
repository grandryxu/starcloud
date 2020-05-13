package com.xywg.admin.modular.report.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.report.model.WorkKindReport;

import java.util.List;

/**
 *
 * @author jingyun_hu
 * @date 2018/6/19
 */
public interface WorkKindReportMapper extends BaseMapper<WorkKindReport> {


    /**
     * 获取工种分布
     * @param deptIds  部门id
     * @return
     * @author yuanyang
     */
    List<WorkKindReport> getWorkKindList(List<String> deptIds);
}
