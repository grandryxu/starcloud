package com.xywg.admin.modular.recruitModular.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xywg.admin.modular.recruit.service.RecruitApplyService;
import com.xywg.admin.modular.recruitModular.dao.RecruitModularMapper;
import com.xywg.admin.modular.recruitModular.model.RecruitModularParams;
import com.xywg.admin.modular.recruitModular.model.dto.RecruitWorkerDto;
import com.xywg.admin.modular.recruitModular.service.IRecruitModularService;
import com.xywg.admin.modular.system.service.IDictService;

@Service
public class RecruitModularServiceImpl implements IRecruitModularService {

	@Autowired
	private RecruitModularMapper recruitModularMapper;
	
	@Autowired
	private IDictService dictService;
	
	@Autowired
	private RecruitApplyService recruitApplyService;
	
	/**
	 * 
	 * @description 获取岗位列表
	 * @author chupp
	 * @date 2018年8月20日
	 * @param params
	 * @return
	 *
	 */
	@Override
	public Object getRecruitList(RecruitModularParams params) {
		if(params.getWorkId() != null && !params.getWorkId().isEmpty()) {
			String[] split = params.getWorkId().split(","); 
			params.setWorkIdList(Arrays.asList(split));
		}
		params.setPageNo((params.getPageNo() - 1) * params.getPageSize());
		List<RecruitWorkerDto> recruitList = recruitModularMapper.getRecruitList(params);
		for(RecruitWorkerDto rwd : recruitList) {
			rwd.setWork(StringUtils.join(dictService.getWorkNameListByWorkSet(rwd.getWorkIdSets()), ","));
		}
		return recruitList;
	}

	/**
	 * 
	 * @description 获取岗位详情
	 * @author chupp
	 * @date 2018年8月21日
	 * @param params
	 * @return
	 *
	 */
	@Override
	@Transactional
	public Object getRecruitDetail(RecruitModularParams params) {
		recruitModularMapper.saveRecruitBrowse(params);
		recruitModularMapper.updateBrowseCount(params);
		RecruitWorkerDto recruitDetail = recruitModularMapper.getRecruitDetail(params);
		recruitDetail.setWork(StringUtils.join(dictService.getWorkNameListByWorkSet(recruitDetail.getWorkIdSets()), ","));
		recruitDetail.setWealContent(StringUtils.join(recruitApplyService.getWealContentListByWealContent(recruitDetail.getWealContent()), ","));
		return recruitDetail;
	}

	/**
	 * 
	 * @description 获取公司基本信息
	 * @author chupp
	 * @date 2018年8月21日
	 * @param id
	 * @return
	 *
	 */
	@Override
	public Object getCompanyBaseData(Long id) {
		return recruitModularMapper.getCompanyBaseData(id);
	}

	/**
	 * 
	 * @description 获取评价列表
	 * @author chupp
	 * @date 2018年8月21日
	 * @param params
	 * @return
	 *
	 */
	@Override
	public Object getRecruitEvaluateList(RecruitModularParams params) {
		params.setPageNo((params.getPageNo() - 1) * params.getPageSize());
		return recruitModularMapper.getRecruitEvaluateList(params);
	}

	/**
	 * 
	 * @description 评价接口
	 * @author chupp
	 * @date 2018年8月21日
	 * @param params
	 *
	 */
	@Override
	@Transactional
	public void saveRecruitEvaluate(RecruitModularParams params) {
		recruitModularMapper.saveRecruitEvaluate(params);
		recruitModularMapper.updateIsEvaluate(params);
	}

	/**
	 * 
	 * @description 获取个人简历详情
	 * @author chupp
	 * @date 2018年8月21日
	 * @param params
	 * @return
	 *
	 */
	@Override
	public Object getPersonRecruitResume(RecruitModularParams params) {
		return recruitModularMapper.getPersonRecruitResume(params);
	}

	/**
	 * 
	 * @description 保存个人简历
	 * @author chupp
	 * @date 2018年8月22日
	 * @param params
	 * @return
	 *
	 */
	@Override
	@Transactional
	public Object saveRecruitResumeWorker(RecruitModularParams params) {
		if(params.getId() == null) {
			recruitModularMapper.saveRecruitResumeWorker(params);
		}else {
			recruitModularMapper.updateRecruitResumeWorker(params);
		}
		return params.getId();
	}

	/**
	 * 
	 * @description 申诉保存
	 * @author chupp
	 * @date 2018年8月22日
	 * @param params
	 *
	 */
	@Override
	@Transactional
	public void saveRecruitAppeal(RecruitModularParams params) {
		recruitModularMapper.saveRecruitAppeal(params);
		recruitModularMapper.updateIsAppeal(params);
	}

	/**
	 * 
	 * @description 查询电话记录
	 * @author chupp
	 * @date 2018年8月22日
	 * @param params
	 * @return
	 *
	 */
	@Override
	public Object getRecruitTelegram(RecruitModularParams params) {
		params.setPageNo((params.getPageNo() - 1) * params.getPageSize());
		return recruitModularMapper.getRecruitTelegram(params);
	}

}
