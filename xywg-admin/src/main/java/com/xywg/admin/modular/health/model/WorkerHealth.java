package com.xywg.admin.modular.health.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 健康信息
 * </p>
 *
 * @author wangcw123
 * @since 2018-08-17
 */
@TableName("buss_worker_health")
public class WorkerHealth extends Model<WorkerHealth> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 证件类型
     */
    @TableField("id_card_type")
    private Integer idCardType;
    /**
     * 证件号
     */
    @TableField("id_card_number")
    private String idCardNumber;
    /**
     * 心率
     */
    @TableField("heart_rate")
    private Integer heartRate;
    /**
     * 血氧
     */
    @TableField("blood_oxygen")
    private Integer bloodOxygen;
    /**
     * 体温
     */
    private BigDecimal temperature;
    /**
     * 姿态
     */
    private String attitude;
    /**
     * 采集时间
     */
    @TableField("create_date")
    private Date createDate;


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

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public Integer getBloodOxygen() {
        return bloodOxygen;
    }

    public void setBloodOxygen(Integer bloodOxygen) {
        this.bloodOxygen = bloodOxygen;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public String getAttitude() {
        return attitude;
    }

    public void setAttitude(String attitude) {
        this.attitude = attitude;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "WorkerHealth{" +
        "id=" + id +
        ", idCardType=" + idCardType +
        ", idCardNumber=" + idCardNumber +
        ", heartRate=" + heartRate +
        ", bloodOxygen=" + bloodOxygen +
        ", temperature=" + temperature +
        ", attitude=" + attitude +
        ", createDate=" + createDate +
        "}";
    }
}
