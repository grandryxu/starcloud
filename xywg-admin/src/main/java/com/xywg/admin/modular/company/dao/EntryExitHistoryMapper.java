package com.xywg.admin.modular.company.dao;

import com.xywg.admin.modular.company.model.EntryExitHistory;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 进退场记录表 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-28
 */
public interface EntryExitHistoryMapper extends BaseMapper<EntryExitHistory> {
    void addHistry(@Param("h") EntryExitHistory entryExitHistory);
    /**
     * 根据项目工人表id删除进退场表信息
     * @param list
     * @author duanfen
     */
    void batchDeleteExitByPwId (@Param("list") List<String> list);
}
