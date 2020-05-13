package com.xywg.admin.modular.report.dao;

import java.util.List;
import java.util.Map;

import com.xywg.admin.modular.report.model.WorkerReport;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.worker.model.WorkerMasterVO;

/* 人员统计 Mapper 接口
 * </p>
 *
 * @author shily
 * @since 2018-06-20
 */
public interface IWorkerReportMapper extends BaseMapper<WorkerMasterVO> {

	/**
	 * 查询人员统计分页列表
	 * @param page
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectWorkerReport(@Param("page") Page<WorkerMasterVO> page, @Param("map") Map<String, Object> map);

	/**
	 * 查询人员统计无分页列表
	 * @param map
	 * @return
	 */
    List<WorkerReport> getWorkerReport(@Param("map") Map<String, Object> map);
}


