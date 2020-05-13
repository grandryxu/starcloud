package com.xywg.admin.modular.smz.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 
 * @description 实名制考勤记录类
 * 
 * @author chupp
 *
 * @date 2018年5月2日
 *
 */
@Data
@JsonInclude(Include.NON_NULL)
public class DeviceRecordSmz implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4410057702261113307L;
	
	private String id;
	private String userId;
	private String createDate;
	private String deviceId;
	private String photo;
	private String projectId;
	private String time;
	private String type;
	private String comId;
	private String source;
	private String workerName;
	
}
