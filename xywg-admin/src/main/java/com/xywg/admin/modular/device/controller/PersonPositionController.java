package com.xywg.admin.modular.device.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.modular.device.model.PersonPosition;
import com.xywg.admin.modular.device.service.IPersonPositionService;
import com.xywg.admin.modular.device.wrapper.PersonPositionWarpper;
import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工人运动轨迹查询
 * @author duanfen
 *
 */
@Controller
@RequestMapping("/position")
public class PersonPositionController extends BaseController {

    private String PREFIX = "/worker/personPosition/";

    @Autowired
    private IPersonPositionService personPositionService;
    
    @Autowired
    private IWorkerMasterService workerMasterService;

    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;
    /**
     * 跳转到考勤机管理首页
     */
    @RequestMapping("")
    public String index(@RequestParam Map map ,Model model) {
    	model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        return PREFIX + "personPosition.html";
    }

    /**
     * 获取考勤机管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String,Object> map) {
        Page<WorkerMaster> page = new PageFactory<WorkerMaster>().defaultPage();
        List<Map<String,Object>> worker= workerMasterService.queryWorkerByPosition(map, page);
        page.setRecords((List<WorkerMaster>) new PersonPositionWarpper(worker).warp());
        return super.packForBT(page);
    }
    
    /**
     * 查看地图轨迹
     */
    @RequestMapping(value = "/detail")
    public String detail(@RequestParam(value ="idCardNumber") String idCardNumber, @RequestParam(value = "shImei", required = false)  String shImei,Model model) {
        model.addAttribute("idCardNumber", idCardNumber);
        model.addAttribute("shImei", shImei == null?"":shImei);
        return PREFIX + "personPosition_map.html";
    }

    /**
     * 查看地图轨迹
     * @return
     */
    @RequestMapping(value = "/getDetail", method = RequestMethod.POST)
    @ResponseBody
    public List<PersonPosition> getDetail(@RequestParam(value ="idCardNumber") String idCardNumber,
                                          @RequestParam(value = "shImei", required = false)  String shImei,
                                          @RequestParam(value = "startDate") String startDate,
                                          @RequestParam(value = "endDate") String endDate) {
        return personPositionService.queryPositionByImei(idCardNumber,shImei,startDate,endDate);
    }


    /**
     * 考勤机管理详情
     */
    @RequestMapping(value = "/detail/{deviceId}")
    @ResponseBody
    public Object detail(@PathVariable("deviceId") Integer deviceId) {
//        return deviceService.selectById(deviceId);
    	return null;
    }

    

}
