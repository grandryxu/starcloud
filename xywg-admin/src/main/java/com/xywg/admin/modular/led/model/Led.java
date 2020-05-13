package com.xywg.admin.modular.led.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * tv
 * </p>
 *
 * @author cw123
 * @since 2018-09-20
 */
@TableName("buss_led")
public class Led extends Model<Led> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * LED屏Imei编号
     */
    private String imei;
    /**
     * 28：左移，1：不移
     */
    @TableField("display_type")
    private String displayType;
    /**
     * 屏上信息移动速度
     */
    @TableField("display_speed")
    private String displaySpeed;
    /**
     * 停留时间
     */
    @TableField("stay_time")
    private String stayTime;
    /**
     * 屏宽
     */
    @TableField("screen_width")
    private String screenWidth;
    /**
     * 屏高
     */
    @TableField("screen_height")
    private String screenHeight;
    /**
     * 1：红色
     */
    @TableField("font_color")
    private String fontColor;
    /**
     * 16：16点阵字库
     */
    @TableField("font_size")
    private String fontSize;
    /**
     * 所属项目
     */
    @TableField("project_code")
    private String projectCode;
    /**
     * 终端名称
     */
    @TableField("client_name")
    private String clientName;
    /**
     * 屏幕显示的文字内容
     */
    @TableField("display_text")
    private String displayText;
    /**
     * 状态 ，1：在用，-1：失效
     */
    private Integer flag;
    /**
     * 1：开启，0：关闭，默认1
     */
    private Integer status;
    /**
     * 奇偶校验flag
     */
    @TableField("jo_flag")
    private Integer joFlag;


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

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public String getDisplaySpeed() {
        return displaySpeed;
    }

    public void setDisplaySpeed(String displaySpeed) {
        this.displaySpeed = displaySpeed;
    }

    public String getStayTime() {
        return stayTime;
    }

    public void setStayTime(String stayTime) {
        this.stayTime = stayTime;
    }

    public String getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(String screenWidth) {
        this.screenWidth = screenWidth;
    }

    public String getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(String screenHeight) {
        this.screenHeight = screenHeight;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getJoFlag() {
        return joFlag;
    }

    public void setJoFlag(Integer joFlag) {
        this.joFlag = joFlag;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Led{" +
        "id=" + id +
        ", imei=" + imei +
        ", displayType=" + displayType +
        ", displaySpeed=" + displaySpeed +
        ", stayTime=" + stayTime +
        ", screenWidth=" + screenWidth +
        ", screenHeight=" + screenHeight +
        ", fontColor=" + fontColor +
        ", fontSize=" + fontSize +
        ", projectCode=" + projectCode +
        ", clientName=" + clientName +
        ", displayText=" + displayText +
        ", flag=" + flag +
        ", status=" + status +
        ", joFlag=" + joFlag +
        "}";
    }
}
