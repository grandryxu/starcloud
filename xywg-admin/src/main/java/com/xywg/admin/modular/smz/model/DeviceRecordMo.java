package com.xywg.admin.modular.smz.model;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/7/12 14:57
 */
@Data
public class DeviceRecordMo {

    private Long lablorId;
    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 人主键
     */
    private Long personId;
    /**
     * 人名字
     */
    private String name;
    private Long deviceId;
    private Long type;
    private String photo;
    private Date time;
    private String remark;
    private String projectCode;
    private int recordMode;
    private String address;
    private Long lng;
    private Long lat;
}
