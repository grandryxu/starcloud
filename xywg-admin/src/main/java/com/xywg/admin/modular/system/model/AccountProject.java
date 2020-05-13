package com.xywg.admin.modular.system.model;

import lombok.Data;

/**
 * @author jingyun_hu
 * @date 2018/7/11
 */
@Data
public class AccountProject {
    /**
     * 用户账号与项目关联表id
     */
    private Long id;
    /**
     * 账号
     */
    private String account;
    /**
     *用户名
     */
    private String userName;
    /**
     *部门id
     */
    private String deptId;
    /**
     *部门名称
     */
    private String deptName;
    /**
     *企业信用代码
     */
    private String organizationCode;
    /**
     *项目编号
     */
    private String projectCode;
    /**
     *项目名称
     */
    private String projectName;
    /**
     *删除标识
     */
    private String isDel;
}
