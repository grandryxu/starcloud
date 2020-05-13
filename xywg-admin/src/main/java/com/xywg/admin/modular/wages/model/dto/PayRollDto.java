package com.xywg.admin.modular.wages.model.dto;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 工资单
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-01
 */
@EqualsAndHashCode(callSuper = true)
@TableName("buss_pay_roll")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PayRollDto extends Model<PayRollDto> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 工资单编号 工资单编号18位：GZD+6位分包商系统编号+YYYYMM+3位序号；
     */
    @TableField("pay_roll_code")
    private String payRollCode;
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
     * 发放月份
     */
    @TableField("pay_month")
    private Date payMonth;
    /**
     * 发放类型   1 按工时发放  2按计工单发放
     */
    private Integer type;
    /**
     * 状态   2=分包已审核,3=总包已复核，50=已发放，初始为空
     */
    private Integer status;
    /**
     * 保存状态  1：暂存  2 提交
     */
    @TableField("save_status")
    private Integer saveStatus;
    /**
     * 发放状态 1:待发放  2：部分发放  3：已发放
     */
    @TableField("pay_status")
    private Integer payStatus;
    /**
     * 建筑活动工程编码（总包）来源JZHD0101
     */
    @TableField("contractor_project_code")
    private String contractorProjectCode;
    /**
     * 建筑活动工程编码（分包） 来源JZHD0101
     */
    @TableField("sub_contractor_project_code")
    private String subContractorProjectCode;
    /**
     * 附件 存放工资单附件路径(上传附件时有对应的附件上传接口)
     */
    @TableField("attach_files")
    private String attachFiles;
    /**
     * 应发总额
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;
    /**
     * 分包审核人
     */
    @TableField("construct_valid")
    private Long constructValid;
    /**
     * 分包审核时间
     */
    @TableField("construct_date")
    private Date constructDate;
    /**
     * 总包审核人
     */
    @TableField("contract_valid")
    private Long contractValid;
    /**
     * 总包审核时间
     */
    @TableField("contract_date")
    private Date contractDate;
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
     * 创建人id
     */
    @TableField("salary_person")
    private Long salaryPerson;
    /**
     * 是否删除 1：是 0:否
     */
    @TableField("is_del")
    private Integer isDel;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 应发工资
     */
    private BigDecimal payAmount;


    /**
     * 实发工资
     */
    private BigDecimal actualAmount;


    /**
     * 剩余工资
     */
    private BigDecimal balanceAmount;

    /**
     * 工资详情
     */
    private List<PayRollDetailDto> detailList;

    /**
     * 计工单ids
     * @return
     */
    private String accountId;

    /**
     * 入参 工人列表
     * @return
     */
    private List<WorkerMaster> workerList;

    /**
     * 是否全部签字
     * @return
     */
    private Integer isAllSign;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    /**
     * 发放人名称
     */
    private String accountName;

    /**
     * 审核人 APP登陆者 id
     */
    private Long userId;

    private Integer pageSize;

    private Integer pageNo;

    private Integer index;

}
