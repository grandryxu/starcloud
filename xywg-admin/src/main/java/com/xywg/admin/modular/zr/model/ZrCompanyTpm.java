package com.xywg.admin.modular.zr.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 
 * @description company类
 * 
 * @author jln
 *
 *
 */
@Data
@JsonInclude(Include.NON_NULL)
public class ZrCompanyTpm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3856227610147460977L;
	
	private String fullName;  //企业全称
	private String simpleName;  //简称
	private String registration;  //注册所在地区代码
    private Integer type;   //企业类型
    private String code;  //企业代码
    private String userId;  //实名制平台用户ID
    private Integer level;  //企业资质等级
    private String contact;  //联系人
    private String contactPhone; //联系人电话
    private String description;  //企业简介
    private String id;  //劳务实名制返回id（修改时需上传）
    private Long registerCapital;  //注册资金
	
}
