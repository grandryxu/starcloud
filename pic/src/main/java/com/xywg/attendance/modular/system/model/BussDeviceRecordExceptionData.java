package com.xywg.attendance.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.sql.Blob;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 异常考勤记录表
 * </p>
 *
 * @author z
 * @since 2019-03-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("buss_device_record_exception_data")
public class BussDeviceRecordExceptionData extends Model<BussDeviceRecordExceptionData> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
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
     * 考勤机
     */
	@TableField("device_sn")
	private String deviceSn;
	@TableField("id_card_type")
	private Integer idCardType;
	@TableField("id_card_number")
	private String idCardNumber;
    /**
     * 身份证
     */
	@TableField("Id_card")
	private String IdCard;
    /**
     * 考勤机
     */
	@TableField("device_id")
	private Long deviceId;
    /**
     * 安全帽id
     */
	@TableField("sh_id")
	private Long shId;
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
	private Double lng;
    /**
     * 纬度
     */
	private Double lat;
    /**
     * 来源  参见字典考勤类型
     */
	private Integer source;
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
     * 是否可用 1：是 -1：删除
     */
	private Integer status;
    /**
     * 备注
     */
	private String remark;
    /**
     * 异常类型：1=注册人不在项目中，2=考勤人不在项目中，3=禁用时考勤数据
     */
	private String exceptionType;
    /**
     * 人脸模板
     */
	@TableField("tx_face")
	private Blob txFace;
	@TableField("alg_version")
	private String algVersion;
	private String personDataType;
    /**
     * 考勤类型，3 上班 4下班
     */
	@TableField("device_type")
	private Integer deviceType;
	@TableField("user_name")
	private String userName;
    /**
     * 是否删除 1：是 0:否
     */
	@TableField("is_del")
	private Integer isDel;



    @Override
	protected Serializable pkVal() {
		return this.id;
	}

}
