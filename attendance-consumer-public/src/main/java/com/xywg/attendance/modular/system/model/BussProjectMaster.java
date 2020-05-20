package com.xywg.attendance.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
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
 * 项目基础信息
 * </p>
 *
 * @author z
 * @since 2019-05-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("buss_project_master")
public class BussProjectMaster extends Model<BussProjectMaster> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 项目编号
     */
	@TableField("project_code")
	private String projectCode;
    /**
     * 合同编号
     */
	@TableField("contract_no")
	private String contractNo;
    /**
     * 建设项目编码
     */
	@TableField("build_project_code")
	private String buildProjectCode;
    /**
     * 承包单位组织机构代码
     */
	@TableField("contractor_org_code")
	private String contractorOrgCode;
    /**
     * 承包单位编号
     */
	@TableField("general_contractor_code")
	private String generalContractorCode;
    /**
     * 承包单位名称
     */
	@TableField("general_contractor_name")
	private String generalContractorName;
    /**
     * 项目名称
     */
	@TableField("project_name")
	private String projectName;
    /**
     * 项目活动类型
     */
	@TableField("project_activity_type")
	private String projectActivityType;
    /**
     * 项目简介
     */
	@TableField("project_description")
	private String projectDescription;
    /**
     * 项目分类
     */
	@TableField("project_category")
	private String projectCategory;
    /**
     * 重点项目
     */
	@TableField("is_major_project")
	private Integer isMajorProject;
    /**
     * 建设单位名称
     */
	@TableField("owner_name")
	private String ownerName;
    /**
     * 建设单位组织机构代码或统一社会信用代码
     */
	@TableField("build_corporation_code")
	private String buildCorporationCode;
    /**
     * 建设单位联系人
     */
	@TableField("build_contacts")
	private String buildContacts;
    /**
     * 建设单位联系人电话
     */
	@TableField("build_phone")
	private String buildPhone;
    /**
     * 企业分管领导
     */
	@TableField("contractor_leader")
	private String contractorLeader;
    /**
     * 企业分管领导电话
     */
	@TableField("contractor_leader_phone")
	private String contractorLeaderPhone;
    /**
     * 项目总监
     */
	@TableField("project_director")
	private String projectDirector;
    /**
     * 项目总监电话
     */
	@TableField("project_director_phone")
	private String projectDirectorPhone;
    /**
     * 是否文明工地 0否 1是
     */
	@TableField("is_civilization")
	private Integer isCivilization;
    /**
     * 是否申报优质工程 0否1是
     */
	@TableField("is_high_grade")
	private Integer isHighGrade;
    /**
     * 是否政府工程 0否 1是
     */
	@TableField("is_government")
	private Integer isGovernment;
    /**
     * 责任人联系电话
     */
	@TableField("person_liable")
	private String personLiable;
    /**
     * 施工许可证编号
     */
	@TableField("builder_licence_num")
	private String builderLicenceNum;
    /**
     * 项目行政区划,参见地区字典数据
     */
	@TableField("area_code")
	private String areaCode;
    /**
     * 项目地址
     */
	private String address;
    /**
     * 承包合同额 单位 万元
     */
	@TableField("total_contract_amt")
	private BigDecimal totalContractAmt;
    /**
     * 建筑面积 单位 平方米
     */
	@TableField("building_area")
	private BigDecimal buildingArea;
    /**
     * 开工日期 精确到天，格式：yyyy-mm-dd
     */
	@TableField("start_date")
	private Date startDate;
    /**
     * 竣工日期 精确到天，格式：yyyy-mm-dd
     */
	@TableField("complete_date")
	private Date completeDate;
    /**
     * 项目来源
     */
	@TableField("project_source")
	private String projectSource;
    /**
     * 状态 参见项目状态字典表
     */
	@TableField("project_status")
	private String projectStatus;
    /**
     * 经度
     */
	private BigDecimal lng;
    /**
     * 纬度
     */
	private BigDecimal lat;
    /**
     * 距离
     */
	private Double radius;
    /**
     * 考勤方式 参见字典考勤类型
     */
	@TableField("device_type")
	private Integer deviceType;
    /**
     * 项目计算工时
     */
	@TableField("work_time")
	private Integer workTime;
    /**
     * 添加人
     */
	@TableField("create_date")
	private Date createDate;
    /**
     * 添加时间
     */
	@TableField("create_user")
	private String createUser;
    /**
     * 修改时间
     */
	@TableField("update_date")
	private Date updateDate;
    /**
     * 修改人
     */
	@TableField("update_user")
	private String updateUser;
    /**
     * 是否可用 1：是 0：否
     */
	private Integer status;
    /**
     * 是否删除 1：是 0:否
     */
	@TableField("is_del")
	private Integer isDel;
    /**
     * 是否同步  1：是 0:否
     */
	@TableField("is_synchro")
	private Integer isSynchro;
    /**
     * 是否需要水印 1 是 0 否
     */
	@TableField("is_watermark")
	private Integer isWatermark;
    /**
     * 是否拉取实名制数据 1是 0否
     */
	@TableField("is_receive")
	private Integer isReceive;
    /**
     * 虚拟考勤机id
     */
	@TableField("virtual_id")
	private Long virtualId;
    /**
     * 是否修改 1：是  0：否
     */
	@TableField("is_update")
	private Integer isUpdate;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
