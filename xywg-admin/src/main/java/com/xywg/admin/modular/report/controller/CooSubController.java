package com.xywg.admin.modular.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.modular.company.service.IContractorWorkerService;
import com.xywg.admin.modular.device.service.IDeviceRecordService;
import com.xywg.admin.modular.project.service.IProjectWorkerService;
import com.xywg.admin.modular.projectSubContractor.service.IProjectSubContractorService;

/**
 * 
 * @description 参建单位首页
 * 
 * @author chupp
 *
 * @date 2018年6月23日
 *
 */
@Controller
@RequestMapping("/cooSub")
public class CooSubController extends BaseController {

    private String PREFIX = "/report/";

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
    	model.addAttribute("getTotalEntry",projectSubContractorService.getTotalEntry());
    	model.addAttribute("getTotalExit",projectSubContractorService.getTotalExit());
    	model.addAttribute("getTotalJoin",projectWorkerService.getTotalJoin());
    	model.addAttribute("getTotalDevice",deviceRecordService.getTotalDevice());
        return PREFIX + "coopration_sub_contractor.html";
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
}
