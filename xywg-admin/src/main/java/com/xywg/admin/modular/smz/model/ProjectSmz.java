package com.xywg.admin.modular.smz.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 
 * @description 实名制Project类
 * 
 * @author chupp
 *
 * @date 2018年4月28日
 *
 */
@Data
@JsonInclude(Include.NON_NULL)
public class ProjectSmz implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4657341403373352121L;
	
	private String id;
	private String begin;
	private String place;
	private String end;
	private String lat;
	private String lng;
	private String btBuilding;
	private String modifyTime;
	private String buildingSize;
	private String buildingCost;
	private String createTime;
	private String builder;
	private String name;
	private String comId;
	private String projectType;
	private String description;
	private String projectProgress;
	private String projectProgressNew;
	private String placePoint;
	private String projectTypeNew;
	private String projectActivityType;
	private String constructorName;
	private String contractorOrgcode;
	private String companyCode;
	private String registerNo;
	private String companyName;
}
