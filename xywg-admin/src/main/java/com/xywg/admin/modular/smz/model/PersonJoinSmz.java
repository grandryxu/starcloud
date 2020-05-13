package com.xywg.admin.modular.smz.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 
 * @description 实名制进退场类
 * 
 * @author chupp
 *
 * @date 2018年5月10日
 *
 */
@Data
@JsonInclude(Include.NON_NULL)
public class PersonJoinSmz implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3179444158083237723L;
	
	private String id;
	private String userId;
	private String projectId;
	private String joinStatus;
	private String createDate;
	private String comId;
}
