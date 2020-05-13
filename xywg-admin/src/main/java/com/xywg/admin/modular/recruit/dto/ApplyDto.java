package com.xywg.admin.modular.recruit.dto;

import lombok.Data;

/**
 * @Auther: wangshibo
 * @Date: 2018/8/20/020 10:19
 * @Description:
 */
@Data
public class ApplyDto {
    /**
     * 岗位id
     */
    private Integer id;
    /**
     * 证件类型
     */
    private String idCardType;
    /**
     * 证件编号
     */
    private String idCardNumber;
    /**
     * 班组简历id
     */
    private Integer resumeClass;

    /**
     * 联系电话
     */
    private String mobile;
}
