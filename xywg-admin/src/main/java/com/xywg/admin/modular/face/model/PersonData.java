package com.xywg.admin.modular.face.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 人脸模型表
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-30
 */
@TableName("buss_person_data")
public class PersonData extends Model<PersonData> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 上传的设备ID
     */
//    @TableField("device_id")
//    private Long deviceId;
    /**
     * 身份证类型
     */
    @TableField("id_card_type")
    private Integer idCardType;
    /**
     * 身份证号
     */
    @TableField("id_card_number")
    private String idCardNumber;
    /**
     * 用户姓名(考勤机部分)
     */
    @TableField("user_Name")
    private String userName;
    /**
     * 用户id用于百度人脸比对
     */
    private String uid;
    /**
     * 算法版本
     */
    @TableField("alg_version")
    private String algVersion;
    /**
     * 人脸模板
     */
    @TableField("tx_face")
    private String txFace;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 0考勤机1手机端
     */
    private Integer type;
    /**
     * 版本号
     */
    @TableField("ocv_version")
    private String ocvVersion;
    /**
     * 人脸模板
     */
    @TableField("ocv_face")
    private String ocvFace;
    /**
     * 保存图片路径
     */
    @TableField("img_path")
    private String imgPath;
    
    /**
     * 算法版本
     */
    @TableField("device_sn")
    private String deviceSn;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Long getDeviceId() {
//        return deviceId;
//    }
//
//    public void setDeviceId(Long deviceId) {
//        this.deviceId = deviceId;
//    }

    public Integer getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(Integer idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAlgVersion() {
        return algVersion;
    }

    public void setAlgVersion(String algVersion) {
        this.algVersion = algVersion;
    }

    public String getTxFace() {
        return txFace;
    }

    public void setTxFace(String txFace) {
        this.txFace = txFace;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOcvVersion() {
        return ocvVersion;
    }

    public void setOcvVersion(String ocvVersion) {
        this.ocvVersion = ocvVersion;
    }

    public String getOcvFace() {
        return ocvFace;
    }

    public void setOcvFace(String ocvFace) {
        this.ocvFace = ocvFace;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
    
    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonData{" +
        "id=" + id +
        ", deviceSn=" + deviceSn +
        ", idCardType=" + idCardType +
        ", idCardNumber=" + idCardNumber +
        ", userName=" + userName +
        ", uid=" + uid +
        ", algVersion=" + algVersion +
        ", txFace=" + txFace +
        ", createDate=" + createDate +
        ", type=" + type +
        ", ocvVersion=" + ocvVersion +
        ", ocvFace=" + ocvFace +
        ", imgPath=" + imgPath +
        "}";
    }
}
