package com.xywg.admin.modular.report.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.modular.company.service.IContractorWorkerService;
import com.xywg.admin.modular.device.service.IDeviceRecordService;
import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import com.xywg.admin.modular.project.service.IProjectWorkerService;
import com.xywg.admin.modular.projectSubContractor.service.IProjectSubContractorService;
import com.xywg.admin.modular.report.model.DeviceReport;
import com.xywg.admin.modular.report.model.DeviceReportVo;
import com.xywg.admin.modular.report.service.IDeviceReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 启动新考勤报表controller
 * @author hh cao
 * @date 2019/4/22
 */
@Controller
@RequestMapping("/deviceReportNew")
public class DeviceReportNewController extends BaseController {
    private String PREFIX = "/report/deviceReportNew/";

    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;

    @Autowired
    private IDeviceReportService deviceReportService;

    @Autowired
    private IProjectSubContractorService projectSubContractorService;

    @Autowired
    private IProjectWorkerService projectWorkerService;

    @Autowired
    private IDeviceRecordService deviceRecordService;

    @Autowired
    private IContractorWorkerService contractorWorkerService;
    /**
     * 跳转到考勤统计首页
     */
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        return PREFIX + "deviceReport.html";
    }

    /**
     * 跳转到添加考勤统计
     */
    @RequestMapping("/deviceReport_add")
    public String deviceReportAdd() {
        return PREFIX + "deviceReport_add.html";
    }

    /**
     * 跳转到考勤统计详情页
     * hujingyun
     */
    @RequestMapping("/deviceReportDetail")
    public String deviceReportDetail() {
        return PREFIX + "deviceReport_detail.html";
    }


    /**
     * 跳转到修改考勤统计
     */
    @RequestMapping("/deviceReport_update/{deviceReportId}")
    public String deviceReportUpdate(@PathVariable Integer deviceReportId, Model model) {
        DeviceReport deviceReport = deviceReportService.selectById(deviceReportId);
        model.addAttribute("item",deviceReport);
        LogObjectHolder.me().set(deviceReport);
        return PREFIX + "deviceReport_edit.html";
    }

    /**
     * 获取考勤统计列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {
        Page<DeviceReportVo> page = new PageFactory<DeviceReportVo>().defaultPage();
        List<DeviceReportVo> drList = deviceReportService.getList(page, map);
        page.setRecords(drList);
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
        deviceReportService.downloadNew(res,req,params);
    }


    /**
     * 新增考勤统计
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(DeviceReport deviceReport) {
        deviceReportService.insert(deviceReport);
        return SUCCESS_TIP;
    }

    /**
     * 删除考勤统计
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer deviceReportId) {
        deviceReportService.deleteById(deviceReportId);
        return SUCCESS_TIP;
    }

    /**
     * 修改考勤统计
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(DeviceReport deviceReport) {
        deviceReportService.updateById(deviceReport);
        return SUCCESS_TIP;
    }

    /**
     * 考勤统计详情
     */
    @RequestMapping(value = "/detail/{deviceReportId}")
    @ResponseBody
    public Object detail(@PathVariable("deviceReportId") Integer deviceReportId) {
        return deviceReportService.selectById(deviceReportId);
    }

    /**
     *
     * @description 考勤统计
     * @author chupp
     * @date 2018年6月16日
     * @return
     *
     */
    @RequestMapping(value = "/deviceReportDeal")
    @ResponseBody
    public Object deviceReportDeal() {
        deviceReportService.deviceReportDeal();
        return SUCCESS_TIP;
    }

    /**
     *
     * @description 获取进场项目数
     * @author chupp
     * @date 2018年6月21日
     * @return
     *
     */
    @RequestMapping(value = "/getTotalEntry")
    @ResponseBody
    public Object getTotalEntry() {
        return projectSubContractorService.getTotalEntry();
    }

    /**
     *
     * @description 获取退场项目数
     * @author chupp
     * @date 2018年6月21日
     * @return
     *
     */
    @RequestMapping(value = "/getTotalExit")
    @ResponseBody
    public Object getTotalExit() {
        return projectSubContractorService.getTotalExit();
    }

    /**
     *
     * @description 获取进场工人数
     * @author chupp
     * @date 2018年6月21日
     * @return
     *
     */
    @RequestMapping(value = "/getTotalJoin")
    @ResponseBody
    public Object getTotalJoin() {
        return projectWorkerService.getTotalJoin();
    }

    /**
     *
     * @description 获取今日考勤人数
     * @author chupp
     * @date 2018年6月21日
     * @return
     *
     */
    @RequestMapping(value = "/getTotalDevice")
    @ResponseBody
    public Object getTotalDevice() {
        return deviceRecordService.getTotalDevice();
    }

    /**
     *
     * @description 获取项目当天考勤情况
     * @author chupp
     * @date 2018年6月22日
     * @return
     *
     */
    @RequestMapping(value = "/getProjectDevice")
    @ResponseBody
    public Object getProjectDevice() {
        return deviceRecordService.getProjectDevice();
    }

    /**
     *
     * @description 获取人员年龄分布
     * @author chupp
     * @date 2018年6月22日
     * @return
     *
     */
    @RequestMapping(value = "/getAgeRange")
    @ResponseBody
    public Object getAgeRange() {
        return contractorWorkerService.getAgeRange();
    }

    /**
     *
     * @description 获取人员籍贯分布
     * @author chupp
     * @date 2018年6月22日
     * @return
     *
     */
    @RequestMapping(value = "/getPlaceCodeRange")
    @ResponseBody
    public Object getPlaceCodeRange() {
        return contractorWorkerService.getPlaceCodeRange();
    }

    /**
     *
     * @description 获取项目人员分布
     * @author chupp
     * @date 2018年6月22日
     * @return
     *
     */
    @RequestMapping(value = "/getProjectJoinRange")
    @ResponseBody
    public Object getProjectJoinRange() {
        return projectWorkerService.getProjectJoinRange();
    }

    /**
     * 跳转设置加班页面
     */
    @RequestMapping("/deviceReport_over/{deviceReportId}")
    public String deviceReportOver(@PathVariable Integer deviceReportId, Model model) {
        DeviceReport deviceReport = deviceReportService.selectById(deviceReportId);
        model.addAttribute("item", deviceReport);
        LogObjectHolder.me().set(deviceReport);
        return PREFIX + "deviceReport_overtime.html";
    }

    /**
     * 设置
     */
    @RequestMapping(value = "/overTime")
    @ResponseBody
    public Object overTime(DeviceReport deviceReport) {
        deviceRecordService.setData(deviceReport);
        return SUCCESS_TIP;
    }
}
