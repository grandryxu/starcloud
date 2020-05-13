package com.xywg.admin.modular.project.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.project.model.ProjectTrainRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 培训人员记录表 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-14
 */
public interface ProjectTrainRecordMapper extends BaseMapper<ProjectTrainRecord> {
    int insertProjectRecord(@Param("list") List<Map<String,Object>> list, @Param("id") Long id);

    List<Map<String,Object>> getTrainRecordList(@Param("idCardType") Integer idCardType, @Param("idCardNumber") String idCardNumber, @Param("page")Page page);
}
