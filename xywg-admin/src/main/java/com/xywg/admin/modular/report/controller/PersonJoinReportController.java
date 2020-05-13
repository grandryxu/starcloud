package com.xywg.admin.modular.report.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.modular.report.model.PersonJoinReport;
import com.xywg.admin.modular.report.service.PersonJoinReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/personJoinReport")
public class PersonJoinReportController extends BaseController {
    private String PREFIX = "/report/personJoinReport/";

    @Autowired
    private PersonJoinReportService personJoinReportService;

    /**
     * 跳转到区域统计首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "personJoinReport.html";
    }


    /**
     * 统计人员进退场
     * 2018年6月23日
     * 下午2:16:42
     *
     * @author wangshibo
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {
        Page<PersonJoinReport> page = new PageFactory<PersonJoinReport>().defaultPage();
        page.setRecords(personJoinReportService.getReportList(page, map));
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
        personJoinReportService.download(res,req,params);
    }

}
