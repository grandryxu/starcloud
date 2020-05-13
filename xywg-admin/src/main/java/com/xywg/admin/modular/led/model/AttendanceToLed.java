package com.xywg.admin.modular.led.model;

import lombok.Data;

import java.util.List;

/**
 * 考情统计实体类
 * @ClassName:  AttendanceToLed   
 * @Description:TODO(考勤统计实体类)   
 * @author: wangshibo 
 * @date:   2018年7月18日 上午11:13:58   
 *     
 * @Copyright: 2018 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏星云网格信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
public class AttendanceToLed {
	/**
	 * 今日项目考勤总人数
	 */
	private Integer	count;
	/**
	 * 按工种统计
	 */
	private List<Record> record;

}
