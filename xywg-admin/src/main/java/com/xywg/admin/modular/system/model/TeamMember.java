package com.xywg.admin.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 班组成员
 * </p>
 *
 * @author cw123
 * @since 2019-01-29
 */
@TableName("buss_team_member")
public class TeamMember extends Model<TeamMember> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 班组编号
     */
    @TableField("team_sys_no")
    private Integer teamSysNo;
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
     * 是否为班组长 0=班组长,
1=组员,
     */
    @TableField("team_worker_type")
    private Integer teamWorkerType;
    /**
     * 是否删除 1：是 0:否
     */
    @TableField("is_del")
    private Integer isDel;
    /**
     * 是否签字 1：是  0：否
     */
    @TableField("is_sign")
    private Integer isSign;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTeamSysNo() {
        return teamSysNo;
    }

    public void setTeamSysNo(Integer teamSysNo) {
        this.teamSysNo = teamSysNo;
    }

    public Integer getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(Integer idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public Integer getTeamWorkerType() {
        return teamWorkerType;
    }

    public void setTeamWorkerType(Integer teamWorkerType) {
        this.teamWorkerType = teamWorkerType;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getIsSign() {
        return isSign;
    }

    public void setIsSign(Integer isSign) {
        this.isSign = isSign;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TeamMember{" +
        "id=" + id +
        ", teamSysNo=" + teamSysNo +
        ", idCardType=" + idCardType +
        ", idCardNumber=" + idCardNumber +
        ", teamWorkerType=" + teamWorkerType +
        ", isDel=" + isDel +
        ", isSign=" + isSign +
        "}";
    }

    public TeamMember() {
    }

    public TeamMember(Integer teamSysNo, Integer idCardType, String idCardNumber) {
        this.teamSysNo = teamSysNo;
        this.idCardType = idCardType;
        this.idCardNumber = idCardNumber;
    }
}
