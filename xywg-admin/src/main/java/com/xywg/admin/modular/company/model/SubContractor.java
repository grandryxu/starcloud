package com.xywg.admin.modular.company.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * <p>
 * 企业表
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-22
 */
@TableName("buss_sub_contractor")
@Data
public class SubContractor extends Model<SubContractor> {
	private static Logger log = LoggerFactory.getLogger(SubContractor.class);
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 企业名称
     */
    @TableField("company_name")
    private String companyName;
    /**
     * 单位性质 参建单位性质字典表
     */
    @TableField("organization_type")
    private String organizationType;
    /**
     * 组织机构编号
     */
    @TableField("organization_code")
    private String organizationCode;
    /**
     * 工商营业执照注册号
     */
    @TableField("biz_register_code")
    private String bizRegisterCode;
    /**
     * 社会统一信用代码，与营业执照号两者中必有一项为必填
     */
    @TableField("social_credit_number")
    private String socialCreditNumber;
    /**
     *  注册地区编码   参见地区字典数据
     */
    @TableField("area_code")
    private String areaCode;
    /**
     * 企业营业地址
     */
    private String address;
    /**
     * 邮政编码
     */
    @TableField("zip_code")
    private String zipCode;
    /**
     * 法定代表人姓名
     */
    @TableField("representative_name")
    private String representativeName;
    /**
     * 法定代表人证件类型  参见人员证件类型字典表
     */
    @TableField("representative_idcard_type")
    private Integer representativeIdcardType;
    /**
     * 法定代表人证件号码
     */
    @TableField("representative_idcard_number")
    private String representativeIdcardNumber;
    /**
     * 注册资本（万元）
     */
    @TableField("registration_capital")
    private BigDecimal registrationCapital;
    /**
     * 注册资本币种,见币种字典表
     */
    @TableField("capital_currency")
    private String capitalCurrency;
    /**
     * 成立日期 精确到日，格式:2014-02-05
     */
    @TableField("establish_date")
    private Date establishDate;
    /**
     * 办公电话
     */
    @TableField("phone_number")
    private String phoneNumber;
    /**
     * 传真号码
     */
    @TableField("fax_number")
    private String faxNumber;
    /**
     * 联系人姓名
     */
    @TableField("contact_people_name")
    private String contactPeopleName;
    /**
     * 联系人办公电话
     */
    @TableField("contact_people_phone")
    private String contactPeoplePhone;
    /**
     * 联系人手机号码
     */
    @TableField("contact_people_cell_phone")
    private String contactPeopleCellPhone;
    /**
     * 企业联系邮箱
     */
    private String email;
    /**
     * 企业网址
     */
    @TableField("home_website_url")
    private String homeWebsiteUrl;
    /**
     * 企业经营状态 0: 注销；1：正常
     */
    @TableField("business_status")
    private String businessStatus;
    /**
     * 企业备注
     */
    private String memo;
    /**
     * 来源  1:pc端  2：APP
     */
    private Integer source;
    /**
     * 是否审核：0未审核 1审核通过  2审核不通过
     */
    @TableField("is_audit")
    private Integer isAudit;


    /**
     * 添加人
     */
    @TableField(value ="create_date",fill = FieldFill.INSERT)
    private Date createDate;
    /**
     * 添加时间
     */
    @TableField(value ="create_user",fill = FieldFill.INSERT)
    private String createUser;
    /**
     * 修改时间
     */
    @TableField(value ="update_date",fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;
    /**
     * 修改人
     */
    @TableField(value = "update_user",fill = FieldFill.INSERT_UPDATE)
    private String updateUser;
    /**
     * 是否可用 1：是 0：否
     */
    private Integer status;
    /**
     * 是否删除 1：是 0:否
     */
    @TableField("is_del")
    @TableLogic
    private Integer isDel;

    /**
     * 是否同步到SMZ 0否1是
     */
    @TableField("is_synchro")
    private Integer isSynchro;

    /**
     * 企业经济性质
     */
    @TableField("economic_nature")
    private Integer economicNature;

    /**
     * 企业市域名称
     */
    @TableField("enterprise_market_type")
    private Integer enterpriseMarketType;
    
    /**
     * 资质等级
     */
    @TableField("qualify_level")
    private String qualifyLevel;
    
    /**
     * 业务类型
     */
    @TableField("business_type")
    private String businessType;
    
    /**
     * 业务范围
     */
    @TableField("business_scope")
    private String businessScope;
    
    /**
     * 企业材料
     */
    @TableField("business_material")
    private String businessMaterial;
    
    /**
     * 法人联系号码
     */
    @TableField("representative_phone")
    private String representativePhone;
    
    /** 
     * 企业评分
    */
   @TableField("star")
   private Double star;
   
   /**
    * 流程id
    */
   private String process;

   @TableField("flow_status")
   private String flowStatus;


   @TableField(exist = false)
   private String fileName;

   @TableField(exist = false)
   private Date date;


    public static Map<String, String> getTitleMap() {
        Map<String, String> titleMap = new HashedMap();
        titleMap.put("企业名称", "companyName");
        titleMap.put("单位性质", "organizationType");
        titleMap.put("社会统一信用代码/组织机构编号", "socialCreditNumber");
        titleMap.put("营业执照注册号", "bizRegisterCode");
        titleMap.put("企业营业地址", "address");
        titleMap.put("邮政编码", "zipCode");
        titleMap.put("法定代表人姓名", "representativeName");
        titleMap.put("法定代表人证件类型", "representativeIdcardType");
        titleMap.put("法定代表人证件号码", "representativeIdcardNumber");
        titleMap.put("传真号码", "faxNumber");
        titleMap.put("联系人姓名", "contactPeopleName");
        titleMap.put("联系人办公电话", "contactPeoplePhone");
        titleMap.put("联系人手机号码", "contactPeopleCellPhone");
        titleMap.put("企业联系邮箱", "email");
        titleMap.put("企业网址", "homeWebsiteUrl");
        titleMap.put("注册资本", "registrationCapital");
        titleMap.put("注册资本币种", "capitalCurrency");
        titleMap.put("成立日期", "establishDate");
        titleMap.put("办公电话", "phoneNumber");
        titleMap.put("经营状态", "businessStatus");
        titleMap.put("企业备注", "memo");
        return titleMap;
    }

    public Integer getEconomicNature() {
        return economicNature;
    }

    public void setEconomicNature(Integer economicNature) {
        this.economicNature = economicNature;
    }

    public Integer getEnterpriseMarketType() {
        return enterpriseMarketType;
    }

    public void setEnterpriseMarketType(Integer enterpriseMarketType) {
        this.enterpriseMarketType = enterpriseMarketType;
    }

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

    public Integer getIsSynchro() {
        return isSynchro;
    }

    public void setIsSynchro(Integer isSynchro) {
        this.isSynchro = isSynchro;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SubContractor{" +
        "id=" + id +
        ", companyName=" + companyName +
        ", organizationType=" + organizationType +
        ", organizationCode=" + organizationCode +
        ", bizRegisterCode=" + bizRegisterCode +
        ", socialCreditNumber=" + socialCreditNumber +
        ", areaCode=" + areaCode +
        ", address=" + address +
        ", zipCode=" + zipCode +
        ", representativeName=" + representativeName +
        ", representativeIdcardType=" + representativeIdcardType +
        ", representativeIdcardNumber=" + representativeIdcardNumber +
        ", registrationCapital=" + registrationCapital +
        ", capitalCurrency=" + capitalCurrency +
        ", establishDate=" + establishDate +
        ", phoneNumber=" + phoneNumber +
        ", faxNumber=" + faxNumber +
        ", contactPeopleName=" + contactPeopleName +
        ", contactPeoplePhone=" + contactPeoplePhone +
        ", contactPeopleCellPhone=" + contactPeopleCellPhone +
        ", email=" + email +
        ", homeWebsiteUrl=" + homeWebsiteUrl +
        ", businessStatus=" + businessStatus +
        ", memo=" + memo +
        ", source=" + source +
        ", isAudit=" + isAudit +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", status=" + status +
        ", isDel=" + isDel +
        "}";
    }


    public SubContractor() {
    }

    public SubContractor(String companyName,String organizationType,String socialCreditNumber,String bizRegisterCode ,
                           String address ,String zipCode ,String representativeName ,Integer representativeIdcardType,
                           String representativeIdcardNumber, String faxNumber ,String contactPeopleName ,String contactPeoplePhone , String contactPeopleCellPhone,
                           String email,String homeWebsiteUrl,BigDecimal registrationCapital,String capitalCurrency,Date establishDate,String phoneNumber,String businessStatus,String memo) {
        this.companyName = companyName;
        this.organizationType = organizationType;
        this.socialCreditNumber = socialCreditNumber;
        this.bizRegisterCode = bizRegisterCode;
        this.address = address;
        this.zipCode = zipCode;
        this.representativeName = representativeName;
        this.representativeIdcardType = representativeIdcardType;
        this.representativeIdcardNumber = representativeIdcardNumber;
        this.faxNumber = faxNumber;
        this.contactPeopleName = contactPeopleName;
        this.contactPeoplePhone = contactPeoplePhone;
        this.contactPeopleCellPhone = contactPeopleCellPhone;
        this.email = email;
        this.homeWebsiteUrl = homeWebsiteUrl;
        this.registrationCapital = registrationCapital;
        this.capitalCurrency = capitalCurrency;
        this.establishDate = establishDate;
        this.phoneNumber = phoneNumber;
        this.businessStatus = businessStatus;
        this.memo = memo;
    }

}
