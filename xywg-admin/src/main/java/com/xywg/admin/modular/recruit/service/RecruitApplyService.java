package com.xywg.admin.modular.recruit.service;

import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.recruit.dto.ApplyDto;
import com.xywg.admin.modular.recruit.dto.ApplyListPersonDto;
import com.xywg.admin.modular.recruit.dto.CallMobileDto;
import com.xywg.admin.modular.recruit.model.*;

import java.util.List;

/**
 * @Auther: wangshibo
 * @Date: 2018/8/20/020 10:31
 * @Description:
 */
public interface RecruitApplyService extends IService<RecruitApply> {
    /**
     * 申请
     * @param applyDto
     */
    void apply(ApplyDto applyDto);

    /**
     * 获取我的申请列表
     * @param applyListPersonDto
     * @return
     */
    List<ApplyListPersonVo> getApplyListByPerson(ApplyListPersonDto applyListPersonDto);

    /**
     * 拨打电话
     * @param callMobileDto
     */
    void callMobile(CallMobileDto callMobileDto);

    /**
     * 查询申请记录
     * @param id
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<ApplyVo> getApplyListById(Integer id, Integer pageNo, Integer pageSize);

    /**
     * 查询评价记录
     * @param id
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<EvaluateVo> getEvaluateListById(Integer id, Integer pageNo, Integer pageSize);

    /**
     * 获取岗位详情
     * @param id
     * @return
     */
    RecruitInfoVo getRecruitInfoById(Integer id);

    /**
     * 获取福利内容
     * @param wealContent
     * @return
     */
    List<String> getWealContentListByWealContent(String wealContent);

    /**
     * 处理申请
     * @param id
     */
    void disposeApply(Integer id);
}
