package com.xywg.admin.modular.device.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.config.handler.RemoteDTO;
import com.xywg.admin.config.handler.ZbusHandler;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.base.tips.ErrorTip;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.modular.device.model.Device;
import com.xywg.admin.modular.device.service.IDeviceService;
import com.xywg.admin.modular.device.wrapper.DeviceWarpper;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import com.xywg.admin.modular.project.service.IProjectMasterService;
import com.xywg.admin.modular.system.service.IDictService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 考勤机管理控制器
 *
 * @author wangcw
 * @Date 2018-05-30 08:36:31
 */
@Controller
@RequestMapping("/device")
public class DeviceController extends BaseController {

    private String PREFIX = "/device/device/";

    @Autowired
    private IDeviceService deviceService;
    @Autowired
    private IDictService dictService;
    @Autowired
    private IProjectMasterService projectMasterService;

    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;
    /**
     * 跳转到考勤机管理首页
     */
    @RequestMapping("")
    public String index(@RequestParam Map map ,Model model) {
        model.addAttribute("projectCode",map.get("projectCode"));
        model.addAttribute("projectName",map.get("projectName"));
        model.addAttribute("projectStatus",map.get("projectStatus"));
        return PREFIX + "device.html";
    }

    /**
     * 跳转到添加考勤机管理
     */
    @RequestMapping("/device_add")
    public String deviceAdd(@RequestParam Map map ,Model model) {
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        model.addAttribute("projectCode",map.get("projectCode"));
        model.addAttribute("projectName",map.get("projectName"));
        model.addAttribute("typeId",dictService.selectByName("设备类型") );
        model.addAttribute("type",dictService.selectByName("出入类型") );
        return PREFIX + "device_add.html";
    }


    /**
     * 跳转到修改考勤机管理
     */
    @RequestMapping("/device_update")
    public String deviceUpdate(@RequestParam Map map ,Model model) {
        Device device = deviceService.selectById(Long.valueOf(map.get("id").toString()));
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        model.addAttribute("typeId",dictService.selectByName("设备类型") );
        model.addAttribute("type",dictService.selectByName("出入类型") );
        model.addAttribute("projectName",map.get("projectName"));
        model.addAttribute("item",device);
        LogObjectHolder.me().set(device);
        return PREFIX + "device_edit.html";
    }

    /**
     * 跳转到修改考勤机管理
     */
    @RequestMapping("/device_view")
    public String deviceView(@RequestParam Map map ,Model model) {
        Device device = deviceService.selectById(Long.valueOf(map.get("id").toString()));
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        model.addAttribute("typeId",dictService.selectByName("设备类型") );
        model.addAttribute("type",dictService.selectByName("出入类型") );
        model.addAttribute("projectName",map.get("projectName"));
        model.addAttribute("item",device);
        LogObjectHolder.me().set(device);
        return PREFIX + "device_info.html";
    }

    /**
     * 获取考勤机管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String,Object> map) {
        Page<Device> page = new PageFactory<Device>().defaultPage();
        List<Map<String,Object>> devices= deviceService.selectList(page ,map);
        page.setRecords((List<Device>) new DeviceWarpper(devices).warp());
        return super.packForBT(page);
    }

    /**
     * 新增考勤机管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Device device) {
        boolean bl = deviceService.insert(device);
        if(bl) {
            //完成调用zbus推送给智慧工地
            ProjectMaster projectMaster = projectMasterService.getProjectByProjectCode(device.getProjectCode());
            //判断所在项目是否已经推送给智慧工地
            if(projectMaster.getIsSynchro().intValue() == 1) {
                Map<String, Object> map = new HashMap<>();
                map.put("relationDeviceId", device.getId());
                map.put("relationProjectId", projectMaster.getId());
                map.put("deviceNo", device.getSn());
                map.put("manufactor", "");
                map.put("platform", 2);
                RemoteDTO dto = new RemoteDTO();
                Map<String, Object> mm = new HashMap<>();
                mm.put("attendance", map);
                mm.put("operationFlag", "insertExternalAttendance");
                dto.setBody(mm);
                ZbusHandler.send(JSONObject.fromObject(dto).toString());
            }
        }
        return SUCCESS_TIP;
    }

    /**
     * 删除考勤机管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long deviceId) {
        Device device = deviceService.selectDeviceById(deviceId);
        boolean bl = deviceService.deleteById(deviceId);
        if(bl) {
            //获取device所在的project
            ProjectMaster projectMaster = projectMasterService.getProjectByProjectCode(device.getProjectCode());
            if(projectMaster.getIsSynchro().intValue() == 1) {
                //完成调用zbus推送给智慧工地
                RemoteDTO dto = new RemoteDTO();
                Map<String, Object> mm = new HashMap<>();
                mm.put("id", deviceId);
                mm.put("relationProjectId", projectMaster.getId());
                mm.put("operationFlag", "deleteExternalAttendance");
                dto.setBody(mm);
                ZbusHandler.send(JSONObject.fromObject(dto).toString());
            }
        }
        return SUCCESS_TIP;
    }

    /**
     * 批量删除考勤机管理
     */
    @RequestMapping(value = "/deleteDevices")
    @ResponseBody
    public Object deleteDevices(@RequestParam String ids) {
        deviceService.deleteDevices(ids);
        return SUCCESS_TIP;
    }

    /**
     * 修改考勤机管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Device device) {
        int i=deviceService.updateDevice(device);
        if(i==0){
            return SUCCESS_TIP;
        }else {
            return new ErrorTip(600,"该序列号已存在！");
        }
    }

    /**
     * 考勤机管理详情
     */
    @RequestMapping(value = "/detail/{deviceId}")
    @ResponseBody
    public Object detail(@PathVariable("deviceId") Integer deviceId) {
        return deviceService.selectById(deviceId);
    }

    /**
     * 考勤机启用禁用
     */
    @RequestMapping(value = "/toggle")
    @ResponseBody
    public Object toggle(@RequestParam Map<String,String> map) {
        int i=deviceService.toggle(map.get("ids") , Integer.parseInt(map.get("state")));
        if(i==0){
            return new ErrorTip(600,"已选设备中有设备号已存在！");
        }else {
            return SUCCESS_TIP;
        }
    }

}
