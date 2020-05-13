package com.xywg.admin.modular.recruitModular.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xywg.admin.modular.recruitModular.model.RecruitModularParams;
import com.xywg.admin.modular.recruitModular.model.dto.RecruitResumeDto;
import com.xywg.admin.modular.recruitModular.model.dto.RecruitWorkerDto;

public interface RecruitModularMapper {

	List<RecruitWorkerDto> getRecruitList(@Param("p") RecruitModularParams params);

	RecruitWorkerDto getRecruitDetail(@Param("p") RecruitModularParams params);

	RecruitWorkerDto getCompanyBaseData(@Param("id") Long id);

	List<RecruitWorkerDto> getRecruitEvaluateList(@Param("p") RecruitModularParams params);

	void saveRecruitEvaluate(@Param("p") RecruitModularParams params);

	void updateIsEvaluate(@Param("p") RecruitModularParams params);
	
	RecruitResumeDto getPersonRecruitResume(@Param("p") RecruitModularParams params);

	long saveRecruitResumeWorker(@Param("p") RecruitModularParams params);
	
	long updateRecruitResumeWorker(@Param("p") RecruitModularParams params);

	void saveRecruitAppeal(@Param("p") RecruitModularParams params);

	void updateIsAppeal(@Param("p") RecruitModularParams params);
	
	List<RecruitWorkerDto> getRecruitTelegram(@Param("p") RecruitModularParams params);

	void saveRecruitBrowse(@Param("p") RecruitModularParams params);

	void updateBrowseCount(@Param("p") RecruitModularParams params);

}
