package com.xywg.admin.modular.recruit.model;

import lombok.Data;

/**
 * @Auther: wangshibo
 * @Date: 2018/8/22/022 14:58
 * @Description:
 */
@Data
public class ResumeClassVo {
    /**
     * 简历id
     */
    private Integer id;

    public ResumeClassVo() {
    }

    public ResumeClassVo(Integer id) {
        this.id = id;
    }
}
