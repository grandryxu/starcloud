package com.xywg.admin.modular.zr.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.ToString;

/**
 * 企业信息
 */
@ToString
@SuppressWarnings("serial")
public class ZrProjectTpm implements Serializable{
	private String name;  //工程名称
	private String contractNo;  //合同备案号
	private Integer status;  //工程状态
    private String companyId;   //企业ID
    private Long type;  //工程类型
    private BigDecimal cost;  //工程造价
    private String startTime;  //开工时间
    private String endTime;  //竣工时间
    private Integer civilSite; //是否申报文明工地
    private Integer quality;  //是否申报优质工程
    private Integer investType;  //投资类型
    private String construction;  //建设单位
    private String bank; //工资专户银行
    private String account; //工资专户账号
    private String description;  //工程简介
    private Long areaCode;  //工程所属地区代码
    private Integer contractType; //承包类型
    private BigDecimal acreage; //建筑面积
    private String contact;  //工程负责人
    private String contactPhone; //工程负责人联系电话
    private Long id; //劳务平台id（修改信息时需上传）
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getConstruction() {
		return construction;
	}
	public void setConstruction(String construction) {
		this.construction = construction;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(Long areaCode) {
		this.areaCode = areaCode;
	}
	
	
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public Integer getCivilSite() {
		return civilSite;
	}
	public void setCivilSite(Integer civilSite) {
		this.civilSite = civilSite;
	}
	public Integer getQuality() {
		return quality;
	}
	public void setQuality(Integer quality) {
		this.quality = quality;
	}
	public Integer getInvestType() {
		return investType;
	}
	public void setInvestType(Integer investType) {
		this.investType = investType;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public Integer getContractType() {
		return contractType;
	}
	public void setContractType(Integer contractType) {
		this.contractType = contractType;
	}
	public BigDecimal getAcreage() {
		return acreage;
	}
	public void setAcreage(BigDecimal acreage) {
		this.acreage = acreage;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
    
}