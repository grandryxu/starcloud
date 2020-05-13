package com.xywg.admin.modular.company.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xywg.admin.modular.system.service.AccountProjectService;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.company.dao.ContractorWorkerMapper;
import com.xywg.admin.modular.company.model.ContractorWorker;
import com.xywg.admin.modular.company.model.dto.ContractorWorkerDto;
import com.xywg.admin.modular.company.service.IContractorWorkerService;
import com.xywg.admin.modular.system.service.IDeptService;

/**
 * <p>
 * 企业工人表 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-28
 */
@Service
public class ContractorWorkerServiceImpl extends ServiceImpl<ContractorWorkerMapper, ContractorWorker> implements IContractorWorkerService {

	@Autowired
    private IDeptService deptService;
	@Autowired
	private AccountProjectService accountProjectService;
	
	/**
	 * 
	 * @description 获取人员年龄分布
	 * @author chupp
	 * @date 2018年6月22日
	 * @return
	 * @see com.xywg.admin.modular.company.service.IContractorWorkerService#getAgeRange()
	 *
	 */
	@Override
	public List<ContractorWorkerDto> getAgeRange() {
		Integer deptId = ShiroKit.getUser().getDeptId();
		if(deptId == null || deptId == 0) {
			return new ArrayList<ContractorWorkerDto>();
		}
		List<String> list = deptService.getOrganizationCodeByDeptId(deptId);
		return this.baseMapper.getAgeRange(list,accountProjectService.getProjectCodes());
	}

	/**
	 * 
	 * @description 获取人员籍贯分布
	 * @author chupp
	 * @date 2018年6月22日
	 * @return
	 * @see com.xywg.admin.modular.company.service.IContractorWorkerService#getPlaceCodeRange()
	 *
	 */
	@Override
	public List<ContractorWorkerDto> getPlaceCodeRange() {
		Integer deptId = ShiroKit.getUser().getDeptId();
		if(deptId == null || deptId == 0) {
			return new ArrayList<ContractorWorkerDto>();
		}
		List<String> list = deptService.getOrganizationCodeByDeptId(deptId);
		return this.baseMapper.getPlaceCodeRange(list);
	}

	@Override
	public void addcontractorWorker(List<Object> addList) {
		for (Object o : addList) {
			ContractorWorker contractorWorker = new ContractorWorker();
			stringToDateException();
			try {
				org.apache.commons.beanutils.BeanUtils.copyProperties(contractorWorker, o);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			Map<String,Long> contractorWorkerMap=baseMapper.selectIdByIdCardAndOrgCode(contractorWorker.getIdCardNumber(),contractorWorker.getOrganizationCode());
			if (contractorWorkerMap.get("num")==0) {
				contractorWorker.setId(null);
				insert(contractorWorker);
			}
		}
	}


	//解决 BeanUtils.copyProperties()的string转date异常
	private void stringToDateException() {
		ConvertUtils.register(new Converter() {
			@Override
			public Object convert(Class type, Object value) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					return simpleDateFormat.parse(value.toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return null;
			}
		}, java.util.Date.class);
	}
}
