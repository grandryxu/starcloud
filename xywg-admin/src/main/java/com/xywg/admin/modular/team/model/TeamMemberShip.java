package com.xywg.admin.modular.team.model;

public class TeamMemberShip {
	private Integer id;
	private Integer teamSysNo;
	private Integer idCardType;
	private String idCardNumber;
	private Integer teamWorkerType;
	private Integer isDel;
	private Integer isSign;
	
	public TeamMemberShip(){}

	public TeamMemberShip(Integer teamSysNo, Integer idCardType, String idCardNumber) {
		this.teamSysNo=teamSysNo;
		this.idCardType=idCardType;
		this.idCardNumber=idCardNumber;
	}
	public TeamMemberShip(Integer teamSysNo, Integer idCardType, String idCardNumber, Integer teamWorkerType) {
		this.teamSysNo = teamSysNo;
		this.idCardType = idCardType;
		this.idCardNumber = idCardNumber;
		this.isDel = 0;
		this.isSign = 0;
		this.teamWorkerType = teamWorkerType;

	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTeamSysNo() {
		return teamSysNo;
	}
	public void setTeamSysNo(Integer teamSysNo) {
		this.teamSysNo = teamSysNo;
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
	public Integer getTeamWorkerType() {
		return teamWorkerType;
	}
	public void setTeamWorkerType(Integer teamWorkerType) {
		this.teamWorkerType = teamWorkerType;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public Integer getIsSign() {
		return isSign;
	}

	public void setIsSign(Integer isSign) {
		this.isSign = isSign;
	}

	@Override
	public String toString() {
		return "TeamMemberShip [id=" + id + ", teamSysNo=" + teamSysNo + ", idCardType=" + idCardType
				+ ", idCardNumber=" + idCardNumber + ", teamWorkerType=" + teamWorkerType + ", isDel=" + isDel
				+ ", isSign=" + isSign + "]";
	}

}
