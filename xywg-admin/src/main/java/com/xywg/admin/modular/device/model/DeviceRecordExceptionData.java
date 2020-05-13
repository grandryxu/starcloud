package com.xywg.admin.modular.device.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 异常考勤记录表
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-12
 */
@TableName("buss_device_record_exception_data")
public class DeviceRecordExceptionData extends Model<DeviceRecordExceptionData> {

    private static final long serialVersionUID = 1L;

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 所属企业组织机构代码
     */
    @TableField("organization_code")
    private String organizationCode;
    /**
     * 项目id
     */
    @TableField("project_code")
    private String projectCode;
    /**
     * 班组编号 外部系统与平台对接创建班组后，由系统返回该编号
     */
    @TableField("team_sys_no")
    private Integer teamSysNo;
    /**
     * 考勤机
     */
    @TableField("device_sn")
    private String deviceSn;
    @TableField("id_card_type")
    private Integer idCardType;
    @TableField("id_card_number")
    private String idCardNumber;
    /**
     * 身份证
     */
    @TableField("Id_card")
    private String idCard;
    /**
     * 考勤机
     */
    @TableField("device_id")
    private Long deviceId;
    /**
     * 安全帽id
     */
    @TableField("sh_id")
    private Long shId;
    /**
     * 考勤时间
     */
    private Date time;
    /**
     * 类型0：补考勤 1：正常
     */
    private Integer type;
    /**
     * 照片地址
     */
    private String photo;
    /**
     * 经度
     */
    private Double lng;
    /**
     * 纬度
     */
    private Double lat;
    /**
     * 来源1：手环 2：安全帽 3：考勤机 4:手机考勤
     */
    private Integer source;
    /**
     * 添加人
     */
    @TableField("create_date")
    private Date createDate;
    /**
     * 添加时间
     */
    @TableField("create_user")
    private String createUser;
    /**
     * 修改时间
     */
    @TableField("update_date")
    private Date updateDate;
    /**
     * 修改人
     */
    @TableField("update_user")
    private String updateUser;
    /**
     * 是否可用 1：是 -1：删除
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 异常类型：1=注册人不在项目中，2=考勤人不在项目中，3=禁用时考勤数据
     */
    @TableField(exist = false)
    private String exceptionType;
    /**
     * 人脸模板
     */
    @TableField("tx_face")
    private String txFace;
    @TableField("alg_version")
    private String algVersion;
    private String personDataType;
    @TableField("user_name")
    private String userName;
    /**
     * 考勤类型，3 上班 4下班
     */
    @TableField("device_type")
    private Integer deviceType;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public Integer getTeamSysNo() {
        return teamSysNo;
    }

    public void setTeamSysNo(Integer teamSysNo) {
        this.teamSysNo = teamSysNo;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getShId() {
        return shId;
    }

    public void setShId(Long shId) {
        this.shId = shId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getTxFace() {
        return txFace;
    }

    public void setTxFace(String txFace) {
        this.txFace = txFace;
    }

    public String getAlgVersion() {
        return algVersion;
    }

    public void setAlgVersion(String algVersion) {
        this.algVersion = algVersion;
    }

    public String getPersonDataType() {
        return personDataType;
    }

    public void setPersonDataType(String personDataType) {
        this.personDataType = personDataType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DeviceRecordExceptionData{" +
        "id=" + id +
        ", organizationCode=" + organizationCode +
        ", projectCode=" + projectCode +
        ", teamSysNo=" + teamSysNo +
        ", deviceSn=" + deviceSn +
        ", idCardType=" + idCardType +
        ", idCardNumber=" + idCardNumber +
        ", idCard=" + idCard +
        ", deviceId=" + deviceId +
        ", shId=" + shId +
        ", time=" + time +
        ", type=" + type +
        ", photo=" + photo +
        ", lng=" + lng +
        ", lat=" + lat +
        ", source=" + source +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", status=" + status +
        ", remark=" + remark +
        ", exceptionType=" + exceptionType +
        ", txFace=" + txFace +
        ", algVersion=" + algVersion +
        ", personDataType=" + personDataType +
        ", userName=" + userName +
        "}";
    }
}
