package com.xywg.admin.modular.report.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.report.model.PersonJoinReport;
import com.xywg.admin.modular.worker.model.WorkerMasterVO;

/**
 * 人员进退场统计数据访问接口
 * @author wangshibo
 *	2018年6月20日
 * 下午3:49:35
 */
public interface PersonJoinReportMapper extends BaseMapper<PersonJoinReport> {
	/**
	 * 查询人员进退场列表
	 * 2018年6月20日
	 *下午3:50:25
	 *@author wangshibo
	 *
	 */
	List<PersonJoinReport> getReportList(@Param("page") Page<PersonJoinReport> page, @Param("deptIds") List<String> deptIds, @Param("searchTime") String searchTime);

	/**
	 * 导出查询
	 * @param list
	 * @return
	 * @author yuanyang
	 */
    List<Map<String,Object>> getPersonJoinReportsList(@Param("deptIds")List<String> list);
}
