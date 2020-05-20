package com.xywg.attendance.modular.system.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author z
 * @since 2019-02-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("TF_F_USER")
public class TfFUser extends Model<TfFUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("NG_ID")
	private Long ngId;
    /**
     * 用户角色ID
     */
	@TableField("NG_ROLE_ID")
	private Long ngRoleId;
    /**
     * 用户ID（身份证号）、登陆账号，冗余字段
     */
	@TableField("SZ_USER_ID")
	private String szUserId;
    /**
     * 用户ID（身份证号）、登陆账号，日志显示用
     */
	@TableField("SZ_LOG_ID")
	private String szLogId;
    /**
     * 姓名
     */
	@TableField("SZ_NAME")
	private String szName;
    /**
     * 密码
     */
	@TableField("SZ_PASSWORD")
	private String szPassword;
    /**
     * 性别
     */
	@TableField("NT_GENDER")
	private Integer ntGender;
    /**
     * 手机
     */
	@TableField("SZ_MOBILE")
	private String szMobile;
    /**
     * 办公电话
     */
	@TableField("SZ_PHONE")
	private String szPhone;
    /**
     * 邮箱地址
     */
	@TableField("SZ_EMAIL")
	private String szEmail;
    /**
     * 人员类型：1=项目人员，2=管理人员
     */
	@TableField("NT_TYPE")
	private Integer ntType;
    /**
     * 人员角色层级
     */
	@TableField("NT_ROLE_LEVEL")
	private Integer ntRoleLevel;
    /**
     * 所属企业
     */
	@TableField("SZ_COMPANY")
	private String szCompany;
    /**
     * 证书1
     */
	@TableField("SZ_CERTIFICATE1")
	private String szCertificate1;
    /**
     * 证书2
     */
	@TableField("SZ_CERTIFICATE2")
	private String szCertificate2;
    /**
     * 证书3
     */
	@TableField("SZ_CERTIFICATE3")
	private String szCertificate3;
    /**
     * 补充信息
     */
	@TableField("TX_DESCRIPTION")
	private String txDescription;
    /**
     * 状态，1=正常，-1=删除
     */
	@TableField("NT_STATE")
	private Integer ntState;
    /**
     * 创建时间
     */
	@TableField("TS_CREATE")
	private Date tsCreate;
    /**
     * 添加、修改时间
     */
	@TableField("TS_MODIFY")
	private Date tsModify;
    /**
     * 修改人IP
     */
	@TableField("SZ_MODIFY_IP")
	private String szModifyIp;
    /**
     * 添加、修改人ID
     */
	@TableField("NG_MODIFIER_ID")
	private Long ngModifierId;
    /**
     * 家庭住址
     */
	@TableField("HOME_ADDRESS")
	private String homeAddress;
    /**
     * 暂住地址
     */
	@TableField("CURR_ADDRESS")
	private String currAddress;
    /**
     * 工种
     */
	@TableField("WORK_KIND")
	private String workKind;
    /**
     * 班组
     */
	@TableField("CLASS_NO")
	private String classNo;
    /**
     * 备注
     */
	@TableField("MEMO")
	private String memo;
    /**
     * 生日
     */
	@TableField("BIRTHDAY")
	private String birthday;
    /**
     * 超过规定年龄，Y表示超过N未超过
     */
	@TableField("OVER_AGE")
	private String overAge;
    /**
     * 用户所属平台
     */
	@TableField("USER_PLAT_FORM")
	private String userPlatForm;
    /**
     * 人员类别
     */
	@TableField("NT_PTYPE")
	private Integer ntPtype;
	@TableField("SZ_PHOTO")
	private String szPhoto;
	@TableField("NT_PROJECT_ID")
	private Long ntProjectId;
    /**
     * 来源
     */
	@TableField("SOURCE")
	private String source;
    /**
     * 劳动合同路径
     */
	@TableField("WIN_BIDDING_FILE")
	private String winBiddingFile;
    /**
     * IMEI编号
     */
	@TableField("IMEI")
	private String imei;
    /**
     * 证件类型
     */
	@TableField("IDCARD_TYPE")
	private Integer idcardType;
    /**
     * 民族
     */
	@TableField("NATION")
	private String nation;
    /**
     * 籍贯
     */
	@TableField("BIRTHPLACE_CODE")
	private String birthplaceCode;
    /**
     * 政治面貌
     */
	@TableField("POLITICS_TYPE")
	private Integer politicsType;
    /**
     * 是否加入工会 1：是 0：否
     */
	@TableField("ISJOINED")
	private Integer isjoined;
    /**
     * 加入工会时间
     */
	@TableField("JOINED_TIME")
	private Date joinedTime;
    /**
     * 文化程度
     */
	@TableField("CULTURE_LEVEL_TYPE")
	private Integer cultureLevelType;
    /**
     * 是否有重大疾病
     */
	@TableField("HASBAD_MEDICAL_HISTORY")
	private Integer hasbadMedicalHistory;
    /**
     * 紧急联系人
     */
	@TableField("URGENT_CONTRACT_NAME")
	private String urgentContractName;
    /**
     * 紧急联系人电话
     */
	@TableField("URGENT_CONTRACT_CELLPHONE")
	private String urgentContractCellphone;
    /**
     * 开始工作日期
     */
	@TableField("WORK_DATE")
	private Date workDate;
    /**
     * 当前职称
     */
	@TableField("PROFESSIONAL_TYPE")
	private Integer professionalType;
    /**
     * 当前职称等级
     */
	@TableField("PROFESSIONAL_LEVEL")
	private Integer professionalLevel;
    /**
     * 默认头像
     */
	@TableField("HEADIMAGE_PATH")
	private String headimagePath;
    /**
     * 人员类型
     */
	@TableField("PERSON_TYPE")
	private String personType;
    /**
     * 0异常  1正常
     */
	@TableField("IS_DELETE")
	private Integer isDelete;
    /**
     * 是否已传群耀平台1：是
     */
	@TableField("UP_FLAG")
	private Integer upFlag;


	@Override
	protected Serializable pkVal() {
		return this.ngId;
	}

}
