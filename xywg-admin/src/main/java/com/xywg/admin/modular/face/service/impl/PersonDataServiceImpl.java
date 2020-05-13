package com.xywg.admin.modular.face.service.impl;

import com.xywg.admin.modular.face.model.PersonData;
import com.xywg.admin.modular.device.dao.DeviceRecordExceptionDataMapper;
import com.xywg.admin.modular.face.dao.PersonDataMapper;
import com.xywg.admin.modular.face.service.IPersonDataService;
import com.xywg.admin.modular.worker.dao.WorkerMasterMapper;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 人脸模型表 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-30
 */
@Service
public class PersonDataServiceImpl extends ServiceImpl<PersonDataMapper, PersonData> implements IPersonDataService {

	@Autowired
	private WorkerMasterMapper workerMasterMapper;
	
	@Autowired
	private DeviceRecordExceptionDataMapper deviceRecordExceptionDataMapper;
	
	@Override
	public void savePersonData(PersonData data,String userId) {
		WorkerMaster personnel = workerMasterMapper.getWorkerByIdCard(userId,1);
		if(personnel != null) {
//			Long id = personnel.getId();
			this.baseMapper.deleteData(personnel, data.getAlgVersion());
			data.setIdCardType(personnel.getIdCardType());
			data.setIdCardNumber(personnel.getIdCardNumber());
			this.baseMapper.create(data);
		}else {
			deviceRecordExceptionDataMapper.removeExceptionFaceLike(data);
			deviceRecordExceptionDataMapper.saveExceptionData(data);
		}
	}

}
