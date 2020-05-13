package com.xywg.admin.modular.workerEnshrine.service.impl;

import com.xywg.admin.modular.recruit.dao.RecruitApplyMapper;
import com.xywg.admin.modular.recruit.model.ApplyListPersonVo;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;
import com.xywg.admin.modular.workerEnshrine.model.PersonEnshrineDto;
import com.xywg.admin.modular.workerEnshrine.model.PersonEnshrineVo;
import com.xywg.admin.modular.workerEnshrine.model.WorkerEnshrine;
import com.xywg.admin.modular.workerEnshrine.dao.WorkerEnshrineMapper;
import com.xywg.admin.modular.workerEnshrine.model.WorkerEnshrineDto;
import com.xywg.admin.modular.workerEnshrine.service.IWorkerEnshrineService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 职位收藏表 服务实现类
 * </p>
 *
 * @author wsb123
 * @since 2018-08-21
 */
@Service
public class WorkerEnshrineServiceImpl extends ServiceImpl<WorkerEnshrineMapper, WorkerEnshrine> implements IWorkerEnshrineService {
    @Autowired
    IDictService dictService;
    @Autowired
    IWorkerMasterService workerMasterService;
    @Resource
    RecruitApplyMapper recruitApplyMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void enshrine(WorkerEnshrineDto workerEnshrineDto) {
        /**
         * 获取当前时间 类型为sql格式的date
         */
        Date time = new Date(new java.util.Date().getTime());
        WorkerEnshrine workerEnshrine = new WorkerEnshrine(workerEnshrineDto.getIdCardType(),workerEnshrineDto.getIdCardNumber(),workerEnshrineDto.getId());
        //查询操作人
        WorkerMaster workerByIdCard = workerMasterService.getWorkerByIdCard(workerEnshrineDto.getIdCardNumber(), Integer.valueOf(workerEnshrineDto.getIdCardType()));
        //判断是收藏还是取消收藏
        if (workerEnshrineDto.getType() == 1){
            workerEnshrine.setCreateUser(workerByIdCard.getWorkerName());
            workerEnshrine.setCreateDate(time);
            workerEnshrine.setUpdateDate(time);
            workerEnshrine.setUpdateUser(workerByIdCard.getWorkerName());
            baseMapper.insert(workerEnshrine);
        }else {
            workerEnshrine.setUpdateDate(time);
            workerEnshrine.setUpdateUser(workerByIdCard.getWorkerName());
            baseMapper.cancleEnshrine(workerEnshrine);
        }
    }

    @Override
    public List<PersonEnshrineVo> getPersonEnshrineList(PersonEnshrineDto personEnshrineDto) {
        personEnshrineDto.setPageNo((personEnshrineDto.getPageNo()-1)*personEnshrineDto.getPageSize());
        List<PersonEnshrineVo> list=baseMapper.getPersonEnshrineList(personEnshrineDto);
        for (PersonEnshrineVo personEnshrineVo : list) {
            /**
             * 查询工种集合
             */
            List<String> workNameListByWorkSet = dictService.getWorkNameListByWorkSet(personEnshrineVo.getWork());
            String work = StringUtils.join(workNameListByWorkSet, ",");
            personEnshrineVo.setWork(work);
            //查询是否申请过
            List<ApplyListPersonVo> applyListPersonVos = recruitApplyMapper.queryPersonRecruit(personEnshrineVo.getId(), personEnshrineDto.getIdCardType(), personEnshrineDto.getIdCardNumber());
            if (applyListPersonVos.size() > 0){
                personEnshrineVo.setIsResume(1);
            }else {
                personEnshrineVo.setIsResume(0);
            }
        }
        return list;
    }
}
