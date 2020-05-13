package com.xywg.admin.modular.project.model;

import java.math.BigDecimal;
import java.util.List;

import com.xywg.admin.modular.project.model.vo.ProjectPositionVo;
import lombok.Data;

/**
 * 工人所在项目信息列表
 * 
 * @author wangshibo 2018年6月4日 下午2:42:50
 */
@Data
public class AppProjectListByPersonVo {
	/**
	 * 项目编号
	 */
	private String projectCode;
	/**
	 * 项目名称
	 */
	private String projectName;
	/**
	 * 项目状态
	 */
	private Integer projectStatus;

	/**
	 * 经度
	 */
	private BigDecimal lng;
	/**
	 * 纬度
	 */
	private BigDecimal lat;
	/**
	 * 半径
	 */
	private Double radius;
	/**
	 * 项目经理名字
	 */
	private String projectManagerName;
	/**
	 * 班组id
	 */
	private Integer teamId;
	/**
	 * 班组名称
	 */
	private String teamName;
	
	/**
	 * 班组编号
	 */
	private Integer teamSysNo;

	/**
	 * 考勤方式
	 */
	private Integer deviceType;
	/**
	 * id
	 */
	private Long projectId;

	/**
	 * 项目经纬度集合
	 */
	private List<ProjectPositionVo> positionList;
}
