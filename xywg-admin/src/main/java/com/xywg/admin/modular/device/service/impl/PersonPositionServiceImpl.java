package com.xywg.admin.modular.device.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.modular.device.dao.PersonPositionMapper;
import com.xywg.admin.modular.device.model.PersonPosition;
import com.xywg.admin.modular.device.model.dto.DeviceRecordDto;
import com.xywg.admin.modular.device.model.dto.WorkerDto;
import com.xywg.admin.modular.device.service.IDeviceRecordService;
import com.xywg.admin.modular.device.service.IPersonPositionService;
import com.xywg.admin.modular.device.utils.MathRadius;
import com.xywg.admin.modular.project.model.ProjectMaster;

/**
 * <p>
 * 考勤帽定位 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-19
 */
@Service
public class PersonPositionServiceImpl extends ServiceImpl<PersonPositionMapper, PersonPosition> implements IPersonPositionService {
	
	@Autowired
	private PersonPositionMapper personPositionMapper;
	
	@Autowired
	private IDeviceRecordService deviceRecordService;
	
	@Override
	public int updatePersonPositionInfo() {
		return personPositionMapper.updatePersonPositionInfo();
	}

	@Override
	public void insertRecord() {
		// 查询所有未生成考勤记录
		List<PersonPosition> list = this.personPositionMapper.findNotCalculate();
		if (list.size() == 0) {
			return;
		}
		for (PersonPosition pp : list) {
			if (pp.getProject() == null || pp.getPersonel()== null) {
				continue;
			}
			ProjectMaster project = pp.getProject();
			WorkerDto person = pp.getPersonel();
			// 计算考勤距离
			Double radius = MathRadius.getDistance(project.getLat(),
					project.getLng(), pp.getBdLat(), pp.getBdLng());
			// 查询最近一次考勤状态
			Boolean range = this.personPositionMapper.findLastRecord(person.getIdCardType(),person.getIdCardNumber());
			BigDecimal recordDistance = new BigDecimal(radius);
			BigDecimal projectRadius = new BigDecimal(project.getRadius());
			DeviceRecordDto device = new DeviceRecordDto(person, pp);
			// 判断考勤点是否在项目内
			if (recordDistance.compareTo(projectRadius) <= 0) {
				device.setIsValid("1");
				range = true;
			} else {
				device.setIsValid("0");
				range = false;
			}
			device.setSource(3);
			deviceRecordService.saveDevice(device);
			this.personPositionMapper.updateRange(range, pp.getId());
		}
	}

	@Override
	public List<PersonPosition> queryPositionByImei(String idCardNumber, String imei, String startDate, String endDate) {
		Map<String, Object> params = new HashMap<>();
		params.put("imei", imei);
		params.put("idCardNumber", idCardNumber);
		params.put("startDate", startDate+" 00:00:00");
		params.put("endDate", endDate+" 23:59:59");
		return this.baseMapper.queryPositionByImei(params);
	}
}
