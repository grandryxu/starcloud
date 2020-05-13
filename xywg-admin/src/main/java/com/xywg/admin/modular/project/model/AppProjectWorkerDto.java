package com.xywg.admin.modular.project.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 项目中工人信息
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-28
 */
@TableName("buss_project_worker")
public class AppProjectWorkerDto extends Model<AppProjectWorkerDto> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 项目编号
     */
    @TableField("project_code")
    private String projectCode;
    /**
     * 所属企业组织机构代码
     */
    @TableField("organization_code")
    private String organizationCode;
    /**
     * 班组编号
     */
    @TableField("team_sys_no")
    private Integer teamSysNo;
    /**
     * 证件类型 参见人员证件类型字典表
     */
    @TableField("id_card_type")
    private String idCardType;
    /**
     * 证件编号
     */
    @TableField("id_card_number")
    private String idCardNumber;
    /**
     * 安全帽
     */
    @TableField("sh_imei")
    private String shImei;
    /**
     * 状态 1:待进场  2：进场  3：退场
     */
    @TableField("join_status")
    private Integer joinStatus;
    /**
     * 当前工种 参见工种字典表中工种编号
     */
    @TableField("work_type_code")
    private String workTypeCode;
    /**
     * 手机号码 工人当前手机号码
     */
    @TableField("cell_phone")
    private String cellPhone;
    /**
     * 制卡时间
     */
    @TableField("issue_card_time")
    private Date issueCardTime;
    /**
     * 工人进场时间
     */
    @TableField("entry_time")
    private Date entryTime;
    /**
     * 工人退场时间
     */
    @TableField("exit_time")
    private Date exitTime;
    /**
     * 销卡时间
     */
    @TableField("complete_card_time")
    private Date completeCardTime;
    /**
     * 门禁卡号
     */
    @TableField("card_number")
    private String cardNumber;
    /**
     * 门禁卡类型 卡的类型
1=正式卡,
3=临工卡(指的短期进入工地工作的卡，默认这类工人不会记入工资结算)
     */
    @TableField("card_type")
    private Integer cardType;
    /**
     * 是否有劳动合同 0=无，
1=有
是否与劳务企业签有劳动合同
     */
    @TableField("has_contract")
    private Integer hasContract;
    /**
     * 工人劳动合同编号
     */
    @TableField("contract_code")
    private String contractCode;
    /**
     * 工人住宿类型 0=场外住宿,
1=场内住宿
     */
    @TableField("worker_accommodation_type")
    private Integer workerAccommodationType;
    /**
     * 工人角色 1=Employe=职员
2=Worker=工人
     */
    @TableField("worker_role")
    private Integer workerRole;
    /**
     * 工资银行卡号
     */
    @TableField("pay_roll_bank_card_number")
    private String payRollBankCardNumber;
    /**
     * 发放工资银行名称
     */
    @TableField("pay_roll_bank_name")
    private String payRollBankName;
    /**
     * 有无购买工伤或意外伤害保险	 0=No=无,
1=Yes=有
     */
    @TableField("has_buy_insurance")
    private Integer hasBuyInsurance;
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
     * 评价
     */
    private String evaluate;
    /**
     * 是否删除
     */
    @TableField("is_del")
    @TableLogic
    private Integer isDel;
    public AppProjectWorkerDto(){}
    public AppProjectWorkerDto(String projectCode, String organizationCode, Integer teamSysNo, String idCardType,
                               String idCardNumber, String workTypeCode, Integer workerRole){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public Integer getTeamSysNo() {
        return teamSysNo;
    }

    public void setTeamSysNo(Integer teamSysNo) {
        this.teamSysNo = teamSysNo;
    }

    public String getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getShImei() {
        return shImei;
    }

    public void setShImei(String shImei) {
        this.shImei = shImei;
    }

    public Integer getJoinStatus() {
        return joinStatus;
    }

    public void setJoinStatus(Integer joinStatus) {
        this.joinStatus = joinStatus;
    }

    public String getWorkTypeCode() {
        return workTypeCode;
    }

    public void setWorkTypeCode(String workTypeCode) {
        this.workTypeCode = workTypeCode;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public Date getIssueCardTime() {
        return issueCardTime;
    }

    public void setIssueCardTime(Date issueCardTime) {
        this.issueCardTime = issueCardTime;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }

    public Date getCompleteCardTime() {
        return completeCardTime;
    }

    public void setCompleteCardTime(Date completeCardTime) {
        this.completeCardTime = completeCardTime;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Integer getHasContract() {
        return hasContract;
    }

    public void setHasContract(Integer hasContract) {
        this.hasContract = hasContract;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public Integer getWorkerAccommodationType() {
        return workerAccommodationType;
    }

    public void setWorkerAccommodationType(Integer workerAccommodationType) {
        this.workerAccommodationType = workerAccommodationType;
    }

    public Integer getWorkerRole() {
        return workerRole;
    }

    public void setWorkerRole(Integer workerRole) {
        this.workerRole = workerRole;
    }

    public String getPayRollBankCardNumber() {
        return payRollBankCardNumber;
    }

    public void setPayRollBankCardNumber(String payRollBankCardNumber) {
        this.payRollBankCardNumber = payRollBankCardNumber;
    }

    public String getPayRollBankName() {
        return payRollBankName;
    }

    public void setPayRollBankName(String payRollBankName) {
        this.payRollBankName = payRollBankName;
    }

    public Integer getHasBuyInsurance() {
        return hasBuyInsurance;
    }

    public void setHasBuyInsurance(Integer hasBuyInsurance) {
        this.hasBuyInsurance = hasBuyInsurance;
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

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
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
        return "ProjectWorker{" +
        "id=" + id +
        ", projectCode=" + projectCode +
        ", organizationCode=" + organizationCode +
        ", teamSysNo=" + teamSysNo +
        ", idCardType=" + idCardType +
        ", idCardNumber=" + idCardNumber +
        ", shImei=" + shImei +
        ", joinStatus=" + joinStatus +
        ", workTypeCode=" + workTypeCode +
        ", cellPhone=" + cellPhone +
        ", issueCardTime=" + issueCardTime +
        ", entryTime=" + entryTime +
        ", exitTime=" + exitTime +
        ", completeCardTime=" + completeCardTime +
        ", cardNumber=" + cardNumber +
        ", cardType=" + cardType +
        ", hasContract=" + hasContract +
        ", contractCode=" + contractCode +
        ", workerAccommodationType=" + workerAccommodationType +
        ", workerRole=" + workerRole +
        ", payRollBankCardNumber=" + payRollBankCardNumber +
        ", payRollBankName=" + payRollBankName +
        ", hasBuyInsurance=" + hasBuyInsurance +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", evaluate=" + evaluate +
        ", isDel=" + isDel +
        "}";
    }
}
