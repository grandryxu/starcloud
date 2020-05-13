package com.xywg.admin.modular.smz.service;

import java.util.Map;

public interface IReceiveLaborTYDataJobService {

    /**
     * 项目（劳务通通用）
     */
    void saveProjectFromLaborTYNew(Map<String, Object> mnt);

    /**
     * 考勤设备（劳务通通用）
     */
    void saveDeviceFromLaborTY(Map<String, Object> mnt);

    /**
     * 班组信息（劳务通通用）
     */
    void saveTeamMasterFromLaborTY(Map<String, Object> mnt);

    /**
     * 人员基础信息（劳务通通用）
     */
    void saveWorkerFromLaborTY(Map<String, Object> mnt);

    /**
     * 项目人员（劳务通通用）
     */
    void saveProjectWorkerFromLaborTY(Map<String, Object> mnt) throws Exception ;

    /**
     * 考勤（劳务通通用）
     */
    void saveDeviceRecordFromLaborTY(Map<String, Object> mnt) throws Exception;

 /*   *//**
     * 项目地址
     * @param mnt
     *//*
    void saveProjectAddress(Map<String, Object> mnt);*/
}
