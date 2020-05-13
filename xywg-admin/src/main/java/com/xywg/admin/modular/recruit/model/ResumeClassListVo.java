package com.xywg.admin.modular.recruit.model;

import com.xywg.admin.modular.recruit.model.WorkSetsVo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Auther: wangshibo
 * @Date: 2018/8/23/023 09:01
 * @Description:
 */
@Data
public class ResumeClassListVo {
    /**
     * 简历id
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 工种String
     */
    private String workSetString;
    /**
     * 工种对象集合
     */
    private List<WorkSetsVo> workSets;
}
