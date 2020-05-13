package com.xywg.admin.modular.smz.model;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/7/13 10:16
 */
@Data
public class ProjectTrainMo {
    private String name;
    private String  content;
    private Long projectId;
    private Date time;
    private Long larlorId;
    private String remark;
    //培训时长
    private Long trainingDuration;
    //培训类型
    private String trainingType;
    //培训人
    private String trainer;
    //项目编码
    private String projectCode;
}
