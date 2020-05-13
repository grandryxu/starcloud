package com.xywg.admin.modular.report.controller;


import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import com.xywg.admin.core.excel.ExcelUtils;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.util.DateUtils;
import com.xywg.admin.modular.company.utils.ServletsUtils;
import com.xywg.admin.modular.projectSubContractor.model.ProjectSubContractor;
import com.xywg.admin.modular.report.model.WorkerReport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.modular.report.service.IWorkerReportService;
import com.xywg.admin.modular.report.warpper.IWorkerReportWarpper;
import com.xywg.admin.modular.worker.model.WorkerMasterVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author shily
 * @date 2018/6/20
 * 人员统计模块
 */
@Controller
@RequestMapping("iworkerReport")
public class IWorkerReportController extends BaseController {
    private String PREFIX = "/report/workerReport/";
    private static  Logger log = LoggerFactory.getLogger(IWorkerReportController.class);
    @Autowired
    private IWorkerReportService workerReportService;

    /**
     * 跳转到人员统计首页
     */
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("deptId", ShiroKit.getUser().getDeptId());
        return PREFIX + "workerReport.html";
    }

    /**
     * 获取人员统计列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {
    	 Page<WorkerMasterVO> page = new PageFactory<WorkerMasterVO>().defaultPage();
         List<Map<String, Object>> workerReport = workerReportService.selectWorkerReport(page, map);
         page.setRecords((List<WorkerMasterVO>) new IWorkerReportWarpper(workerReport).warp());
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
        List<WorkerReport> workerReports = this.workerReportService.getWorkerReport(params);
        //构建下载文件时的文件名
        String fileName = "人员统计一览" + DateUtils.getDate("yyyyMMddHHmmss");
        boolean isMSIE = ServletsUtils.isMSBrowser(req);
//        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            if (isMSIE) {
                //IE浏览器的乱码问题解决
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                //万能乱码问题解决
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }
            res.setContentType("application/vnd.ms-excel");
            res.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"+.xlsx");
            os = res.getOutputStream();
            ExcelUtils.getInstance().exportObjects2Excel(workerReports, WorkerReport.class, true, os);
        } catch (Exception e) {
        	log.error(e.getMessage());
        } finally {
//            if (bis != null) {
//                try {
//                    bis.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }

}
