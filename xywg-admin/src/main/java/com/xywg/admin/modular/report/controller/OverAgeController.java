package com.xywg.admin.modular.report.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.modular.project.model.Injury;
import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import com.xywg.admin.modular.project.wrapper.InjuryWrapper;
import com.xywg.admin.modular.report.model.PayrollReport;
import com.xywg.admin.modular.report.model.WorkerOverAgeReport;
import com.xywg.admin.modular.report.service.IOverAgeReportService;
import com.xywg.admin.modular.report.service.WorkKindReportService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/overAgeReport")
public class OverAgeController  extends BaseController{
    @Autowired
    private IOverAgeReportService overAgeReportService;
    
    private String PREFIX = "/report/overAgeReport/";
    
    
    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;

    /**
     * 跳转到统计首页
     */
    @RequestMapping("")
    public String index(Model model) {
    	model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        return PREFIX + "overAgeReport.html";
    }

    /**
     * 根据公司id按区域统计工种分布
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {
        Page<WorkerOverAgeReport> page = new PageFactory<WorkerOverAgeReport>().defaultPage();
	   	List<WorkerOverAgeReport> list = overAgeReportService.getWorkerList(page,map);
	   	page.setRecords(list);
	   	return super.packForBT(page);
    }
    /**
     * 导出
     *
     * @param res
     * @param req
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public void downloadFile(HttpServletResponse res, HttpServletRequest req, @RequestParam Map<String, Object> params) {
    	overAgeReportService.download(res,req,params);
    }

}
