package com.xywg.admin.modular.led.model;

import lombok.Data;
/**
 * LED数据接口实体类
 * @ClassName:  UserProjectForLed   
 * @Description:TODO(LED数据接口实体类)   
 * @author: wangshibo 
 * @date:   2018年7月17日 上午10:28:03   
 *     
 * @Copyright: 2018 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏星云网格信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
public class UserProjectForLed {
	/**
	 * 姓名
	 */
	private String userName;
	/**
	 * 身份证
	 */
	private String userId;
	/**
	 * 项目id
	 */
	private Long projectId;
	/**
	 * 项目名称
	 */
	private String ProjectName;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 工种
	 */
	private int workKind;
	/**
	 * 工种名称
	 */
	private String workKindName;
	/**
	 * 班组
	 */
	private String classNo;
	/**
	 * 身份证图片
	 */
	private String  photo;
	/**
	 * 创建时间
	 */
	private String createDate;
	
}
