package com.xywg.admin.modular.report.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.modular.device.service.IDeviceRecordService;
import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import com.xywg.admin.modular.team.service.ITeamMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工人考勤统计
 * @date 2019/5/5
 * @author hh cao
 */
@Controller
@RequestMapping("/workerAttendanceReport")
public class WorkerAttendanceReportController extends BaseController {
    private final String PREFIX = "/report/attendanceReport/";

    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;

    @Autowired
    private ITeamMasterService teamMasterService;

    @Autowired
    private IDeviceRecordService recordService;

    /**
     * 跳转到考勤报表统计页面
     * @param model
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String jump(Model model) {
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        return PREFIX + "attendanceReport.html";
    }

    /**
     * 通过项目编号获取班组列表
     * @param projectCode
     * @return
     */
    @RequestMapping(value = "/getTeams", method = RequestMethod.POST)
    @ResponseBody
    public Object getTeams(@RequestParam("projectCode") String projectCode) {
        return teamMasterService.getTeamMasterByProjectCode(projectCode);
    }

    /**
     * 获取列表
     * @param map
     * @return
     */
    @RequestMapping("/getList")
    @ResponseBody
    public Object getList(@RequestParam Map<String, Object> map) {
        Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
        List<Map<String, Object>> list = recordService.getReport(page, map);
        page.setRecords(list);
        return super.packForBT(page);
    }

    /**
     * 导出
     * @param response
     */
    @RequestMapping("/export")
    public void export(@RequestParam("teamCode") String teamCode, @RequestParam("projectCode") String projectCode, @RequestParam("searchMonth") String searchMonth, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        map.put("teamCode", teamCode);
        map.put("projectCode", projectCode);
        map.put("searchMonth", searchMonth);
        recordService.exportReport(map, response);
    }
}
