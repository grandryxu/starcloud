package com.xywg.admin.modular.recruit.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Auther: wangshibo
 * @Date: 2018/8/22/022 09:56
 * @Description:
 */
@Data
public class ExperienceDto {
    /**
     * 工作经历主题
     */
    private String theme;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 工作内容
     */
    private String content;
    /**
     * 项目名称
     */
    private String projectName;
}
