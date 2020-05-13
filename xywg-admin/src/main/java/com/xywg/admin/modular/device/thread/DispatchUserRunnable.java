package com.xywg.admin.modular.device.thread;

import com.xywg.admin.core.util.DataUtil;
import com.xywg.admin.core.util.JointCommand;
import com.xywg.admin.modular.device.dao.DeviceMapper;
import com.xywg.admin.modular.device.model.Device;
import com.xywg.admin.modular.device.model.DeviceCommand;
import com.xywg.admin.modular.device.service.IDeviceCommandService;
import com.xywg.admin.modular.device.utils.RedisUtil;
import com.xywg.admin.modular.faceUtils.Base64Util;
import com.xywg.admin.modular.faceUtils.FileUtil;
import com.xywg.admin.modular.smz.utils.ImageUtil;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.xywg.admin.modular.device.service.impl.DeviceCommandServiceImpl.COMMAND_EXPIRE_TIME;
import static com.xywg.admin.modular.device.service.impl.DeviceCommandServiceImpl.DEVICE_GET_COMMAND_REDIS_KEY;

/**
 * * @Package com.xywg.admin.modular.device.thread
 * * @Description: 人员命令下发Runnable
 * * @author caiwei
 * * @date 2019/5/10
 **/
public class DispatchUserRunnable implements Runnable {

    private IDeviceCommandService deviceCommandService;

    private DeviceCommand command;

    private RedisUtil redisUtil;

    private DeviceMapper deviceMapper;

    private IWorkerMasterService workerMasterService;


    public DispatchUserRunnable(IDeviceCommandService deviceCommandService, DeviceCommand command, RedisUtil redisUtil, DeviceMapper deviceMapper, IWorkerMasterService workerMasterService) {
        this.deviceCommandService = deviceCommandService;
        this.command = command;
        this.redisUtil = redisUtil;
        this.deviceMapper = deviceMapper;
        this.workerMasterService = workerMasterService;
    }

    @Override
    public void run() {
        try {
            if (command.getDeviceSns() == null || command.getUserIds() == null || command.getDeviceSns().size() == 0 || command.getUserIds().size() == 0) {
                return;
            }

            List<DeviceCommand> toDbList = new ArrayList<>();
            for (String deviceSn : command.getDeviceSns()) {
                String key = DEVICE_GET_COMMAND_REDIS_KEY + deviceSn;
                Object o = redisUtil.get(key);
                List<String> commandList = null;
                if (o == null) {
                    commandList = new ArrayList<>();
                } else {
                    commandList = (List<String>) o;
                }
                Device device = deviceMapper.selectDeviceBySn(new Device(deviceSn));

                for (String userId : command.getUserIds()) {
                    WorkerMaster user = workerMasterService.getWorkerByProjectWorker(userId.split(",")[0], Integer.parseInt(userId.split(",")[1].toString()), device.getProjectCode());
                    if (user == null || StringUtils.isBlank(user.getIdCardNumber())) {
                        continue;
                    }
                    String uuidUserInfo = DataUtil.getUUID();
                    //人员基本信息命令
                    String baseInfoCommand = JointCommand.jointUserInfo(uuidUserInfo, user.getIdCardNumber(), user.getWorkerName(), user.getCardNumber());
                    System.out.println("[人员信息]:"+ baseInfoCommand);
                    DeviceCommand deviceCommand = new DeviceCommand(deviceSn, command.getType(),
                            user.getIdCardNumber(), new Date(),
                            uuidUserInfo, baseInfoCommand);
                    	
                    commandList.add(baseInfoCommand);
                    toDbList.add(deviceCommand);

                    //大头照
                    if (StringUtils.isNotBlank(user.getHeadImage())) {
                        String uuidImg = DataUtil.getUUID();
                        String imgBase64Str = "";
						try {
							imgBase64Str = ImageUtil.encodeNetImageToBase64("http://lxjs.jsxywg.cn/labor/"+user.getHeadImage().split(",")[0]);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//                        String imgBase64Str = Base64Util.encode(imgData);、
						System.out.println(imgBase64Str);
                        String imgCommandString = JointCommand.jointUserPhoto(uuidImg, user.getIdCardNumber(), imgBase64Str);
                        System.out.println("[人员头像]:"+ imgCommandString);
                        String imgCommandString2 = JointCommand.jointUserPhoto(uuidImg, user.getIdCardNumber(), "");
                        DeviceCommand deviceCommandImg = new DeviceCommand(deviceSn, command.getType(),
                                user.getIdCardNumber(), new Date(),
                                uuidImg, imgCommandString2);

                        commandList.add(imgCommandString);
                        toDbList.add(deviceCommandImg);
                    }
                    //人脸模板
                    if (StringUtils.isNotBlank(user.getFace())) {
                        String uuidFace = DataUtil.getUUID();
//                        byte[] faceData = FileUtil.getFile(user.getFace().split(",")[0]);
//                        String faceBase64Str = Base64Util.encode(faceData);
//                        String faceBase64Str = "http://labor.jsxywg.cn/labor/"+user.getFace().split(",")[0];
                        
                        String faceBase64Str = "";
  						try {
  							faceBase64Str = ImageUtil.encodeNetImageToBase64("http://lxjs.jsxywg.cn/labor/"+user.getFace().split(",")[0]);
  						} catch (Exception e) {
  							e.printStackTrace();
  						}
                        String faceBase64StrCommand = JointCommand.jointUserFace(uuidFace, user.getIdCardNumber(), faceBase64Str);
                        System.out.println("[人员人脸]:"+ faceBase64StrCommand);
                        String faceBase64StrCommand2 = JointCommand.jointUserFace(uuidFace, user.getIdCardNumber(), "");
                        DeviceCommand deviceCommandImg = new DeviceCommand(deviceSn, command.getType(),
                                user.getIdCardNumber(), new Date(),
                                uuidFace, faceBase64StrCommand2);

                        commandList.add(faceBase64StrCommand);
                        toDbList.add(deviceCommandImg);
                    }
                }
                redisUtil.set(key, commandList, COMMAND_EXPIRE_TIME);
            }

            if (toDbList.size() > 0) {
                deviceCommandService.insertBatch(toDbList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
