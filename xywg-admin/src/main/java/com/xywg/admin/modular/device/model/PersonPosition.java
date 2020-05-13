package com.xywg.admin.modular.device.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.xywg.admin.modular.device.model.dto.WorkerDto;
import com.xywg.admin.modular.project.model.ProjectMaster;

/**
 * <p>
 * 考勤帽定位
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-19
 */
@TableName("buss_person_position")
public class PersonPosition extends Model<PersonPosition> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * imei编号
     */
    private String imei;
    /**
     * 身份证类型
     */
    @TableField("id_card_type")
    private Integer idCardType;
    /**
     * 身份证号
     */
    @TableField("id_card_number")
    private String idCardNumber;
    /**
     * 经度
     */
    private BigDecimal lng;
    /**
     * 纬度
     */
    private BigDecimal lat;
    /**
     * 考勤时间
     */
    private Date time;
    /**
     * 是否圈内:圈内1,圈外0
     */
    private Integer range;
    /**
     * 备注
     */
    private String remark;
    /**
     * 百度经度
     */
    @TableField("bd_lng")
    private BigDecimal bdLng;
    /**
     * 百度纬度
     */
    @TableField("bd_lat")
    private BigDecimal bdLat;
    
	/**
	 * 人员信息
	 */
	private WorkerDto personel;
	
	/**
	 * 项目信息
	 */
	private ProjectMaster project;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
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

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getBdLng() {
        return bdLng;
    }

    public void setBdLng(BigDecimal bdLng) {
        this.bdLng = bdLng;
    }

    public BigDecimal getBdLat() {
        return bdLat;
    }

    public void setBdLat(BigDecimal bdLat) {
        this.bdLat = bdLat;
    }
    
    public WorkerDto getPersonel() {
        return personel;
    }

    public void setPersonel(WorkerDto personel) {
        this.personel = personel;
    }

    public ProjectMaster getProject() {
        return project;
    }

    public void setProject(ProjectMaster project) {
        this.project = project;
    }
    
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonPosition{" +
        "id=" + id +
        ", imei=" + imei +
        ", idCardType=" + idCardType +
        ", idCardNumber=" + idCardNumber +
        ", lng=" + lng +
        ", lat=" + lat +
        ", time=" + time +
        ", range=" + range +
        ", remark=" + remark +
        ", bdLng=" + bdLng +
        ", bdLat=" + bdLat +
        "}";
    }
}
