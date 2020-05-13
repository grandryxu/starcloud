package com.xywg.admin.modular.zr.model;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;

/**
 * 黑名单信息
 */
@ToString
@SuppressWarnings("serial")
public class SmzZrRelationTpm implements Serializable{
	private Long id;
	private Long lwtId;  
    private String zrId;  
    private String tableName;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getZrId() {
		return zrId;
	}
	public void setZrId(String zrId) {
		this.zrId = zrId;
	}
	public Long getLwtId() {
		return lwtId;
	}
	public void setLwtId(Long lwtId) {
		this.lwtId = lwtId;
	}  
    
}