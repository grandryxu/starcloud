package com.xywg.admin.modular.workerEnshrine.dao;

import com.xywg.admin.modular.workerEnshrine.model.PersonEnshrineDto;
import com.xywg.admin.modular.workerEnshrine.model.PersonEnshrineVo;
import com.xywg.admin.modular.workerEnshrine.model.WorkerEnshrine;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 职位收藏表 Mapper 接口
 * </p>
 *
 * @author wsb123
 * @since 2018-08-21
 */
public interface WorkerEnshrineMapper extends BaseMapper<WorkerEnshrine> {
    /**
     * 查询人员是否收藏过某项目
     * @param recruitId
     * @param idCardType
     * @param idCardNumber
     * @return
     */
 List<WorkerEnshrine> queryRelation(@Param("recruitId") Long recruitId,@Param("idCardType")String idCardType,@Param("idCardNumber")String idCardNumber);

    /**
     * 再次收藏
     * @param workerEnshrine
     */
 void cancleEnshrine(WorkerEnshrine workerEnshrine);

    /**
     * 获取我的收藏列表
     * @param personEnshrineDto
     * @return
     */
    List<PersonEnshrineVo> getPersonEnshrineList(PersonEnshrineDto personEnshrineDto);
}
