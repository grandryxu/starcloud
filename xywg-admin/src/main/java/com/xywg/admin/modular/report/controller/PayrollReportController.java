package com.xywg.admin.modular.report.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.modular.report.model.PayrollReport;
import com.xywg.admin.modular.report.model.WorkerPayrollDetailReport;
import com.xywg.admin.modular.report.service.PayrollReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 *
 * @author jingyun_hu
 * @date 2018/6/19
 * 工资统计模块
 */
@Controller
@RequestMapping("/payrollReport")
public class PayrollReportController extends BaseController {
    private String PREFIX = "/report/payrollReport/";
    @Autowired
    private PayrollReportService payrollReportService;


    /**
     * 跳转到工资统计首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "payrollReport.html";
    }

    /**
     * 跳转到工资详细统计
     */
    @RequestMapping("detail")
    public String detail() {
        return PREFIX + "payrollReportDetail.html";
    }

    /**
     * 根据项目code 获取工资统计
     * @param deptId
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) Integer deptId) {
        Page<PayrollReport> page = new PageFactory<PayrollReport>().defaultPage();
        List<PayrollReport>   payrollReportList =payrollReportService.getReportListByDeptId(page,deptId);
        page.setRecords(payrollReportList);
        return super.packForBT(page);
    }


    /**
     * 根据项目编号查询和工人信息 查询工人工资明细
     *  projectCode 项目编号  必须参数
     *  workerInfo 工人信息   可选参数
     * @return
     */
    @RequestMapping(value = "/getDetailByProjectCodeAndWorkerInfo")
    @ResponseBody
    public Object getDetailByProjectCodeAndWorkerInfo(@RequestParam Map<String,Object> map) {
        Page<WorkerPayrollDetailReport> page = new PageFactory<WorkerPayrollDetailReport>().defaultPage();
        List<WorkerPayrollDetailReport> list = payrollReportService.getDetailByProjectCodeAndWorkerInfo(page, map);
        page.setRecords(list);
        return super.packForBT(page);
    }
    /**
     *@Description:导出
     *@Author xieshuaishuai
     *@Date 2018/7/5 20:21
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ResponseBody
    public void downloadFile(HttpServletResponse res, HttpServletRequest req, @RequestParam Map<String, Object> params) {
        payrollReportService.export(res,req,params);
    }

}
