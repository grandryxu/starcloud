package com.xywg.admin.modular.smz.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 
 * @description 实名制Role类
 * 
 * @author chupp
 *
 * @date 2018年4月28日
 *
 */
@Data
@JsonInclude(Include.NON_NULL)
public class RoleSmz implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1674785532361105718L;
	
	private String id;
	private String createDate;
	private String createUser;
	private String districtRole;
	private String level;
	private String name;
	private String platform;
	private String state;
	private String txDescription;
	private String type;
	private String comId;
	
}
