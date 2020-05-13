package com.xywg.admin.modular.recruit.model;

import lombok.Data;

import java.util.Date;

/**
 * @Auther: wangshibo
 * @Date: 2018/8/20/020 15:33
 * @Description:
 */
@Data
public class ApplyListPersonVo {
    /**
     * 岗位id
     */
    private Integer id;
    /**
     * 招聘岗位
     */
    private String recruitStation;
    /**
     * 岗位描述
     */
    private String description;
    /**
     * 工种名称
     */
    private String work;
    /**
     * 工资类型
     */
    private Integer salaryType;
    /**
     * 工资内容
     */
    private String salaryContent;
    /**
     * 工资内容
     */
    private String salaryText;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 更新时间
     */
    private Date updateDate;
    /**
     * 是否处理
     */
    private Integer isDispose;

    /**
     * 是否评价
     */
    private Integer isEvaluate;
    /**
     * 招聘类型
     */
    private Integer recruitType;
    /**
     * 联系号码
     */
    private String phone;
}
