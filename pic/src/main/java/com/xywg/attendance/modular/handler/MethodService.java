package com.xywg.attendance.modular.handler;

import com.xywg.attendance.common.global.RequestUrlEnum;
import com.xywg.attendance.common.model.TransmissionMessageTemplate;

import com.xywg.attendance.common.model.TransmissionMessageTemplateSubclass;
import com.xywg.attendance.common.utils.DateUtils;
import com.xywg.attendance.common.utils.UrlUtil;
import com.xywg.attendance.modular.system.model.Device;
import com.xywg.attendance.modular.system.service.IDeviceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xywg.attendance.common.global.GlobalStaticConstant.*;
import static com.xywg.attendance.common.global.GlobalStaticConstant.SEPARATOR_EQUAL_SIGN;
import static com.xywg.attendance.common.global.GlobalStaticConstant.SEPARATOR_WITH;

/**
 * @author hjy
 * @date 2019/2/21
 * 所有的设备上报处理
 */
@Service
public class MethodService {
    @Autowired
    private MethodServiceHandle methodServiceHandle;
    @Autowired
    private IDeviceService deviceService;
    @Autowired
    private MongoTemplate mongoTemplate;

    private Logger logger = LoggerFactory.getLogger(MethodService.class.getName());

    /**
     * @return
     */
    public void runMethod(TransmissionMessageTemplate msg) {
        TransmissionMessageTemplateSubclass subclass = getMessageTemplateSubclass(msg);

//        mongoTemplate.save(subclass, MONGODB_ORIGINAL_DATA);
        try {
            String path = msg.getUrl();
            RequestUrlEnum methodNameEnum = RequestUrlEnum.getMethodName(path, msg.getMethod().toString());
            if (methodNameEnum == null) {
                logger.info("There is no match to the associated URL regular expression: " + path);
                return;
            }
            logger.info("Function Description: " + methodNameEnum.getName() + "\tRequest Method: "
                    + msg.getMethod() + " \tReceipt Time:" + msg.getDate() + " \tRequest Path: " + msg.getUrl());

            if (!judgeDeviceValid(msg.getUrl())) {
                return;
            }
            switch (methodNameEnum) {

//            //交换公钥（支持通信加密的场合）
//            case "handleExchangePublicKey":
//                methodServiceHandle.handleExchangePublicKey( msg);
//                break;
//            //交换因子（支持通信加密的场合）
//            case "handleExchangeFactor":
//                methodServiceHandle.handleExchangeFactor( msg);
//                break;
//            //推送配置信息
//            case "pushConfigInformation":
//                methodServiceHandle.pushConfigInformation(msg);
//                break;
                //上传更新信息
//                case UPLOAD_UPDATE_INFORMATION:
//                    methodServiceHandle.uploadUpdateInformation(msg);
//                    break;
                //上传考勤记录
//                case ATTENDANCE_RECORD:
//                    methodServiceHandle.handleUploadAttendance(msg);
//                    break;
                //上传考勤图片
                case ATTENDANCE_RECORD_PICTURE:
                    methodServiceHandle.handleUploadAttendancePhoto(msg);
                    break;
//            //上传一体化模板(该功能实际被用于上传指纹模板)
//            case "handleUploadTempPhoto":
//                methodServiceHandle.handleUploadTempPhoto(msg);
//                break;
                //处理具有相同urL: /iclock/cdata?SN=${SerialNumber}&table=OPERLOG&ContentType=${Value} HTTP/1.1
//                case TABLE_OPERLOG:
//                    methodServiceHandle.handleTableOperLog(msg);
//                    break;
//            //上传身份证信息
//            case UPLOAD_IDCARD_INFORMATION:
//                methodServiceHandle.uploadIDCardInformation( msg);
//                break;
//                //获取命令
//                case HANDLE_GET_COMMAND:
//                    methodServiceHandle.handleGetCommand(msg);
//                    break;
//                // 设备自动获取当前项目下所有人员命令
//                case HANDLE_GET_ALL_COMMAND:
//                   // methodServiceHandle.handleGetAllCommand(msg);
//                    break;
//                //命令回复
//                case HANDLE_COMMAND_RESPONSE:
//                    methodServiceHandle.handleCommandResponse(msg);
//                    break;
                //异地考勤
//            case "handleBeyondAttendance":
//                methodServiceHandle.handleBeyondAttendance(msg);
//                break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            subclass.setError(e.getMessage());
            mongoTemplate.save(subclass, MONGODB_ORIGINAL_DATA_ERROR);
        }
    }

    public TransmissionMessageTemplateSubclass getMessageTemplateSubclass(TransmissionMessageTemplate message) {
        TransmissionMessageTemplateSubclass tm = new TransmissionMessageTemplateSubclass();
        BeanUtils.copyProperties(message, tm);
        tm.setInsertDate(DateUtils.getDateTime());
        return tm;
    }

    /**
     * 判断是否是有效设备
     *
     * @return
     */
    private Boolean judgeDeviceValid(String url) {
        String sn = getSn(url);
        if (StringUtils.isBlank(sn)) {
            return false;
        }
        Map<String, Object> params = new HashMap<>(3);
        params.put("sn", sn);
        params.put("status", 1);
        params.put("is_del", 0);
        List<Device> allDevices = deviceService.selectByMap(params);
        if (allDevices == null || allDevices.size() == 0) {
            //如果这个设备不存在库中,那么这个设备上报的数据也直接丢弃
            return false;
        }
        return true;
    }


    public static Map<String, String> handleUrl(String url) {
        String[] path = url.split("\\?");
        if (!url.contains(SEPARATOR_WITH) && !url.contains(SEPARATOR_EQUAL_SIGN)) {
            return null;
        }
        Map<String, String> map = new HashMap<>(1);
        if (path[1].contains(SEPARATOR_WITH)) {
            map = UrlUtil.handleSeparate(path[1], SEPARATOR_WITH, SEPARATOR_EQUAL_SIGN);
        } else {
            String[] split = path[1].split(SEPARATOR_EQUAL_SIGN);
            map.put(split[0], split[1]);
        }
        return map;
    }


    public static String getSn(String url) {
        Map<String, String> map = handleUrl(url);
        if (map == null) {
            return "";
        }
        if (!map.containsKey("SN")) {
            return "";
        }
        return map.get("SN");
    }


}
