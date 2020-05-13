package com.xywg.admin.modular.device.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.device.model.DeviceQr;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 二维码设备维护 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-29
 */
public interface DeviceQrMapper extends BaseMapper<DeviceQr> {
    List<Map<String,Object>> list(@Param("map") Map<String,Object> map, @Param("page")Page page);

    List<Map<String,Object>> checkDeviceSn(@Param("sn")String sn);

    int saveDeviceQr(DeviceQr deviceQr);

    int updateDeviceQr(DeviceQr deviceQr);

    List<DeviceQr> checkBySn(@Param("d") DeviceQr deviceQr);

    void deleteByIds(@Param("map") Map<String,Object> map);

    int getUpStatus(@Param("sn") String sn);
    
    String getVersionPath(@Param("name") String name,@Param("status") int status);
    
    void upload(@Param("map")Map<String, String> map);

    List<Map<String,String>> selectVersions();

    String selectByName(@Param("name")String name);

    void deleteByName(@Param("name")String name);

    DeviceQr getOneById(@Param("id") Long id);

    Map<String, Object> selectIdByProjectCodeAndSn(@Param("projectCode") String projectCode, @Param("sn") String sn);
}
