package com.xywg.admin.modular.smz.controller;

import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.device.model.Device;
import com.xywg.admin.modular.device.model.DeviceRecord;
import com.xywg.admin.modular.project.model.PorjectWorkerDto;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.smz.service.IReceiveDataService;
import com.xywg.admin.modular.smz.service.ISendDataToQidongService;
import com.xywg.admin.modular.team.model.TeamMaster;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/lx")
public class ReceiveDataController extends BaseController {

    @Autowired
    private IReceiveDataService receiveDataService;

    @Autowired
    private ISendDataToQidongService service;

    /**
     * 登录
     *
     * @Title: login
     * @return: JSONObject
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public JSONObject login(@RequestBody Map<String, Object> map) {
        return service.login(map);
    }

    /**
     * <p>
     * Title: saveCompany
     * </p>
     * <p>
     * Description:接收公司信息
     * </p>
     *
     * @author duanfen
     * @date 2019年7月24日
     */
    @RequestMapping(value = "/company", method = RequestMethod.POST)
    @ResponseBody
    public Object saveCompany(@RequestBody SubContractor contract) {
        Map<String, Object> map = new HashMap<>();
        try {
            this.receiveDataService.saveCompany(contract);
            map.put("code", 200);
            map.put("message", "添加成功");
        } catch (XywgException e) {
            e.printStackTrace();
            map.put("code", e.getCode());
            map.put("message", e.getMessage());
        }
        return map;
    }

    /**
     * <p>
     * Title: saveProject
     * </p>
     * <p>
     * Description:接收项目信息
     * </p>
     *
     * @author duanfen
     * @date 2019年7月24日
     */
    @RequestMapping(value = "/project", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveProject(@RequestBody ProjectMaster project) {
        Map<String, Object> map = new HashMap<>();
        try {
            String projectCode = this.receiveDataService.saveProject(project);
            map.put("code", 200);
            map.put("projectCode", projectCode);
            map.put("message", "添加成功");
        } catch (XywgException e) {
            e.printStackTrace();
            map.put("code", e.getCode());
            map.put("message", e.getMessage());
        }
        return map;
    }

    /**
     * <p>
     * Title: saveTeamMaster
     * </p>
     * <p>
     * Description:接收外部班组信息
     * </p>
     *
     * @author duanfen
     * @date 2019年7月22日
     */
    @RequestMapping(value = "/team", method = RequestMethod.POST)
    @ResponseBody
    public Object saveTeamMaster(@RequestBody TeamMaster teamMaster) {
        Map<String, Object> map = new HashMap<>();
        try {
            this.receiveDataService.saveTeamMaster(teamMaster);
            map.put("code", 200);
            map.put("message", "添加成功");
        } catch (XywgException e) {
            e.printStackTrace();
            map.put("code", e.getCode());
            map.put("message", e.getMessage());
        }
        return map;
    }

    /**
     * <p>
     * Title: saveDevice
     * </p>
     * <p>
     * Description:接收外部设备信息
     * </p>
     *
     * @author duanfen
     * @date 2019年7月22日
     */
    @RequestMapping(value = "/device", method = RequestMethod.POST)
    @ResponseBody
    public Object saveDevice(@RequestBody Device device) {
        Map<String, Object> map = new HashMap<>();
        try {
            this.receiveDataService.saveDevice(device);
            map.put("code", 200);
            map.put("message", "添加成功");
        } catch (XywgException e) {
            e.printStackTrace();
            map.put("code", e.getCode());
            map.put("message", e.getMessage());
        }
        return map;
    }

    /**
     * <p>
     * Title: saveProjectPersonnel
     * </p>
     * <p>
     * Description:接收外项目人员关系信息
     * </p>
     *
     * @author duanfen
     * @date 2019年7月22日
     */
    @RequestMapping(value = "/person", method = RequestMethod.POST)
    @ResponseBody
    public Object saveProjectPersonnel(@RequestBody PorjectWorkerDto projectPerson) {
        Map<String, Object> map = new HashMap<>();
        try {
            String uuid = this.receiveDataService.saveProjectPersonnel(projectPerson);
            map.put("code", 200);
            map.put("projectWorkerId", uuid);
            map.put("message", "添加成功");
        } catch (XywgException e) {
            e.printStackTrace();
            map.put("code", e.getCode());
            map.put("message", e.getMessage());
        }
        return map;
    }

    /**
     * <p>
     * Title: saveDeviceRecord
     * </p>
     * <p>
     * Description:接收外部考勤记录
     * </p>
     *
     * @author duanfen
     * @date 2019年7月22日
     */
    @RequestMapping(value = "/record", method = RequestMethod.POST)
    @ResponseBody
    public Object saveDeviceRecord(@RequestBody DeviceRecord record) {
        Map<String, Object> map = new HashMap<>();
        try {
            this.receiveDataService.saveDeviceRecord(record);
            map.put("code", 200);
            map.put("message", "添加成功");
        } catch (XywgException e) {
            e.printStackTrace();
            map.put("code", e.getCode());
            map.put("message", e.getMessage());
        }
        return map;
    }
}
