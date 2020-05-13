package com.xywg.admin.modular.wages.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 工资明细
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-04
 */
@TableName("buss_pay_roll_detail")
public class PayRollDetail extends Model<PayRollDetail> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 工资单编号
     */
    @TableField("pay_roll_code")
    private String payRollCode;
    /**
     * 证件类型
     */
    @TableField("id_card_type")
    private Integer idCardType;
    /**
     * 证件编号
     */
    @TableField("id_card_number")
    private String idCardNumber;
    /**
     * 务工工种
     */
    @TableField("worker_type")
    private String workerType;
    /**
     * 工种工资
     */
    private String price;
    /**
     * 出勤天数
     */
    private Integer days;
    /**
     * 基本工资
     */
    private BigDecimal amount;
    /**
     * 奖励金额
     */
    @TableField("reward_amount")
    private BigDecimal rewardAmount;
    /**
     * 惩罚金额
     */
    @TableField("punish_amount")
    private BigDecimal punishAmount;
    /**
     * 应发金额
     */
    @TableField("pay_amount")
    private BigDecimal payAmount;
    /**
     * 实发金额
     */
    @TableField("actual_amount")
    private BigDecimal actualAmount;
    /**
     * 剩余工资
     */
    @TableField("balance_amount")
    private BigDecimal balanceAmount;
    /**
     * 发放时间
     */
    private Date time;
    /**
     * 发放状态 0= NoPaid = 未发放, 20=paid = 已发放，30 =payfaild=发放失败 ，40=retry =重新发放中
     */
    @TableField("pay_status")
    private Integer payStatus;
    /**
     * 签字
     */
    private String sign;
    /**
     * 签字图片缩略图
     */
    @TableField("icon_sign")
    private String iconSign;
    /**
     * 拍照
     */
    private String photo;
    /**
     * 图像压缩
     */
    @TableField("icon_photo")
    private String iconPhoto;
    /**
     * 凭证类型
     */
    @TableField("certificate_type")
    private Integer certificateType;
    /**
     * 添加时间
     */
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;
    /**
     * 添加人
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
     * 付款人
     */
    @TableField("pay_person")
    private String payPerson;
    /**
     * 备注
     */
    private String remark;
    /**
     * 个人计工单工资绑定的生活费
     */
    @TableField("living_cost")
    private String livingCost;
    /**
     * 计工单生成时所有的生活费情况
     */
    @TableField("all_livingcost")
    private String allLivingcost;
    /**
     * 确认时间
     */
    @TableField("confirm_time")
    private Date confirmTime;
    /**
     * 单位：小时
     */
    @TableField("balance_date")
    private Date balanceDate;
    /**
     * 发放工资银行卡号
     */
    @TableField("pay_roll_bank_card_number")
    private String payRollBankCardNumber;
    /**
     * 发放工资银行名称
     */
    @TableField("pay_roll_bank_name")
    private String payRollBankName;
    /**
     * 发放工资银行编号
     */
    @TableField("pay_roll_bank_code")
    private String payRollBankCode;
    /**
     * 该人员在本工程累计支付总金额（元）
     */
    @TableField("pay_total_amount")
    private BigDecimal payTotalAmount;
    /**
     * 该人员在本工程务工累计结算总金额（元）
     */
    @TableField("settle_total_amount")
    private BigDecimal settleTotalAmount;
    /**
     * 是否删除 1：是 0:否
     */
    @TableField("is_del")
    private Integer isDel;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPayRollCode() {
        return payRollCode;
    }

    public void setPayRollCode(String payRollCode) {
        this.payRollCode = payRollCode;
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

    public String getWorkerType() {
        return workerType;
    }

    public void setWorkerType(String workerType) {
        this.workerType = workerType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(BigDecimal rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public BigDecimal getPunishAmount() {
        return punishAmount;
    }

    public void setPunishAmount(BigDecimal punishAmount) {
        this.punishAmount = punishAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getIconSign() {
        return iconSign;
    }

    public void setIconSign(String iconSign) {
        this.iconSign = iconSign;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getIconPhoto() {
        return iconPhoto;
    }

    public void setIconPhoto(String iconPhoto) {
        this.iconPhoto = iconPhoto;
    }

    public Integer getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(Integer certificateType) {
        this.certificateType = certificateType;
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

    public String getPayPerson() {
        return payPerson;
    }

    public void setPayPerson(String payPerson) {
        this.payPerson = payPerson;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLivingCost() {
        return livingCost;
    }

    public void setLivingCost(String livingCost) {
        this.livingCost = livingCost;
    }

    public String getAllLivingcost() {
        return allLivingcost;
    }

    public void setAllLivingcost(String allLivingcost) {
        this.allLivingcost = allLivingcost;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Date getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(Date balanceDate) {
        this.balanceDate = balanceDate;
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

    public String getPayRollBankCode() {
        return payRollBankCode;
    }

    public void setPayRollBankCode(String payRollBankCode) {
        this.payRollBankCode = payRollBankCode;
    }

    public BigDecimal getPayTotalAmount() {
        return payTotalAmount;
    }

    public void setPayTotalAmount(BigDecimal payTotalAmount) {
        this.payTotalAmount = payTotalAmount;
    }

    public BigDecimal getSettleTotalAmount() {
        return settleTotalAmount;
    }

    public void setSettleTotalAmount(BigDecimal settleTotalAmount) {
        this.settleTotalAmount = settleTotalAmount;
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
        return "PayRollDetail{" +
        "id=" + id +
        ", payRollCode=" + payRollCode +
        ", idCardType=" + idCardType +
        ", idCardNumber=" + idCardNumber +
        ", workerType=" + workerType +
        ", price=" + price +
        ", days=" + days +
        ", amount=" + amount +
        ", rewardAmount=" + rewardAmount +
        ", punishAmount=" + punishAmount +
        ", payAmount=" + payAmount +
        ", actualAmount=" + actualAmount +
        ", balanceAmount=" + balanceAmount +
        ", time=" + time +
        ", payStatus=" + payStatus +
        ", sign=" + sign +
        ", iconSign=" + iconSign +
        ", photo=" + photo +
        ", iconPhoto=" + iconPhoto +
        ", certificateType=" + certificateType +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", payPerson=" + payPerson +
        ", remark=" + remark +
        ", livingCost=" + livingCost +
        ", allLivingcost=" + allLivingcost +
        ", confirmTime=" + confirmTime +
        ", balanceDate=" + balanceDate +
        ", payRollBankCardNumber=" + payRollBankCardNumber +
        ", payRollBankName=" + payRollBankName +
        ", payRollBankCode=" + payRollBankCode +
        ", payTotalAmount=" + payTotalAmount +
        ", settleTotalAmount=" + settleTotalAmount +
        ", isDel=" + isDel +
        "}";
    }
}
