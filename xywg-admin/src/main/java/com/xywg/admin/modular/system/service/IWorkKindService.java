package com.xywg.admin.modular.system.service;

import com.xywg.admin.modular.system.model.Dict;
import com.xywg.admin.modular.system.model.WorkKind;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工种表 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-05
 */
public interface IWorkKindService extends IService<WorkKind> {
    /**
     * 获取工种
     * @return
     * @author yuanyang
     */
    List<WorkKind> getWorkKinds();

    /**
     * 根据name获取num
     * @param name
     * @return
     * @author yuanyang
     */
    Integer getNumByName(String name);
    
    /**
     * 查询工种列表
     * @param page
     * @param map
     * @return
     */
    List<Map<String,Object>> selectWorkKind(Page<WorkKind> page,Map<String, Object> map);
    
    /**
     * 通过id查询工种列表
     * @param id
     * @return
     */
    WorkKind selectWorkKindById(Long workKindId);
    
    /**
     * 插入公司工种信息到工种表
     * @param organizationCode
     * @return
     */
    String addWorkKinds(String organizationCode);
    
    /**
     * 工种字典同步到工种管理表
     * @param dict
     * @return
     */
    String synWorkKinds(Dict dict);
    
    /**
     * 获取工种发送实名制
     * @return
     * @author duanfen
     */
    boolean getWorkKindSendSMZ(Map<String, String> m );


    void addWorkKind(List<Object> addList);
}
