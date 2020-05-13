package com.xywg.admin.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 版本管理
 * </p>
 *
 * @author cw123
 * @since 2018-08-21
 */
@TableName("sys_version")
public class Version extends Model<Version> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 1.IOS 2安卓
     */
    @TableField("phone_type")
    private Integer phoneType;
    /**
     * 1.企业端 2.工人端
     */
    private Integer kind;
    /**
     * 下载路径
     */
    private String url;
    /**
     * 版本
     */
    private String version;
    /**
     * 是否强制更新 1强制i更新 2不强制更新
     */
    private Integer flag;
    /**
     * 备注
     */
    private String remark;
    /**
     * 新增时间
     */
    @TableField("create_date")
    private Date createDate;
    /**
     * 添加时间
     */
    @TableField("create_user")
    private String createUser;
    /**
     * 新增时间
     */
    @TableField("update_date")
    private Date updateDate;
    /**
     * 添加时间
     */
    @TableField("update_user")
    private String updateUser;
    /**
     * 1.IOS 2安卓
     */
    @TableField("version_code")
    private Integer versionCode;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(Integer phoneType) {
        this.phoneType = phoneType;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Version{" +
        "id=" + id +
        ", phoneType=" + phoneType +
        ", kind=" + kind +
        ", url=" + url +
        ", version=" + version +
        ", flag=" + flag +
        ", remark=" + remark +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        "}";
    }
}
