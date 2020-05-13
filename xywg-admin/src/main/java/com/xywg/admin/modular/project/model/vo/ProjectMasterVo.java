package com.xywg.admin.modular.project.model.vo;


import com.baomidou.mybatisplus.annotations.TableName;

import com.xywg.admin.modular.project.model.ProjectMaster;
import lombok.Data;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 项目基础信息
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-23
 */
@Data
@TableName("buss_project_master")
public class ProjectMasterVo extends ProjectMaster {

    /**
     * 参建公司社会信用代码
     */
    private String cOrganizationCode;

    /**
     * 参建公司名称
     */
    private String cCompanyName;

    /**
     * 项目分类
     */
    private String projectCategoryName;

    /**
     * 项目状态
     */
    private String projectStatusName;

    /**
     * 人是否和项目关联 0否 1是
     */
    private String isRelation;
    /**
     * 是否是默认项目
     */
    private String isDefault;

    /**
     * 项目地址
     */
    private List<Map<String,Object>> projectAddressList;
    
    /**
     * 修改之前的考勤类型
     */
    private Integer oldDeviceType;

}
