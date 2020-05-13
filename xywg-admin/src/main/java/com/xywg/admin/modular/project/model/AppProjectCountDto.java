package com.xywg.admin.modular.project.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 项目基础信息
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-23
 */
@TableName("buss_project_master")
public class AppProjectCountDto extends Model<AppProjectCountDto> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 项目编号
     */
    @TableField("project_code")
    private String projectCode;
    /**
     * 承包单位组织机构代码
     */
    @TableField("contractor_org_code")
    private String contractorOrgCode;
    /**
     * 建设单位组织机构代码或统一社会信用代码
     */
    @TableField("build_corporation_code")
    private String buildCorporationCode;


    /**
     * 进场人数
     */

    private Integer entryCount;
    /**
     * 班组数
     */
    private Integer teamCount;

    /**
     * 18岁以下人数
     */
    private Integer underEightteenCount;
    /**
     * 18岁-30岁人数
     */
    private Integer eightteenCount;
    /**
     * 31岁40岁人数
     */
    private Integer thirtyCount;
    /**
     * 41岁-50岁人数
     */
    private Integer fortyCount;
    /**
     * 51岁-60岁人数
     */
    private Integer fiftyCount;
    /**
     * 61岁及以上人数
     */
    private Integer sixtyCount;
    /**
     * 男性人数
     */
    private Integer  maleCount;

    /**
     * 女性人数
     */
    private Integer   femaleCount;
    /**
     * 总人数
     */
    private Integer   totalCount;
    /**
     * 工种名称
     */
    private String   workKind;

    public Integer getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(Integer teamCount) {
        this.teamCount = teamCount;
    }

    public Integer getEntryCount() {
        return entryCount;
    }

    public void setEntryCount(Integer entryCount) {
        this.entryCount = entryCount;
    }

    public Integer getMaleCount() {
        return maleCount;
    }

    public void setMaleCount(Integer maleCount) {
        this.maleCount = maleCount;
    }

    public Integer getFemaleCount() {
        return femaleCount;
    }

    public void setFemaleCount(Integer femaleCount) {
        this.femaleCount = femaleCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getWorkKind() {
        return workKind;
    }

    public void setWorkKind(String workKind) {
        this.workKind = workKind;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 个数
     */
    private Integer count;

    public Integer getUnderEightteenCount() {
        return underEightteenCount;
    }

    public void setUnderEightteenCount(Integer underEightteenCount) {
        this.underEightteenCount = underEightteenCount;
    }

    public Integer getEightteenCount() {
        return eightteenCount;
    }

    public void setEightteenCount(Integer eightteenCount) {
        this.eightteenCount = eightteenCount;
    }

    public Integer getThirtyCount() {
        return thirtyCount;
    }

    public void setThirtyCount(Integer thirtyCount) {
        this.thirtyCount = thirtyCount;
    }

    public Integer getFortyCount() {
        return fortyCount;
    }

    public void setFortyCount(Integer fortyCount) {
        this.fortyCount = fortyCount;
    }

    public Integer getFiftyCount() {
        return fiftyCount;
    }

    public void setFiftyCount(Integer fiftyCount) {
        this.fiftyCount = fiftyCount;
    }

    public Integer getSixtyCount() {
        return sixtyCount;
    }

    public void setSixtyCount(Integer sixtyCount) {
        this.sixtyCount = sixtyCount;
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


    public String getContractorOrgCode() {
        return contractorOrgCode;
    }

    public void setContractorOrgCode(String contractorOrgCode) {
        this.contractorOrgCode = contractorOrgCode;
    }


    public String getBuildCorporationCode() {
        return buildCorporationCode;
    }

    public void setBuildCorporationCode(String buildCorporationCode) {
        this.buildCorporationCode = buildCorporationCode;
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
