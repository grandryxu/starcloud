package com.xywg.admin.modular.smz.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 
 * @description 实名制User类
 * 
 * @author chupp
 *
 * @date 2018年4月28日
 *
 */
@Data
@JsonInclude(Include.NON_NULL)
public class UserSmz implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6297863162568379826L;
	
	private String id;
	private String birthday;
	private String createTime;
	private String gender;
	private String level;
	private String logId;
	private String mobile;
	private String name;
	private String password;
	private String roleId;
	private String state;
	private String type;
	private String userId;
	private String homeAddress;
	private String comId;
	private String email;
	private String idImage;
	private String isFace;
	private String modifyTime;
	private String photo;
	private String isAuth;
	
}
