package com.xywg.admin.modular.project.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 培训人员记录表
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-14
 */
@TableName("buss_project_train_record")
public class ProjectTrainRecord extends Model<ProjectTrainRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 培训id
     */
    @TableField("train_id")
    private Long trainId;
    /**
     * 证件类型
     */
    @TableField("id_card_type")
    private Long idCardType;
    /**
     * 证件编号
     */
    @TableField("id_card_number")
    private String idCardNumber;


    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public Long getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(Long idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }



    @Override
    public String toString() {
        return "ProjectTrainRecord{" +
        "trainId=" + trainId +
        ", idCardType=" + idCardType +
        ", idCardNumber=" + idCardNumber +
        "}";
    }

    @Override
    protected Serializable pkVal() {
        return 0;
    }
}
