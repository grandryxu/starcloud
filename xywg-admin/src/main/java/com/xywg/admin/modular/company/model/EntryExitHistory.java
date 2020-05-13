package com.xywg.admin.modular.company.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 进退场记录表
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-28
 */
@TableName("buss_entry_exit_history")
public class EntryExitHistory extends Model<EntryExitHistory> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 项目编号
     */
    @TableField("project_code")
    private String projectCode;
    /**
     * 所属企业组织机构代码
     */
    @TableField("organization_code")
    private String organizationCode;
    /**
     * 证件类型 参见人员证件类型字典表
     */
    @TableField("id_card_type")
    private String idCardType;
    /**
     * 证件编号
     */
    @TableField("id_card_number")
    private String idCardNumber;
    /**
     * 时间
     */
    private Date date;
    /**
     * 类型 0=进场,
1=退场
     */
    private Integer type;
    /**
     * 是否删除 1：是 0:否
     */
    @TableField("is_del")
    @TableLogic
    private Integer isDel;

    public EntryExitHistory(){};

    public EntryExitHistory(String projectCode, String organizationCode, String idCardType, String idCardNumber) {
        this.projectCode=projectCode;
        this.organizationCode=organizationCode;
        this.idCardType=idCardType;
        this.idCardNumber=idCardNumber;
    }
    public EntryExitHistory(String projectCode, String organizationCode, String idCardType, String idCardNumber, int type) {
        this.projectCode=projectCode;
        this.organizationCode=organizationCode;
        this.idCardType=idCardType;
        this.idCardNumber=idCardNumber;
        this.type=type;
    }



    public EntryExitHistory(String projectCode, String organizationCode, String idCardType, String idCardNumber, Date date, Integer type) {
        this.projectCode = projectCode;
        this.organizationCode = organizationCode;
        this.idCardType = idCardType;
        this.idCardNumber = idCardNumber;
        this.date = date;
        this.type = type;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "EntryExitHistory{" +
        "id=" + id +
        ", projectCode=" + projectCode +
        ", organizationCode=" + organizationCode +
        ", idCardType=" + idCardType +
        ", idCardNumber=" + idCardNumber +
        ", date=" + date +
        ", type=" + type +
        ", isDel=" + isDel +
        "}";
    }
}
