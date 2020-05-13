package com.xywg.admin.modular.recruit.model;

import lombok.Data;

import java.util.Date;

/**
 * @Auther: wangshibo
 * @Date: 2018/8/23/023 15:32
 * @Description:
 */
@Data
public class ApplyVo {
    /**
     * 申请记录id
     */
    private Integer id;
    /**
     *  申请人名称
     */
    private String workName;
    /**
     *  申请时间
     */
    private Date createDate;
    /**
     *  是否处理
     */
    private Integer isDispose;
    /**
     * 工人电话
     */
    private String cellPhone;
    /**
     * 头像路径
     */
    private String headImage;
    private String idCardType;
    private String idCardNumber;
}
