package com.xywg.admin.modular.device.thread;

import com.xywg.admin.core.util.DataUtil;
import com.xywg.admin.modular.device.dao.DeviceCommandMapper;
import com.xywg.admin.modular.device.model.DeviceCommand;
import com.xywg.admin.modular.device.utils.RedisUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.xywg.admin.modular.device.service.impl.DeviceCommandServiceImpl.COMMAND_EXPIRE_TIME;
import static com.xywg.admin.modular.device.service.impl.DeviceCommandServiceImpl.DEVICE_GET_COMMAND_REDIS_KEY;

/**
 * * @Package com.xywg.admin.modular.device.thread
 * * @Description: 删除人员线程
 * * @author caiwei
 * * @date 2019/5/10
 **/
public class DeleteUserRunnable implements Runnable {

    private DeviceCommand command;

    private DeviceCommandMapper deviceCommandMapper;

    private RedisUtil redisUtil;

    public DeleteUserRunnable(DeviceCommand command , DeviceCommandMapper deviceCommandMapper, RedisUtil redisUtil) {
        this.command = command;
        this.deviceCommandMapper = deviceCommandMapper;
        this.redisUtil = redisUtil;
    }

    @Override
    public void run() {
        if (command.getUserIds().size() == 0) {
            return;
        }
        Date date = new Date();
        for (String deviceSn : command.getDeviceSns()) {
            String uuid = DataUtil.getUUID();
            //删除人员命令
            DeviceCommand deviceCommand = new DeviceCommand(deviceSn, command.getType(), date, uuid);
            //先入库
            this.deviceCommandMapper.insert(deviceCommand);

            StringBuilder cmdString = new StringBuilder();
            for (String userId : command.getUserIds()) {
                String idCardNumber = userId.split(",")[0];
                cmdString.append("C:").append(uuid).append(":DATA ").append("DELETE ").append("USERINFO ");
                cmdString.append("PIN=").append(idCardNumber).append("\n");
            }


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
}
