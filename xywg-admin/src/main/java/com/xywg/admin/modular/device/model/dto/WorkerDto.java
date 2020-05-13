package com.xywg.admin.modular.device.model.dto;

/**
 * 安全帽考勤記錄
 * 
 * @author: duanfen
 * @version: 2018年6月19日 下午2:41:55
 */
public class WorkerDto {
	
    /**
     * 证件类型 引用附录字典表
     */
    private Integer idCardType;
    /**
     * 证件编号
     */
    private String idCardNumber;
	/**
	 * 经度
	 * @author duanfen
	 */
	private Double lng;
	
	/**
	 * 纬度
	 * @author duanfen
	 */
	private Double lat;
	
	/**
	 * 距离
	 * @author duanfen
	 */
	private Double radius;
	
	/**
	 * 考勤天数
	 */
	private Integer recordCount;
	
	/**
	 *班组
	 */
	private Integer teamSysNo;
	
    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
    
    public Integer getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }
	
    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }
    
    public Integer getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(Integer idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }
    
    public Integer getTeamSysNo() {
        return teamSysNo;
    }

    public void setTeamSysNo(Integer teamSysNo) {
        this.teamSysNo = teamSysNo;
    }
}
