package com.xywg.admin.modular.report.controller;

import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.system.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.modular.device.model.DeviceRecord;
import com.xywg.admin.modular.device.service.IDeviceRecordService;
import com.xywg.admin.modular.project.service.IProjectWorkerService;

/**
 * 项目首页统计控制类
 * @author wangshibo
 *	2018年6月20日
 * 上午8:56:45
 */
@Controller
@RequestMapping("/projectIndexReprot")
public class ProjectIndexReprotContoller extends BaseController {

    private String PREFIX = "/report/projectIndexReprot/";
    @Autowired
    private  IDeviceRecordService  deviceRecordService;
    @Autowired
    private IProjectWorkerService projectWorkerService;
    @Autowired
    private IDeptService deptService;

    /**
     * 跳转到项目首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "projectIndexReprot.html";
    }

    /**
     * 统计7天出勤人数
     * 2018年6月20日
     *上午9:56:46
     *@author wangshibo
     *
     */
    @RequestMapping(value = "/getTotalNumFromSevenDays")
    @ResponseBody
    public Object getTotalNumFromSevenDays(@RequestParam String projectCode) {
        return deviceRecordService.getTotalNumFromSevenDays(deptService.selectById(ShiroKit.getUser().getDeptId()).getSocialCreditNumber(),projectCode);
    }


    /**
     * 获取项目班组数
     * @return
     */
    @RequestMapping(value = "/getTeamCount")
    @ResponseBody
    public Object getTeamCount(@RequestParam String projectCode){
        return projectWorkerService.getTeamCount(null,projectCode);
    }

    /**
     * 获取项目进场人数
     * @return
     */
    @RequestMapping(value = "/getJoinedCount")
    @ResponseBody
    public Object getJoinedCount(@RequestParam String projectCode){
        return projectWorkerService.getJoinedCount(deptService.selectById(ShiroKit.getUser().getDeptId()).getSocialCreditNumber(),projectCode);
    }


    /**
     * 获取工人工种分布
     * @return
     */
    @RequestMapping(value = "/getWorkTypeCount")
    @ResponseBody
    public Object getWorkTypeCount(@RequestParam String projectCode){
        return projectWorkerService.getWorkTypeCount(null,projectCode);
    }

    /**
     * 获取籍贯分布
     * 2018年6月20日
     *下午1:46:01
     *@author wangshibo
     *
     */
    @RequestMapping(value = "/getBirthPlaceCount")
    @ResponseBody
    public Object getBirthPlaceCount(){
        return projectWorkerService.getBirthPlaceCount(null, ShiroKit.getSessionAttr("toggleProjectCode"));
    }


    /**
     * 获取籍贯分布
     * @auth caiwei
     */
    @RequestMapping(value = "/getBirthPlaceCountPC")
    @ResponseBody
    public Object getBirthPlaceCountPC(){
        return projectWorkerService.getBirthPlaceCountPC(null, ShiroKit.getSessionAttr("toggleProjectCode"));
    }


    /**
     * 获取工人性别分布
     * @return
     */
    @RequestMapping(value = "/getGenderCount")
    @ResponseBody
    public Object getGenderCount(@RequestParam String projectCode){
        return projectWorkerService.getGenderCount(null,projectCode);
    }
    
    /**
     * 统计项目今日考勤人数
     * 2018年6月23日
     *下午2:12:31
     *@author wangshibo
     *
     */
    @RequestMapping(value = "/getWorkerTotalCount")
    @ResponseBody
    public Object getWorkerTotalCount(@RequestParam String projectCode){
    	DeviceRecord deviceRecord = new DeviceRecord();
    	deviceRecord.setProjectCode(projectCode);
        return deviceRecordService.getWorkerTotalCount(deviceRecord);
    }

    /**
     * 获取年龄统计
     * 2018年7月25日
     *下午2:12:31
     *@author 蔡伟
     *
     */
    @RequestMapping(value = "/getAgeRange")
    @ResponseBody
    public Object getAgeRange(){
        return projectWorkerService.getAgeRange(ShiroKit.getSessionAttr("toggleProjectCode"));
    }
}
