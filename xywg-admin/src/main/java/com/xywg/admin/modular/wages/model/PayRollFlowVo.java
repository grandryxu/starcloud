package com.xywg.admin.modular.wages.model;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * <p>
 * 工资流水表
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-01
 */
@TableName("buss_pay_roll_flow")
@Data
public class PayRollFlowVo extends PayRollFlow {

    /**
     * 发放状态 2.部分发放 3已发放
     */
    private String paySatus;

    /**
     * 项目名称
     */
    private String projectName;


    /**
     * 1 按计工单发放 2按工种发放
     */
    private String payType;


}
