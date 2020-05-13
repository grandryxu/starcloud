package com.xywg.admin.modular.smz.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 
 * @description 实名制Device类
 * 
 * @author chupp
 *
 * @date 2018年4月28日
 *
 */
@Data
@JsonInclude(Include.NON_NULL)
public class DeviceSmz implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4700216268958224001L;
	
	private String id;
	private String sn;
	private String algVersion;
	private String ctime;
	private String ip;
	private String logSn;
	private String mac;
	private String modifyId;
	private String modifyTime;
	private String name;
	private String proid;
	private String state;
	private String tsTalk;
	private String typeid;
	private String updateType;
	private String version;
	private String comId;
	private String code;
	private String securityKey;
	private String serverIp;
	private String serverPort;
	private String description;
	private String deviceProperty;
	
}
