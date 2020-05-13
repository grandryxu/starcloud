package com.xywg.admin.modular.recruit.service;

import com.xywg.admin.modular.recruit.dto.ExperienceDto;
import com.xywg.admin.modular.recruit.dto.RecruitPageDto;
import com.xywg.admin.modular.recruit.dto.ResumeClassDto;
import com.xywg.admin.modular.recruit.model.*;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 班组简历表 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-08-22
 */
public interface IResumeClassService extends IService<ResumeClass> {

    /**
     * 新增
     * @param resumeClassDto
     * @return
     */
    ResumeClassVo addResumeClass(ResumeClassDto resumeClassDto);

    /**
     * 修改
     * @param resumeClassDto
     */
    ResumeClassVo updateResumeClass(ResumeClassDto resumeClassDto);

    /**
     * list转换
     * @param list
     * @param resumeId
     * @return
     */
    List<Experience> changeModel(List<ExperienceDto> list,Long resumeId);

    /**
     * DTO转换为model
     * @param resumeClassDto
     * @return
     */
    ResumeClass changeDtoToModel(ResumeClassDto resumeClassDto);

    /**
     * 获取工种集合
     * @param workSets
     * @return
     */
    List<WorkSetsVo> getWorkList(String workSets);

    /**
     * 获取班组简历列表
     * @param recruitPageDto
     * @return
     */
    List<ResumeClassListVo> getResumeClassList(RecruitPageDto recruitPageDto);

    /**
     * 查询班组简历详情
     * @param id
     * @return
     */
    RecruitResumeClassVo getResumeClass(Integer id);

    /**
     * 根据简历id获取工作经历
     * @param id
     * @return
     */
    List<ExperienceDto> getExperienceByResumeId(Integer id);
}
