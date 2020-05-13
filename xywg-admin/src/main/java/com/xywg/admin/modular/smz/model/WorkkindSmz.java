package com.xywg.admin.modular.smz.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 
 * @description 实名制工种类
 * 
 * @author chupp
 *
 * @date 2018年5月2日
 *
 */
@Data
@JsonInclude(Include.NON_NULL)
public class WorkkindSmz implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3268058707862731414L;
	
	private String id;
	private String createDate;
	private String createUser;
	private String kindName;
	private String lastUpdateDate;
	private String lastUpdateUser;
	private String recordStatus;
	private String version;
	private String code;
}
