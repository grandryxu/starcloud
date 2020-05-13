package com.xywg.admin.modular.recuritWorker.service;

import com.xywg.admin.modular.recuritWorker.model.RecruitWorker;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.recuritWorker.model.dto.GetBrowseHistoryAppParam;
import com.xywg.admin.modular.recuritWorker.model.dto.GetBrowseHistoryAppResult;
import com.xywg.admin.modular.recuritWorker.model.dto.GetMyRecruitAppParam;
import com.xywg.admin.modular.recuritWorker.model.dto.RecuritWorkerDto;

import java.util.List;

/**
 * <p>
 * 招聘信息表 服务类
 * </p>
 *
 * @author cw123
 * @since 2018-08-17
 */
public interface IRecruitWorkerService extends IService<RecruitWorker> {

    /**
     * 保存招聘信息
     * @return Integer
     * @author 蔡伟
     * @param recruitWorker
     */
    Integer saveRecruitInfoApp(RecruitWorker recruitWorker);

    /**
     * 查询我发布的职位信息
     * @param  getMyRecruitAppParam
     * @return List<RecruitWorker>
     * @author 蔡伟
     */
    List<RecuritWorkerDto> getMyRecruitApp(GetMyRecruitAppParam getMyRecruitAppParam);

    /**
     * 刷新发布的时间
     * @param recuritWorkerDto
     * @return Integer
     * @author 蔡伟
     */
    Integer refreshRecruitApp(RecuritWorkerDto recuritWorkerDto);

    /**
     * 删除之前发布的职位
     * @param recuritWorkerDto
     * @return Integer
     * @author 蔡伟
     */
    Integer deleteRecruitApp(RecuritWorkerDto recuritWorkerDto);

    /**
     * 统计招聘的浏览和申请数量
     * @param id
     * @return RecruitWorker
     * @author 蔡伟
     */
    RecruitWorker getRecruitNumsApp(Long id);

    /**
     * 查询浏览记录
     * @param getBrowseHistoryAppParam
     * @return List<GetBrowseHistoryAppResult>
     * @author 蔡伟
     */
    List<GetBrowseHistoryAppResult> getBrowseHistoryApp(GetBrowseHistoryAppParam getBrowseHistoryAppParam);
}
