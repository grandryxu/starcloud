package com.xywg.admin.modular.device.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.enums.IdType;
import com.xywg.admin.modular.worker.model.WorkerMaster;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 考勤记录表
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-30
 */
@TableName("buss_device_record")
public class DeviceRecord extends Model<DeviceRecord> {

    private static final long serialVersionUID = 1L;

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
     * 考勤机
     */
    @TableField("device_sn")
    private String deviceSn;
    /**
     * 安全帽id
     */
    @TableField("sh_imei")
    private String shImei;
    /**
     * 代考勤人员字段
     */
    @TableField("record_user_id")
    private Long recordUserId;
    /**
     * 考勤时间
     */
    private Date time;
    
    
    /**
     * 考勤时间
     */
    @TableField(exist = false)
    private String recordTime;
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
    private BigDecimal lng;
    /**
     * 纬度
     */
    private BigDecimal lat;
    /**
     * 考勤地址
     */
    private String address;
    /**
     * 来源1：手环 2：安全帽 3：考勤机 4:手机考勤 5：二维码
     */
    private Integer source;
    /**
     * 是否有效 0无效 1有效
     */
    @TableField("is_valid")
    private Integer isValid;
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
     * 备注
     */
    private String remark;
    /**
     * 地图
     */
    private String map;
    /**
     * 缩略图
     */
    @TableField("icon_photo")
    private String iconPhoto;
    /**
     * 考勤类型，3 上班 4下班
     */
    @TableField("device_type")
    private Integer deviceType;
    /**
     * 是否统计（0：否；1：是）
     */
    @TableField("is_deal")
    private String isDeal;
    
    @TableField("worker_name")
    private String workerName;

    @TableField(exist = false)
    private Device device;

    @TableField(exist = false)
    private WorkerMaster person;
    /**
	 * 对外项目工人id
	 */
	@TableField(exist = false)
	private String projectWorkerId;
    
    public String getProjectWorkerId() {
		return projectWorkerId;
	}

	public void setProjectWorkerId(String projectWorkerId) {
		this.projectWorkerId = projectWorkerId;
	}

	public WorkerMaster getPerson() {
		return person;
	}

	public void setPerson(WorkerMaster person) {
		this.person = person;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

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

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    public String getShImei() {
        return shImei;
    }

    public void setShImei(String shImei) {
        this.shImei = shImei;
    }

    public Long getRecordUserId() {
        return recordUserId;
    }

    public void setRecordUserId(Long recordUserId) {
        this.recordUserId = recordUserId;
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

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getIconPhoto() {
        return iconPhoto;
    }

    public void setIconPhoto(String iconPhoto) {
        this.iconPhoto = iconPhoto;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(String isDeal) {
        this.isDeal = isDeal;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    
    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	@Override
    public String toString() {
        return "DeviceRecord{" +
        "id=" + id +
        ", organizationCode=" + organizationCode +
        ", projectCode=" + projectCode +
        ", teamSysNo=" + teamSysNo +
        ", idCardType=" + idCardType +
        ", idCardNumber=" + idCardNumber +
        ", deviceSn=" + deviceSn +
        ", shImei=" + shImei +
        ", recordUserId=" + recordUserId +
        ", time=" + time +
        ", type=" + type +
        ", photo=" + photo +
        ", lng=" + lng +
        ", lat=" + lat +
        ", address=" + address +
        ", source=" + source +
        ", isValid=" + isValid +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", remark=" + remark +
        ", map=" + map +
        ", iconPhoto=" + iconPhoto +
        ", deviceType=" + deviceType +
        ", isDeal=" + isDeal +
        ", workerName=" + workerName +
        "}";
    }
}
