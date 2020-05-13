package com.xywg.admin.modular.wages.model;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * <p>
 * 工资单
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-01
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PayRollVo extends PayRoll {

    private static final long serialVersionUID = -5289524165640166027L;

    private String accountIds;

    private List<Map<String,Object>> payRollDetailList;

    private Date payMonthDate;

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
     * 项目名称
     */
    private String projectName;


    /**
     * 班组名称
     */
    private String teamName;

    /**
     * 分包审核人
     */
    private String constructValidName;

    /**
     * 总包审核人
     */
    private String contractValidName;

    /**
     * 关联文件个数
     */
    private Integer fileNums;

    /**
     * 项目级 总包是否能操作参建公司的数据
     */
    private Integer isGeneralContractorOperation;

    /**
     * 所属公司
     */
    private String companyName;

}
