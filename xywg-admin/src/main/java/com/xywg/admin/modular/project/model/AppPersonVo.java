package com.xywg.admin.modular.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * app工人信息实体类
 * 
 * @author wangshibo 2018年6月6日 上午8:50:38
 */
@Data
@JsonInclude(Include.NON_NULL)
public class AppPersonVo {

	/**
	 * 证件类型
	 */
	private Integer idCardType;
	/**
	 * 工种
	 */
	private String workKind;
	/**
	 * 证件号
	 */
	private String idCardNumber;
	/**
	 * 工人姓名
	 */
	private String workerName;
	/**
	 * 头像
	 */
	private String headImage;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 籍贯
	 */
	private String birthPlace;
	/**
	 * 电话
	 */
	private String cellPhone;
	/**
	 * 班组名称
	 */
	private String teamName;
	/**
	 * 生日
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	
	/**
	 * 进退场状态
	 */
	private Integer joinStatus;
	/**
	 * 考勤天数
	 */
	private Integer count;
	/**
	 * 进场时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date entryTime;
	
	private String teamSysNo;
}
