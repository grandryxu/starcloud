package com.xywg.attendance.business;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.xywg.attendance.common.model.TransmissionMessageTemplate;
import com.xywg.attendance.common.utils.Base64Util;
import com.xywg.attendance.common.utils.DataUtil;
import com.xywg.attendance.common.utils.FileUtil;
import com.xywg.attendance.common.utils.RedisUtil;
import com.xywg.attendance.core.config.RemoteDTO;
import com.xywg.attendance.core.config.ZbusHandler;
import com.xywg.attendance.modular.command.JointCommand;
import com.xywg.attendance.modular.handler.MethodService;
import com.xywg.attendance.modular.system.model.*;
import com.xywg.attendance.modular.system.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

import static com.xywg.attendance.common.global.GlobalStaticConstant.DEVICE_GET_COMMAND_REDIS_KEY;


/**
 * @author cbb
 * @date 2019/3/5
 * 业务处理方法
 */
@Component
public class LaborSystem {

    @Autowired
    private IBussDeviceCommandService bussDeviceCommandService;
    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private IWorkerMasterService workerMasterService;
    @Autowired
    private IRecordService recordService;
    @Autowired
    private IBussDeviceRecordExceptionDataService bussDeviceRecordExceptionDataService;
    @Autowired
    private IBussProjectWorkerService bussProjectWorkerService;
    @Autowired
    private IBussPersonDataService bussPersonDataService;
    @Autowired
    private JointCommand command;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private IBussProjectMasterService projectMasterService;
    private Logger logger = LoggerFactory.getLogger("");

    /**
     * 更新考勤
     *
     * @param sn         设备序列号
     * @param picUrl     图片地址
     * @param recordTime 考勤时间
     * @param userId     用户身份证号码
     * @return
     */
    public void saveRecord(String sn, String picUrl, String recordTime, String userId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sn", sn);
        params.put("status", 1);
        params.put("is_del", 0);
        List<Device> allDevices = deviceService.selectByMap(params);
        if (allDevices != null && allDevices.size() > 0) {
            Device device = allDevices.get(0);
            Map<String, Object> projectParams = new HashMap<String, Object>();
            projectParams.put("project_code", device.getProjectCode());
            projectParams.put("id_card_number", userId);
            projectParams.put("is_del", 0);
            //1 带进场 2 进场 3退场
            projectParams.put("join_status", 2);
            List<BussProjectWorker> bussProjectWorkers = bussProjectWorkerService.selectByMap(projectParams);
            WorkerMaster wm = workerMasterService.selectOne(new EntityWrapper<WorkerMaster>().eq("id_card_type", 1).eq("id_card_number", userId).eq("is_del", 0));
            //正常和非正常考勤
            if (null != bussProjectWorkers && bussProjectWorkers.size() > 0) {
                Record record = new Record();
                record.setTime(recordTime + ".0");
                record.setOrganizationCode(bussProjectWorkers.get(0).getOrganizationCode());
                record.setDeviceSn(sn);
                record.setProjectCode(bussProjectWorkers.get(0).getProjectCode());
                record.setTeamSysNo(bussProjectWorkers.get(0).getTeamSysNo());
                //插入用户身份证
                record.setIdCardType(1);
                record.setIdCardNumber(userId);
                record.setWorkerName(wm.getWorkerName());
                record.setType(1);
                record.setPhoto(picUrl);
                record.setIsValid(1);
                record.setSource(4);
                record.setDeviceType(device.getType());
                record.setCreateDate(new Date());
                recordService.insert(record);
            } else {
                BussDeviceRecordExceptionData exceptionData = new BussDeviceRecordExceptionData();
                exceptionData.setTime(recordTime + ".0");
                exceptionData.setOrganizationCode(device.getOrganizationCode());
                exceptionData.setDeviceSn(sn);
                exceptionData.setIdCardType(1);
                exceptionData.setDeviceId(device.getId());
                exceptionData.setProjectCode(device.getProjectCode());
                //插入用户身份证
                exceptionData.setIdCardNumber(userId);
                exceptionData.setType(1);
                exceptionData.setPhoto(picUrl);
                exceptionData.setSource(4);
                exceptionData.setCreateDate(new Date());
                exceptionData.setWorkerName(wm.getWorkerName());

                bussDeviceRecordExceptionDataService.insert(exceptionData);

            }
            BussProjectMaster projectMaster = projectMasterService.selectOne(new EntityWrapper<BussProjectMaster>().eq("project_code", device.getProjectCode()));

            //完成调用zbus推送给智慧工地
            if(projectMaster.getIsSynchro() == 1){
                Map<String, Object> body = new HashMap<>();
                Map<String, Object> recordMap = new HashMap<>();
                recordMap.put("relationProjectId", projectMaster.getProjectCode());
                recordMap.put("deviceNo", sn);
                recordMap.put("relationUserId", wm.getId());
                recordMap.put("name", wm.getWorkerName());
                recordMap.put("identityCode", wm.getIdCardNumber());
                recordMap.put("nativePlace", wm.getBirthPlaceCode());
                recordMap.put("workerType", wm.getWorkTypeCode());
                recordMap.put("team", wm.getTeamSysNo());
                recordMap.put("attendanceTime", recordTime);
                recordMap.put("platform", 2);
                body.put("operationFlag", "insertAttendance");
                body.put("record", recordMap);
                RemoteDTO<Map<String, Object>> dto = new RemoteDTO<>();
                dto.setBody(body);
                ZbusHandler.send(JSON.toJSONString(dto));
            }
        }
    }

    /**
     * 更新考勤图片
     *
     * @param sn         设备序列号
     * @param picUrl     图片地址
     * @param recordTime 考勤时间
     * @param userId     用户身份证号码
     * @return
     */
    public void updateRecord(String sn, String picUrl, String recordTime, String userId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sn", sn);
        params.put("status", 1);
        params.put("is_del", 0);
        List<Device> allDevices = deviceService.selectByMap(params);
        if (allDevices != null && allDevices.size() > 0) {
            Device device = allDevices.get(0);


            Map<String, Object> projectParams = new HashMap<String, Object>();
            projectParams.put("project_code", device.getProjectCode());
            projectParams.put("id_card_number", userId);
            projectParams.put("is_del", 0);
            //1 带进场 2 进场 3退场
            projectParams.put("join_status", 2);
            List<BussProjectWorker> projects = bussProjectWorkerService.selectByMap(projectParams);
            //正常和非正常考勤
            if (null != projects && projects.size() > 0) {
                Record record = new Record();

                record.setPhoto(picUrl);
                Wrapper<Record> wrapper = new EntityWrapper<Record>();
                wrapper.eq("device_sn", sn);
                wrapper.eq("time", recordTime);
                wrapper.eq("organization_code", device.getOrganizationCode());
                wrapper.eq("project_code", device.getProjectCode());

                recordService.update(record, wrapper);
            } else {

                BussDeviceRecordExceptionData exceptionData = new BussDeviceRecordExceptionData();

                exceptionData.setPhoto(picUrl);
                Wrapper<BussDeviceRecordExceptionData> wrapperException = new EntityWrapper<BussDeviceRecordExceptionData>();
                wrapperException.eq("device_sn", sn);
                wrapperException.eq("time", recordTime);
                wrapperException.eq("organization_code", device.getOrganizationCode());
                wrapperException.eq("project_code", device.getProjectCode());

                bussDeviceRecordExceptionDataService.update(exceptionData, wrapperException);


            }
        }
    }

    /**
     * 考勤机上传的人员信息
     */
    public void saveUserInfo(Map<String, String> userParams,TransmissionMessageTemplate msg) {
        String sn = MethodService.getSn(msg.getUrl());
        String idCardNumber = userParams.get("PIN");
        String userName = userParams.get("Name");
        String card = userParams.get("Card");
       /* if (StringUtils.isBlank(card.trim())) {
            return;
        }*/
        EntityWrapper<Device> dw = new EntityWrapper<>();
        dw.eq("sn", sn);
        dw.eq("is_del", 0);
        Device device = deviceService.selectOne(dw);
        if (device == null || device.getProjectCode() == null) {
            return;
        }

        EntityWrapper<BussProjectWorker> ew = new EntityWrapper<>();
        ew.eq("project_code", device.getProjectCode());
        ew.eq("id_card_number", idCardNumber);
        ew.eq("is_del", 0);
        ew.eq("id_card_type", 1);
        List<BussProjectWorker> projectWorkerList = bussProjectWorkerService.selectList(ew);
        if (projectWorkerList == null || projectWorkerList.size() == 0) {
            //项目下没有这个人 不允许设备端主动添加人员
            return;
        }
        BussProjectWorker bpw = new BussProjectWorker();
        bpw.setUpdateDate(new Date());
        bpw.setCardType(1);
        bpw.setCardNumber(card);
        bussProjectWorkerService.update(bpw, ew);


        List<Device> allDevices = getAllDevice(sn);
        if (allDevices == null || allDevices.size() == 0) {
            return;
        }
        /*WorkerMaster worker = new WorkerMaster();
        worker.setIdCardNumber(idCardNumber);
        worker.setIdCardType(1);
        //不存在
        worker.setCellPhone("");
        worker.setWorkerName(userName);
        String projectCode = allDevices.get(0).getProjectCode();
        String orgCode = allDevices.get(0).getOrganizationCode();

        BussProjectWorker pw = new BussProjectWorker();
        pw.setIdCardNumber(userId);
        pw.setIdCardType(1);
        pw.setCellPhone("");
        pw.setCreateDate(new Date());
        pw.setOrganizationCode(orgCode);
        pw.setProjectCode(projectCode);
        // 默认值
        pw.setTeamSysNo(0);
        pw.setWorkTypeCode("");
        pw.setHasContract(0);
        pw.setWorkerRole(0);

        workerMasterService.insert(worker);
        bussProjectWorkerService.insert(pw);*/


        //下发到其他设备上去
        List<Device> otherDevices = getAllOtherDevice(sn, device.getProjectCode(), device.getOrganizationCode());
        // 下发命令
        Map<String, Object> p = new HashMap<>();
        p.put("userId", idCardNumber);
        p.put("userName", userName);
        p.put("card", card);
        distributeCommand(otherDevices, 1, p);
    }


    /**
     * 更新用户头像
     *
     * @param sn
     * @param userId 用户身份号
     * @param url    头像
     * @param base64
     */
    public void updateUserPic(String sn, String userId, String fileName, String url, String base64) {
        List<Device> allDevice = getAllDevice(sn);
        if (allDevice == null || allDevice.size() == 0) {
            return;
        }
        EntityWrapper<WorkerMaster> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("id_card_type", 1);
        entityWrapper.eq("id_card_number", userId);
        entityWrapper.eq("is_del", 0);
        WorkerMaster worker = workerMasterService.selectOne(entityWrapper);
        if(worker == null ){
            return;
        }
        worker.setHeadImage(url);
        worker.setUpdateDate(new Date());
        workerMasterService.update(worker, entityWrapper);
        // 下发人员照片
        List<Device> otherDevices = getAllOtherDevice(sn, allDevice.get(0).getProjectCode(), allDevice.get(0).getOrganizationCode());
        Map<String, Object> p = new HashMap<>(2);
        p.put("userId", userId);
        p.put("base64", base64);
        distributeCommand(otherDevices, 3, p);
    }


    /**
     * 保存用户的人脸模板
     *
     * @param sn        设备sn
     * @param userId    用户身份证号
     * @param url       用户人脸服务器路径
     * @param personPic 用户人脸
     */
    public void savePersonPicData(String sn, String userId, String url, String personPic) {
        List<Device> allDevice = getAllDevice(sn);
        if (allDevice == null || allDevice.size() == 0) {
            return;
        }
        BussPersonData bussPersonData = new BussPersonData();
        bussPersonData.setOcvFace(personPic);
        bussPersonData.setImgPath(url);
        EntityWrapper<BussPersonData> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("id_card_number", userId);
        List<BussPersonData> personDatas = bussPersonDataService.selectList(entityWrapper);
        if (null != personDatas && personDatas.size() > 0) {
            //这个人不一定是头一个设备录入人脸
            bussPersonDataService.update(bussPersonData, entityWrapper);
        } else {
            bussPersonData.setIdCardType(1);
            bussPersonData.setIdCardNumber(userId);
            bussPersonData.setDeviceSn(sn);
            bussPersonData.setCreateDate(new Date());
            bussPersonDataService.insert(bussPersonData);
        }
        List<Device> otherDevices = getAllOtherDevice(sn, allDevice.get(0).getProjectCode(), allDevice.get(0).getOrganizationCode());
        // 下发人脸模板
        Map<String, Object> p = new HashMap<>();
        p.put("userId", userId);
        p.put("base64", personPic);
        distributeCommand(otherDevices, 2, p);

    }

    /**
     * 保存更新设备信息
     *
     * @param sn         设备sn
     * @param version    设备固件版本
     * @param ip         设备ip地址
     * @param algVersion 设备算法版本
     */
    public void saveDeviceInfo(String sn, String version, String ip, String algVersion) {
        EntityWrapper<Device> wrapper = new EntityWrapper<>();
        wrapper.eq("sn", sn);
        List<Device> devices = deviceService.selectList(wrapper);
        if (null != devices && devices.size() > 0) {
            Device device = new Device();
            device.setId(devices.get(0).getId());
            device.setIp(ip);
            device.setVersion(version);
            device.setAlgVersion(algVersion);
            device.setUpdateDate(new Date());
            device.setTalk(new Date());
            deviceService.updateById(device);
        }
    }

    /**
     * 更新设备信息
     *
     * @param sn 设备sn
     */
    public void udpateDeviceInfo(String sn) {
        EntityWrapper<Device> wrapper = new EntityWrapper<>();
        wrapper.eq("sn", sn);
        wrapper.eq("is_del", 0);
        List<Device> devices = deviceService.selectList(wrapper);
        if (null != devices && devices.size() > 0) {
            Device device = new Device();
            device.setId(devices.get(0).getId());
            device.setUpdateDate(new Date());
            device.setTalk(new Date());
            deviceService.updateById(device);
        }
    }

    /**
     * 查询考勤记录
     *
     * @param sn
     * @param recordTime
     * @param userId
     */
    public boolean queryRecord(String sn, String recordTime, String userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("sn", sn);
        params.put("status", 1);
        params.put("is_del", 0);
        List<Device> allDevices = deviceService.selectByMap(params);
        if (allDevices != null && allDevices.size() > 0) {
            Device device = allDevices.get(0);
            Map<String, Object> projectParams = new HashMap<>();
            projectParams.put("project_code", device.getProjectCode());
            projectParams.put("id_card_number", userId);
            projectParams.put("is_del", 0);
            //1 带进场 2 进场 3退场
            projectParams.put("join_status", 2);
            List<BussProjectWorker> bussProjectWorkers = bussProjectWorkerService.selectByMap(projectParams);
            //正常和非正常考勤
            if (null != bussProjectWorkers && bussProjectWorkers.size() > 0) {
                EntityWrapper<Record> wrapper = new EntityWrapper<>();
                wrapper.eq("device_sn", sn);
                wrapper.eq("time", recordTime);
                wrapper.eq("id_card_number", userId);
                List<Record> records = recordService.selectList(wrapper);
                if (null != records && records.size() > 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                EntityWrapper<BussDeviceRecordExceptionData> wrapper = new EntityWrapper<>();
                wrapper.eq("device_sn", sn);
                wrapper.eq("time", recordTime);
                wrapper.eq("id_card_number", userId);
                List<BussDeviceRecordExceptionData> records = bussDeviceRecordExceptionDataService.selectList(wrapper);
                return null != records && records.size() > 0;

            }
        }
        return false;
    }

    /**
     * 新设备上线 下发所有人员信息
     *
     * @param sn
     */
    public void queryAllUser(String sn) {
        Device device = deviceService.getBySn(sn);
        String projectCode = device.getProjectCode();
        List<WorkerMaster> workerList = workerMasterService.getWorkerListByProjectCode(projectCode);
        // 人员信息
        List<String> userInfoList = new ArrayList<>();
        // 头像
        List<String> userPhotoList = new ArrayList<>();
        // 考勤照片
        List<String> userFaceList = new ArrayList<>();
        workerList.forEach(worker -> {
            userInfoList.add(command.jointUserInfo(DataUtil.getUUID(),
                    worker.getIdCardNumber(),
                    worker.getWorkerName(),
                    worker.getCardNumber())
            );
            try {
                if (StringUtils.isNotBlank(worker.getFace())) {
                    String faceStr = worker.getFace().split(",")[0];
                    byte[] faceData = fileUtil.getFile(faceStr);
                    String imgBase64Str = Base64Util.encode(faceData);
                    userFaceList.add(command.jointUserFace(DataUtil.getUUID(), worker.getIdCardNumber(), imgBase64Str));
                }
                if (StringUtils.isNotBlank(worker.getHeadImage())) {
                    byte[] headImageData = fileUtil.getFile(worker.getHeadImage().split(",")[0]);
                    String headImageBase64 = Base64Util.encode(headImageData);
                    userPhotoList.add(command.jointUserPhoto(DataUtil.getUUID(), worker.getIdCardNumber(), headImageBase64));
                }
/*
                if(worker.getHeadImage() != null){
                    URL url = new URL("http://localhost:9066" + worker.getHeadImage());
                    byte[] arr = HttpClientUtil.getByteFromUrl(url);
                    if(arr != null){
                        String photo = Base64.getEncoder().encodeToString(arr);
                        userPhotoList.add(command.jointUserPhoto(DataUtil.getUUID(),worker.getIdCardNumber(),photo));
                    }
                }*/
            } catch (IOException e) {
                e.printStackTrace();
            }


        });
        List<String> commandList = getCommandList(device.getSn());
        commandList.addAll(userInfoList);
        commandList.addAll(userPhotoList);
        commandList.addAll(userFaceList);
        logger.info("DEVICE SN: " + sn + " 共计下发 " + (userInfoList.size() + userPhotoList.size() + userFaceList.size()) + "条命令");
        redisUtil.set(DEVICE_GET_COMMAND_REDIS_KEY + device.getSn(), commandList,60*24L);

    }


    /**
     * 多条命令入库
     *
     * @param commandList
     */
    public void saveCommand(List<BussDeviceCommand> commandList) {
        bussDeviceCommandService.insertBatch(commandList);
    }

    /**
     * 命令入库
     *
     * @param command
     */
    public void saveCommand(BussDeviceCommand command) {
        bussDeviceCommandService.insert(command);
    }

    /**
     * 更新命令执行状态
     *
     * @param commandId
     * @param result    状态，0=未执行，1=成功，-1=失败，999=取消
     * @return
     */
    public void updateCommand(String commandId, Long result) {
        BussDeviceCommand command = new BussDeviceCommand();
        command.setState(result);
        command.setProcessDate(new Date());
        bussDeviceCommandService.update(command, new EntityWrapper<BussDeviceCommand>().eq("uuid", commandId));
    }


    /**
     * 获取设备
     *
     * @param sn 设别号
     * @return
     */
    private List<Device> getAllDevice(String sn) {
        Map<String, Object> params = new HashMap<>(3);
        params.put("sn", sn);
        params.put("status", 1);
        params.put("is_del", 0);

        return deviceService.selectByMap(params);
    }

    /**
     * 获取剩余设备
     *
     * @param projectCode
     * @param orgCode
     * @return
     */
    private List<Device> getAllOtherDevice(String sn, String projectCode, String orgCode) {
        return deviceService.selectList(new EntityWrapper<Device>().eq("project_code", projectCode)
                .eq("organization_code", orgCode).ne("sn", sn));
    }


    /**
     * @param devices 设备列表
     * @param type    1-人员信息 2-人脸信息 3-头像信息
     * @param params  参数
     * @return
     */
    private void distributeCommand(List<Device> devices, int type, Map<String, Object> params) {
        Date now = new Date();
        List<BussDeviceCommand> commandList = new ArrayList<>();
        devices.forEach(device -> {
            List<String> redisCommand = getCommandList(device.getSn());
            String uuid = DataUtil.getUUID();
            String strCommand;
            if (type == 1) {
                strCommand = command.jointUserInfo(uuid, params.get("userId").toString(), params.get("userName").toString(), params.get("card") + "");
                logger.info("Device Number: " + device.getSn() + " 发送人员信息");
            } else if (type == 2) {
                strCommand = command.jointUserFace(uuid, params.get("userId").toString(), params.get("base64").toString());
                logger.info("Device Number: " + device.getSn() + " 发送人脸信息");
            } else {
                strCommand = command.jointUserPhoto(uuid, params.get("userId").toString(), params.get("base64").toString());
                logger.info("Device Number: " + device.getSn() + " 发送头像信息");
            }
            // 组装命令
            redisCommand.add(strCommand);
            BussDeviceCommand bizCommand = new BussDeviceCommand();
            bizCommand.setDeviceSn(device.getSn());
            bizCommand.setUuid(uuid);
            bizCommand.setIdCardNumber(params.get("userId").toString());
            bizCommand.setIdCardType(1);
            bizCommand.setState(0L);
            bizCommand.setCreateDate(now);
            bizCommand.setDescription(strCommand);
            commandList.add(bizCommand);
            // 存入redis
            redisUtil.set(DEVICE_GET_COMMAND_REDIS_KEY + device.getSn(), redisCommand,60*24L);
        });
        saveCommand(commandList);
    }

    /**
     * 获取redis中该设备的命令列表
     *
     * @param sn
     * @return
     */
    private List<String> getCommandList(String sn) {
        List<String> redisCommand;
        // 查找redis中是否有待发命令
        if (redisUtil.exists(DEVICE_GET_COMMAND_REDIS_KEY + sn)) {
            logger.info("Device Number: " + sn + " 已存在命令队列");
            redisCommand = (List<String>) redisUtil.get(DEVICE_GET_COMMAND_REDIS_KEY + sn);
            logger.info("Device Number: " + sn + " 队列长度: " + redisCommand.size());
        } else {
            redisCommand = new ArrayList<>();
        }
        return redisCommand;
    }
}
