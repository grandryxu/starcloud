package com.xywg.admin.modular.recruit.model;

import lombok.Data;

/**
 * @Auther: wangshibo
 * @Date: 2018/8/23/023 16:00
 * @Description:
 */
@Data
public class EvaluateVo {
    /**
     * id
     */
    private Integer id;
    /**
     *手机号
     */
    private String mobile;
    /**
     *评分
     */
    private Integer score;
    /**
     *评价
     */
    private String evaluate;
    /**
     *是否申诉
     */
    private Integer isAppeal;
    /**
     *处理状态
     */
    private Integer isAudit;
    /**
     *驳回理由
     */
    private String rejectReason;

    /**
     * 头像路径
     */
    private String headImage;
}
