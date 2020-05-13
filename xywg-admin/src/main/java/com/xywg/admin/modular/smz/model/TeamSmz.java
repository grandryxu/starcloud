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
public class TeamSmz implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 3106090369026732729L;
	
	private String id;
	private String name;
	private String projectId;
	private String comId;
	private String teamLeader;
	private String contract;
	private String teamIdNumber;
	private String responseIdNumber;
	
}
