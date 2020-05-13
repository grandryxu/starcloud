package com.xywg.admin.modular.system.model;

import lombok.Data;

/**
 * <p>
 * 时间设置表
 * </p>
 *
 * @author shily
 * @since 2018-06-22
 */
@SuppressWarnings("serial")
@Data
public class TimeSetVO extends TimeSet {
    
    private String projectName;
    
    private String name;

    private  String typeName;
    
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

}
