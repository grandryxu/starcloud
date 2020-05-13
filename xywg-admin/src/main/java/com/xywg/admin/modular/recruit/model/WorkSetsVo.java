package com.xywg.admin.modular.recruit.model;

import lombok.Data;

/**
 * @Auther: wangshibo
 * @Date: 2018/8/22/022 16:02
 * @Description:
 */
@Data
public class WorkSetsVo {
    /**
     * 工种id
     */
    private Integer workId;
    /**
     * 工种名称
     */
    private String work;

    public WorkSetsVo() {
    }

    public WorkSetsVo(Integer workId, String work) {
        this.workId = workId;
        this.work = work;
    }
}
