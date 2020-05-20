package com.xywg.attendance.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
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
 * 人脸模型表
 * </p>
 *
 * @author z
 * @since 2019-03-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("buss_person_data")
public class BussPersonData extends Model<BussPersonData> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 上传的设备ID
     */
	@TableField("device_sn")
	private String deviceSn;
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
     * 用户姓名(考勤机部分)
     */
	@TableField("user_Name")
	private String userName;
    /**
     * 用户id用于百度人脸比对
     */
	private String uid;
    /**
     * 算法版本
     */
	@TableField("alg_version")
	private String algVersion;
    /**
     * 人脸模板
     */
	@TableField("tx_face")
	private String txFace;
    /**
     * 创建日期
     */
	private Date createDate;
    /**
     * 0考勤机1手机端
     */
	private Integer type;
    /**
     * 版本号
     */
	@TableField("ocv_version")
	private String ocvVersion;
    /**
     * 人脸模板
     */
	@TableField("ocv_face")
	private String ocvFace;
    /**
     * 保存图片路径
     */
	@TableField("img_path")
	private String imgPath;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
