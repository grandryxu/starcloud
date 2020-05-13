package com.xywg.admin.modular.company.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 企业工人表
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-28
 */
@TableName("buss_contractor_worker")
public class ContractorWorker extends Model<ContractorWorker> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
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
     * 工人名称
     */
    @TableField("work_name")
    private String workName;
    /**
     * 所属企业组织机构代码
     */
    @TableField("organization_code")
    private String organizationCode;
    /**
     * 企业名称
     */
    @TableField("contractor_name")
    private String contractorName;

    @TableField(exist = false)
    private Integer isDel;

    public ContractorWorker() {
    }

    public ContractorWorker(Integer idCardType, String idCardNumber, String workName, String organizationCode) {
        this.idCardType = idCardType;
        this.idCardNumber = idCardNumber;
        this.workName = workName;
        this.organizationCode = organizationCode;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ContractorWorker{" +
        "id=" + id +
        ", idCardType=" + idCardType +
        ", idCardNumber=" + idCardNumber +
        ", workName=" + workName +
        ", organizationCode=" + organizationCode +
        ", contractorName=" + contractorName +
        "}";
    }
}
