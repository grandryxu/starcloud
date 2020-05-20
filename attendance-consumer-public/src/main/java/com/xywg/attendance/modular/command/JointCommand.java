package com.xywg.attendance.modular.command;

import org.springframework.stereotype.Service;

/**
 * @author hjy
 * @date 2019/5/7
 * 拼接命令
 */
@Service
public class JointCommand {

    /**
     * 用户基本信息
     * @param uuid
     * @param idCardNumber
     * @param userName
     */
    public String  jointUserInfo(String uuid,String idCardNumber,String userName,String card){
        StringBuilder userInfoBuilder= new StringBuilder();
        //用户信息命令
        userInfoBuilder.append("C:").append(uuid).append(":DATA UPDATE USERINFO PIN=")
                .append(idCardNumber)
                .append("\tName=").append(userName).append("\tPri=").append("0").append("\tPasswd=")
                .append("\tCard=").append(card)
                .append("\tGrp=").append("1").append("\tTZ=").append("0000000000000000")
                .append("\tVerify=").append("\tViceCard=").append("\n");
        return userInfoBuilder.toString();
    }

    /**
     * 人脸模板(考勤对比照)
     * @param uuid
     * @param idCardNumber
     * @param base64
     */
    public String  jointUserFace(String uuid,String idCardNumber,String base64){
        StringBuilder userFaceBuilder= new StringBuilder();
        userFaceBuilder.append("C:").append(uuid).append(":DATA UPDATE BIOPHOTO PIN=")
                .append(idCardNumber).append("\tType=").append(0).append("\tSize=")
                .append(base64.length()).append("\tContent=").append(base64)
                .append("\tFormat=").append(0).append("\tUrl=").append("\n");
        return userFaceBuilder.toString();
    }

    /**
     * 大头贴
     * @param uuid
     * @param idCardNumber
     * @param base64
     */
    public String  jointUserPhoto(String uuid,String idCardNumber,String base64){
        StringBuilder userPhotoBuilder= new StringBuilder();
        userPhotoBuilder.append("C:").append(uuid).append(":DATA UPDATE USERPIC PIN=")
                .append(idCardNumber)
                .append("\tSize=").append(base64.length()).append("\t").append("Content=")
                .append(base64).append("\n");

        return userPhotoBuilder.toString();

    }


}
