package com.xywg.admin.modular.report.controller;

import com.xywg.admin.modular.report.service.WorkKindReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/workKindReport")
public class WorkKindReportController {
    @Autowired
    private WorkKindReportService workKindReportService;
    private String PREFIX = "/report/workKindReport/";

    /**
     * 跳转到工种统计首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "workKindReport.html";
    }

    /**
     * 根据公司id按区域统计工种分布
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) Integer deptId) {

        return workKindReportService.getWorkKindList(deptId);
    }

}
