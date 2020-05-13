package com.xywg.admin.modular.device.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.xywg.admin.modular.project.model.ProjectMaster;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 考勤机
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-30
 */
@TableName("buss_device")
public class Device extends Model<Device> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
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
     * 类型id
     */
    @TableField("type_id")
    private Long typeId;
    /**
     * 设备序列号
     */
    private String sn;
    /**
     * 设备名称
     */
    private String name;
    /**
     * ip地址
     */
    private String ip;
    /**
     * mac地址
     */
    private String mac;
    /**
     * 版本
     */
    private String version;
    /**
     * 设备号
     */
    private String code;
    /**
     * 通信密码
     */
    @TableField("security_key")
    private String securityKey;
    /**
     * 服务器ip
     */
    @TableField("server_ip")
    private String serverIp;
    /**
     * 服务器端口
     */
    @TableField("server_port")
    private String serverPort;
    /**
     * 最后通信时间
     */
    private Date talk;
    /**
     * 设备状态1：正常，0：停用，-1：删除
     */
    private Integer state;
    /**
     * 描述信息
     */
    private String description;
    /**
     * 更新类型1：手工修改，2：系统修改
     */
    @TableField("update_type")
    private Integer updateType;
    /**
     * 添加人
     */
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;
    /**
     * 添加时间
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private String createUser;
    /**
     * 修改时间
     */
    @TableField(value = "update_date", fill = FieldFill.UPDATE)
    private Date updateDate;
    /**
     * 修改人
     */
    @TableField(value = "update_user", fill = FieldFill.UPDATE)
    private String updateUser;
    /**
     * 是否可用 1：是 0：否
     */
    private Integer status;
    /**
     * 出入类型 1：其他， 2录入  3进门， 4出门
     */
    private Integer type;
    
    /**
     * 考勤机类型
     */
    @TableField(exist = false)
    private String typeName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 算法版本
     */
    @TableField("alg_version")
    private String algVersion;
    /**
     * 是否删除 1：是 0:否
     */
    @TableField("is_del")
    @TableLogic
    private Integer isDel;

    @TableField(exist = false)
    private ProjectMaster project;
    
    //中如id
    @TableField(exist = false)
    private String zrId;

    public Device(String sn) {
        this.sn = sn;
    }

    public Device() {
    }

    public Device(String newSn, String contractorOrgCode, String projectCode) {
        this.sn = newSn;
        this.organizationCode  =contractorOrgCode;
        this.projectCode = projectCode;
    }

    public ProjectMaster getProject() {
		return project;
	}

	public void setProject(ProjectMaster project) {
		this.project = project;
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

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
    
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public Date getTalk() {
        return talk;
    }

    public void setTalk(Date talk) {
        this.talk = talk;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUpdateType() {
        return updateType;
    }

    public void setUpdateType(Integer updateType) {
        this.updateType = updateType;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAlgVersion() {
        return algVersion;
    }

    public void setAlgVersion(String algVersion) {
        this.algVersion = algVersion;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    
    public String getZrId() {
        return zrId;
    }

    public void setZrId(String zrId) {
        this.zrId = zrId;
    }


    @Override
    public String toString() {
        return "Device{" +
        "id=" + id +
        ", organizationCode=" + organizationCode +
        ", projectCode=" + projectCode +
        ", typeId=" + typeId +
        ", sn=" + sn +
        ", name=" + name +
        ", ip=" + ip +
        ", mac=" + mac +
        ", version=" + version +
        ", code=" + code +
        ", securityKey=" + securityKey +
        ", serverIp=" + serverIp +
        ", serverPort=" + serverPort +
        ", talk=" + talk +
        ", state=" + state +
        ", description=" + description +
        ", updateType=" + updateType +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", status=" + status +
        ", type=" + type +
        ", remark=" + remark +
        ", algVersion=" + algVersion +
        ", isDel=" + isDel +
        "}";
    }
}
