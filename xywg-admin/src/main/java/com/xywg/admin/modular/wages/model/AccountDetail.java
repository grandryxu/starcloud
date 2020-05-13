package com.xywg.admin.modular.wages.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 计工明细
包工   money*number+/- money =amount   点工  record_day*price +/- money =amount
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-01
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("buss_account_detail")
public class AccountDetail extends Model<AccountDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 计工id
     */
    @TableField("account_id")
    private Long accountId;
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
     * 考勤天
     */
    @TableField("record_day")
    private Float recordDay;
    /**
     * 计工方式  1：计时 2：计量 3 包月
     */
    private Integer type;
    /**
     * 单位
     */
    private Integer unit;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 计件/工时/数量
     */
    private Float number;
    /**
     * 基本工资
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;
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
     * 实际应发金额
     */
    @TableField("pay_amuont")
    private BigDecimal payAmuont;
    /**
     * 工单开始时间
     */
    @TableField("start_date")
    private Date startDate;
    /**
     * 工单结束时间
     */
    @TableField("end_date")
    private Date endDate;
    /**
     * 是否已签字 1是 0否
     */
    @TableField("is_sign")
    private Integer isSign;
    /**
     * 确认签字
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
     * 确认签字时间
     */
    @TableField("sign_date")
    private Date signDate;
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
    private String note;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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

    public Float getRecordDay() {
        return recordDay;
    }

    public void setRecordDay(Float recordDay) {
        this.recordDay = recordDay;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Float getNumber() {
        return number;
    }

    public void setNumber(Float number) {
        this.number = number;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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

    public BigDecimal getPayAmuont() {
        return payAmuont;
    }

    public void setPayAmuont(BigDecimal payAmuont) {
        this.payAmuont = payAmuont;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getIsSign() {
        return isSign;
    }

    public void setIsSign(Integer isSign) {
        this.isSign = isSign;
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

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AccountDetail{" +
        "id=" + id +
        ", accountId=" + accountId +
        ", idCardType=" + idCardType +
        ", idCardNumber=" + idCardNumber +
        ", recordDay=" + recordDay +
        ", type=" + type +
        ", unit=" + unit +
        ", price=" + price +
        ", number=" + number +
        ", totalAmount=" + totalAmount +
        ", rewardAmount=" + rewardAmount +
        ", punishAmount=" + punishAmount +
        ", payAmuont=" + payAmuont +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", isSign=" + isSign +
        ", sign=" + sign +
        ", iconSign=" + iconSign +
        ", photo=" + photo +
        ", iconPhoto=" + iconPhoto +
        ", signDate=" + signDate +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", note=" + note +
        "}";
    }
}
