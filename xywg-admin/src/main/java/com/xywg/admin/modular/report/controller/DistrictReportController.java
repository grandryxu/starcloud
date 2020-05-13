package com.xywg.admin.modular.report.controller;

import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.modular.report.service.DistrictReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 *
 * @author jingyun_hu
 * @date 2018/6/19
 * 区域统计模块
 */
@Controller
@RequestMapping("/districtReport")
public class DistrictReportController extends BaseController {
    private String PREFIX = "/report/districtReport/";
    @Autowired
    private  DistrictReportService  districtReportService;


    /**
     * 跳转到区域统计首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "districtReport.html";
    }


    /**
     * 根据公司id按区域统计项目数量
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) Integer deptId) {
        return districtReportService.getReportListByDistrictAndDeptId(deptId);
    }

}
