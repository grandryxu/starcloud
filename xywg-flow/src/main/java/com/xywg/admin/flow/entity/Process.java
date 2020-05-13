package com.xywg.admin.flow.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author 严鹏
 * @date 2019/3/31
 */
@Data
public class Process{
    /**
     *
     */
    private String id;

    private Date createTime;

    private Integer createUser;

    private Date updateTime;

    private Integer updateUser;

    private Integer deptId;

    private Integer isDel;

    /**
     * 流程名称
     */
    private String processName;

    /**
     * 展示名称
     */
    private String displayName;


    /**
     * 发起人id
     */
    private String userIds;

    /**
     * 发起人角色
     */
    private String roleIds;

    /**
     * 发起人部门
     */
    private String deptIds;


    /**
     * 流程参数
     *
     * example
     * {
     *     "node1":{
     *         "prev":"",                             //上一个节点
     *         "next":"node1",                        //下一个节点
     *         "par":{"user":[],"role":[],"dept":[]}  //参与人
     *     }
     * }
     */
    private String vars;


    /**
     * 版本
     */
    private Integer version;

    /**
     * 类型 0 默认 1 自定义
     */
    private Integer type;

    private String projectId;

}
