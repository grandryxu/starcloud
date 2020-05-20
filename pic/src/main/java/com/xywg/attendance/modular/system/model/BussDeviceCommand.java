package com.xywg.attendance.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 设备下发命令
 * </p>
 *
 * @author z
 * @since 2019-03-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("buss_device_command")
public class BussDeviceCommand extends Model<BussDeviceCommand> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;

	@TableField(value="uuid")
    private String uuid;

    /**
     * 设备序列号
     */
	@TableField("device_sn")
	private String deviceSn;
    /**
     * 证件类型
     */
	@TableField("id_card_type")
	private Integer idCardType;
    /**
     * 证件编号
     */
	@TableField("id_card_number")
	private String idCardNumber;
    /**
     * 升级设备时的文件ID
     */
	@TableField("file_id")
	private Long fileId;
    /**
     * 命令类型1.同步设备时间2重启设备3获取设备信息4升级汉王内核5清空考勤记录6删除所有人员7手动下发人员信息8移除个别人员
     */
	private Long type;
    /**
     * 状态，0=未执行，1=成功，-1=失败，999=取消
     */
	private Long state;
    /**
     * 创建时间
     */
	private Date createDate;
    /**
     * 执行时间
     */
	private Date processDate;
    /**
     * 描述
     */
	private String description;
    /**
     * 修改人IP
     */
	@TableField("modify_ip")
	private String modifyIp;
    /**
     * 管理员的ID
     */
	@TableField("modify_id")
	private Long modifyId;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
