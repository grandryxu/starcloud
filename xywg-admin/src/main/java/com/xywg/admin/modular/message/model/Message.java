package com.xywg.admin.modular.message.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-29
 */

@TableName("buss_message")
public class Message extends Model<Message> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 项目id
     */
    @TableField("project_code")
    private String projectCode;
    /**
     * 班组编号 外部系统与平台对接创建班组后，由系统返回该编号
     */
    @TableField("team_sys_no")
    private Integer teamSysNo;
    /**
     * 发送人姓名
     */
    @TableField("send_id")
    private Long sendId;
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
    /**
     * 接收人姓名
     */
    @TableField("receive_id" )
    private Long receiveId;
    /**
     * 消息类型  0 加入班组 1 是否同意  2 邀请加入项目   
     */
    private Integer type;
    /**
     * 内容
     */
    private String content;
    /**
     * 发送时间
     */
    @TableField("send_time")
    private Date sendTime;
    /**
     * 是否读取
     */
    @TableField("is_read")
    private Integer isRead;
    /**
     * 读取时间
     */
    @TableField("read_time")
    private Integer readTime;
    
    /**
     * 登录设备
     */
    private String equipment;
    /**
     * 1 管理端 2 工人端
     */
    private Integer kind;

    public Message(){}
    public Message(String projectCode, Integer teamSysNo, Long idCardType, String idCardNumber,Long sendId,Long receiveId, int type, int isRead, int kind,String content){
        this.projectCode=projectCode;
        this.teamSysNo=teamSysNo;
        this.idCardType=idCardType;
        this.idCardNumber=idCardNumber;
        this.sendId=sendId;
        this.receiveId=receiveId;
        this.type=type;
        this.isRead=isRead;
        this.kind=kind;
        this.content=content;
    }


    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
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

    public Integer getTeamSysNo() {
        return teamSysNo;
    }

    public void setTeamSysNo(Integer teamSysNo) {
        this.teamSysNo = teamSysNo;
    }

    public Long getSendId() {
        return sendId;
    }

    public void setSendId(Long sendId) {
        this.sendId = sendId;
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

    public Long getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Long receiveId) {
        this.receiveId = receiveId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getReadTime() {
        return readTime;
    }

    public void setReadTime(Integer readTime) {
        this.readTime = readTime;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Message{" +
        "id=" + id +
        ", projectCode=" + projectCode +
        ", teamSysNo=" + teamSysNo +
        ", sendId=" + sendId +
        ", idCardType=" + idCardType +
        ", idCardNumber=" + idCardNumber +
        ", receiveId=" + receiveId +
        ", type=" + type +
        ", content=" + content +
        ", sendTime=" + sendTime +
        ", isRead=" + isRead +
        ", readTime=" + readTime +
        ", kind=" + kind +
        "}";
    }
}
