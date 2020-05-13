package com.xywg.admin.modular.recruit.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.modular.recruit.dao.RecruitApplyMapper;
import com.xywg.admin.modular.recruit.dto.ApplyDto;
import com.xywg.admin.modular.recruit.dto.ApplyListPersonDto;
import com.xywg.admin.modular.recruit.dto.CallMobileDto;
import com.xywg.admin.modular.recruit.model.*;
import com.xywg.admin.modular.recruit.service.IResumeClassService;
import com.xywg.admin.modular.recruit.service.RecruitApplyService;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Auther: wangshibo
 * @author ;wangshibo
 * @Date: 2018/8/20/020 10:34
 * @Description:
 */
@Service
public class RecruitApplyServiceImpl extends ServiceImpl<RecruitApplyMapper,RecruitApply> implements RecruitApplyService {
    @Autowired
    IDictService dictService;

    @Autowired
    IWorkerMasterService workerMasterService;
    @Autowired
    IResumeClassService resumeClassService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void apply(ApplyDto applyDto) {
        //获取手机号
        if (applyDto.getResumeClass() == null || applyDto.getResumeClass() == 0){
            //个人手机号
            WorkerMaster workerByIdCard = workerMasterService.getWorkerByIdCard(applyDto.getIdCardNumber(), Integer.valueOf(applyDto.getIdCardType()));
            applyDto.setMobile(workerByIdCard.getCellPhone());
        }else {
            RecruitResumeClassVo resumeClass = resumeClassService.getResumeClass(applyDto.getResumeClass());
            applyDto.setMobile(resumeClass.getComPhone());
        }
        //查询是否申请过
        List<ApplyListPersonVo> applyListPersonVos = baseMapper.queryPersonRecruit(applyDto.getId(), applyDto.getIdCardType(), applyDto.getIdCardNumber());
        if (applyListPersonVos.size() <= 0 ){
            baseMapper.apply(applyDto);
            //申请数+1
            baseMapper.addRecordCount(applyDto.getId());
        }else {
            //获取当前时间
            Date today = new Date();
            //判断是否在7天内
            Calendar c = Calendar.getInstance();
            c.setTime(applyListPersonVos.get(0).getUpdateDate());
            c.add(Calendar.DAY_OF_MONTH, 7);
            if (c.getTime().getTime() > today.getTime()){
                throw new XywgException(701,"您已申请过该岗位，7天内无法再次申请！");
            }else {
                baseMapper.apply(applyDto);
                //申请数+1
                baseMapper.addRecordCount(applyDto.getId());
            }
        }

    }

    @Override
    public List<ApplyListPersonVo> getApplyListByPerson(ApplyListPersonDto applyListPersonDto) {
        applyListPersonDto.setPageNo((applyListPersonDto.getPageNo()-1)*applyListPersonDto.getPageSize());
        List<ApplyListPersonVo> applyListByPerson = baseMapper.getApplyListByPerson(applyListPersonDto);
        for (ApplyListPersonVo applyListPersonVo : applyListByPerson) {
            List<String> workNameListByWorkSet = dictService.getWorkNameListByWorkSet(applyListPersonVo.getWork());
            String work = StringUtils.join(workNameListByWorkSet, ",");
            applyListPersonVo.setWork(work);
        }
        return applyListByPerson;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void callMobile(CallMobileDto callMobileDto) {
        baseMapper.callMobile(callMobileDto);
    }

    @Override
    public List<ApplyVo> getApplyListById(Integer id, Integer pageNo, Integer pageSize) {
        pageNo=(pageNo-1)*pageSize;
        List<ApplyVo> list = baseMapper.getApplyListById(id,pageNo,pageSize);
        return list;
    }

    @Override
    public List<EvaluateVo> getEvaluateListById(Integer id, Integer pageNo, Integer pageSize) {
        pageNo=(pageNo-1)*pageSize;
        List<EvaluateVo> list =baseMapper.getEvaluateListById(id,pageNo,pageSize);
        return list;
    }

    @Override
    public RecruitInfoVo getRecruitInfoById(Integer id) {
        RecruitInfoVo recruitInfoVo = baseMapper.getRecruitInfoById(id);
        List<String> workNameListByWorkSet = dictService.getWorkNameListByWorkSet(recruitInfoVo.getWorkNameSets());
        String work = StringUtils.join(workNameListByWorkSet, ",");
        List<String> wealContentListByWealContents=this.getWealContentListByWealContent(recruitInfoVo.getWealContent());
        String welContents=StringUtils.join(wealContentListByWealContents, ",");
        recruitInfoVo.setWorkNameSets(work);
        recruitInfoVo.setWealContent(welContents);
        return recruitInfoVo;
    }

    @Override
    public List<String> getWealContentListByWealContent(String wealContent) {
        List<String> result = Arrays.asList(wealContent.split(","));
        return baseMapper.getWealContentListByWealContent(result);
    }

    @Override
    public void disposeApply(Integer id) {
        baseMapper.disposeApply(id);
    }
}
