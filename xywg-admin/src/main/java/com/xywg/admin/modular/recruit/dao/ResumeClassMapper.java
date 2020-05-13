package com.xywg.admin.modular.recruit.dao;

import com.xywg.admin.modular.recruit.dto.ExperienceDto;
import com.xywg.admin.modular.recruit.dto.RecruitPageDto;
import com.xywg.admin.modular.recruit.model.RecruitResumeClassVo;
import com.xywg.admin.modular.recruit.model.ResumeClass;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.recruit.model.ResumeClassListVo;

import java.util.List;

/**
 * <p>
 * 班组简历表 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-08-22
 */
public interface ResumeClassMapper extends BaseMapper<ResumeClass> {
    /**
     * 删除工作经历
     * @param resumeId
     * @return
     */
    Integer delExperience(Long resumeId);

    /**
     * 获取工种名称
     * @param workNum
     * @return
     */
    String getWorkName(String workNum);

    /**
     * 获取工种列表
     * @param recruitPageDto
     * @return
     */
    List<ResumeClassListVo> getResumeClassList(RecruitPageDto recruitPageDto);

    /**
     * 获取班组简历详情
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
