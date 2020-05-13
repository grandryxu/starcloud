package com.xywg.admin.modular.device.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * <p>
 * 二维码设备维护
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-29
 */
@Data
@TableName("buss_device_qr")
public class DeviceQr extends Model<DeviceQr> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 项目id
     */
    @TableField("project_code")
    private String projectCode;
    /**
     * 设备名称
     */
    private String name;
    /**
     * 设备序列号（唯一标识）
     */
    private String sn;
    /**
     * 设备状态：0：离线  1：在线 -1：删除
     */
    private Integer state;
    /**
     * 版本
     */
    private String version;
    /**
     * 最后通讯时间
     */
    @TableField("talk_time")
    private Date talkTime;
    /**
     * 备注
     */
    private String remark;
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
     * 软件版本
     */
    @TableField(exist = false)
    private String softVersion;
    /**
     * 升级状态
     */
    @TableField(exist = false)
    private Integer softStatus;
    /**
     * 是否删除 1：是 0:否
     */
    @TableField("is_del")
    @TableLogic
    private Integer isDel;




    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DeviceQr{" +
        "id=" + id +
        ", projectCode=" + projectCode +
        ", name=" + name +
        ", sn=" + sn +
        ", state=" + state +
        ", version=" + version +
        ", talkTime=" + talkTime +
        ", remark=" + remark +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", status=" + status +
        ", isDel=" + isDel +
        "}";
    }
}
