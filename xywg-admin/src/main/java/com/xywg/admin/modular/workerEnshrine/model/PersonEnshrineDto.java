package com.xywg.admin.modular.workerEnshrine.model;

import lombok.Data;

/**
 * @Auther: wangshibo
 * @Date: 2018/8/21/021 15:52
 * @Description:
 */
@Data
public class PersonEnshrineDto {
    /**
     *用户输入的字符串
     */
    private String keyword;
    /**
     *证件类型
     */
    private String idCardType;
    /**
     *证件编号
     */
    private String idCardNumber;
    /**
     *页码（从1开始）
     */
    private Integer pageNo;
    /**
     *每一页返回的数据个数
     */
    private Integer pageSize;
}
