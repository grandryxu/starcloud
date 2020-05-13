package com.xywg.admin.modular.report.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.report.model.PayrollReport;
import com.xywg.admin.modular.report.model.WorkerPayrollDetailReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 * @author jingyun_hu
 * @date 2018/6/19
 */
public interface PayrollReportMapper{

    /**
     * 查询当前部门以及子部门下的工资统计数据
     * @param deptIds
     * @return
     */
    List<PayrollReport> getReportListByOrganizationCodeList(@Param("page")Page<PayrollReport> page,@Param("deptIds")List<String> deptIds);

    /**
     *@Description:导出
     *@Author xieshuaishuai
     *@Date 2018/7/5 20:28
     */
    List<Map<String,Object>> getExportList(@Param("deptIds")List<String> deptIds);

    /**
     * 查询当前项目下工人工资明细
     * @return
     */
    List<WorkerPayrollDetailReport> getDetailByProjectCodeAndWorkerInfo(@Param("page") Page<WorkerPayrollDetailReport> page,@Param("map")Map<String,Object> map);

}
