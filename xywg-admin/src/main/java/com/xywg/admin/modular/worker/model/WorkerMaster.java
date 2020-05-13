package com.xywg.admin.modular.worker.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.xywg.admin.core.excel.annotation.ExcelField;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 工人实名基础信息
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-22
 */
@TableName("buss_worker_master")
@Data
public class WorkerMaster extends Model<WorkerMaster> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 头像
     */
    @TableField("head_image")
    private String headImage;
    /**
     * 工人姓名
     */
    @TableField("worker_name")
    private String workerName;

    /**
     * 手机号码
     */
    @TableField("cell_phone")
    private String cellPhone;

    /**
     * 出生日期 身份证上获取的出生日期，格式：1990-04-08
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 参见工种字典表中工种编号
     */
    @TableField("work_type_code")
    private String workTypeCode;

    /**
     * 证件类型 引用附录字典表
     */
    @TableField("id_card_type")
    private Integer idCardType;
    /**
     * 证件编号
     */
    @TableField("id_card_number")
    private String idCardNumber;


    /**
     * 性别
     */
    private Integer gender;
    /**
     * 民族
     */
    private String nation;

    /**
     * 籍贯
     */
    @TableField("birth_place_code")
    private String birthPlaceCode;
    /**
     * 地址
     */
    private String address;
    /**
     * 政治面貌
     */
    @TableField("politics_type")
    private Integer politicsType;
    /**
     * 文化程度,
     */
    @TableField("culture_level_type")
    private Integer cultureLevelType;
    /**
     * 是否加入工会 0=未加入,
     * 1=已加入
     */
    @TableField(value="is_joined")
    private Integer isJoined;
    /**
     * 加入工会时间
     */
    @TableField("joined_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date joinedTime;

    /**
     * 是否有重大病史
     */
    @TableField("has_bad_medical_history")
    private Integer hasBadMedicalHistory;
    /**
     * 开始工作日期 精确到月即可
     */
    @TableField("work_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date workDate;
    /**
     * 紧急联系人姓名
     */
    @TableField("urgent_contract_name")
    private String urgentContractName;
    /**
     * 紧急联系人联系电话
     */
    @TableField("urgent_contract_cellphone")
    private String urgentContractCellphone;
    /**
     * 是否人脸录入
     */
    @TableField("is_face")
    private Integer isFace;
    /**
     * 是否认证  1 已认证  0未认证  3黑名单
     */
    @TableField("is_auth")
    private Integer isAuth;

    /**
     * 用户图像压缩
     */

    @TableField("icon_image")
    private String iconImage;
    /**
     * 身份证照片
     */
    @TableField("id_image")
    private String idImage;
    /**
     * 录入人脸
     */
    private String face;
    /**
     * 添加人
     */
    //@TableField("create_date")
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;
    /**
     * 添加时间
     */
    //@TableField("create_user")
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
     * 备注
     */
    @ExcelField(title = "备注", order = 21)
    private String note;
    /**
     * 是否删除 1：是 0:否
     */
    @TableField("is_del")
    @TableLogic
    private Integer isDel;

    @TableField(exist = false)
    private String image;

    @TableField(exist = false)
    private String aliId;

    /**
     * 用工类型
     */
    @TableField(exist = false)
    private Long pwId;

    /**
     *
     */
    @TableField(exist = false)
    private String cardNumber;


    /**
     * 项目编号
     */
    @TableField(exist = false)
    private String projectCode;
    /**
     * 组织机构代码
     */
    @TableField(exist = false)
    private String organizationCode;
    

    /**
     * 是否为班组老板
     */
    @TableField(exist = false)
    private Integer isTeamLeader;
    
    /**
     * 班组
     */
    @TableField(exist = false)
    private String teamSysNo;
    
    /**
     * 进退场状态
     */
    @TableField(exist = false)
    private Integer joinStatus;
    
    /**
     * 是否黑名单
     */
    @TableField(exist = false)
    private Integer isBlack;

    /**
     * 有效期开始时间
     */
    @TableField("start_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 身份证有效结束时间
     */
    @TableField("end_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    @TableField("worker_company_type")
    private String companyType;

    @TableField("type")
    private String workerType;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getIsBlack() {
		return isBlack;
	}

	public void setIsBlack(Integer isBlack) {
		this.isBlack = isBlack;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBirthPlaceCode() {
        return birthPlaceCode;
    }

    public void setBirthPlaceCode(String birthPlaceCode) {
        this.birthPlaceCode = birthPlaceCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public Integer getPoliticsType() {
        return politicsType;
    }

    public void setPoliticsType(Integer politicsType) {
        this.politicsType = politicsType;
    }

    public Integer getIsJoined() {
        return isJoined;
    }

    public void setIsJoined(Integer isJoined) {
        this.isJoined = isJoined;
    }

    public Date getJoinedTime() {
        return joinedTime;
    }

    public void setJoinedTime(Date joinedTime) {
        this.joinedTime = joinedTime;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public Integer getCultureLevelType() {
        return cultureLevelType;
    }

    public void setCultureLevelType(Integer cultureLevelType) {
        this.cultureLevelType = cultureLevelType;
    }

    public Integer getHasBadMedicalHistory() {
        return hasBadMedicalHistory;
    }

    public void setHasBadMedicalHistory(Integer hasBadMedicalHistory) {
        this.hasBadMedicalHistory = hasBadMedicalHistory;
    }

    public String getUrgentContractName() {
        return urgentContractName;
    }

    public void setUrgentContractName(String urgentContractName) {
        this.urgentContractName = urgentContractName;
    }

    public String getUrgentContractCellphone() {
        return urgentContractCellphone;
    }

    public void setUrgentContractCellphone(String urgentContractCellphone) {
        this.urgentContractCellphone = urgentContractCellphone;
    }

    public String getWorkTypeCode() {
        return workTypeCode;
    }

    public void setWorkTypeCode(String workTypeCode) {
        this.workTypeCode = workTypeCode;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    public Integer getIsFace() {
        return isFace;
    }

    public void setIsFace(Integer isFace) {
        this.isFace = isFace;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public Integer getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Integer isAuth) {
        this.isAuth = isAuth;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    
    public Integer getJoinStatus() {
		return joinStatus;
	}

	public void setJoinStatus(Integer joinStatus) {
		this.joinStatus = joinStatus;
	}

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getTeamSysNo() {
        return teamSysNo;
    }

    public void setTeamSysNo(String teamSysNo) {
        this.teamSysNo = teamSysNo;
    }

    public WorkerMaster() {
    }
    
    public WorkerMaster(String cellPhone, Integer idCardType, String idCardNumber) {
        this.cellPhone = cellPhone;
        this.idCardType = idCardType;
        this.idCardNumber = idCardNumber;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
	/**
	 * 用于对接实名制新增工人信息时使用
	 * 
	 * @param workerName
	 * @param idCardNumber
	 * @param gender
	 * @param nation
	 * @param birthPlaceCode
	 * @param address
	 * @param cellPhone
	 * @param workTypeCode
	 * @author duanfen
	 * @date 2019-01-08 15:14:38
	 */
	public WorkerMaster(String workerName, String idCardNumber, Integer gender, String nation, String birthPlaceCode,
			String address, String cellPhone, String workTypeCode, Date startTime, Date endTime) {
		this.idCardType = 1;
		this.isAuth = 1;
		this.workerName = workerName;
		this.idCardNumber = idCardNumber;
		// 判断性别
		if (gender == null) {
			this.gender = gender == 0 ? 1 : 0;
		} else {
			this.gender = 0;
		}
		// 判断民族
		if (nation == null) {
			this.nation = "1";
		} else {
			this.nation = nation;
		}
		this.birthPlaceCode = birthPlaceCode;
		this.address = address;
		// 手机号码无实际意义
		this.cellPhone = cellPhone;
		// 工种处理
		this.workTypeCode = workTypeCode;
		this.startTime = startTime;
		this.endTime = endTime;
	}
}
