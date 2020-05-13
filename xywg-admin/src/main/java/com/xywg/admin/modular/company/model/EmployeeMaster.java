package com.xywg.admin.modular.company.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.xywg.admin.core.excel.annotation.ExcelField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 从业人员基础信息
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-24
 */
@TableName("buss_employee_master")
public class  EmployeeMaster extends Model<EmployeeMaster> {


    public EmployeeMaster(){
    }

    public EmployeeMaster(String employeeName, String cellPhone, Integer idCardType, String idCardNumber,
                          String nation, Integer gender, Date birthday, String address, Integer cultureLevelType,
                          Integer professionalType, Integer professionalLevel, String headImagePath,Date workDate) {
        this.employeeName = employeeName;
        this.cellPhone = cellPhone;
        this.idCardType = idCardType;
        this.idCardNumber = idCardNumber;
        this.nation = nation;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.cultureLevelType = cultureLevelType;
        this.professionalType = professionalType;
        this.professionalLevel = professionalLevel;
        this.headImagePath = headImagePath;
        this.workDate = workDate;
    }

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 姓名
     */
    @ExcelField(title = "*姓名", order = 1)
    @TableField("employee_name")
    private String employeeName;
    /**
     * 公司
     */
    //@TableId(value = "id", type = IdType.AUTO)
    @ExcelField(title = "*公司", order = 2)
    @TableField(exist = false)
    private String companyName;
    /**
     * 手机号码
     */
    @ExcelField(title = "*手机号", order = 3)
    @TableField("cell_phone")
    private String cellPhone;
    /**
     * 证件类型
     */
    @ExcelField(title = "*证件类型", order = 4)
    @TableField("id_card_type")
    private Integer idCardType;
    /**
     * 证件编号
     */
    @ExcelField(title = "*证件编号", order = 5)
    @TableField("id_card_number")
    private String idCardNumber;
    /**
     * 民族
     */
    @ExcelField(title = "*民族", order = 6)
    private String nation;
    /**
     * 性别
     */
    @ExcelField(title = "*性别", order = 7)
    private Integer gender;

    /**
     * 出生日期
     */
    @ExcelField(title = "*出生日期", order = 8)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    /**
     * 住址
     */
    @ExcelField(title = "*住址", order = 9)
    private String address;

    /**
     * 文化程度
     */
    @ExcelField(title = "文化程度", order = 10)
    @TableField("culture_level_type")
    private Integer cultureLevelType;

    /**
     * 职称
     */
    @ExcelField(title = "*职称", order = 11)
    @TableField("professional_type")
    private Integer professionalType;
    /**
     * 职称等级
     */
    @ExcelField(title = "*职称等级", order = 12)
    @TableField("professional_level")
    private Integer professionalLevel;

    /**
     * 开始工作时间
     */
    @ExcelField(title = "开始工作时间", order = 13)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField("work_date")
    private Date workDate;
    /**
     * 是否实名
     */
    @ExcelField(title = "是否实名", order = 14)
    @TableField("is_auth")
    private Integer isAuth;
    /**
     * 是否人脸录入
     */
    @ExcelField(title = "是否人脸录入", order = 15)
    @TableField("is_face")
    private Integer isFace;


    /**
     * 审核状态 0 待审核  1 审核通过 2 审核不通过
     */
    @TableField("is_audit")
    private Integer isAudit;
    /**
     * 默认头像
     */
    @TableField("head_image_path")
    private String headImagePath;
    /**
     * 身份证照片
     */
    @TableField("id_image")
    private String idImage;
    /**
     * 用户图像压缩
     */
    @TableField("icon_image")
    private String iconImage;
    /**
     * 添加人
     */
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;
    /**
     * 添加时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private String createUser;
    /**
     * 修改时间
     */
    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;
    /**
     * 修改人
     */
    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    private String updateUser;
    /**
     * 是否删除 1：是 0:否
     */
    @TableField("is_del")
    @TableLogic
    private Integer isDel;

    /**
     * 公司编码
     */ 
    @TableField(exist = false)
    private String organizationCode;
    
    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }



    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    public Integer getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Integer isAuth) {
        this.isAuth = isAuth;
    }

    public Integer getIsFace() {
        return isFace;
    }

    public void setIsFace(Integer isFace) {
        this.isFace = isFace;
    }

    public Integer getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(Integer isAudit) {
        this.isAudit = isAudit;
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

    public Integer getCultureLevelType() {
        return cultureLevelType;
    }

    public void setCultureLevelType(Integer cultureLevelType) {
        this.cultureLevelType = cultureLevelType;
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

    public String getHeadImagePath() {
        return headImagePath;
    }

    public void setHeadImagePath(String headImagePath) {
        this.headImagePath = headImagePath;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Integer getProfessionalType() {
        return professionalType;
    }

    public void setProfessionalType(Integer professionalType) {
        this.professionalType = professionalType;
    }

    public Integer getProfessionalLevel() {
        return professionalLevel;
    }

    public void setProfessionalLevel(Integer professionalLevel) {
        this.professionalLevel = professionalLevel;
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

    @Override
    public String toString() {
        return "EmployeeMaster{" +
        "id=" + id +
        ", employeeName=" + employeeName +
        ", iconImage=" + iconImage +
        ", idImage=" + idImage +
        ", isAuth=" + isAuth +
        ", isFace=" + isFace +
        ", isAudit=" + isAudit +
        ", idCardType=" + idCardType +
        ", idCardNumber=" + idCardNumber +
        ", gender=" + gender +
        ", cultureLevelType=" + cultureLevelType +
        ", nation=" + nation +
        ", birthday=" + birthday +
        ", headImagePath=" + headImagePath +
        ", address=" + address +
        ", cellPhone=" + cellPhone +
        ", workDate=" + workDate +
        ", professionalType=" + professionalType +
        ", professionalLevel=" + professionalLevel +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", isDel=" + isDel +
        "}";
    }
}
