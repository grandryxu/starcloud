package com.xywg.admin.modular.report.controller;

import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.project.service.IProjectWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 项目在场统计(工种 或者 班组)
 * @date 2019/6/4
 * @author  hh cao
 */
@Controller
@RequestMapping("/projectWorker/report")
public class ProjectWorkerReportController extends BaseController {

    private final String PREFIX = "/report/projectWorker/";

    @Autowired
    private IProjectWorkerService projectWorkerService;

    /**
     * 班组统计
     * @return
     */
    @RequestMapping("/teamPage")
    public String jumpTeamPage() {
        return PREFIX + "teamReport.html";
    }

    /**
     * 工种统计
     * @return
     */
    @RequestMapping("/workerKindPage")
    public String jumpWorkerKindPage() {
        return PREFIX + "workerKindReport.html";
    }

    /**
     * 获取信息
     * @return
     */
    @RequestMapping(value="/list", method = RequestMethod.POST)
    @ResponseBody
    public Object list(@RequestParam("type") String type) {
        String projectCode = ShiroKit.getSessionAttr("toggleProjectCode");
        return projectWorkerService.listReport(projectCode, type);
    }
}
