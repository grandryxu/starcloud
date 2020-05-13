package com.xywg.admin.modular.smz.model;

import lombok.Data;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/7/13 15:16
 */
@Data
public class ProjectTrainFileMo {
    //培训主键
    private long trainRecordId;
    //附件名称
    private String attementName;
    //附件
    private String attement;
}
