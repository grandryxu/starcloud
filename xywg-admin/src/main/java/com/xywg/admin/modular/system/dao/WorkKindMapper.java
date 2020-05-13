package com.xywg.admin.modular.system.dao;

import com.xywg.admin.modular.system.model.WorkKind;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工种表 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-05
 */
public interface WorkKindMapper extends BaseMapper<WorkKind> {
    /**
     * 获取工种
     * @return
     * @author yuanyang
     */
    List<WorkKind> getWorkKinds();

    /**
     * 根据工种num获取工种名称
     * @param num
     * @return
     */
    String getWorkKindName(@Param("num") Integer num);

    /**
     * 根据name获取num
     * @param name
     * @return
     */
    Integer getNumByName(@Param("name") String name);
    
    /**
     * 查询工种列表
     * @param page
     * @param map
     * @return
     */
    List<Map<String,Object>> selectWorkKind(@Param("page") Page<WorkKind> page, @Param("map")Map<String, Object> map);
    
    /**
     * 根据id查询工种信息
     * @param id
     * @return
     */
    WorkKind selectWorkKindById(@Param("workKindId") Long workKindId);
    
    /**
     * 查询工种表里现有工种集合
     * @return
     */
    List<WorkKind> getWorkKindsNameList();
    
    /**
     * 查询工种表里的企业集合
     * @return
     */
    List<WorkKind> getWorkKindsCompanyList();
    
    /**
     * 删除所有工种
     * @return
     */
    int deleteWorkKind();
    
    /**
     * 插入公司工种信息到工种表
     * @param list
     * @return
     */
    int addWorkKind(@Param("fileList") List<WorkKind> list);
    
    /**
     * 获取工种发送实名制
     * @return
     * @author duanfen
     */
    List<Map<String,Object>> getWorkKindSendSMZ();

    Map<String, Long> selectIdByOrgCodeAndNum(@Param("organizationCode") String organizationCode, @Param("num") Integer num);
}
