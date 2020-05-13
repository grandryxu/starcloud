package com.xywg.admin.modular.recruit.model;

import com.xywg.admin.modular.recruit.dto.ExperienceDto;
import lombok.Data;

import java.util.List;

/**
 * @Auther: wangshibo
 * @Date: 2018/8/23/023 10:43
 * @Description:
 */
@Data
public class RecruitResumeClassVo {

    private Long id;
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
    private String workIdSetString;
    /**
     * 工程类型
     */
    private String projectTypeName;
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
     * 省
     */
    private String provinces;
    /**
     * 市
     */
    private String cities;
    /**
     * 县
     */
    private String areas;

    /**
     * 期望省
     */
    private String wishProvinces;
    /**
     * 期望市
     */
    private String wishCities;
    /**
     * 期望县（区）
     */
    private String wishAreas;
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
    /**
     * 工种对象集合
     */
    private List<WorkSetsVo> workSets;

    /**
     * 省ID
     */
    private String provincesId;
    /**
     * 市ID
     */
    private String citiesId;
    /**
     * 县ID
     */
    private String areasId;

    /**
     * 期望省ID
     */
    private String wishProvincesId;
    /**
     * 期望市ID
     */
    private String wishCitiesId;
    /**
     * 期望县（区）ID
     */
    private String wishAreasId;
}
