package com.xywg.admin.modular.smz.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.smz.model.IfaLabor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/7/3 17:57
 */
public interface IfaLaborMapper {

    List<Long> getIdList(@Param("tableName") String tableName);

    void del(@Param("tableName") String tableName);
    
    /**
     * 批量删除
     * @param tableName
     * @param ids
     */
    void delbatch(@Param("tableName") String tableName,@Param("ids")List<Long> ids);


    boolean insert(IfaLabor ifaLabor);

    Long getLastNumber(@Param("tableName") String tableName);

    void updateNumber(@Param("id")Long id,@Param("tableName") String tableName);
    
    
    int getLastNumberByTableName(@Param("tableName") String tableName);
    
    /***
     * 查询是否同步数据
     * @param type
     * @return  List<HashMap<String, Object>>
     * @author duanfen
     */
    List<HashMap<String, Object>> findSynchro(@Param("page") Page<HashMap<String, Object>> page,@Param("param") Map<String,Object> map);

    /**
     * 批量新增
     * @param tableName
     * @param ids
     */
    int batchInsert(@Param("tableName") String tableName,@Param("ids")List<Long> ids);
}
