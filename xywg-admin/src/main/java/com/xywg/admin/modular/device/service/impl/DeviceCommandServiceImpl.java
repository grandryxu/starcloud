package com.xywg.admin.modular.device.service.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.util.DataUtil;
import com.xywg.admin.core.util.JointCommand;
import com.xywg.admin.modular.device.dao.DeviceMapper;
import com.xywg.admin.modular.device.model.SafetyHelmet;
import com.xywg.admin.modular.device.service.IDeviceService;
import com.xywg.admin.modular.device.thread.DeleteUserRunnable;
import com.xywg.admin.modular.device.thread.DispatchUserRunnable;
import com.xywg.admin.modular.device.utils.RedisUtil;
import com.xywg.admin.modular.face.model.PersonData;
import com.xywg.admin.modular.faceUtils.Base64Util;
import com.xywg.admin.modular.faceUtils.FileUtil;
import com.xywg.admin.modular.system.service.AccountProjectService;
import com.xywg.admin.modular.wages.model.Settlement;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.base.Splitter;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.shiro.ShiroUser;
import com.xywg.admin.modular.company.dao.EmployeeMasterMapper;
import com.xywg.admin.modular.company.model.EmployeeMaster;
import com.xywg.admin.modular.device.dao.DeviceCommandMapper;
import com.xywg.admin.modular.device.model.Device;
import com.xywg.admin.modular.device.model.DeviceCommand;
import com.xywg.admin.modular.device.model.Type;
import com.xywg.admin.modular.device.service.IDeviceCommandService;
import com.xywg.admin.modular.mina.TheApp;
import com.xywg.admin.modular.mina.Utils;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 设备下发命令 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-12
 */
@Service
@SuppressWarnings("all")
public class DeviceCommandServiceImpl extends ServiceImpl<DeviceCommandMapper, DeviceCommand>
        implements IDeviceCommandService {

    public static final Long COMMAND_EXPIRE_TIME = 60 * 24L;

    @Value("${spring.mvc.view.imageHost}")
    private String imageHost;

    @Resource
    private DeviceCommandMapper deviceCommandMapper;

    @Autowired
    private EmployeeMasterMapper employeeMasterMapper;
    @Autowired
    private AccountProjectService accountProjectService;
    @Autowired
    public RedisUtil redisUtil;
    @Autowired
    private IWorkerMasterService workerMasterService;
    @Autowired
    private IDeviceService deviceService;
    @Autowired
    private DeviceMapper deviceMapper;
    /**
     * 设备获取命令redis的key 的前缀
     */
    public static final String DEVICE_GET_COMMAND_REDIS_KEY = "lwt.zk.getCommand.longxin.common.";

    /**
     * 取消命令
     */
    public static final String CANCEL_VALUE = "999";

    @Override
    public int getDeviceCommandCount(String id) {
        Date now = new Date();
        int validDay = TheApp.getDeviceCommandValidDay();
        if (validDay > 1) {
            now = Utils.startOfDay(now, 1 - validDay);
        } else {
            now = Utils.startOfDay(now);
        }
        return this.baseMapper.getDeviceCommandCount(id, now);
    }

    @Override
    public void setCommandState(DeviceCommand command) {
        this.baseMapper.setCommandState(command);
    }

    @Override
    public List<DeviceCommand> getDeviceCommand(String sn) {
        Date now = new Date();
        int validDay = TheApp.getDeviceCommandValidDay();
        if (validDay > 1) {
            now = Utils.startOfDay(now, 1 - validDay);
        } else {
            now = Utils.startOfDay(now);
        }
        return this.baseMapper.getDeviceCommand(sn, now);
    }

    @Override
    public int getDeviceCommandCount(Long deviceId) {
        Date now = new Date();
        int validDay = TheApp.getDeviceCommandValidDay();
        if (validDay > 1) {
            now = Utils.startOfDay(now, 1 - validDay);
        } else {
            now = Utils.startOfDay(now);
        }
        return this.deviceCommandMapper.getDeviceCommandCount(deviceId + "", now);
    }

    @Override
    public void cancelCommand(Long id, ShiroUser user) throws Exception {
        this.deviceCommandMapper.cancelCommand(id, user);
    }

    /**
     * @Description: 執行插入命令
     * @author cxl
     * @date 2018年1月5日 上午11:10:54
     */
    @Override
    public String executeCommand(List<String> deviceIds, int type, List<String> workerIds) throws Exception {
        DeviceCommand command = new DeviceCommand(deviceIds, type, workerIds);
        String errorMsg = null;
        //获取执行人信息
        ShiroUser user = ShiroKit.getUser();
        command.setUserId(Long.valueOf(user.getId()));
        EmployeeMaster emp = this.employeeMasterMapper.getByCellPhone(user.getAccount());
        if (emp != null) {
            command.setIdCardType(emp.getIdCardType());
            command.setIdCardNumber(emp.getIdCardNumber());
        }
        // DispatchUser(7, "下发个别人员信息"), DeleteUser(8, "删除个别人员信息")
        if ((command.getType().intValue() == Type.DeleteUser.getValue())
                || (command.getType().intValue() == Type.DispatchUser.getValue())) {
            String districtDevice = this.deviceCommandMapper.getDistrictDeviceInfo(command.getDeviceSns());
            if (Utils.isEmpty(districtDevice)) {
                throw new Exception(
                        new StringBuilder().append("无法对以下设备下发命令，因为它们没有对应的项目：<br/>").append(districtDevice).toString());
            }
        } else {
            Date now = new Date();
            int validDay = TheApp.getDeviceCommandValidDay();
            if (validDay > 1) {
                now = Utils.startOfDay(now, 1 - validDay);
            } else {
                now = Utils.startOfDay(now);
            }
            List<Device> devices = this.deviceCommandMapper.getSameCommandDevice(command, now);
            if (!Utils.isEmpty(devices)) {
                Set ids = new HashSet();
                StringBuilder builder = new StringBuilder("设备命令提交成功，但是以下设备因为已有重复的未执行命令，忽略本次下发：");
                for (Device d : devices) {
                    if (!ids.contains(d.getId())) {
                        ids.add(d.getId());
                        builder.append("<br/>").append(d.getName()).append("(").append(d.getSn()).append(")");
                    }
                }
                errorMsg = builder.toString();
                List<String> did = new ArrayList<String>();
                for (String id : command.getDeviceSns()) {
                    if (!ids.contains(id)) {
                        did.add(id);
                    }
                }
                if (did.size() == 0) {
                    return errorMsg;
                }
                command.setDeviceSns(did);
            }
        }
        this.deviceCommandMapper.executeCommand(command);
        return errorMsg;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object executeCommand(DeviceCommand command) {
        if (command.getType() == null) {
            throw new XywgException(800, "请指定下达命令类型");
        }
        DeviceCommand.Type commandType;
        try {
            commandType = DeviceCommand.Type.valueOf(command.getType().intValue());
        } catch (Exception e) {
            throw new XywgException(800, "请指定下达命令类型");
        }

        StringBuilder stringBuilder = new StringBuilder();
        switch (commandType) {
            case SyncTime:
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return stringBuilder.append("SetDeviceInfo(time=\"").append(format.format(new Date())).append("\")")
                        .toString();
            case Reboot:
                this.reBoot(command);
                break;
            case GetInfo:
                return "GetDeviceInfo()";
            case Upgrade:
                return "";
            case ClearRecord:
                this.deleteAllRecord(command);
                break;
            case ClearUser:
                this.deleteAllUser(command);
                break;
            case DispatchUser:
                this.dispatchUser(command);
                break;
            case DeleteUser:
                this.deleteUser(command);
                break;
            default:
        }
        return super.toString();
    }

    /**
     * 重启设备
     *
     * @param command
     */
    private void reBoot(DeviceCommand command) {
        Date date = new Date();
        for (String deviceSn : command.getDeviceSns()) {
            String uuid = DataUtil.getUUID();
            //删除命令
            DeviceCommand deviceCommand = new DeviceCommand(deviceSn, command.getType(), date, uuid);
            //先入库
            this.deviceCommandMapper.insert(deviceCommand);

            StringBuilder cmdString = new StringBuilder();
            cmdString.append("C:").append(uuid).append(":REBOOT").append("\n");


            //更新入库的command
            deviceCommand.setDescription(cmdString.toString());
            this.deviceCommandMapper.updateById(deviceCommand);

            //存入redis
            String key = DEVICE_GET_COMMAND_REDIS_KEY + deviceSn;
            Object o = redisUtil.get(key);
            List<String> commandList = null;
            if (o == null) {
                commandList = new ArrayList<>();
            } else {
                commandList = (List<String>) o;
            }
            commandList.add(cmdString.toString());

            redisUtil.set(key, commandList, COMMAND_EXPIRE_TIME);
        }
    }

    /**
     * 清空考勤记录
     *
     * @param command
     */
    private void deleteAllRecord(DeviceCommand command) {

        Date date = new Date();
        for (String deviceSn : command.getDeviceSns()) {
            String uuid = DataUtil.getUUID();
            //删除命令
            DeviceCommand deviceCommand = new DeviceCommand(deviceSn, command.getType(), date, uuid);
            //先入库
            this.deviceCommandMapper.insert(deviceCommand);

            StringBuilder cmdString = new StringBuilder();
            cmdString.append("C:").append(uuid).append(":CLEAR ").append("DATA").append("\n");


            //更新入库的command
            deviceCommand.setDescription(cmdString.toString());
            this.deviceCommandMapper.updateById(deviceCommand);

            //存入redis
            String key = DEVICE_GET_COMMAND_REDIS_KEY + deviceSn;
            Object o = redisUtil.get(key);
            List<String> commandList = null;
            if (o == null) {
                commandList = new ArrayList<>();
            } else {
                commandList = (List<String>) o;
            }
            commandList.add(cmdString.toString());

            redisUtil.set(key, commandList, COMMAND_EXPIRE_TIME);
        }


    }

    /**
     * 删除所有人
     *
     * @param command
     */
    private void deleteAllUser(DeviceCommand command) {
        Date date = new Date();
        for (String deviceSn : command.getDeviceSns()) {
            List<WorkerMaster> workerList = workerMasterService.getWorkerListByProjectCode(deviceService.getBySn(deviceSn).getProjectCode());
            String uuid = DataUtil.getUUID();
            //删除人员命令
            DeviceCommand deviceCommand = new DeviceCommand(deviceSn, command.getType(), date, uuid);
            //先入库
            this.deviceCommandMapper.insert(deviceCommand);

            StringBuilder cmdString = new StringBuilder();
            for (WorkerMaster workerMaster : workerList) {
                cmdString.append("C:").append(uuid).append(":DATA ").append("DELETE ").append("USERINFO ");
                cmdString.append("PIN=").append(workerMaster.getIdCardNumber()).append("\n");
            }
            //删除考勤记录
            cmdString.append("C:").append(uuid).append(":CLEAR ").append("DATA").append("\n");

            //更新入库的command
            deviceCommand.setDescription(cmdString.toString());
            this.deviceCommandMapper.updateById(deviceCommand);

            //存入redis
            String key = DEVICE_GET_COMMAND_REDIS_KEY + deviceSn;
            Object o = redisUtil.get(key);
            List<String> commandList = null;
            if (o == null) {
                commandList = new ArrayList<>();
            } else {
                commandList = (List<String>) o;
            }
            commandList.add(cmdString.toString());

            redisUtil.set(key, commandList, COMMAND_EXPIRE_TIME);
        }

    }

    /**
     * 删除个别人员
     *
     * @param command
     */
    private void deleteUser(DeviceCommand command) {
        new Thread(new DeleteUserRunnable(command,deviceCommandMapper,redisUtil)).start();
    }


    /**
     * 人员下发
     *
     * @param command
     */
    @Override
    public void dispatchUser(DeviceCommand command) {
        new Thread(new DispatchUserRunnable(this, command, redisUtil, deviceMapper, workerMasterService)).start();
    }


    @Override
    public List<DeviceCommand> getDeviceCommand(Long deviceId) {
        Date now = new Date();
        int validDay = TheApp.getDeviceCommandValidDay();
        if (validDay > 1) {
            now = Utils.startOfDay(now, 1 - validDay);
        } else {
            now = Utils.startOfDay(now);
        }
        return this.deviceCommandMapper.getDeviceCommand(deviceId + "", now);
    }

    @Override
    public List<DeviceCommand> queryCommand(Map<String, Object> params) {
        if (params.get("deviceIds") != null) {
            params.put("deviceIds", Splitter.on(",").trimResults().splitToList(params.get("deviceIds") + ""));
        }
        return deviceCommandMapper.queryCommand(params);
    }

    @Override
    public List<DeviceCommand> queryCommandBySeries(Page<DeviceCommand> page, Map<String, Object> map) {
        map.put("projectCodes", accountProjectService.getProjectCodes());
        return deviceCommandMapper.queryCommandBySeries(page, map);
    }

    @Override
    public void addDeviceCommand(List<Object> addList) {
        for (Object o : addList) {
            //DeviceCommand deviceCommand = new DeviceCommand();
            stringToDateException();

            DeviceCommand  deviceCommand  = JSON.parseObject(o.toString(), DeviceCommand.class);

      /*      try {
               BeanUtils.copyProperties(deviceCommand, o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }*/
            deviceCommand.setId(null);
            insert(deviceCommand);

        }
    }



    //解决 BeanUtils.copyProperties()的string转date异常
    private void stringToDateException() {
        ConvertUtils.register(new Converter() {
            @Override
            public Object convert(Class type, Object value) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    return simpleDateFormat.parse(value.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }, java.util.Date.class);
    }
}
