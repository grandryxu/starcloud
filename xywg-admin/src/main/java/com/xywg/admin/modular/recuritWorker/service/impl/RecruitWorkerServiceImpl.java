package com.xywg.admin.modular.recuritWorker.service.impl;

import com.xywg.admin.modular.recuritWorker.model.RecruitWorker;
import com.xywg.admin.modular.recuritWorker.dao.RecruitWorkerMapper;
import com.xywg.admin.modular.recuritWorker.model.dto.GetBrowseHistoryAppParam;
import com.xywg.admin.modular.recuritWorker.model.dto.GetBrowseHistoryAppResult;
import com.xywg.admin.modular.recuritWorker.model.dto.GetMyRecruitAppParam;
import com.xywg.admin.modular.recuritWorker.model.dto.RecuritWorkerDto;
import com.xywg.admin.modular.recuritWorker.service.IRecruitWorkerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 招聘信息表 服务实现类
 * </p>
 *
 * @author cw123
 * @since 2018-08-17
 */
@Service
public class RecruitWorkerServiceImpl extends ServiceImpl<RecruitWorkerMapper, RecruitWorker> implements IRecruitWorkerService {

    @Override
    public Integer saveRecruitInfoApp(RecruitWorker recruitWorker) {
        Date date = new Date();
        recruitWorker.setCreateDate(date);
        recruitWorker.setUpdateDate(date);
        recruitWorker.setBrowseCount(0);
        recruitWorker.setRecordCount(0);
        return this.baseMapper.insert(recruitWorker);
    }

    @Override
    public List<RecuritWorkerDto> getMyRecruitApp(GetMyRecruitAppParam getMyRecruitAppParam) {
        getMyRecruitAppParam.setIndex((getMyRecruitAppParam.getPageNo()-1)*getMyRecruitAppParam.getPageSize());
        return this.baseMapper.getMyRecruitApp(getMyRecruitAppParam);
    }

    @Override
    public Integer refreshRecruitApp(RecuritWorkerDto recuritWorkerDto) {
        return this.baseMapper.refreshRecruitApp(recuritWorkerDto);
    }

    @Override
    public Integer deleteRecruitApp(RecuritWorkerDto recuritWorkerDto) {
        return this.baseMapper.deleteRecruitApp(recuritWorkerDto);
    }

    @Override
    public RecruitWorker getRecruitNumsApp(Long id) {
        return this.baseMapper.getRecruitNumsApp(id);
    }

    @Override
    public List<GetBrowseHistoryAppResult> getBrowseHistoryApp(GetBrowseHistoryAppParam getBrowseHistoryAppParam) {
        getBrowseHistoryAppParam.setIndex((getBrowseHistoryAppParam.getPageNo()-1)*getBrowseHistoryAppParam.getPageSize());
        return this.baseMapper.getBrowseHistoryApp(getBrowseHistoryAppParam);
    }
}
