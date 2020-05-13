package com.xywg.admin.modular.wages.model;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 工资明细
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-29
 */
@EqualsAndHashCode(callSuper = true)
@TableName("buss_pay_roll_detail")
@Data
public class PayRollDetailVo extends PayRollDetail {

    /**
     * 身份证名称
     */
    private String idCardName;

    /**
     * 工人名称
     */
    private String workerName;

    /**
     * 工种名称
     */
    private String workKindName;

    /**
     * 考勤天数
     */
    private Integer days;

}
