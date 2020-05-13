package com.xywg.admin.modular.mina;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xywg.admin.modular.device.model.Device;
import com.xywg.admin.modular.device.model.DeviceRecord;
import com.xywg.admin.modular.device.model.DeviceRecordExceptionData;
import com.xywg.admin.modular.device.service.IDeviceRecordExceptionDataService;
import com.xywg.admin.modular.device.service.IDeviceService;

//import com.xingyun.labor.device.RecordTpm;
@Service
public class QuartzTask {

	@Autowired
	private IDeviceService deviceService;

	/**
	 * 上班
	 */
	private final Integer onWork = 3;

	/**
	 * 下班
	 */
	private final Integer offWork = 4;

	@Autowired
	private IDeviceRecordExceptionDataService deviceRecordExceptionDataService;

	private static  Logger logger = LoggerFactory.getLogger(QuartzTask.class);

	/**
	 * 
	 * @param
	 * @Description: 心跳信息
	 * @author cxl
	 * @date 2017年12月29日 下午2:41:13
	 */
	public void heartbeat() {
		this.logger.debug(new StringBuilder().append("Q---------Heartbeat:")
				.append(new Timestamp(System.currentTimeMillis())).toString());

		List<Long> devices = TheApp.getCacheDevice();
		if (Utils.isEmpty(devices)) {
			return;
		}
		this.deviceService.heartbeat(devices);
	}

	/**
	 * 
	 * @param
	 * @Description: 实例化数据到库
	 * @author cxl
	 * @date 2017年12月29日 下午2:41:34
	 */
	public void persistRecord() {
		this.logger.debug(new StringBuilder().append("Q---------Record persist:")
				.append(new Timestamp(System.currentTimeMillis())).toString());
		long endline = System.currentTimeMillis();
		// while (true) {
		List<DeviceRecord> records = TheApp.getCacheRecord(endline);
		// 正常数据
		List<DeviceRecord> recordList = new ArrayList<DeviceRecord>();
		// 考勤机禁用状态的数据
		List<DeviceRecordExceptionData> recordExceptionDataList = new ArrayList<DeviceRecordExceptionData>();
		if (Utils.isEmpty(records)) {
			return;
		}
		try {
			for (DeviceRecord record : records) {
				Device device = deviceService.selectDeviceById(record.getDevice().getId());
				record.setDeviceSn(device.getSn());
				if (device != null) {
					Integer type = device.getType();
					if (onWork.equals(type)) {
						// 上班
						record.setDeviceType(onWork);
					} else if (offWork.equals(type)) {
						// 下班
						record.setDeviceType(offWork);
					} else {
						// 待处理
						record.setDeviceType(onWork);
					}
					if (record.getDevice().getState() == 0) {// 考勤机停用状态
						DeviceRecordExceptionData deviceRecordExceptionData = Utils.Json2Java(Utils.Java2Json(record),
								DeviceRecordExceptionData.class);
						deviceRecordExceptionData.setExceptionType("3");
						recordExceptionDataList.add(deviceRecordExceptionData);
					} else if (record.getDevice().getState() == 1) {// 考勤机正常状态
						record.setOrganizationCode(device.getOrganizationCode());
						record.setProjectCode(device.getProjectCode());
						record.setDevice(device);
						recordList.add(record);
					} else {
						return;
					}
				}
				// 正常数据入库
				if (recordList.size() != 0) {
					this.deviceService.saveRecord(recordList);
				}
				// 停用考勤机数据入口
				if (recordExceptionDataList.size() != 0) {
					this.deviceRecordExceptionDataService.saveDisabledRecord(recordExceptionDataList);
				}
			}
		} catch (Exception e) {
			this.logger.error(new StringBuilder().append("持久化考勤数据失败：").append(e.getMessage()).toString());
			logger.error(e.getMessage());
			TheApp.cacheRecord(records);
		}
		// }
	}
}