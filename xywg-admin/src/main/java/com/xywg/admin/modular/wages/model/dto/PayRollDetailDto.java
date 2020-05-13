package com.xywg.admin.modular.wages.model.dto;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

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
@Data
public class PayRollDetailDto extends Model<PayRollDetailDto> {

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
     * 班组编号
     */
    private Integer teamSysNo;

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
    @TableField("create_date")
    private Date createDate;
    /**
     * 添加人
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

    /**
     * 头像
     */
    private String headImage;

    /**
     * 工人名字
     */
    private String workerName;

    /**
     * 是否签字 1是 0否
     */
    private Integer isSign;

    /**
     * 累计应发金额
     */
    private BigDecimal addPayAmount ;

    /**
     * 累计实发金额
     */
    private BigDecimal addActualAmount ;

    /**
     * 累计剩余工资
     */
    private BigDecimal addBalanceAmount ;

    /**
     * 是否认证  1 已认证  0未认证  3黑名单
     */
    private Integer isAuth;

    /**
     * 此人对应的工资单
     */
    private String payrollDetailIds;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
