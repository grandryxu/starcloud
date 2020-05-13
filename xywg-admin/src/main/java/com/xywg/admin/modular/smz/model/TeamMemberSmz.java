package com.xywg.admin.modular.smz.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 
 * @description 实名制班组类
 * 
 * @author chupp
 *
 * @date 2018年7月11日
 *
 */
@Data
@JsonInclude(Include.NON_NULL)
public class TeamMemberSmz implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 3106090369026732729L;
	
	private Integer idCardType;
	private String idCardNumber;
	private String classNo;
	private Long userId;
	private Long projectId;
	
}
