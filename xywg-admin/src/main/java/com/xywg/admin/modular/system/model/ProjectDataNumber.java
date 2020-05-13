package com.xywg.admin.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

@TableName("sys_project_data_number")
public class ProjectDataNumber extends Model<ProjectDataNumber> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 项目名称
     */
    @TableField("project_name")
    private String projectName;
    /**
     * 数据指纹
     */
    @TableField("data_number")
    private String dataNumber;
    /**
     * 区域
     */
    private Long area;
    /**
     * 添加人
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 添加时间
     */
    @TableField("create_user")
    private String createUser;
    /**
     * 修改时间
     */
    @TableField("modity_time")
    private Date modityTime;
    /**
     * 修改人
     */
    @TableField("modifyer")
    private String modifyer;
    /**
     * 状态 -1为删除  1正常
     */
    @TableField("nt_state")
    private int ntState;
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDataNumber() {
        return dataNumber;
    }

    public void setDataNumber(String dataNumber) {
        this.dataNumber = dataNumber;
    }

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getModityTime() {
        return modityTime;
    }

    public void setModityTime(Date modityTime) {
        this.modityTime = modityTime;
    }

    public String getModifyer() {
        return modifyer;
    }

    public void setModifyer(String modifyer) {
        this.modifyer = modifyer;
    }

    public int getNtState() {
        return ntState;
    }

    public void setNtState(int ntState) {
        this.ntState = ntState;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ProjectDataNumber{" +
                "id=" + id +
                ", projectNameT=" + projectName +
                ", dataNumber=" + dataNumber +
                ", area=" + area +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                ", modifyer=" + modifyer +
                ", modityTime=" + modityTime +
                ", ntState=" + ntState +
                ", remark=" + remark +
                "}";
    }
}
