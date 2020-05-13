package com.xywg.admin.modular.smz.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 
 * @description 实名制企业社会统一信用代码关系表
 * 
 * @author jln
 *
 * @date 2019年4月15日
 *
 */
@Data
@JsonInclude(Include.NON_NULL)
public class SmzCorProject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3705751532722230544L;
	
	private Long id;
	private Long projectId;  //社会统一信用代码对应的实名制id
	private String registerNo;  //企业社会统一信用代码
	private String fixValue;  //固定值
}
