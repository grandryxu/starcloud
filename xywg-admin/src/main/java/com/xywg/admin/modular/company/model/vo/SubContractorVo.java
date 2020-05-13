package com.xywg.admin.modular.company.model.vo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xywg.admin.core.excel.annotation.ExcelField;
import com.xywg.admin.modular.company.model.SubContractorCertifications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 企业表
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-22
 */
@TableName("buss_sub_contractor")
public class SubContractorVo extends Model<SubContractorVo> {
	private static Logger log = LoggerFactory.getLogger(SubContractorVo.class);
    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 企业名称
     */
    @ExcelField(title = "企业名称", order = 1)
    private String companyName;
    /**
     * 单位性质 参建单位性质字典表
     */
    private String organizationType;

    /**
     * 单位性质名称
     */
    @ExcelField(title = "单位性质", order = 2)
    private String organizationTypeName;
    /**
     * 组织机构编号
     */
    private String organizationCode;
    /**
     * 工商营业执照注册号
     */

    @ExcelField(title = "营业执照注册号", order = 4)
    private String bizRegisterCode;

    /**
     * 社会统一信用代码，与营业执照号两者中必有一项为必填
     */
    @ExcelField(title = "社会统一信用代码", order = 3)
    private String socialCreditNumber;
    /**
     *  注册地区编码   参见地区字典数据
     */
    private String areaCode;

    /**
     * 行政区划
     */
    @ExcelField(title = "注册地区", order = 5)
    private String areaName;

    /**
     * 企业营业地址
     */
    @ExcelField(title = "企业营业地址", order = 6)
    private String address;
    /**
     * 邮政编码
     */
    @ExcelField(title = "邮政编码", order = 7)
    private String zipCode;
    /**
     * 法定代表人姓名
     */
    @ExcelField(title = "法定代表人姓名", order = 11)
    private String representativeName;
    /**
     * 法定代表人证件类型  参见人员证件类型字典表
     */
    private Integer representativeIdcardType;

    /**
     * 法定代表人证件类型  参见人员证件类型字典表
     */
    @ExcelField(title = "法定代表人证件类型", order =12)
    private Integer representativeIdcardTypeName;

    /**
     *
     *
     * 法定代表人证件号码
     */
    @ExcelField(title = "法定代表人证件号码", order =13)
    private String representativeIdcardNumber;
    /**
     * 注册资本（万元）
     */
    @ExcelField(title = "注册资本", order =17)
    private BigDecimal registrationCapital;
    /**
     * 注册资本币种,见币种字典表
     */
    private String capitalCurrency;

    /**
     * 币种名称
     */
    @ExcelField(title = "注册资本币种", order =18)
    private String capitalCurrencyName;
    /**
     * 成立日期 精确到日，格式:2014-02-05
     */
    @ExcelField(title = "成立日期", order =19)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date establishDate;
    /**
     * 办公电话
     */
    @ExcelField(title = "办公电话", order =20)
    private String phoneNumber;
    /**
     * 传真号码
     */
    @ExcelField(title = "传真号码", order =14)
    private String faxNumber;
    /**
     * 联系人姓名
     */
    @ExcelField(title = "联系人姓名", order =8)
    private String contactPeopleName;
    /**
     * 联系人办公电话
     */
    @ExcelField(title = "联系人办公电话", order =10)
    private String contactPeoplePhone;
    /**
     * 联系人手机号码
     */
    @ExcelField(title = "联系人手机号码", order =9)
    private String contactPeopleCellPhone;
    /**
     * 企业联系邮箱
     */
    @ExcelField(title = "企业联系邮箱", order =15)
    private String email;
    /**
     * 企业网址
     */
    @ExcelField(title = "企业网址", order =16)
    private String homeWebsiteUrl;
    /**
     * 企业经营状态 0: 注销；1：正常
     */

    private String businessStatus;

    @ExcelField(title = "经营状态", order =21)
    private String bussinessStatusName;
    /**
     * 企业备注
     */
    @ExcelField(title = "企业备注", order =22)
    private String memo;
    /**
     * 来源  1:pc端  2：APP
     */
    private Integer source;
    /**
     * 是否审核：0未审核 1审核通过  2审核不通过
     */
    private Integer isAudit;
    /**
     * 添加人
     */
    private Date createDate;
    /**
     * 添加时间
     */
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
     * 是否可用 1：是 0：否
     */
    private Integer status;
    /**
     * 是否删除 1：是 0:否
     */
    @TableLogic
    private Integer isDel;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getBizRegisterCode() {
        return bizRegisterCode;
    }

    public void setBizRegisterCode(String bizRegisterCode) {
        this.bizRegisterCode = bizRegisterCode;
    }

    public String getSocialCreditNumber() {
        return socialCreditNumber;
    }

    public void setSocialCreditNumber(String socialCreditNumber) {
        this.socialCreditNumber = socialCreditNumber;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public Integer getRepresentativeIdcardType() {
        return representativeIdcardType;
    }

    public void setRepresentativeIdcardType(Integer representativeIdcardType) {
        this.representativeIdcardType = representativeIdcardType;
    }

    public String getRepresentativeIdcardNumber() {
        return representativeIdcardNumber;
    }

    public void setRepresentativeIdcardNumber(String representativeIdcardNumber) {
        this.representativeIdcardNumber = representativeIdcardNumber;
    }

    public BigDecimal getRegistrationCapital() {
        return registrationCapital;
    }

    public void setRegistrationCapital(BigDecimal registrationCapital) {
        this.registrationCapital = registrationCapital;
    }

    public String getCapitalCurrency() {
        return capitalCurrency;
    }

    public void setCapitalCurrency(String capitalCurrency) {
        this.capitalCurrency = capitalCurrency;
    }

    public Date getEstablishDate() {
        return establishDate;
    }

    public void setEstablishDate(String establishDate) {
        if(establishDate !=""){
            try {
                this.establishDate = (Date)new SimpleDateFormat("yyyy-MM-dd").parse(establishDate);
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
        }
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getContactPeopleName() {
        return contactPeopleName;
    }

    public void setContactPeopleName(String contactPeopleName) {
        this.contactPeopleName = contactPeopleName;
    }

    public String getContactPeoplePhone() {
        return contactPeoplePhone;
    }

    public void setContactPeoplePhone(String contactPeoplePhone) {
        this.contactPeoplePhone = contactPeoplePhone;
    }

    public String getContactPeopleCellPhone() {
        return contactPeopleCellPhone;
    }

    public void setContactPeopleCellPhone(String contactPeopleCellPhone) {
        this.contactPeopleCellPhone = contactPeopleCellPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomeWebsiteUrl() {
        return homeWebsiteUrl;
    }

    public void setHomeWebsiteUrl(String homeWebsiteUrl) {
        this.homeWebsiteUrl = homeWebsiteUrl;
    }

    public String getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(Integer isAudit) {
        this.isAudit = isAudit;
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

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getOrganizationTypeName() {
        return organizationTypeName;
    }

    public void setOrganizationTypeName(String organizationTypeName) {
        this.organizationTypeName = organizationTypeName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getRepresentativeIdcardTypeName() {
        return representativeIdcardTypeName;
    }

    public void setRepresentativeIdcardTypeName(Integer representativeIdcardTypeName) {
        this.representativeIdcardTypeName = representativeIdcardTypeName;
    }

    public String getCapitalCurrencyName() {
        return capitalCurrencyName;
    }

    public void setCapitalCurrencyName(String capitalCurrencyName) {
        this.capitalCurrencyName = capitalCurrencyName;
    }

    public void setEstablishDate(Date establishDate) {
        this.establishDate = establishDate;
    }

    public String getBussinessStatusName() {
        return bussinessStatusName;
    }

    public void setBussinessStatusName(String bussinessStatusName) {
        this.bussinessStatusName = bussinessStatusName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SubContractorVo{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", organizationType='" + organizationType + '\'' +
                ", organizationTypeName='" + organizationTypeName + '\'' +
                ", organizationCode='" + organizationCode + '\'' +
                ", bizRegisterCode='" + bizRegisterCode + '\'' +
                ", socialCreditNumber='" + socialCreditNumber + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", areaName='" + areaName + '\'' +
                ", address='" + address + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", representativeName='" + representativeName + '\'' +
                ", representativeIdcardType=" + representativeIdcardType +
                ", representativeIdcardTypeName=" + representativeIdcardTypeName +
                ", representativeIdcardNumber='" + representativeIdcardNumber + '\'' +
                ", registrationCapital=" + registrationCapital +
                ", capitalCurrency='" + capitalCurrency + '\'' +
                ", capitalCurrencyName='" + capitalCurrencyName + '\'' +
                ", establishDate=" + establishDate +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", faxNumber='" + faxNumber + '\'' +
                ", contactPeopleName='" + contactPeopleName + '\'' +
                ", contactPeoplePhone='" + contactPeoplePhone + '\'' +
                ", contactPeopleCellPhone='" + contactPeopleCellPhone + '\'' +
                ", email='" + email + '\'' +
                ", homeWebsiteUrl='" + homeWebsiteUrl + '\'' +
                ", businessStatus='" + businessStatus + '\'' +
                ", bussinessStatusName='" + bussinessStatusName + '\'' +
                ", memo='" + memo + '\'' +
                ", source=" + source +
                ", isAudit=" + isAudit +
                ", createDate=" + createDate +
                ", createUser='" + createUser + '\'' +
                ", updateDate=" + updateDate +
                ", updateUser='" + updateUser + '\'' +
                ", status=" + status +
                ", isDel=" + isDel +
                '}';
    }
}
