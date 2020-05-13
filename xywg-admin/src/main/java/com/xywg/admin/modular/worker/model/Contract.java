package com.xywg.admin.modular.worker.model;

import lombok.Data;

import java.util.Date;

/**
 * @Auther: wangshibo
 * @Date: 2019/1/15/015 17:04
 * @Description:
 */
@Data
public class Contract {
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 班组名称
     */
    private String teamName;
    /**
     * 劳务公司
     */
    private String laborCompany;
    /**
     * 工人名称
     */
    private String workName;
    /**
     * 证件号码
     */
    private String idCardNumber;
    /**
     * 标签
     */
    private String type;
    /**
     * 合同名称
     */
    private String fileName;
    /**
     * 合同地址
     */
    private String filePath;
    /**
     * 时间
     */
    private Date time;
}
