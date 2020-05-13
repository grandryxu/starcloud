package com.xywg.admin.modular.workerEnshrine.service;

import com.xywg.admin.modular.workerEnshrine.model.PersonEnshrineDto;
import com.xywg.admin.modular.workerEnshrine.model.PersonEnshrineVo;
import com.xywg.admin.modular.workerEnshrine.model.WorkerEnshrine;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.workerEnshrine.model.WorkerEnshrineDto;

import java.util.List;

/**
 * <p>
 * 职位收藏表 服务类
 * </p>
 *
 * @author wsb123
 * @since 2018-08-21
 */
public interface IWorkerEnshrineService extends IService<WorkerEnshrine> {

    /**
     * 收藏
     * @param workerEnshrineDto
     */
    void enshrine(WorkerEnshrineDto workerEnshrineDto);

    /**
     * 获取我的收藏列表
     * @param personEnshrineDto
     * @return
     */
    List<PersonEnshrineVo> getPersonEnshrineList(PersonEnshrineDto personEnshrineDto);
}
