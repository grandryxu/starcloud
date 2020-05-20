package com.xywg.attendance.modular.system.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 项目表
 * </p>
 *
 * @author z
 * @since 2019-02-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Project extends Model<Project> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("NG_ID")
	private Long ngId;
    /**
     * 上级节点ID
     */
	@TableField("NG_PARENT_ID")
	private Long ngParentId;
    /**
     * 项目名称
     */
	@TableField("SZ_NAME")
	private String szName;
    /**
     * 类型：1=区域，2=项目
     */
	@TableField("NT_TYPE")
	private Double ntType;
    /**
     * 项目/区域代码
     */
	@TableField("SZ_CODE")
	private String szCode;
    /**
     * 状态，1=正常，2=停工，-1=删除
     */
	@TableField("NT_STATE")
	private Double ntState;
    /**
     * 开工时间
     */
	@TableField("DT_BEGIN")
	private Date dtBegin;
    /**
     * 结束时间
     */
	@TableField("DT_END")
	private Date dtEnd;
    /**
     * 顺序号
     */
	@TableField("NT_ORDER")
	private Integer ntOrder;
    /**
     * 项目地址
     */
	@TableField("SZ_PLACE")
	private String szPlace;
    /**
     * 类型：1房建，2市政
     */
	@TableField("NT_PROJECT_TYPE")
	private Integer ntProjectType;
    /**
     * 施工许可证
     */
	@TableField("SZ_LICENSE")
	private String szLicense;
    /**
     * 监督机构
     */
	@TableField("SZ_SUPERVISE_AGENCY")
	private String szSuperviseAgency;
    /**
     * 施工单位名称（企业ID）
     */
	@TableField("SZ_BUILDER")
	private String szBuilder;
    /**
     * 施工单位代码
     */
	@TableField("SZ_BUILDER_CODE")
	private String szBuilderCode;
    /**
     * 监理单位名称
     */
	@TableField("SZ_SUPERVISOR")
	private String szSupervisor;
    /**
     * 监理单位代码
     */
	@TableField("SZ_SUPERVISOR_CODE")
	private String szSupervisorCode;
    /**
     * 建设单位名称
     */
	@TableField("SZ_CONSTRUCTOR")
	private String szConstructor;
    /**
     * 设计单位名称
     */
	@TableField("SZ_DESIGNER")
	private String szDesigner;
    /**
     * 勘察单位名称
     */
	@TableField("SZ_SURVEYOR")
	private String szSurveyor;
    /**
     * 质量报监编号
     */
	@TableField("SZ_QUALITY_CODE")
	private String szQualityCode;
    /**
     * 安全报监编号
     */
	@TableField("SZ_SECURITY_CODE")
	private String szSecurityCode;
    /**
     * 建筑面积
     */
	@TableField("FT_BUILDING_SIZE")
	private Float ftBuildingSize;
    /**
     * 工程造价
     */
	@TableField("FT_BUILDING_COST")
	private Float ftBuildingCost;
    /**
     * 建筑层数
     */
	@TableField("NT_BUILDING_FLOOR")
	private Integer ntBuildingFloor;
    /**
     * 结构类型：砖混，框架，剪力墙，混合
     */
	@TableField("SZ_STRUCTURE")
	private String szStructure;
    /**
     * 备注
     */
	@TableField("TX_DESCRIPTION")
	private String txDescription;
    /**
     * 创建时间
     */
	@TableField("TS_CREATE")
	private Date tsCreate;
    /**
     * 是否正在进行
     */
	@TableField("BT_BUILDING")
	private Integer btBuilding;
    /**
     * 创建、修改时间
     */
	@TableField("TS_MODIFY")
	private Date tsModify;
    /**
     * 修改者IP
     */
	@TableField("SZ_MODIFY_IP")
	private String szModifyIp;
    /**
     * 创建、修改者ID
     */
	@TableField("NG_MODIFIER_ID")
	private Long ngModifierId;
    /**
     * 参保证明编号
     */
	@TableField("SOCIAL_PROVIE_CODE")
	private String socialProvieCode;
    /**
     * 合同编号
     */
	@TableField("CONTRACT_NO")
	private String contractNo;
    /**
     * 安全施工措施备案号
     */
	@TableField("PROTECT_NO")
	private String protectNo;
    /**
     * 中标通知书路径
     */
	@TableField("WIN_BIDDING_FILE")
	private String winBiddingFile;
    /**
     * 备案合同路径
     */
	@TableField("CONTRACT_FILE")
	private String contractFile;
    /**
     * 合同工期
     */
	@TableField("CONTRACT_BEGIN_DATE")
	private Date contractBeginDate;
	@TableField("CONTRACT_END_DATE")
	private Date contractEndDate;
    /**
     * 额度工期
     */
	@TableField("FIX_DAYS")
	private String fixDays;
    /**
     * 基础阶段
主体阶段
装饰阶段
配套施工阶段
竣工
     */
	@TableField("PROJECT_STATUS")
	private String projectStatus;
    /**
     * 开工
中止
终止
     */
	@TableField("PROJECT_PROGRESS")
	private String projectProgress;
    /**
     * 分包内容
     */
	@TableField("DISTRIBUTE_CONTENT")
	private String distributeContent;
    /**
     * 项目经理
     */
	@TableField("PROJECT_MANAGER")
	private String projectManager;
    /**
     * 联系方式
     */
	@TableField("PROJECT_MANAGER_PHONE")
	private String projectManagerPhone;
    /**
     * 建管处审核状态
     */
	@TableField("MANAGEMENT_AUDIT_STATUS")
	private String managementAuditStatus;
    /**
     * 建管处审核备注
     */
	@TableField("MANAGEMENT_AUDIT_MEMO")
	private String managementAuditMemo;
    /**
     * 工伤保险中心审核状态
     */
	@TableField("INJURY_AUDIT_STATUS")
	private String injuryAuditStatus;
    /**
     * 工伤保险中心审核备注
     */
	@TableField("INJURY_AUDIT_MEMO")
	private String injuryAuditMemo;
    /**
     * 项目位置
     */
	@TableField("PLACE_POINT")
	private String placePoint;
	@TableField("SOCIAL_PROVIE_DATE")
	private Date socialProvieDate;
    /**
     * 数据来源
     */
	@TableField("SOURCE")
	private String source;
    /**
     * 设备实施状态，1：未接入（商务手动修改），2：已接入（商务接入设备时修改状态），3：已安排实施（商务安排现场实施时修改状态），4：实施完成（现场实施完成时修改状态）
     */
	@TableField("PROJECT_IMPLEMENT_STATUS")
	private String projectImplementStatus;
    /**
     * 设备实施备注
     */
	@TableField("PROJECT_IMPLEMENT_MEMO")
	private String projectImplementMemo;
	@TableField("BANK_NAME")
	private String bankName;
	@TableField("PAY_ACCOUNT")
	private String payAccount;
    /**
     * 项目责任人
     */
	@TableField("PROJECT_RESPONSIBLE_PERSON")
	private String projectResponsiblePerson;
    /**
     * 项目责任人联系电话
     */
	@TableField("RESPONSIBLE_PERSON_PHONE")
	private String responsiblePersonPhone;
    /**
     * 项目编号
     */
	@TableField("PROJECT_CODE")
	private String projectCode;
    /**
     * 建设项目编码
     */
	@TableField("BUILDPROJECT_CODE")
	private String buildprojectCode;
    /**
     * 承包单位组织机构代码
     */
	@TableField("CONTRACTOR_ORGCODE")
	private String contractorOrgcode;
    /**
     * 承包单位编号
     */
	@TableField("GENERAL_CONTRACTORCODE")
	private String generalContractorcode;
    /**
     * 项目活动类型
     */
	@TableField("PROJECT_ACTIVITY_TYPE")
	private String projectActivityType;
	@TableField("PROJECT_DESCRIPTION")
	private String projectDescription;
    /**
     * 项目简介
     */
	@TableField("ISMAJAOR_PROJECT")
	private Long ismajaorProject;
    /**
     * 重点项目
     */
	@TableField("CONTRACTOR_TYPE")
	private Long contractorType;
    /**
     * 进场时间
     */
	@TableField("ENTRY_TIME")
	private Date entryTime;
    /**
     * 退场时间
     */
	@TableField("EXIT_TIME")
	private Date exitTime;
    /**
     * 发放工资的银行名称
     */
	@TableField("BANKNAME")
	private String bankname;
    /**
     * 发放工资的共管银行账号
     */
	@TableField("BANKNUMBER")
	private String banknumber;
    /**
     * 银行联号
     */
	@TableField("BANK_LINKNUMBER")
	private String bankLinknumber;
    /**
     * 工资发放模式
     */
	@TableField("PAY_MODE")
	private Long payMode;
    /**
     * 身份类型
     */
	@TableField("PM_IDCARD_TYPE")
	private Long pmIdcardType;
    /**
     * 身份证号码
     */
	@TableField("PM_IDCARD_NUMBER")
	private String pmIdcardNumber;
    /**
     * 群耀回传项目编号
     */
	@TableField("QY_PRJNUM")
	private String qyPrjnum;
    /**
     * 建设单位联系人
     */
	@TableField("BUILDE_CONTRACTOR")
	private String buildeContractor;
    /**
     * 建设单位联系人电话
     */
	@TableField("BUILDE_CONTRACTOR_PHONE")
	private String buildeContractorPhone;
    /**
     * 企业分管领导
     */
	@TableField("LEADER")
	private String leader;
    /**
     * 企业分管领导电话
     */
	@TableField("LEADER_PHONE")
	private String leaderPhone;
    /**
     * 项目总监
     */
	@TableField("MANGER")
	private String manger;
    /**
     * 项目总监联系电话
     */
	@TableField("MANGER_PHONE")
	private String mangerPhone;
    /**
     * 是否申报文明工地
     */
	@TableField("CIVILIZED_PROJECT")
	private Long civilizedProject;
    /**
     * 是否申报优质工程
     */
	@TableField("GOOD_PROJECT")
	private Long goodProject;
    /**
     * 是否政府项目
     */
	@TableField("GOVERNMENT_PROJECT")
	private Long governmentProject;
    /**
     * 考勤设置的开始时间
     */
	@TableField("ATTEND_START_TIME")
	private Date attendStartTime;
    /**
     * 考勤设置的结束时间
     */
	@TableField("ATTEND_END_TIME")
	private Date attendEndTime;


	@Override
	protected Serializable pkVal() {
		return this.ngId;
	}

}
