package com.xywg.attendance.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 项目中工人信息
 * </p>
 *
 * @author z
 * @since 2019-03-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("buss_project_worker")
public class BussProjectWorker extends Model<BussProjectWorker> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
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
	private Integer idCardType;
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
	private Integer isDel;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
