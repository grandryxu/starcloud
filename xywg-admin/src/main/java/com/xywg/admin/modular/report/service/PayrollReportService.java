package com.xywg.admin.modular.report.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.report.model.PayrollReport;
import com.xywg.admin.modular.report.model.WorkerPayrollDetailReport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 工资统计service
 *
 * @author hujingyun
 */
public interface PayrollReportService{

    /**
     * 根据部门id(公司id)查询工资统计列表
     *
     * @return
     */
    List<PayrollReport> getReportListByDeptId(Page<PayrollReport> page,Integer deptId);

    /**
     * 根据项目编号查询和工人信息 查询工人工资明细
     * @return
     */
    List<WorkerPayrollDetailReport>  getDetailByProjectCodeAndWorkerInfo(Page<WorkerPayrollDetailReport> page, Map<String,Object> map);

    /**
     *@Description:获取导出数据list
     *@Author xieshuaishuai
     *@Date 2018/7/5 20:31
     */
    List<Map<String,Object>> getExportList(Map<String, Object> params);

    /**
     *@Description:导出
     *@Author xieshuaishuai
     *@Date 2018/7/5 20:23
     */
    void export(HttpServletResponse res, HttpServletRequest req, Map<String, Object> params);

}
