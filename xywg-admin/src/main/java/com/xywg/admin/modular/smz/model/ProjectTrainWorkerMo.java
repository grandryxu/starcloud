package com.xywg.admin.modular.smz.model;

import lombok.Data;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/7/13 16:18
 */
@Data
public class ProjectTrainWorkerMo {
    //培训主键
    private long trainRecordId          ;
    //人员id
    private Long ngId         ;
    //项目Id
    private Long projectId         ;
    //证件号码
    private String idCardNumber         ;
    //证件类型
    private Integer idCardType;
}
