package com.xywg.admin.modular.system.model;

import lombok.Data;

import java.util.Date;

/**
 * * @Package com.xywg.admin.modular.system.model
 * * @Description: ${todo}
 * * @author caiwei
 * * @date 2018/8/20
 **/
@Data
public class AppealVo extends Appeal{

    /**
     * 招聘公司的名称
     */
    private String companyName;
    /**
     * 评价人名称
     */
    private String workerName;
    /**
     * 评价内容
     */
    private String evaluate;
    /**
     * 评价分
     */
    private Integer score;
    /**
     * 评价时间
     */
    private Date evaluateDate;
    /**
     * 申诉类型
     */
    private String typeName;

    /**
     * 招聘主题
     */
    private String recruitStation;

    /**
     * 申诉时间
     */
    private Date appealDate;

}
