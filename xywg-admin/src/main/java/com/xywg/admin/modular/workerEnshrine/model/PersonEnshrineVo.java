package com.xywg.admin.modular.workerEnshrine.model;

import lombok.Data;

import java.util.Date;

/**
 * @Auther: wangshibo
 * @Date: 2018/8/21/021 15:58
 * @Description:
 */
@Data
public class PersonEnshrineVo {
    /**
     * 岗位id
     */
    private Integer id;
    /**
     * 招聘岗位
     */
    private String recruitStation;
    /**
     * 岗位描述
     */
    private String description;
    /**
     * 工种名称
     */
    private String work;
    /**
     * 工资类型
     */
    private Integer salaryType;
    /**
     * 工资内容
     */
    private String salaryContent;
    /**
     * 工资内容
     */
    private String salaryText;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 更新时间
     */
    private Date updateDate;
    /**
     * 是否申请
     */
    private Integer isResume;
    /**
     * 招聘类型
     */
    private Integer recruitType;
    /**
     * 联系号码
     */
    private String phone;
    
    private Integer isDel;
}
