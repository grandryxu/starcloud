package com.xywg.admin.modular.report.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.report.model.WorkerOverAgeReport;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 查询超龄人员
 * @author duanfen
 * @date 2019/6/11
 */
public interface OverAgeReportMapper extends BaseMapper<WorkerOverAgeReport> {


    /**
     * 查询超龄人员
     * @author duanfen
     * @param page 
     */
    List<WorkerOverAgeReport> getWorkerList(Page<WorkerOverAgeReport> page, @Param("map") Map<String, Object> map);

	List<WorkerOverAgeReport> getExportWorkerList(@Param("map")Map<String, Object> params);
}
