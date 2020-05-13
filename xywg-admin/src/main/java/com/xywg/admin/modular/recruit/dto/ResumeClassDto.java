package com.xywg.admin.modular.recruit.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Auther: wangshibo
 * @Date: 2018/8/22/022 10:00
 * @Description:
 */
@Data
public class ResumeClassDto {
    private Long id;
    /**
     * 身份证类型
     */
    private Integer idCardType;
    /**
     * 身份证号
     */
    private String idCardNumber;
    /**
     * 联系人姓名
     */
    private String comUsername;
    /**
     * 联系人电话
     */
    private String comPhone;
    /**
     * 工种主键集合（用英文逗号分隔）
     */
    private String workIdSets;
    /**
     * 工程类型
     */
    private Integer projectType;
    /**
     * 工作年限
     */
    private Integer workYears;
    /**
     * 工人数量
     */
    private String workerTotal;
    /**
     * 团队简介
     */
    private String teamBrief;

    /**
     * 省主键
     */
    private String provincesId;
    /**
     * 市主键
     */
    private String citiesId;
    /**
     * 县（区）主键
     */
    private String areasId;
    /**
     * 期望省主键
     */
    private String wishProvincesId;
    /**
     * 期望市主键
     */
    private String wishCitiesId;
    /**
     * 期望县（区）主键
     */
    private String wishAreasId;

    /**
     * 工资类型（1：面议；2：按天结算；3：工资区间；4：X平/X元）
     */
    private String salaryType;
    /**
     * 工资内容
     */
    private String salaryContent;
    /**
     * 工资内容
     */
    private String salaryText;

    /**
     * 工作经历
     */
    private List<ExperienceDto> experience;
}
