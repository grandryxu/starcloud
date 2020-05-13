package com.xywg.admin.modular.recruit.dto;

import lombok.Data;

/**
 * @Auther: wangshibo
 * @Date: 2018/8/20/020 15:29
 * @Description:
 */
@Data
public class ApplyListPersonDto {
    /**
     * 用户输入的字符串
     */
    private String keyword;
    /**
     * 证件类型
     */
    private String idCardType;
    /**
     * 证件编号
     */
    private String idCardNumber;
    /**
     * 页码
     */
    private Integer pageNo;
    /**
     * 页面大小
     */
    private Integer pageSize;
}
