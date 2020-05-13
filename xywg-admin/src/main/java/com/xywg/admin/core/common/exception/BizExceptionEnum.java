package com.xywg.admin.core.common.exception;

import com.xywg.admin.core.constant.HttpStatus;
import com.xywg.admin.core.exception.ServiceExceptionEnum;

/**
 * @Description 所有业务异常的枚举
 * @author wangcw
 * @date 2016年11月12日 下午5:04:51
 */
public enum BizExceptionEnum implements ServiceExceptionEnum{

	/**
	 * 账户问题
	 */
	USER_ALREADY_REG(401,"该账号已被注册"),
	NO_THIS_USER(400,"没有此用户"),
	USER_NOT_EXISTED(400, "没有此用户"),
	ACCOUNT_FREEZED(401, "账号被冻结"),
	OLD_PWD_NOT_RIGHT(402, "原密码不正确"),
	TWO_PWD_NOT_MATCH(405, "两次输入密码不一致"),
	ACCOUNT_ERROR(406, "账号异常"),
	DATA_ERROR(407, "数据异常"),
	USER_NOT_LOGIN(401,"请先登陆"),
	/***
	 * 对外接口异常
	 */
	ORGANIZATION_CODE_NOT_NULL(400,"社会统一信用代码不能为空"),
	/**
	 * 字典
	 */
	DICT_EXISTED(400,"字典已经存在"),
	ERROR_CREATE_DICT(500,"创建字典失败"),
	ERROR_WRAPPER_FIELD(500,"包装字典属性失败"),

	/**
	 * 文件上传
	 */
	FILE_READING_ERROR(400,"FILE_READING_ERROR!"),
	FILE_NOT_FOUND(400,"FILE_NOT_FOUND!"),
	UPLOAD_ERROR(500,"上传图片出错"),
	FILE_FORMAT_ERROR(501,"上传文件格式错误"),

	/**
	 * 权限和数据问题
	 */
	DB_RESOURCE_NULL(400,"数据库中没有该资源"),
	NO_PERMITION(405, "权限不足"),
	REQUEST_INVALIDATE(400,"请求数据格式不正确"),
	INVALID_KAPTCHA(400,"验证码不正确"),
	CANT_DELETE_ADMIN(600,"不能删除超级管理员"),
	CANT_FREEZE_ADMIN(600,"不能冻结超级管理员"),
	CANT_CHANGE_ADMIN(600,"不能修改超级管理员角色"),

/*	*//**
	 * 账户问题
	 *//*
	USER_ALREADY_REG(401,"该账号已被注册"),
	NO_THIS_USER(400,"没有此用户"),
	USER_NOT_EXISTED(400, "没有此用户"),
	ACCOUNT_FREEZED(401, "账号被冻结"),
	OLD_PWD_NOT_RIGHT(402, "原密码不正确"),
	TWO_PWD_NOT_MATCH(405, "两次输入密码不一致"),
	ACCOUNT_ERROR(406, "账号异常"),
	DATA_ERROR(407, "数据异常"),*/

	/**
	 * 错误的请求
	 */
	MENU_PCODE_COINCIDENCE(400,"菜单编号和副编号不能一致"),
	EXISTED_THE_MENU(400,"菜单编号重复，不能添加"),
	DICT_MUST_BE_NUMBER(400,"字典的值必须为数字"),
	REQUEST_NULL(400, "请求有错误"),
	SESSION_TIMEOUT(400, "会话超时"),
	SERVER_ERROR(500, "服务器异常"),

	/**
	 * 设备类型
	 */
	DEVICE_CANNOT_DELETE(HttpStatus.SC_NOT_MODIFIED , "已使用的类型禁止删除"),

	/**
	 * app接口响应信息
	 */
	WORKER_NO_EXIST(701,"该工人不存在"),
	ID_CARD_NUMBER_HAS_USED(702,"该身份证已被使用"),
	AUTH_PERSONNEL_FAILED(703,"实名认证失败"),
	IMAGE_NO_DISCERN(704,"该图片不能识别"),
	INPUT_FACE_FAILED(705,"人脸录入失败"),
	FIND_FACE_FAILED(706,"人脸查找失败"),
	FIND_FACE_WORKER_NO_EXIST(707,"人脸查找工人不存在"),
	IMAGE_NO_EXIST(708,"考勤图片不存在"),
	PROJECT_NO_EXIST(709,"所选项目不存在"),
	NO_JOIN_PROJECT(710,"您没有加入所选项目"),
	NO_JOIN(711,"您没有进场"),
	FACE_PROJECT_CODE_FAILED(712,"人脸项目录入失败"),
	FACE_PROJECT_RESULT_ERROR(724,"人脸项目结果异常"),
	ID_CARD_INFO_NOT_SAME(713,"身份信息不一致"),
	PROJECT_INFO_ERROR(714,"项目信息异常"),
	JOIN_TEAM_ERROR(715,"加入班组异常"),
	TEAM_INFO_ERROR(716,"班组异常"),
	JOIN_PROJECT_ERROR(717,"参建关系异常"),
	CONTRACT_NOT_UPLOAD(718,"合同模板未录入"),
	CONTRACT_SIGN_ERROR(719,"合同签署异常"),
	WORKER_EXIST(720,"该工人已存在系统中,无法添加,请直接邀请加入班组"),
	PHONE_EXIST(721,"手机号已被使用"),
	FACE_GET_NO_EXIST(722,"人脸识别无此人"),
	PROJECT_INFO_NOT_OK(723,"项目信息不完整"),
	REPEAT_INPUT_FACE(724,"人脸图片异常，请重新录入人脸"),
	LAT_LNG_ERROR(725,"项目经纬半径异常"),
	NOT_JOIN_PROJECT(726,"未加入此项目"),
	
	
	/***
	 * 公司问题
	 */
	COMPANY_ALREADY_EXISTS(802,"该公司已存在"),
	
	

	/***
	 * 对外接口异常
	 */
	//ORGANIZATION_CODE_NOT_NULL(400,"社会统一信用代码不能为空"),
	COMPANY_NAME_NOT_NULL(400,"企业名称不能为空"),
	REPRESENTATIVE_NAME_NOT_NULL(400,"法定代表人姓名不能为空"),
	PROJECT_NAME_NOT_NULL(400,"项目名称不能为空"),
	PROJECT_CODE_NOT_NULL(400,"项目编号不能为空"),
	AREA_CODE_NOT_NULL(400,"项目区域不能为空"),
	DEVICE_NAME_NOT_NULL(400,"设备名称不能为空"),
	SN_NOT_NULL(400,"设备编号不能为空"),
	TEAM_NAME_IS_NULL(400,"班组名称不能为空"),
	WORKER_IS_NULL(400,"工人信息不能为空"),
	WORKER_NAME_IS_NULL(400,"名字不能为空"),
	ID_CARD_NUMBER_IS_NULL(400,"证件号不能为空"),
	CELL_PHONE_IS_NULL(400,"电话号码不能为空"),
	ADDRESS_IS_NULL(400,"地址不能为空"),
	WORK_TYPE_CODE_IS_NULL(400,"工种不能为空"),
	UUID_IS_NULL(400,"项目人员关系编号不能为空"),
	STATE_TYPE_CODE_IS_NULL(400,"工种不能为空"),
	TYPE_IS_NULL(400,"项目人员关系编号不能为空"),
	ENTRYTIME_IS_NULL(400,"进场时间不能为空"),
	TIME_IS_NULL(400,"考勤时间不能为空"),
	
	COMPANY_NOT_EXISTS(500,"该公司不存在"),
	PROJECT_NOT_EXISTS(500,"项目不存在"),
	PROJECT_NOT_EXIST(500,"项目不存在"),
	TEAM_IS_NUll(500,"项目下班组不存在"),
	WORK_TYPE_CODE_NOT_EXIST(500,"工种不存在"),
	NATION_NOT_EXIST(500,"民族不存在"),
	UUID_ERROR(500,"该项目关系不存在"),
	
	PROJECT_ACTIVITY_TYPE_ERROR(600,"项目活动类型错误"),
	PROJECT_CATEGROY_ERROR(600,"项目分类错误"),
	PROJECT_STATUS_ERROR(600,"项目状态错误"),
	ORGANIZATION_CODE_ERROR(600,"社会统一信用代码错误"),
	BUILD_ORGANIZATION_CODE_ERROR(600,"建设单位社会统一信用代码错误"),
	AREA_CODE_ERROR(600,"项目区域需国家标准地区码"),
	ID_CARD_NUMBER_ERROR(600,"证件号格式错误"),
	CELL_PHONE_NOT_VALID(600,"电话号码长度为11到13"),
	GENDER_ERROR(600,"性别错误"),
	JOIN_STATUS_ERROR(600,"进场状态错误"),
	DEVICE_TYPE_ERROR(600,"考勤类型错误"),
	ORGANIZATION_ALREADY_EXISTS(700,"该公司已存在"),
	SN_ALREADY_EXISTS(700,"设备已存在"),
	SN_NOT_EXISTS(700,"设备不存在"),
	PROJECT_ALREADY_EXISTS(700,"该项目已存在"),
	TEAM_ALREADY_EXISTS(700,"项目下班组已存在"),
	WORKER_ALREADY_EXISTS(700,"工人信息已存在"),
	PROJECT_WORKER_RELATION(700,"项目人员已存在");

	BizExceptionEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	private Integer code;

	private String message;

	@Override
	public Integer getCode() {
		return code;
	}

//	public void setCode(Integer code) {
//		this.code = code;
//	}

	@Override
	public String getMessage() {
		return message;
	}

//	public void setMessage(String message) {
//		this.message = message;
//	}
}
