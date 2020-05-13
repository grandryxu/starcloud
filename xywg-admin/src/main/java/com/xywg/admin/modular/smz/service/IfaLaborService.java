package com.xywg.admin.modular.smz.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.smz.model.IfaLabor;
import com.xywg.admin.modular.system.model.VersionVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/7/26 18:34
 */
public interface IfaLaborService {

    List<Long> getIdList(String tableName);

    void del(String tableName);

    Long getLastNumber(String tableName);

    boolean insert(IfaLabor ifaLabor);

    int updateNumber(Long id,String tableName);
    
    int getLastNumberByTableName(String tableName);
    
    

    void del(String tableName,List<Long> id);
    
    /***
     * 查询是否同步数据
     * @param type
     * @return List<HashMap<String, Object>>
     * @author duanfen
     */
    List<HashMap<String, Object>> findSynchro(Page<HashMap<String, Object>> page,Map<String,Object> map);
    
    /**
     * 同步选中的数据
     * @author duanfen
     */
    void synchro(Map<String,Object> map);
    /**
     * 批量新增
     * <p>Title: batchInsert</p>
     * <p>Description: </p>
     * @author duanfen
     * @date 2019年8月28日
     */
    boolean batchInsert(String tableName,List<Long> id);

}
