package com.xywg.admin.modular.workerEnshrine.model;

import lombok.Data;

/**
 * @Auther: wangshibo
 * @Date: 2018/8/21/021 10:49
 * @Description:
 */
@Data
public class WorkerEnshrineDto {
    /**
     * 岗位id
     */
    private Long id;
    /**
     * 证件类型
     */
    private String idCardType;
    /**
     * 证件编号
     */
    private String idCardNumber;
    /**
     * 收藏/取消收藏
     */
    private Integer type;
}
