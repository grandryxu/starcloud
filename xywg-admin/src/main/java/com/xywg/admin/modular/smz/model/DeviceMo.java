package com.xywg.admin.modular.smz.model;

import lombok.Data;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/7/12 13:56
 */
@Data
public class DeviceMo {
    private Long deviceId;
    /**
     * 设备序列号
     */
    private String sn;

    /**
     * 设备类型
     */
    private Integer deviceType;

    /**
     * 名称
     */
    private String name;

    /**
     * 所属项目
     */
    private String projectName;

    /**
     * 算法版本
     */
    private String algVersion;



    /**
     * IP地址
     */
    private String ip;

    /**
     * 备注
     */
    private String remark;

    private Long projectId;



    /**
     *版本
     */
    private String edition;

    /**
     *设备号
     */
    private String code;

    /**
     *mac地址
     */
    private String mac;
    private String szKey;
    private String serverIp;
    private Long serverPoort;
    private Integer updateType;
    /**
     *设备属性，0=其他，1=录入，2=进门，3=出门
     */
    private String deviceProperty;
}
