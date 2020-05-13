package com.xywg.admin.modular.smz.utils;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;


/**
 * 
 * 常量设置类
 * 
 * @author duanfen
 * @date 2017年11月28日 下午3:34:04
 *
 */
public class Constant {

	/****** 状态--有效 ******/
	public final static Integer RECORD_STATUS_VALID = 1;
	/****** 状态--无效 ******/
	public final static Integer RECORD_STATUS_INVALID = 0;
	
	/****** 公司id字段，用于条件过滤查询     ******/
	public final static String COM_ID="comId";
	
	/****** 项目id字段，用于条件过滤查询     ******/
	public final static String PROJECT_IDS="projectIds";
	
	/****** miniUI传过来的分页起始页    ******/
	public final static String PAGE_INDEX="pageIndex";
	
	/****** miniUI传过来的分页条数    ******/
	public final static String PAGE_SIZE="pageSize";
	
	/****** 返回前台数据条数     ******/
	public final static String TOTAL="total";
	
	/****** 返回前台数据     ******/
	public final static String DATA="data";
	
	
	/****** app系统用户标识     ******/
	public final static String APP_USER="user";
	
	/****** app工人用户标识     ******/
	public final static String APP_PERSON="person";
	
	/****** 角色标识     ******/
	public final static String ROLE="ROLE_ADMIN";

	/** 
	* @Fields DEFAULT_PASSWORD : 默认密码
	*/
	public static final String DEFAULT_PASS_WORD = "000000";
	
	/**
	 * @Fields STORE_FILEPATH : 文件保存路径
	 */
	public static final String STORE_FILEPATH = "/home/jack";
	
	/**
	 * @Fields M_TRAIN : 培训
	 */
	public static final String M_TRAIN = "train";
	/**
	 * @Fields M_NORM : 规范
	 */
	public static final String M_NORM = "norm";
	
	
	/**特殊字符*/
	public final static String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~@#￥%……&*（）——+|{}【】‘”“’、？]";
	
	/***********************************生成号码标识开始******************************************/
	
	/**生成队伍编号标识*/
	public static final String TEAM_CODE = "TC";
	
	/**生成队伍编号长度 6*/
	public static final Integer TEAM_CODE_SIX = 6;
	
	
	/***********************************生成号码标识结束******************************************/
	
	
	/***********************************合作单位常量******************************************/
	public static final BiMap<Integer,String> COOPERATION_TYPE = HashBiMap.create();
	static{
		COOPERATION_TYPE.put(1, "劳务分包");
		COOPERATION_TYPE.put(2, "专业分包");
	}
	
	
	public static final BiMap<Integer,String> LEVEL_TYPE = HashBiMap.create();
	static{
		LEVEL_TYPE.put(1, "优秀");
		LEVEL_TYPE.put(2, "良好");
		LEVEL_TYPE.put(3, "一般");
		LEVEL_TYPE.put(4, "差");
	}
	
	/***********************************合作单位常量******************************************/
	
	
	/****** 成功提示    ******/
	public final static String SUCCESS="操作成功";
	
	/****** 失败提示     ******/
	public final static String ERROR="操作失败";
	
	/****** 失败提示      ******/
	public final static String IS_EXITS="用户已存在";
	
	/****** 失败提示     ******/
	public final static String GETERROR="数据不存在";

	/***********************************接口调用返回信息******************************************/
	public static final String PARAMETER_ERROR = "传入参数有误";
	
	
	/***********************************考勤机******************************************/
	public static final String DEVICE_COMMAND_SUCCESS = "设备命令提交成功";
	
	public static final String DEVICE_COMMAND_CANCEL = "取消成功";
	
	/***********************************公司用户类型**************************************/
	public static final Integer USER_TYPE_COMPANY=3;
	
	/**班组标识**/
	public final static String CLASS = "C";
	
	/**工人标识**/
	public final static String PERSON = "P";
	
	/**工种标识**/
	public final static String WORKKIND = "W";
	
	/** 数字长度*/
	public final static Integer PERSON_NUMBER = 10;
	
	/** 数字长度*/
	public final static Integer WORKKIND_NUMBER = 5;
	
	/***********************************FTP服务器******************************************/
	/** IP */
	public final static String FTP_HOST = "192.168.10.124";
	/** 端口 */
	public final static int FTP_PORT = 21;
	/** 用户名 */
	public final static String FTP_USERNAME = "jack";
	/** 密码 */
	public final static String FTP_PASS_WORD = "qwer1234";
	/** 静态资源服务地址 */
	public final static String FTP_URL_PATH = "/labor/";
	
	/***********************************短信模板******************************************/
	public static final String COMPANY_AUDIT_PASS = "【星云网格劳务通】您注册的公司审核已通过，请使用手机号直接登陆。";
	
	public static final String COMPANY_AUDIT_UNPASS = "【星云网格劳务通】您注册的公司审核未通过，请重新注册。";
	
	/***********************************考勤来源******************************************/
	protected static final Map<Integer,String> DEVICE_RECORD_SOURCE_MAP = new HashMap<Integer,String>();
	static{
		DEVICE_RECORD_SOURCE_MAP.put(0, "补考勤");
		DEVICE_RECORD_SOURCE_MAP.put(1, "手环");
		DEVICE_RECORD_SOURCE_MAP.put(2, "安全帽");
		DEVICE_RECORD_SOURCE_MAP.put(3, "考勤机");
		DEVICE_RECORD_SOURCE_MAP.put(4, "手机考勤");
	}
	
	/***********************************socket服务器ip****************************/
	public static final String SOCKET_IP="192.168.20.218";
	/***********************************socket服务器端口****************************/
	public static final Integer SOCKET_PORT=8830;
	
	/** 公司 */
	public final static String REDIS_COMPANY="company";
	
	/** 项目  */
	public final static String REDIS_PROJECT="project";
	
	/** 人员管理  */
	public final static String REDIS_USER="user";
	
	/** 工人管理  */
	public final static String REDIS_PERSON="person";
	
	/** 工种管理  */
	public final static String REDIS_WORKKIND="workkind";
	
	/** 工人项目  */
	public final static String REDIS_PERSONP_ROJECT="personProject";
	
	/** 考勤记录  */
	public final static String REDIS_RECORD="record";

	/** 考勤设备  */
	public final static String REDIS_DEVICE_COMMAND="deviceComand";
	
	/** 培训记录  */
	public final static String REDIS_TRAIN="train";
	
	/** 角色  */
	public final static String REDIS_ROLE="role";
	
	/**
	 * 人员加入项目
	 * add by wangpeixu
	 * 20180309
	 */
	public static final String LOCAL_ENTERPROJECT_URL = "http://192.168.1.176:8000/addprojidtopersonidinmt";
	
	
	public final static  String LOG_STRING = "%s用户在%s访问";
	
	
	public final static String LONG_USER_TYPE="xyuser";

	/**
	 * 人脸录入本地
	 * add by wangpeixu
	 * 20180301
	 */
	public static final String LOCAL_ADD_URL = "http://192.168.1.176:8000/getjiexifrom1tolaterftp";

	/**
	 * 人脸新增
	 */
	public static final String BAIDU_ADD_URL = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/add";

	/***
	 * 获取token
	 */
	public static final String BAIDU_TOKEN_URL = "https://aip.baidubce.com/oauth/2.0/token?";

	/**考勤表*/
	public static final String BUSS_DEVICE_RECORD = "buss_device_record";
	
	/**考勤表图片表*/
	public static final String BUSS_DEVICE_RECORD_IMAGE = "buss_device_record_image";
	
	/**考勤表图片表*/
	public static final String BUSS_PROJECT_WORKER_IMAGE = "buss_project_worker_image";
	
	/**
	 * 请求地址头
	 */
	public static final String HTTP_URL_PREFIX = "httpUrlPrefix";
	
	/**
	 * 请求地址头
	 */
	public static final String HTTP_URL_PREFIX_IMAGE = "httpUrlPrefixImage";
	
	/**请求方法名*/
	public static final String SAVE_DEVICE_RECORD_FROM_LABOR = "saveDeviceRecordFromLabor";

	/**企业表*/
	public static final String BUSS_SUB_CONTRACTOR = "buss_sub_contractor";
	
	/**项目表*/
	public static final String BUSS_PROJECT_MASTER = "buss_project_master";
	
	/**项目人员表*/
	public static final String BUSS_PROJECT_WORKER = "buss_project_worker";
	
	/**班组表*/
	public static final String BUSS_TEAM_MASTER = "buss_team_master";
	
	/**工种表*/
	public static final String BUSS_WORK_KIND = "buss_work_kind";
	
	/**项目人员合同表*/
	public static final String BUSS_WORKER_CONTRACT_RULE = "buss_worker_contract_rule";
	
	
	/**设备表*/
	public static final String BUSS_DEVICE = "buss_device";
	
	/**培训表*/
	public static final String BUSS_PROJECT_TRAINING = "buss_project_training";
	
	/**培训附件表*/
	public static final String BUSS_PROJECT_TRAINING_FILE = "buss_project_training_file";
	
	/**培训人员表*/
	public static final String BUSS_PROJECT_TRAINING_PERSON = "buss_project_training_person";
}
