package com.xywg.admin.modular.device.controller;

import com.xywg.admin.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.xywg.admin.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.xywg.admin.modular.device.model.DeviceType;
import com.xywg.admin.modular.device.service.IDeviceTypeService;

/**
 * 考勤机类型控制器
 *
 * @author wangcw
 * @Date 2018-06-12 21:00:38
 */
@Controller
@RequestMapping("/deviceType")
public class DeviceTypeController extends BaseController {

    private String PREFIX = "/device/deviceType/";

    @Autowired
    private IDeviceTypeService deviceTypeService;

    /**
     * 跳转到考勤机类型首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "deviceType.html";
    }

    /**
     * 跳转到添加考勤机类型
     */
    @RequestMapping("/deviceType_add")
    public String deviceTypeAdd() {
        return PREFIX + "deviceType_add.html";
    }

    /**
     * 跳转到修改考勤机类型
     */
    @RequestMapping("/deviceType_update/{deviceTypeId}")
    public String deviceTypeUpdate(@PathVariable Integer deviceTypeId, Model model) {
        DeviceType deviceType = deviceTypeService.selectById(deviceTypeId);
        model.addAttribute("item",deviceType);
        LogObjectHolder.me().set(deviceType);
        return PREFIX + "deviceType_edit.html";
    }

    /**
     * 获取考勤机类型列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return deviceTypeService.selectList(null);
    }

    /**
     * 新增考勤机类型
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(DeviceType deviceType) {
        deviceTypeService.insert(deviceType);
        return SUCCESS_TIP;
    }

    /**
     * 删除考勤机类型
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer deviceTypeId) {
        deviceTypeService.deleteById(deviceTypeId);
        return SUCCESS_TIP;
    }

    /**
     * 修改考勤机类型
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(DeviceType deviceType) {
        deviceTypeService.updateById(deviceType);
        return SUCCESS_TIP;
    }

    /**
     * 考勤机类型详情
     */
    @RequestMapping(value = "/detail/{deviceTypeId}")
    @ResponseBody
    public Object detail(@PathVariable("deviceTypeId") Integer deviceTypeId) {
        return deviceTypeService.selectById(deviceTypeId);
    }
}
