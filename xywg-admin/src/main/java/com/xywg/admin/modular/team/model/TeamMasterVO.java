package com.xywg.admin.modular.team.model;

/**
 * <p>
 * 班组基础信息
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-22
 */
@SuppressWarnings("serial")
public class TeamMasterVO extends TeamMaster {

    private String companyName;
    
    public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
