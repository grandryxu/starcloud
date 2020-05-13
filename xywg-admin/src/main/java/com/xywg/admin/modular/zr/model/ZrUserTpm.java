package com.xywg.admin.modular.zr.model;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;

/**
 * 人员信息
 */
@ToString
@SuppressWarnings("serial")
public class ZrUserTpm implements Serializable{
	private String realName;  //真实姓名
    private Integer gender;  //性别
    private Long areaId;  //籍贯
    private String birthday;  // 出生日期
    private String idNo;  // 身份证号 
    private String nation;  //民族
    private Integer edu;  //学历
    private Long phone;  //手机
    private Integer maritalStatus;  //婚姻状况
    private String team;  //班组
    private String projectId;  // 工程ID 
    private Long workTypeId;  //工种ID
    private String workTypeName;
    
    private String status;  //人员在职状态
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	
	public Long getPhone() {
		return phone;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public Long getWorkTypeId() {
		return workTypeId;
	}
	public void setWorkTypeId(Long workTypeId) {
		this.workTypeId = workTypeId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getEdu() {
		return edu;
	}
	public void setEdu(Integer edu) {
		this.edu = edu;
	}
	public Integer getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(Integer maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getWorkTypeName() {
		return workTypeName;
	}
	public void setWorkTypeName(String workTypeName) {
		this.workTypeName = workTypeName;
	}
    
}