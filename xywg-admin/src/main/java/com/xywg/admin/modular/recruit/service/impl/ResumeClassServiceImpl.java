package com.xywg.admin.modular.recruit.service.impl;

import com.xywg.admin.modular.recruit.dto.ExperienceDto;
import com.xywg.admin.modular.recruit.dto.RecruitPageDto;
import com.xywg.admin.modular.recruit.dto.ResumeClassDto;
import com.xywg.admin.modular.recruit.model.*;
import com.xywg.admin.modular.recruit.dao.ResumeClassMapper;
import com.xywg.admin.modular.recruit.service.IExperienceService;
import com.xywg.admin.modular.recruit.service.IResumeClassService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 班组简历表 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-08-22
 */
@Service
public class ResumeClassServiceImpl extends ServiceImpl<ResumeClassMapper, ResumeClass> implements IResumeClassService {

    @Autowired
    IWorkerMasterService workerMasterService;
    @Autowired
    IExperienceService experienceService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResumeClassVo addResumeClass(ResumeClassDto resumeClassDto) {
        /**
         * 获取当前时间 类型为sql格式的date
         */
        Date time = new Date(new java.util.Date().getTime());
        //查询操作人
        WorkerMaster workerByIdCard = workerMasterService.getWorkerByIdCard(resumeClassDto.getIdCardNumber(), resumeClassDto.getIdCardType());
        ResumeClass resumeClass = this.changeDtoToModel(resumeClassDto);
        resumeClass.setCreateUser(workerByIdCard.getWorkerName());
        resumeClass.setCreateDate(time);
        resumeClass.setUpdateDate(time);
        resumeClass.setUpdateUser(workerByIdCard.getWorkerName());
        baseMapper.insert(resumeClass);
        Long id =resumeClass.getId();
        /**
         * 批量插入经历
         */
        //转换类型
        List<Experience> experienceList = this.changeModel(resumeClassDto.getExperience(), id);
        experienceService.insertBatch(experienceList);
        return new ResumeClassVo(id.intValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResumeClassVo updateResumeClass(ResumeClassDto resumeClassDto) {
        //获取当前时间 类型为sql格式的date
        Date time = new Date(new java.util.Date().getTime());
        //查询操作人
        WorkerMaster workerByIdCard = workerMasterService.getWorkerByIdCard(resumeClassDto.getIdCardNumber(), resumeClassDto.getIdCardType());
        ResumeClass resumeClass = this.changeDtoToModel(resumeClassDto);
        resumeClass.setUpdateDate(time);
        resumeClass.setUpdateUser(workerByIdCard.getWorkerName());
        baseMapper.updateById(resumeClass);
        //判断是否要覆盖经历
        if (resumeClassDto.getExperience().size()>0){
            //先删除之前的工作经历
            baseMapper.delExperience(resumeClassDto.getId());
            //批量新增
            List<Experience> experienceList = this.changeModel(resumeClassDto.getExperience(), resumeClassDto.getId());
            experienceService.insertBatch(experienceList);
        }
        return new ResumeClassVo(resumeClassDto.getId().intValue());
    }

    @Override
    public List<Experience> changeModel(List<ExperienceDto> list,Long resumeId) {
        //获取当前时间 类型为sql格式的date
        Date time = new Date(new java.util.Date().getTime());
        List<Experience> experienceList=new ArrayList<>();
        for (ExperienceDto experienceDto: list) {
            Experience experience  = new Experience();
            experience.setResumeId(resumeId);
            experience.setContent(experienceDto.getContent());
            experience.setProjectName(experienceDto.getProjectName());
            experience.setTheme(experienceDto.getTheme());
            experience.setStartDate(experienceDto.getStartDate());
            experience.setEndDate(experienceDto.getEndDate());
            experience.setCreateDate(time);
            experience.setUpdateDate(time);
            experienceList.add(experience);
        }
        return experienceList;
    }

    @Override
    public ResumeClass changeDtoToModel(ResumeClassDto resumeClassDto) {
        ResumeClass resumeClass = new ResumeClass();
        if (resumeClassDto.getId() != null && resumeClassDto.getId() !=0){
            resumeClass.setId(resumeClassDto.getId());
        }
        resumeClass.setIdCardType(resumeClassDto.getIdCardType());
        resumeClass.setIdCardNumber(resumeClassDto.getIdCardNumber());
        resumeClass.setComUsername(resumeClassDto.getComUsername());
        resumeClass.setComPhone(resumeClassDto.getComPhone());
        resumeClass.setProjectType(resumeClassDto.getProjectType());
        resumeClass.setWorkIdSets(resumeClassDto.getWorkIdSets());
        resumeClass.setWorkYears(resumeClassDto.getWorkYears());
        resumeClass.setWorkerTotal(resumeClassDto.getWorkerTotal());
        resumeClass.setTeamBrief(resumeClassDto.getTeamBrief());
        resumeClass.setProvincesId(resumeClassDto.getProvincesId());
        resumeClass.setCitiesId(resumeClassDto.getCitiesId());
        resumeClass.setAreasId(resumeClassDto.getAreasId());
        resumeClass.setWishProvincesId(resumeClassDto.getWishProvincesId());
        resumeClass.setWishCitiesId(resumeClassDto.getWishCitiesId());
        resumeClass.setWishAreasId(resumeClassDto.getWishAreasId());
        resumeClass.setSalaryType(resumeClassDto.getSalaryType());
        resumeClass.setSalaryContent(resumeClassDto.getSalaryContent());
        resumeClass.setSalaryText(resumeClassDto.getSalaryText());
        return resumeClass;
    }

    @Override
    public List<WorkSetsVo> getWorkList(String workSets) {
        List<WorkSetsVo> list= new ArrayList<>();
        List<String> result = Arrays.asList(workSets.split(","));
        for (String workNum:result) {
            String workName = baseMapper.getWorkName(workNum);
            WorkSetsVo workSetsVo = new WorkSetsVo(Integer.valueOf(workNum),workName);
            list.add(workSetsVo);
        }
        return list;
    }

    @Override
    public List<ResumeClassListVo> getResumeClassList(RecruitPageDto recruitPageDto) {
        recruitPageDto.setPageNo((recruitPageDto.getPageNo() - 1)*recruitPageDto.getPageSize());
        List<ResumeClassListVo> list =  baseMapper.getResumeClassList(recruitPageDto);
        //处理对象集合
        for (ResumeClassListVo resumeVo: list) {
            if (resumeVo.getWorkSetString() != null && resumeVo.getWorkSetString() !=""){
                List<WorkSetsVo> workList = this.getWorkList(resumeVo.getWorkSetString());
                resumeVo.setWorkSets(workList);
            }
        }
        return list;
    }

    @Override
    public RecruitResumeClassVo getResumeClass(Integer id) {
        RecruitResumeClassVo recruitResumeClassVo= baseMapper.getResumeClass(id);
        //工种对象集合
        if (recruitResumeClassVo.getWorkIdSetString() != null && recruitResumeClassVo.getWorkIdSetString() !=""){
            List<WorkSetsVo> workList = this.getWorkList(recruitResumeClassVo.getWorkIdSetString());
            recruitResumeClassVo.setWorkSets(workList);
        }
        //工作经历
        List<ExperienceDto> experienceByResumeId = this.getExperienceByResumeId(id);
        recruitResumeClassVo.setExperience(experienceByResumeId);
        return recruitResumeClassVo;
    }

    @Override
    public List<ExperienceDto> getExperienceByResumeId(Integer id) {
        List<ExperienceDto> list=baseMapper.getExperienceByResumeId(id);
        return list;
    }
}
