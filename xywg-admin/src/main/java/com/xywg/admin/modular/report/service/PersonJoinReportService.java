package com.xywg.admin.modular.report.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.report.model.PersonJoinReport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 人员进退场数据访问接口
 * @author wangshibo
 *	2018年6月20日
 * 下午3:45:14
 */
public interface PersonJoinReportService extends IService<PersonJoinReport> {

	/**
	 * 查询列表
	 * 2018年6月20日
	 *下午3:51:26
	 *@author wangshibo
	 *
	 */
	List<PersonJoinReport> getReportList(Page<PersonJoinReport> page,Map<String, Object> map);


	/**
	 * 导出
	 * @param params
	 * @return
	 * @author yuanyang
	 */
	List<Map<String,Object>> getPersonJoinReportsList(Map<String, Object> params);

    /**
     * 导出
     * @param res
     * @param req
     * @param params
     * @author yuanyang
     */
    void download(HttpServletResponse res, HttpServletRequest req, Map<String, Object> params);
}
