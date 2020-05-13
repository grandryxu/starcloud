package com.xywg.admin.modular.recruit.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.recruit.dto.ApplyDto;
import com.xywg.admin.modular.recruit.dto.ApplyListPersonDto;
import com.xywg.admin.modular.recruit.dto.CallMobileDto;
import com.xywg.admin.modular.recruit.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: wangshibo
 * @Date: 2018/8/20/020 10:41
 * @Description:
 */
public interface RecruitApplyMapper extends BaseMapper<RecruitApply> {
    /**
     * 申请
     * @param applyDto
     */
    void apply(ApplyDto applyDto);

    /**
     * 获取某个人的申请列表（分页）
     * @param applyListPersonDto
     * @return
     */
    List<ApplyListPersonVo> getApplyListByPerson(ApplyListPersonDto applyListPersonDto);

    /**
     * 查询某人与某项目的申请记录
     * @param recruitId
     * @param idCardType
     * @param idCardNumber
     * @return
     */
    List<ApplyListPersonVo> queryPersonRecruit(@Param("recruitId")Integer recruitId,@Param("idCardType")String idCardType,@Param("idCardNumber") String idCardNumber);

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
    List<ApplyVo> getApplyListById(@Param("id") Integer id, @Param("pageNo")Integer pageNo,@Param("pageSize") Integer pageSize);

    /**
     * 查询评价记录
     * @param id
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<EvaluateVo> getEvaluateListById(@Param("id") Integer id, @Param("pageNo")Integer pageNo,@Param("pageSize") Integer pageSize);

    /**
     * 根据id获取岗位详情
     * @param id
     * @return
     */
    RecruitInfoVo getRecruitInfoById(Integer id);

    /**
     * 获取福利内容
     * @param codes
     * @return
     */
    List<String> getWealContentListByWealContent(@Param("codes") List<String> codes);

    /**
     * 申请数+1
     * @param id
     */
    void addRecordCount(Integer id);

    /**
     * 处理申请
     * @param id
     */
    void disposeApply(Integer id);
}
