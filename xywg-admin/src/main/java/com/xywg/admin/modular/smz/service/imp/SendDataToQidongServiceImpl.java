package com.xywg.admin.modular.smz.service.imp;

import com.xywg.admin.core.common.exception.BizExceptionEnum;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.util.DateUtils;
import com.xywg.admin.core.util.MD5Util;
import com.xywg.admin.modular.device.model.Device;
import com.xywg.admin.modular.device.model.DeviceRecord;
import com.xywg.admin.modular.device.service.IDeviceRecordService;
import com.xywg.admin.modular.device.service.IDeviceService;
import com.xywg.admin.modular.device.utils.RedisUtil;
import com.xywg.admin.modular.project.model.ProjectAddress;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.model.ProjectWorker;
import com.xywg.admin.modular.project.service.IProjectAddressService;
import com.xywg.admin.modular.project.service.IProjectMasterService;
import com.xywg.admin.modular.project.service.IProjectWorkerService;
import com.xywg.admin.modular.smz.service.ISendDataToQidongService;
import com.xywg.admin.modular.system.model.User;
import com.xywg.admin.modular.system.service.IUserService;
import com.xywg.admin.modular.team.model.TeamMaster;
import com.xywg.admin.modular.team.service.ITeamMasterService;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SendDataToQidongServiceImpl implements ISendDataToQidongService {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisUtil redis;

    @Autowired
    private IProjectMasterService projectMasterService;

    @Autowired
    private ITeamMasterService teamMasterService;

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private IWorkerMasterService workerMasterService;

    @Autowired
    private IProjectWorkerService projectWorkerService;

    @Autowired
    private IDeviceRecordService deviceRecordService;

    @Autowired
    private IProjectAddressService projectAddressService;


    @Override
    public JSONObject login(Map<String, Object> map) {
        Object username = map.get("account");
        Object password = map.get("password");
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "登陆成功");
        // 判断账号是否为空
        if (username == null || password == null) {
            result.put("code", 400);
            result.put("message", "账号或密码不能为空");
        }
        User user = this.userService.getByAccount((String) username);
        // 判断用户是否存在
        if (user == null) {
            result.put("code", 400);
            result.put("message", "账号不存在");
        }
        // password = MD5Util.encrypt((String) password);
        String pass = ShiroKit.md5(MD5Util.encrypt((String) password), user.getSalt());
        // 判断密码是否正确
        if (!pass.equals(user.getPassword())) {
            result.put("code", 400);
            result.put("message", "密码错误");
        }
        // 账号过期判断
        if (user.getEndTime().getTime() < System.currentTimeMillis()) {
            result.put("code", 400);
            result.put("message", "账号已过期");
        }
        // 生成token值
        String token = MD5Util.encrypt(DateUtils.getDateTime());
        redis.set(token, username, 1800L);
        result.put("token", token);
        JSONObject fromObject = JSONObject.fromObject(result);
        return fromObject;
    }

    @Override
    public Map<String, Object> project(Map<String, Object> map) {
        Object organizationCode = map.get("organizationCode");
        validAccount(map, organizationCode);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", 200);
        result.put("message", "查询成功");
        Long id = Long.valueOf(String.valueOf(map.get("id")));
        // 返回项目数据
        List<ProjectMaster> project = this.projectMasterService.queryProjectById((String) organizationCode, id);
        if (project.size() > 0) {
            result.put("project", JSONArray.fromObject(project));
        }
        return result;
    }


    /**
     * 验证登陆账号
     */
    public void validAccount(Map<String, Object> map, Object organizationCode) {
        Object token = map.get("token");
        // 判断账号是否为空
        if (token == null) {
            throw new XywgException(BizExceptionEnum.USER_NOT_LOGIN);
        }
        Object username = redis.get((String) token);
        // 判断账号是否为空
        if (username == null) {
            throw new XywgException(BizExceptionEnum.USER_NOT_LOGIN);
        }
        // 判断用户是否存在
        if (organizationCode == null) {
            throw new XywgException(BizExceptionEnum.ORGANIZATION_CODE_NOT_NULL);
        }
    }

    @Override
    public Map<String, Object> teamMaster(Map<String, Object> map) {
        Object organizationCode = map.get("organizationCode");
        validAccount(map, organizationCode);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", 200);
        result.put("message", "查询成功");
        Long id = Long.valueOf(String.valueOf(map.get("id")));
        // 返回班组
        List<TeamMaster> team = this.teamMasterService.queryTeamMasterById((String) organizationCode, id, null);
        if (team.size() > 0) {
            result.put("data", JSONArray.fromObject(team));
        }
        return result;
    }


    @Override
    public Map<String, Object> device(Map<String, Object> map) {
        Object organizationCode = map.get("organizationCode");
        validAccount(map, organizationCode);
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "查询成功");
        Long id = Long.valueOf(String.valueOf(map.get("id")));
        //返回考勤机数据
        List<Device> device = this.deviceService.queryDeviceById((String) organizationCode, id, null);
        if (device.size() > 0) {
            result.put("device", JSONArray.fromObject(device));
        }
        return result;
    }


    @Override
    public Map<String, Object> workerMaster(Map<String, Object> map) {
        Object organizationCode = map.get("organizationCode");
        validAccount(map, organizationCode);
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "查询成功");
        //获取redis里存的id
        Long id = Long.valueOf(String.valueOf(map.get("id")));
        List<WorkerMaster> pw = this.workerMasterService.queryWorkerMasterById((String) organizationCode, id);
        if (pw.size() > 0) {
            result.put("data", JSONArray.fromObject(pw));
        }
        return result;
    }

    @Override
    public Map<String, Object> projectWorker(Map<String, Object> map) {
        Object organizationCode = map.get("organizationCode");
        validAccount(map, organizationCode);
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "查询成功");
        //获取redis的id
        Long id = Long.valueOf(String.valueOf(map.get("id")));
        List<ProjectWorker> pw = this.projectWorkerService.queryProjectWorkerById((String) organizationCode, id, null);
        if (pw.size() > 0) {
            result.put("data", JSONArray.fromObject(pw));
        }
        return result;
    }

    @Override
    public Map<String, Object> deviceRecord(Map<String, Object> map) {
        Object organizationCode = map.get("organizationCode");
        validAccount(map, organizationCode);
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "查询成功");
        //获取redis的id
        Long id = Long.valueOf(String.valueOf(map.get("id")));
        //返回考勤数据
        List<DeviceRecord> device = this.deviceRecordService.queryDeviceRecordById((String) organizationCode, id, null);
        if (device.size() > 0) {
            result.put("data", JSONArray.fromObject(device));
        }
        return result;
    }

    @Override
    public Map<String, Object> projectAddress(Map<String, Object> map) {
        Object organizationCode = map.get("organizationCode");
        validAccount(map, organizationCode);
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "查询成功");
        //获取redis的id
        Long id = Long.valueOf(String.valueOf(map.get("id")));
        //返回项目地址数据
        List<ProjectAddress> projectAddress = this.projectAddressService.queryProjectAddressById(
                (String) organizationCode, id);
        if (projectAddress.size() > 0) {
            result.put("data", JSONArray.fromObject(projectAddress));
        }
        return null;
    }
}
