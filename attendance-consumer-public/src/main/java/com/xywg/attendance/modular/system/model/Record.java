package com.xywg.attendance.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * <p>
 * 考勤记录表
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-30
 */
@TableName("buss_device_record")
@Data
public class Record extends Model<Record> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * 所属企业组织机构代码
	 */
	@TableField("organization_code")
	private String organizationCode;
	/**
	 * 项目id
	 */
	@TableField("project_code")
	private String projectCode;
	/**
	 * 班组编号 外部系统与平台对接创建班组后，由系统返回该编号
	 */
	@TableField("team_sys_no")
	private Integer teamSysNo;

	@TableField("worker_name")
	private String workerName;

	/**
	 * 身份证类型
	 */
	@TableField("id_card_type")
	private Integer idCardType;
	/**
	 * 身份证号
	 */
	@TableField("id_card_number")
	private String idCardNumber;
	/**
	 * 考勤机
	 */
	@TableField("device_sn")
	private String deviceSn;
	/**
	 * 安全帽id
	 */
	@TableField("sh_imei")
	private String shImei;
	/**
	 * 代考勤人员字段
	 */
	@TableField("record_user_id")
	private Long recordUserId;
	/**
	 * 考勤时间
	 */
	private String time;



	/**
	 * 类型0：补考勤 1：正常
	 */
	private Integer type;
	/**
	 * 照片地址
	 */
	private String photo;
	/**
	 * 经度
	 */
	private BigDecimal lng;
	/**
	 * 纬度
	 */
	private BigDecimal lat;
	/**
	 * 考勤地址
	 */
	private String address;
	/**
	 * 来源1：手环 2：安全帽 3：考勤机 4:手机考勤 5：二维码
	 */
	private Integer source;
	/**
	 * 是否有效 0无效 1有效
	 */
	@TableField("is_valid")
	private Integer isValid;
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
	 * 备注
	 */
	private String remark;
	/**
	 * 地图
	 */
	private String map;
	/**
	 * 缩略图
	 */
	@TableField("icon_photo")
	private String iconPhoto;
	/**
	 * 考勤类型，3 上班 4下班
	 */
	@TableField("device_type")
	private Integer deviceType;
	/**
	 * 是否统计（0：否；1：是）
	 */
	@TableField("is_deal")
	private String isDeal;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
