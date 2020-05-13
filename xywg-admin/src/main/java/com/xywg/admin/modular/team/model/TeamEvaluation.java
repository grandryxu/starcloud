package com.xywg.admin.modular.team.model;

import lombok.Data;

/**
 * 班组评价实体类
 * 
 * @ClassName: TeamEvaluation
 * @Description:TODO(班组评价实体类)
 * @author: wangshibo
 * @date: 2018年7月10日 下午2:32:25
 * 
 * @Copyright: 2018 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于江苏星云网格信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
public class TeamEvaluation {
	/**
	 * id
	 */
	private Long id;
	/**
	 * 项目编号
	 */
	private String projectCode;
	/**
	 * 所属企业组织机构代码
	 */
	private String organizationCode;
	/**
	 * 班组编号
	 */
	private Integer teamSysNo;
	/**
	 * 质量评价星级 1-5星级
	 */
	private Integer option1;
	/**
	 * 进度评价星级 1-5星级
	 */
	private Integer option2;
	/**
	 * 安全评价星级 1-5星级
	 */
	private Integer option3;
	/**
	 * 预留评价星级 1-5星级
	 */
	private Integer option4;
	/**
	 * 评价说明
	 */
	private String note;
	/**
	 * 是否删除标识
	 */
	private Integer isDel;

}
