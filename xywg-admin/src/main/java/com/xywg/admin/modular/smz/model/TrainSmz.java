package com.xywg.admin.modular.smz.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 
 * @description 实名制培训类
 * 
 * @author chupp
 *
 * @date 2018年4月28日
 *
 */
@Data
@JsonInclude(Include.NON_NULL)
public class TrainSmz implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6043781069039356541L;
	
	private String id;
	private String createDate;
	private String createUser;
	private String lastUpdateDate;
	private String lastUpdateUser;
	private String recordStatus;
	private String trainCode;
	private String trainContent;
	private String trainName;
	private String comId;
	private String projectId;
	private String trainDate;
	private String trainTitle;
	private String trainDuration;
	private String trainTypeCode;
	private String trainner;
	private String trainRecordId;
	private String idcardNumber;
	private String trainId;
	private String attentName;
	private String attentPath;
	private String hzm;
	
}
