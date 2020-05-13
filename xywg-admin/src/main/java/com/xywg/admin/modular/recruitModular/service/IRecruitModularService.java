package com.xywg.admin.modular.recruitModular.service;

import com.xywg.admin.modular.recruitModular.model.RecruitModularParams;

public interface IRecruitModularService {

	/**
	 * 
	 * @description 获取岗位列表
	 * @author chupp
	 * @date 2018年8月20日
	 * @param params
	 * @return
	 *
	 */
	Object getRecruitList(RecruitModularParams params);

	/**
	 * 
	 * @description 获取岗位详情
	 * @author chupp
	 * @date 2018年8月21日
	 * @param params
	 * @return
	 *
	 */
	Object getRecruitDetail(RecruitModularParams params);

	/**
	 * 
	 * @description 获取公司基本信息
	 * @author chupp
	 * @date 2018年8月21日
	 * @param id
	 * @return
	 *
	 */
	Object getCompanyBaseData(Long id);

	/**
	 * 
	 * @description 获取评价列表
	 * @author chupp
	 * @date 2018年8月21日
	 * @param params
	 * @return
	 *
	 */
	Object getRecruitEvaluateList(RecruitModularParams params);

	/**
	 * 
	 * @description 评价接口
	 * @author chupp
	 * @date 2018年8月21日
	 * @param params
	 *
	 */
	void saveRecruitEvaluate(RecruitModularParams params);

	/**
	 * 
	 * @description 获取个人简历详情
	 * @author chupp
	 * @date 2018年8月21日
	 * @param params
	 * @return
	 *
	 */
	Object getPersonRecruitResume(RecruitModularParams params);

	/**
	 * 
	 * @description 保存个人简历
	 * @author chupp
	 * @date 2018年8月22日
	 * @param params
	 * @return
	 *
	 */
	Object saveRecruitResumeWorker(RecruitModularParams params);

	/**
	 * 
	 * @description 申诉保存
	 * @author chupp
	 * @date 2018年8月22日
	 * @param params
	 *
	 */
	void saveRecruitAppeal(RecruitModularParams params);

	/**
	 * 
	 * @description 查询电话记录
	 * @author chupp
	 * @date 2018年8月22日
	 * @param params
	 * @return
	 *
	 */
	Object getRecruitTelegram(RecruitModularParams params);

}
