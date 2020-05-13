package com.xywg.admin.modular.recruit.dto;

import lombok.Data;

/**
 * @Auther: wangshibo
 * @Date: 2018/8/23/023 08:58
 * @Description:
 */
@Data
public class RecruitPageDto {
    /**
     * 证件类型
     */
    private String idCardType;
    /**
     *证件编号
     */
    private String idCardNumber;
    /**
     *页码
     */
    private Integer pageNo;
    /**
     *页面大小
     */
    private Integer pageSize;
}
