package com.xywg.admin.modular.company.service;

import com.xywg.admin.modular.company.model.ContractorWorker;
import com.xywg.admin.modular.company.model.dto.ContractorWorkerDto;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 企业工人表 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-28
 */
public interface IContractorWorkerService extends IService<ContractorWorker> {

	/**
	 * 
	 * @description 获取人员年龄分布
	 * @author chupp
	 * @date 2018年6月22日
	 * @return
	 *
	 */
	List<ContractorWorkerDto> getAgeRange();

	/**
	 * 
	 * @description 获取人员籍贯分布
	 * @author chupp
	 * @date 2018年6月22日
	 * @return
	 *
	 */
	List<ContractorWorkerDto> getPlaceCodeRange();

    void addcontractorWorker(List<Object> addList);
}
