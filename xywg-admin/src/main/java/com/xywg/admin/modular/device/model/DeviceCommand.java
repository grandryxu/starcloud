package com.xywg.admin.modular.device.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 设备下发命令
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-12
 */
@TableName("buss_device_command")
public class DeviceCommand extends Model<DeviceCommand> {
    private static Logger log = LoggerFactory.getLogger(DeviceCommand.class);
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String uuid;
    /**
     * 设备序列号
     */
    @TableField("device_sn")
    private String deviceSn;
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
     * 升级设备时的文件ID
     */
    @TableField("file_id")
    private Long fileId;
    /**
     * 命令类型1.同步设备时间2重启设备3获取设备信息4升级汉王内核5清空考勤记录6删除所有人员7手动下发人员信息8移除个别人员
     */
    @TableField("type")
    private Integer type;
    /**
     * 状态，0=未执行，1=成功，-1=失败，999=取消
     */
    private Long state;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 执行时间
     */
    private Date processDate;
    /**
     * 描述
     */
    private String description;
    /**
     * 修改人IP
     */
    @TableField("modify_ip")
    private String modifyIp;
    /**
     * 管理员的ID
     */
    @TableField("modify_id")
    private Long modifyId;

    @TableField(exist = false)
    private String workerName;
    @TableField(exist = false)
    private String loginName;

    private List<String> deviceSns;

    /**
     * 工人id集合
     */
    private List<String> userIds;
    /**
     * 员工
     */
    private WorkerMaster personnel;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户信息集合
     */
    @TableField(exist = false)
    private List<WorkerMaster> userList;

    /**
     * 考勤命令
     */
    @TableField(exist = false)
    private String command;

    public DeviceCommand() {
        super();
    }

    public DeviceCommand(List<String> deviceSns, int type, List<String> userIds, List<WorkerMaster> userList) {
        this.deviceSns = deviceSns;
        this.type = type;
        this.userIds = userIds;
        this.userList = userList;
    }

    public DeviceCommand(List<String> deviceSns, Integer type, List<String> userIds) {
        this.deviceSns = deviceSns;
        this.type = type;
        this.userIds = userIds;
    }

    public DeviceCommand(String deviceSn, Integer type, Date date, String uuid) {
        this.deviceSn = deviceSn;
        this.type = type;
        this.createDate = date;
        this.uuid = uuid;
    }

    public DeviceCommand(String deviceSn, Integer type,String idCardNumber, Date date, String uuid, String description) {
        this.deviceSn = deviceSn;
        this.type = type;
        this.idCardNumber =idCardNumber;
        this.createDate = date;
        this.uuid = uuid;
        this.description = description;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<String> getUserIds() {
        return userIds;
    }


    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public WorkerMaster getPersonnel() {
        return personnel;
    }

    public void setPersonnel(WorkerMaster personnel) {
        this.personnel = personnel;
    }

    public List<String> getDeviceSns() {
        return deviceSns;
    }

    public void setDeviceSns(List<String> deviceSns) {
        this.deviceSns = deviceSns;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
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

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModifyIp() {
        return modifyIp;
    }

    public void setModifyIp(String modifyIp) {
        this.modifyIp = modifyIp;
    }

    public Long getModifyId() {
        return modifyId;
    }

    public void setModifyId(Long modifyId) {
        this.modifyId = modifyId;
    }

    public List<WorkerMaster> getUserList() {
        return userList;
    }

    public void setUserList(List<WorkerMaster> userList) {
        this.userList = userList;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public enum Type {
        SyncTime(1, "同步时间"), Reboot(2, "重启"), GetInfo(3, "获取设备信息"), Upgrade(4, "升级固件"), ClearRecord(5,
                "清空考勤记录"), ClearUser(6, "清空人员信息"), DispatchUser(7, "下发个别人员信息"), DeleteUser(8, "删除个别人员信息");

        private int value;
        private String description;

        private Type(int value, String description) {
            this.value = value;
            this.description = description;
        }

        public int getValue() {
            return this.value;
        }

        public String getDescription() {
            return this.description;
        }

        public static Type valueOf(int value) {
            Type[] vs = values();
            for (Type s : vs) {
                if (s.getValue() == value) {
                    return s;
                }
            }
            throw new IndexOutOfBoundsException("Invalid Enum Value");
        }

        public String toString() {
            return this.description;
        }
    }

    public boolean checkValid() {
        return (this.type != null) && (!StringUtils.isEmpty(this.deviceSn + ""))
                && ((this.type.intValue() != Type.Upgrade.getValue()) || ((this.fileId != null)))
                && (((this.type.intValue() != Type.DeleteUser.getValue())
                && (this.type.intValue() != Type.DispatchUser.getValue()))
                || (!StringUtils.isEmpty(this.modifyId + "")));
    }

}
