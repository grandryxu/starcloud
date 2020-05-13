package com.xywg.admin.modular.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.modular.report.service.BirthPlaceReportService;

/**
 * 籍贯统计控制类
 * @author wangshibo
 *	2018年6月20日
 * 上午11:07:08
 */
@Controller
@RequestMapping("/birthPlaceReport")
public class BirthPlaceReportController extends BaseController {
	 private String PREFIX = "/report/birthPlaceReport/";
	    @Autowired
	    private  BirthPlaceReportService  birthPlaceReportService;


	    /**
	     * 跳转到籍贯统计首页
	     */
	    @RequestMapping("")
	    public String index() {
	        return PREFIX + "birthPlaceReport.html";
	    }


	    /**
	     * 根据公司id按区域统计项目数量
	     */
	    @RequestMapping(value = "/list")
	    @ResponseBody
	    public Object list(@RequestParam(required = false) Integer deptId) {
	        return birthPlaceReportService.birthPlaceReport(deptId);
	    }
}
