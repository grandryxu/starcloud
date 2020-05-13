package com.xywg.admin.modular.smz.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 
 * @description 实名制、劳务通主键关系表
 * 
 * @author chupp
 *
 * @date 2018年5月2日
 *
 */
@Data
@JsonInclude(Include.NON_NULL)
@TableName("r_smz_lwt")
public class SmzLwt implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3705751532722230544L;
	
	private Long id;
	@TableField(value = "smz_id")
	private Long smzId;
	@TableField(value = "lwt_id")
	private Long lwtId;
	@TableField(value = "table_name")
	private String tableName;
	@TableField(exist = false)
	private Long projectId;  //社会统一信用代码对应的实名制id
	@TableField(exist = false)
	private String registerNo;  //企业社会统一信用代码
}
