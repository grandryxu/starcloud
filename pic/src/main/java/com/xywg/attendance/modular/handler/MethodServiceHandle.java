package com.xywg.attendance.modular.handler;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.xywg.attendance.business.LaborSystem;
import com.xywg.attendance.common.global.UploadFlagEnum;
import com.xywg.attendance.common.model.TransmissionMessageTemplate;
import com.xywg.attendance.common.utils.*;
import com.xywg.attendance.common.utils.FTPClientUtil;
import com.xywg.attendance.modular.model.DataMessage;
import com.xywg.attendance.modular.system.model.BussWorkerMaster;
import com.xywg.attendance.modular.system.service.BussWorkerMasterService;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author hjy
 * @date 2019/2/21
 */
@Service
public class MethodServiceHandle {
    @Autowired
    private LaborSystem laborSystem;

    @Autowired
    private BussWorkerMasterService workerMasterService;


    @Value("${ftp.host}")
    private String host;
    @Value("${ftp.port}")
    private String port;
    @Value("${ftp.username}")
    private String username;
    @Value("${ftp.password}")
    private String password;

    private Logger logger = LoggerFactory.getLogger(MethodServiceHandle.class.getName());

    private Logger logger2 = LoggerFactory.getLogger("");

    /**
     * table=OPERLOG相同URL
     * POST /iclock/cdata?SN=${SerialNumber}&table=OPERLOG&Stamp=${XXX} HTTP/1.1
     *
     * @param msg 中所带参数
     */
    public void handleTableOperLog(TransmissionMessageTemplate msg) {


        List<DataMessage> bodys = UrlUtil.handleSeparateList(msg.getBody(), "\n", " ", "\t", "=");

        //说明: 因为上传数据包时,里面包含了各种操作数据,所以必须循环选择处理
        for (DataMessage dataMessage : bodys) {
            UploadFlagEnum uploadFlagEnum = UploadFlagEnum.getUploadFlagEnum(dataMessage.getKeyName());
            if (uploadFlagEnum == null) {
                logger.info("OPERLOG没有匹配到相关正则:" + msg.getBody());
                logger.info("<OPERLOG> No match to the associated regex: " + msg.getBody());
                continue;
            }

            switch (uploadFlagEnum) {
                //上传用户信息(设备新增用户)
                case USER:
                    handleUploadUserInfo(msg, dataMessage);
                    break;
                //上传指纹模板(设备新增记录)
//                case FP:
//                    handleFingerprintTemplate(msg, dataMessage);
//                    break;
                //上传面部模板(设备新增记录)
//                case FACE:
//                    handleFaceTemplate(msg, dataMessage);
//                    break;
                //上传指静脉模板(设备新增记录)
//                case FVEIN:
//                    handleVeinTemplate(msg, dataMessage);
//                    break;
                //上传用户照片(设备新增记录)
                case USERPIC:
                    handleUploadUserPhoto(msg);
                    break;
                //上传比对照片(设备新增记录)
                case BIOPHOTO:
                    handleUploadContrastPhoto(msg);
                    break;
                default:
                    break;
            }
        }

    }


    /**
     * 获取命令
     *
     * @param msg
     */
    void handleGetCommand(TransmissionMessageTemplate msg) {
        String sn = getUrlKey(msg, "SN");
        laborSystem.udpateDeviceInfo(sn);
    }

    /**
     * 获取命令
     *
     * @param msg
     */
    void handleGetAllCommand(TransmissionMessageTemplate msg) {
        String sn = getUrlKey(msg, "SN");
        logger.info("Device SN: " + sn + " 新上线设备，下发所有人员");
        laborSystem.queryAllUser(sn);
    }


    /**
     * 命令回复\
     * POST /iclock/devicecmd?SN=${SerialNumber}
     * ID=info8487&Return=0&CMD=DATA
     * ID=info8488&Return=0&CMD=DATA
     * ID=info8489&Return=0&CMD=DATA
     *
     * @param msg
     */
    public void handleCommandResponse(TransmissionMessageTemplate msg) {
        String body = msg.getBody().trim();
        String[] lines = body.split("\n");
        if (lines.length > 0) {
            for (String line1 : lines) {
                Map<String, String> line = UrlUtil.handleSeparate(line1, "&", "=");
                if (Integer.parseInt(line.get("Return")) == 0) {
                    laborSystem.updateCommand(line.get("ID"), 1L);
                } else {
                    laborSystem.updateCommand(line.get("ID"), -1L);
                }
            }
        }

    }

    /**
     * 上传更新信息
     * 主要上传客户端的固件版本号、登记用户数、登记指纹数、考勤记录数、设备IP地址、指纹算法版本、人脸算法版本、
     * 注册人脸所需人脸个数、登记人脸数、设备支持功能标示信息
     * Get请求
     * 示例数据:iclock/getrequest?SN=CEXA184460007&INFO=1.0.78,2,0,0,192.168.20.21,12,3,12,0,11110
     * *  info中分别表示了 固件版本号,登记用户数,登记指纹数,考勤记录数,设备IP地址,指纹算法版本,人脸算法版本,注册人脸所需人脸个数,登记人脸数
     * *   ,设备支持功能标示(0表示不支持,1支持)
     * *   设备支持功能标示:
     * *       1 指纹功能
     * *       2 人脸功能
     * *       3 用户照片功能
     * *       4 比对照片功能（支持比对照片功能，参数BioPhotoFun需要设置为1）
     * *       5 可见光人脸模板功能（支持人脸模板该功能，参数BioDataFun需要设置为1）
     *
     * @param msg
     */
    public void uploadUpdateInformation(TransmissionMessageTemplate msg) {
        logger.info("上传更新信息: " + msg.getUrl());
        String info = getUrlKey(msg, "INFO");
        String[] values = info.split(",");
        if (values.length > 9) {
            laborSystem.saveDeviceInfo(getUrlKey(msg, "SN"), values[0], values[4], values[5]);
        }
    }

    /**
     * 处理上传考勤记录
     * /iclock/cdata?SN=0316144680030&table=ATTLOG&Stamp=9999 HTTP/1.1
     * 1452 2015-07-30 15:16:28 0 1 0 0 0
     * 1452 2015-07-30 15:16:29 0 1 0 0 0
     * 1452 2015-07-30 15:16:30 0 1 0 0 0
     * ${Pin}${HT}${Time}${HT}${Status}${HT}${Verify}${HT}${Workcode}${HT}${Reserved}${HT}${Reserved}
     * 说明:pin:工号,time:考勤时间,Status:考勤状态,Verify:验证方式 ,Workcode: workcode编码,Reserved:保留字段
     *
     * @param msg url 中所带参数
     */
    public void handleUploadAttendance(TransmissionMessageTemplate msg) {
        String body = msg.getBody().trim();
        String[] bodyMessage = body.split("\n");
        for (String aBodyMessage : bodyMessage) {
            String[] params = aBodyMessage.split("\t");
            //这个值取决于下发人员时下发的实际值
            String identityCard = params[0];
            String dateTime = params[1];
            if (params.length > 6) {

                Wrapper<BussWorkerMaster> perInfEw = new EntityWrapper<>();
                perInfEw.eq("id_card_number", identityCard);
                perInfEw.eq("is_del", 0);
                BussWorkerMaster bussWorkerMaster = workerMasterService.selectOne(perInfEw);
                if (bussWorkerMaster == null) {
                    continue;
                }
                String userId = bussWorkerMaster.getIdCardNumber();
                String sn = getUrlKey(msg, "SN");
                //如果存在了考勤记录就不在插入(因为有可能考勤照片先插入)
                if (!laborSystem.queryRecord(sn, dateTime, userId)) {
                    logger2.info("考勤记录: " + "\t考勤人: " + identityCard + "\t考勤时间: " + dateTime);
                    //考勤时间
                    laborSystem.saveRecord(getUrlKey(msg, "SN"), null, dateTime, userId);
                }

            }
        }
    }

    /**
     * 处理上传考勤图片
     * /iclock/cdata?SN=${SerialNumber}&table=ATTPHOTO&Stamp=${XXX} HTTP/1.1
     * <p>
     * PIN=${XXX}${LF}SN=${SerialNumber}${LF}size=${XXX}${LF}CMD=uploadphoto${LF}picture=${XXX}
     *
     * @param msg 中所带参数
     */
    public void handleUploadAttendancePhoto(TransmissionMessageTemplate msg) {
        String body = msg.getBody();
        String[] bodys = msg.getBody().split("\nCMD=uploadphoto\npicture=");
        if (body.length() < 2) {
            return;
        }
        Map<String, String> params = UrlUtil.handleSeparate(bodys[0], "\n", "=");
        if (null == params) {
            return;
        }
        String base64 = bodys[1];
        String pictureName = params.get("PIN");
        String[] pins = params.get("PIN").replace(".jpg", "").trim().split("-");
        if (pins.length < 2) {
            return;
        }
        //考勤时间
        String timeParam = DateUtils.getDate(pins[0]);
        String identityCard = pins[1];

        String url = uploadFileToFtp(base64, pictureName);
        Wrapper<BussWorkerMaster> perInfEw = new EntityWrapper<>();
        perInfEw.eq("id_card_number", identityCard);
        perInfEw.eq("is_del", 0);
        BussWorkerMaster bussWorkerMaster = workerMasterService.selectOne(perInfEw);
        if (bussWorkerMaster == null) {
            return;
        }
        //身份证号码信息
        String userId = bussWorkerMaster.getIdCardNumber();

        String sn = getUrlKey(msg, "SN");

        logger2.info("考勤照片: " + pictureName + "\t考勤人: " + identityCard + "\t考勤时间: " + timeParam);
        //如果存储考勤记录就更新考勤照片
        if (laborSystem.queryRecord(sn, timeParam, userId)) {
            laborSystem.updateRecord(sn, url, timeParam, userId);
        } else {
            laborSystem.saveRecord(sn, url, timeParam, userId);
        }
    }

    /**
     * 上传用户信息
     * url=/iclock/cdata?SN=CEXA191160044&table=OPERLOG&Stamp=9999,
     * body=USER PIN=1124568556	Name=看看女	Pri=0	Passwd=	Card=	Grp=1	TZ=0000000100000000	Verify=0	ViceCard=
     *
     * @param msg 中所带参数
     */
    private void handleUploadUserInfo(TransmissionMessageTemplate msg, DataMessage dataMessage) {
        Map<String, String> userParams = dataMessage.getMap();
        laborSystem.saveUserInfo(userParams,msg);
    }

    /**
     * 上传用户照片 (用户头像)
     * 示例数据:USERPIC PIN=1002	FileName=1002.jpg	Size=36288	Content=/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDABsSFBcUERsXFhceHBsgKEIrKCUlKFE6PTBC
     *
     * @param msg 中所带参数
     */
    private void handleUploadUserPhoto(TransmissionMessageTemplate msg) {
        String body = msg.getBody();
        String[] bodys = body.split("\tContent=");
        if (bodys.length < 2) {
            return;
        }
        Map<String, String> params = UrlUtil.handleSeparate(bodys[0], "\t", "=");
        String base64 = bodys[1];
        String url = uploadFileToFtp(base64, params.get("FileName"));
        laborSystem.updateUserPic(getUrlKey(msg, "SN"), params.get("USERPIC PIN"), params.get("FileName"), url, bodys[1]);
    }


    /**
     * 上传比对照片
     * 注意: 实际中上传的对比照片是添加人员时的录入人脸信息,不是人脸头像信息
     * url=/iclock/cdata?SN=CEXA191160044&table=OPERLOG&Stamp=9999,
     * body=BIOPHOTO PIN=1124568556	FileName=1124568556.jpg	Type=9	Size=55804	Content=/9j/4AAQSkZJRgABAQAAAQABAAD/2//2Q==
     *
     * @param msg
     */
    private void handleUploadContrastPhoto(TransmissionMessageTemplate msg) {
        String body = msg.getBody();
        String[] bodys = body.split("\tContent=");
        if (bodys.length < 2) {
            return;
        }
        Map<String, String> personPicData = UrlUtil.handleSeparate(bodys[0], "\t", "=");
        String url = uploadFileToFtp(bodys[1], personPicData.get("FileName"));
        laborSystem.savePersonPicData(getUrlKey(msg, "SN"), personPicData.get("BIOPHOTO PIN"), url, bodys[1]);
    }


    /**
     * 获取设备sn
     *
     * @param msg
     * @return
     */
    public String getUrlKey(TransmissionMessageTemplate msg, String key) {
        String url = msg.getUrl();
        String[] path = url.split("\\?");
        if (path.length > 1) {
            Map<String, String> map = UrlUtil.handleSeparate(path[1], "&", "=");
            return map.get(key);
        } else {
            return "";
        }
    }

    /**
     * 上传文件
     *
     * @return
     */
    private String uploadFileToFtp(String base64, String fileName) {
        String url = null;
        InputStream inputStream = null;
        FTPClient client = null;
        try {
            client = FTPClientUtil.connectFtp(host, Integer.parseInt(port), username, password);
            inputStream = new ByteArrayInputStream(Base64.decodeBase64(base64));
            String filePath= "/attendance/" + DateUtils.getDate("yyyy-MM-dd") + "/";
            FTPClientUtil.uploadFile(filePath, inputStream, fileName, client);
            url = filePath+ fileName;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                FTPClientUtil.disconnect(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return url;
    }

//---------------------------------------------------------------------------------------------------------------------------

    /**
     * 交换公钥
     *
     * @param msg
     */
    void handleExchangePublicKey(TransmissionMessageTemplate msg) {
        //todo  业务逻辑处理

    }

    /**
     * 交换因子
     *
     * @param msg
     */
    void handleExchangeFactor(TransmissionMessageTemplate msg) {
        //todo  业务逻辑处理


    }


    /**
     * 推送配置信息
     * 当设备配置信息有变化时将主动推送服务器
     *
     * @param msg
     */
    public void pushConfigInformation(TransmissionMessageTemplate msg) {
        //todo  业务逻辑处理
        String sn = getUrlKey(msg, "SN");

        laborSystem.udpateDeviceInfo(sn);


    }


    /**
     * 上传指纹模板
     * 注意: 该功能在设备上实际 是用设备一体化 的请求 处理的    所有该方法在当前版本相当于弃用
     *
     * @param msg 中所带参数
     */
    public void handleUploadFpTemp(TransmissionMessageTemplate msg) {
        //String [] bodyMessage= body.split("\n");
        //todo  业务逻辑处理


    }

    /**
     * 上传一体化模板
     *
     * @param msg 中所带参数
     *            //样例数据:
     *            BIODATA Pin=306 No=0 Index=2 Valid=1 Duress=0 Type=8 MajorVer=1 MinorVer=0 Format=0 Tmp=AAAAAAAA
     */
    public void handleUploadTempPhoto(TransmissionMessageTemplate msg) {

        String body = msg.getBody();
        List<DataMessage> list = UrlUtil.handleSeparateList(body, "\n", " ", "\t", "=");
        //todo  业务逻辑处理
       /* String[] temps = body.split("\n");
        for (int i = 0; i < temps.length; i++) {
            String[] temp = temps[i].split("\t");
            if (temp != null && temp.length > 1) {
                Map<String, String> allParams = UrlUtil.handleSeparate(temp[1], "\t", "=");
                String userId = allParams.get("Pin");
                switch (Integer.parseInt(allParams.get("Type"))) {
                    //通用
                    case 0:
                        break;
                    //指纹
                    case 1:
                        break;
                    //面部
                    case 2:
                        break;
                    default:
                        break;
                }
                byte[] userTemp = allParams.get("Content").getBytes();
            }
        }*/

    }


    /**
     * 异地考勤
     */
    void handleBeyondAttendance(TransmissionMessageTemplate msg) {
        //todo 异地考勤
        /**
         * 正常相应
         *DATA${SP}UPDATE${SP}USERINFO${SP}PIN=${XXX}${HT}Name=${XXX}${HT}Passwd=${XXX}
         * ${HT}Card=${XXX}${HT}Grp=${XXX}${HT}TZ=${XXX}${HT}Pri=${XXX}
         * DATA${SP}UPDATE${SP}FINGERTMP${SP}PIN=${XXX}${HT}FID=${XXX}${HT}Size=${XXX}${HT}
         * Valid=${XXX}${HT}TMP=${XXX}
         *
         *
         *
         */

        //commonMethod.sendMessage(ctx, "OK");
    }


}
