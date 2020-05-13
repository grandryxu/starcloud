package com.xywg.admin.modular.worker.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.xywg.admin.core.excel.annotation.ExcelField;
import lombok.Data;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 工人实名基础信息
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-22
 */
@Data
public class WorkerMasterImport extends Model<WorkerMasterImport> {

    public static Map<String, String> getTitleMap() {
        Map<String, String> titleMap = new HashedMap();
        titleMap.put("姓名", "workerName");
        titleMap.put("手机号码", "cellPhone");
        titleMap.put("出生日期", "birthday");
        titleMap.put("当前工种", "workTypeCode");
        titleMap.put("证件类型", "idCardType");
        titleMap.put("证件编号", "idCardNumber");
        titleMap.put("性别", "gender");
        titleMap.put("民族", "nation");
        titleMap.put("籍贯", "birthPlaceCode");
        titleMap.put("地址", "address");
        titleMap.put("政治面貌", "politicsType");
        titleMap.put("文化程度", "cultureLevelType");
        titleMap.put("是否加入工会", "isJoined");
        titleMap.put("加入工会时间", "joinedTime");
        titleMap.put("是否有重大病史", "hasBadMedicalHistory");
        titleMap.put("开始工作日期", "workDate");
        titleMap.put("紧急联系人姓名", "urgentContractName");
        titleMap.put("紧急联系人联系电话", "urgentContractCellphone");
        titleMap.put("备注", "note");
        return titleMap;
    }

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 头像
     */
    private String headImage;
    /**
     * 工人姓名
     */
    private String workerName;

    /**
     * 手机号码
     */
    private String cellPhone;

    /**
     * 出生日期 身份证上获取的出生日期，格式：1990-04-08
     */
    private Date birthday;

    /**
     * 参见工种字典表中工种编号
     */

    private String workTypeCode;
    private String workTypeCodeName;
    /**
     * 证件类型 引用附录字典表
     */

    private Integer idCardType;
    private String idCardTypeName;
    /**
     * 证件编号
     */
    private String idCardNumber;


    /**
     * 性别
     */

    private Integer gender;
    private String genderName;
    /**
     * 民族
     */

    private String nation;
    private String nationName;

    /**
     * 籍贯
     */

    private String birthPlaceCode;
    private String birthPlaceCodeName;

    /**
     * 地址
     */
    private String address;
    /**
     * 政治面貌
     */

    private Integer politicsType;
    private String politicsTypeName;
    /**
     * 文化程度,
     */

    private Integer cultureLevelType;
    private String cultureLevelTypeName;
    /**
     * 是否加入工会 0=未加入,
     * 1=已加入
     */

    private Integer isJoined;
    private String isJoinedName;
    /**
     * 加入工会时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date joinedTime;

    /**
     * 是否有重大病史
     */

    private Integer hasBadMedicalHistory;
    private String hasBadMedicalHistoryName;
    /**
     * 开始工作日期 精确到月即可
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date workDate;
    /**
     * 紧急联系人姓名
     */
    private String urgentContractName;
    /**
     * 紧急联系人联系电话
     */
    private String urgentContractCellphone;
    /**
     * 是否人脸录入
     */

    private Integer isFace;
    private String isFaceName;
    /**
     * 是否认证  1 已认证  0未认证  3黑名单
     */

    private Integer isAuth;
    private String isAuthName;

    /**
     * 用户图像压缩
     */

    private String iconImage;
    /**
     * 身份证照片
     */
    private String idImage;
    /**
     * 录入人脸
     */
    private String face;
    /**
     * 添加人
     */
    //@TableField("create_date")
    private Date createDate;
    /**
     * 添加时间
     */
    //@TableField("create_user")
    private String createUser;
    /**
     * 修改时间
     */
    private Date updateDate;
    /**
     * 修改人
     */
    private String updateUser;
    /**
     * 备注
     */
    private String note;
    /**
     * 是否删除 1：是 0:否
     */
    private Integer isDel;

    @TableField(exist = false)
    private String image;

    @TableField(exist = false)
    private String aliId;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
