package com.xywg.admin.modular.wages.model.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.xywg.admin.core.excel.annotation.ExcelField;
import com.xywg.admin.modular.wages.model.PayRoll;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

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
@Data
public class PayRollExport {

    private static final long serialVersionUID = -5289524165640166027L;

    private String accountIds;

    private List<Map<String,Object>> payRollDetailList;

    private Date payMonthDate;

    /**
     * 项目名称
     */
    @ExcelField(title = "项目名称", order = 1)
    private String projectName;


    /**
     * 班组名称
     */
    @ExcelField(title = "班组", order = 2)
    private String teamName;
    /**
     * 应发金额
     */
    @ExcelField(title = "应发金额", order = 3)
    private BigDecimal payAmount;
    /**
     * 实发金额
     */
    @ExcelField(title = "实发金额", order = 4)
    private BigDecimal actualAmount;
    /**
     * 剩余工资
     */
    @ExcelField(title = "剩余金额", order = 5)
    private BigDecimal balanceAmount;



    /**
     * 发放类型   1 按工时发放  2按计工单发放
     */
    @ExcelField(title = "发放类型", order = 6)
    private String typeName;
    private Integer type;
    /**
     * 保存状态  1：暂存  2 提交
     */
    @ExcelField(title = "保存状态", order = 7)
    private String saveStatusName;
    private Integer saveStatus;
    /**
     * 发放状态 1:待发放  2：部分发放  3：已发放
     */
    @ExcelField(title = "状态", order = 8)
    private String statusName;

    private Integer payStatus;

    /**
     * 分包审核人
     */
    @ExcelField(title = "分包审核人", order = 10)
    private String constructValidName;

    /**
     * 总包审核人
     */
    @ExcelField(title = "总包审核人", order = 11)
    private String contractValidName;


    /**
     * 添加时间
     */
    @ExcelField(title = "添加时间", order = 12)
    private Date createDate;

}
