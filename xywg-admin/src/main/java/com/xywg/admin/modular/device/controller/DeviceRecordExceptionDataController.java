package com.xywg.admin.modular.device.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.modular.device.wrapper.DeviceExceptionWarpper;
import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import com.xywg.admin.modular.system.model.Banner;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.modular.system.warpper.BannerWarpper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.xywg.admin.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.xywg.admin.modular.device.model.DeviceRecordExceptionData;
import com.xywg.admin.modular.device.service.IDeviceRecordExceptionDataService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 考勤机异常控制器
 *
 * @author wangcw
 * @Date 2018-06-12 21:00:56
 */
@Controller
@RequestMapping("/deviceRecordExceptionData")
public class DeviceRecordExceptionDataController extends BaseController {
    @Autowired
    private IDictService dictService;
    private String PREFIX = "/device/deviceRecordExceptionData/";
    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;
    @Autowired
    private IDeviceRecordExceptionDataService deviceRecordExceptionDataService;

    /**
     * 跳转到考勤机异常首页
     */
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        model.addAttribute("dictSource", dictService.selectByName("考勤类型"));
        return PREFIX + "deviceRecordExceptionData.html";
    }

    /**
     * 跳转到添加考勤机异常
     */
    @RequestMapping("/deviceRecordExceptionData_add")
    public String deviceRecordExceptionDataAdd() {
        return PREFIX + "deviceRecordExceptionData_add.html";
    }

    /**
     * 跳转到修改考勤机异常
     */
    @RequestMapping("/deviceRecordExceptionData_update/{deviceRecordExceptionDataId}")
    public String deviceRecordExceptionDataUpdate(@PathVariable Integer deviceRecordExceptionDataId, Model model) {
        DeviceRecordExceptionData deviceRecordExceptionData = deviceRecordExceptionDataService.selectById(deviceRecordExceptionDataId);
        model.addAttribute("item",deviceRecordExceptionData);
        LogObjectHolder.me().set(deviceRecordExceptionData);
        return PREFIX + "deviceRecordExceptionData_edit.html";
    }
    /**
     * 跳转到考勤异常详情
     */
    @RequestMapping("/deviceRecordExceptionData_view/{deviceRecordExceptionDataId}")
    public String deviceRecordExceptionDataInfo(@PathVariable Integer deviceRecordExceptionDataId, Model model) {
        Map<String,Object> map= deviceRecordExceptionDataService.getExceptionDeviceById(deviceRecordExceptionDataId);
        model.addAttribute("item",map);
        LogObjectHolder.me().set(map);
        return PREFIX + "deviceRecordExceptionData_info.html";
    }

    /**
     * 跳转到处理考勤异常
     */
    @RequestMapping("/deviceRecordExceptionData_action/{deviceRecordExceptionDataId}")
    public String deviceRecordExceptionDataAction(@PathVariable Integer deviceRecordExceptionDataId, Model model) {
        Map<String,Object> map= deviceRecordExceptionDataService.getExceptionDeviceById(deviceRecordExceptionDataId);
        model.addAttribute("item",map);
        model.addAttribute("dictIdCardType", dictService.selectByName("人员证件类型"));
        LogObjectHolder.me().set(map);
        return PREFIX + "deviceRecordExceptionData_action.html";
    }







    /**
     * 获取考勤机异常列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {
        Page<Banner> page = new PageFactory<Banner>().defaultPage();
        List<Map<String, Object>> deviceExceptions=deviceRecordExceptionDataService.selectDeviceExceptions(page,map);
        page.setRecords((List<Banner>) new DeviceExceptionWarpper(deviceExceptions).warp());
        return super.packForBT(page);
    }


    /**
     * 新增考勤机异常
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(DeviceRecordExceptionData deviceRecordExceptionData) {
        deviceRecordExceptionDataService.insert(deviceRecordExceptionData);
        return SUCCESS_TIP;
    }

    /**
     * 新增考勤机异常
     */
    @RequestMapping(value = "/action")
    @ResponseBody
    public Object action(@RequestParam Map<String,Object> map) {
        deviceRecordExceptionDataService.action(map);
        return SUCCESS_TIP;
    }


    /**
     * 删除考勤机异常
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Map<String,Object> map) {
        deviceRecordExceptionDataService.deleteByIds(map);
        return SUCCESS_TIP;
    }

    /**
     * 启用考勤机禁用时考勤记录
     */
    @RequestMapping(value = "/changeStatus")
    @ResponseBody
    public Object changeStatus(@RequestParam Integer id) {
        deviceRecordExceptionDataService.changeStatus(id);
        return SUCCESS_TIP;
    }
    /**
     * 修改考勤机异常
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(DeviceRecordExceptionData deviceRecordExceptionData) {
        deviceRecordExceptionDataService.updateById(deviceRecordExceptionData);
        return SUCCESS_TIP;
    }

}
