package com.xywg.admin.modular.faceUtils;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.math.BigDecimal;


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
	public static final String DEFAULT_PASS_WORD = "123456";
	
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
	
	/** 已实名认证 */
	public final static Integer HAS_AUTHENTICATION = 1;
	
	/** 未实名认证 */
	public final static Integer HAS_NOT_AUTHENTICATION = 0;
	
	/** 未人脸录入 */
	public final static Integer UN_FACE_AUTHENTICATION = 0;
	
	/** 需要压缩 */
	public final static Integer NEED_COMPRESS = 1;
	
	
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
	
	/** 企业端 */
	public final static int ENTERPRISE_PART = 1;
	
	/** 工友端 */
	public final static int WORKER_PART = 2;
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
	
	
	
	/************************图片处理*********************************/


	/***
	 * 获取token
	 */
	public static final String BAIDU_TOKEN_URL = "https://aip.baidubce.com/oauth/2.0/token?";
	
	/***
	 * 图片比较地址
	 */
	public static final String BAIDU_MATCH_URL = "https://aip.baidubce.com/rest/2.0/faceutils/v2/match";
	
	/**
	 * 阈值
	 */
	public static final BigDecimal BAIDU_MATCH_SCORE = new BigDecimal(80);
	
	/**
	 * 图片检测地址
	 */
	public static final String BAIDU_DETECT_URL = "https://aip.baidubce.com/rest/2.0/face/v1/detect";
	
	/**
	 * 人脸查找接口
	 */
	public static final String BAIDU_IDENTIFY_URL = "https://aip.baidubce.com/rest/2.0/face/v2/identify";
	
	/**
	 * 人脸新增
	 */
	public static final String BAIDU_ADD_URL = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/add";

	/**
	 * 人脸新增v3
	 */
	public static final String BAIDU_ADD_URL_V3 = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";

	/**
	 * 人脸更新v3
	 */
	public static final String BAIDU_UPDATE_URL_V3 = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/update";

	/**
	 * 图片检测地址v3
	 */
	public static final String BAIDU_DETECT_URL_V3 = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
	/** 人脸模糊阈值 */
	public static final BigDecimal BLUR_SCORE = BigDecimal.valueOf(0.7);

	/** 脸部区域的光照程度阈值 */
	public static final BigDecimal ILLUMINATION_SCORE = BigDecimal.valueOf(40);

	/** 左眼遮挡比例阈值 */
	public static final BigDecimal LEFT_EYE_SCORE = BigDecimal.valueOf(0.6);

	/** 右眼遮挡比例阈值 */
	public static final BigDecimal RIGHT_EYE_SCORE = BigDecimal.valueOf(0.6);

	/** 鼻子遮挡比例阈值 */
	public static final BigDecimal NOSE_SCORE = BigDecimal.valueOf(0.7);

	/** 嘴巴遮挡比例阈值 */
	public static final BigDecimal MOUTH_SCORE = BigDecimal.valueOf(0.7);

	/** 左脸遮挡比例阈值 */
	public static final BigDecimal LEFT_FACE_SCORE = BigDecimal.valueOf(0.8);

	/** 右脸遮挡比例阈值 */
	public static final BigDecimal RIGHT_FACE_SCORE = BigDecimal.valueOf(0.8);

	/** 下巴遮挡比例阈值 */
	public static final BigDecimal CHIN_CONTOUR_SCORE = BigDecimal.valueOf(0.6);


	/**
	 * 人脸查找接口v3
	 */
	public static final String BAIDU_IDENTIFY_URL_V3 = "https://aip.baidubce.com/rest/2.0/face/v3/search";

	/**
	 * 人脸对比地址v3
	 */
	public static final String BAIDU_MATCH_URL_V3 = "https://aip.baidubce.com/rest/2.0/face/v3/match";
	
	/**班组标识**/
	public final static String CLASS = "C";
	
	
	/**工人标识**/
	public final static String PERSON = "P";
	
	
	/** 数字长度*/
	public final static Integer PERSON_NUMBER = 10;
	
	/** 数字长度*/
	public final static Integer NUMBER = 6;
	
	/***********************************FTP服务器******************************************/
	/** IP */
	public final static String FTP_HOST = "192.168.1.124";
	/** 端口 */
	public final static int FTP_PORT = 21;
	/** 用户名 */
	public final static String FTP_USERNAME = "jack";
	/** 密码 */
	public final static String FTP_PASS_WORD = "qwer1234";
	
	
	
//	/** IP */
//	public final static String FTP_HOST = "192.168.1.209";
//	/** 端口 */
//	public final static int FTP_PORT = 21;
//	/** 用户名 */
//	public final static String FTP_USERNAME = "wangshibo";
//	/** 密码 */
//	public final static String FTP_PASS_WORD = "123456";
	
	/**
	 * 工人默认密码
	 */
	public static final String PERSON_DEFAULT_PASS_WORD = "123456";
	
	public static final String AGREE_CONTENT="name同意了您邀请他加入project的请求";
	public static final String DISAGREE_CONTENT="name拒绝了您邀请他加入project的请求";
	public static final String YOU_AGREE_CONTENT="您同意了name邀请您加入project的请求";
	public static final String YOU_DISAGREE_CONTENT="您拒绝了name邀请你加入project的请求";
	
	
//	public static final String AGREE_CONTENT="%s同意了您邀请他加入%s的请求";
//	
//	public static final String DISAGREE_CONTENT="%s拒绝了您邀请他加入%s的请求";
//	
//	public static final String YOU_AGREE_CONTENT="您同意了%s邀请您加入%s的请求";
//	
//	public static final String YOU_DISAGREE_CONTENT="您拒绝了%s邀请你加入%s的请求";
	
	public static final String AGREE_APPLY_CONTENT="name同意了您加入project下的class的申请";
	public static final String DISAGREE_APPLY_CONTENT="name拒绝了您加入project下的class的申请";
	public static final String YOU_AGREE_APPLY_CONTENT="您同意了name加入project下的class的申请";
	public static final String YOU_DISAGREE_APPLY_CONTENT="您拒绝了name加入project下的class的申请";
	
//	public static final String AGREE_APPLY_CONTENT="%s同意了您加入%s下的%s的申请";
//	
//	public static final String DISAGREE_APPLY_CONTENT="%s拒绝了您加入%s下的%s的申请";
//	
//	public static final String YOU_AGREE_APPLY_CONTENT="您同意了%s加入%s下的%s的申请";
//	
//	public static final String YOU_DISAGREE_APPLY_CONTENT="您拒绝了%s加入%s下的%s的申请";
	
	public static final String CLASS_ASSIGN="你已被%s指派为班组长，请下载劳务通管理端“劳务管理”";
	
	public static final String AGREE_CONTENT_AUDIT ="你的账号已通过审核";
	
	public static final String DISAGREE_CONTENT_AUDIT ="你的账号未通过审核";
	
	public static final String YOU_AGREE_CONTENT_AUDIT ="你审核通过了name的账号";
	
	public static final String YOU_DISAGREE_CONTENT_AUDIT ="你审核未通过name的账号";
	
	/***********************************用户表审核状态******************************************/
	/** 需要审核 */
	public final static int NEED_AUDIT = 0;
	
	/** 审核通过 */
	public final static int AUDIT = 1;
	
	/** 审核不通过 */
	public final static int UN_AUDIT = 2;
	
	/** 系统消息 */
	public final static int SYSTEM_MESSAGE = 1;
	
	/** 非系统消息 */
	public final static int UN_SYSTEM_MESSAGE = 0;
	
	/** 项目经理邀请工人 */
	public final static int MANAGER_INVITE_WORKER=1;
	
	/** 工人同意项目经理邀请 */
	public final static int WORKER_AGREE_INVITE=2;
	
	/** 工人拒绝项目经理邀请 */
	public final static int WORKER_REFUSE_INVITE=3;
	
	/** 工人申请加入班组 */
	public final static int WORKER_APPLY_CLASS=4;
	
	/** 班组长同意工人申请 */
	public final static int CLASSER_AGREE_APPLY=5;
	
	/** 班组长拒绝工人申请 */
	public final static int CLASSER_REFUSE_APPLY=6;
	
	/***********************************短信模板******************************************/
	public static final String VALIDATION_MESSAGE = "【星云网格劳务通】注册验证码：";

	public static final String PASS_WORD_VALIDATION_MESSAGE = "【星云网格劳务通】找回密码验证码：";

	public static final String MESSAGE_PART_2 = "，请在5分钟内填写。如非本人操作，请忽略。";
	
	public static final String AUDIT_PASS = "【星云网格劳务通】您的账号审核已通过，请使用手机号直接登陆。";
	
	public static final String AUDIT_UNPASS = "【星云网格劳务通】您的账号审核未通过，请使用手机号登陆，修改个人信息后再次申请审核。";
	
	/***********************************角色ID******************************************/
	
	public final static long ID_PROGRAM_MANAGER = 1;
	
	/** 公司账号 */
	public final static int ROLE_COMPANY_TYPE = 3;

	/** 默认姓名 */
	public final static String DEFAULT_NAME = "劳务工";

	/**
	 * 人脸录入本地
	 * add by wangpeixu
	 * 20180301
	 */
	public static final String LOCAL_ADD_URL = "http://192.168.1.176:8000/getjiexifrom1tolaterftp";

	/**
	 * 人脸查找本地
	 * add by wangpeixu
	 * 20180301
	 *
	 */
	public static final String lOCAL_IDENTIFY_URL = "http://192.168.1.176:8000/getProjectidandftpurltofindpersonid";

	/**
	 * 阈值本地
	 * add by wangpeixu
	 */
	public static final BigDecimal LOCAL_MATCH_SCORE = BigDecimal.valueOf(0.85);

	/**
	 * 人员加入项目
	 * add by wangpeixu
	 * 20180309
	 */
	public static final String LOCAL_ENTERPROJECT_URL = "http://192.168.1.176:8000/addprojidtopersonidinmt";
	
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
	 * 安全帽检测
	 * add by wangpeixu
	 * 20180525
	 */
	public static final String BAIDU_HAT_DETECT_URL = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/classification/hatdetection";

	/**
	 * BASE64:图片的base64值，base64编码后的图片数据，编码后的图片大小不超过2M；
	 * URL:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)；
	 * FACE_TOKEN：人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个。
	 */
	public static final String IMAGE_TYPE = "BASE64";
	/**
	 * 图片质量控制
	 * NONE: 不进行控制
	 * LOW:较低的质量要求
	 * NORMAL: 一般的质量要求
	 * HIGH: 较高的质量要求
	 * 默认 NONE
	 * 若图片质量不满足要求，则返回结果中会提示质量检测失败
	 */
	public static final String QUALITY_CONTROL = "NORMAL";

	/**
	 * 活体检测控制
	 * NONE: 不进行控制
	 * LOW:较低的活体要求(高通过率 低攻击拒绝率)
	 * NORMAL: 一般的活体要求(平衡的攻击拒绝率, 通过率)
	 * HIGH: 较高的活体要求(高攻击拒绝率 低通过率)
	 * 默认NONE
	 * 若活体检测结果不满足要求，则返回结果中会提示活体检测失败
	 */
	public static final String LIVENESS_CONTROL = "NORMAL";

	/**
	 * 包括age,beauty,expression,faceshape,gender,glasses,landmark,race,quality,facetype信息
	 * 逗号分隔. 默认只返回face_token、人脸框、概率和旋转角度
	 */
	public static final String FACE_DETECT_FACE_FIELD = "age,beauty,expression,faceshape,gender,glasses,landmark,race,quality,facetype";

	
	public static final String IDENTIFIED_HOST = "https://idenauthen.market.alicloudapi.com";
	
	public static final String IDENTIFIED_PATH = "/idenAuthentication";

	public static final String IDENTIFIED_METHOD = "POST";

	public static final String IDENTIFIED_APPCODE = "23655baee8444d2fa72f2d0eeb230741";
}
