package com.xywg.admin.modular.smz.service;

import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.device.model.Device;
import com.xywg.admin.modular.device.model.DeviceRecord;
import com.xywg.admin.modular.project.model.PorjectWorkerDto;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.team.model.TeamMaster;

/**
 * <p>
 * Title: IReceiveDataService
 * </p>
 * <p>
 * Description:接收外部数据接口
 * </p>
 * 
 * @author duanfen
 * @date 2019年7月22日
 */
public interface IReceiveDataService {
	/**
	 * 接收公司信息
	 * @param project
	 * @return
	 */
	void saveCompany(SubContractor contract);
	
	/**
	 * 接收外部项目信息
	 * <p>Title: saveProject</p>  
	 * <p>Description: </p>  
	 * @author duanfen  
	 * @date 2019年7月22日
	 */
	String saveProject(ProjectMaster project);

	/**
	 * 接收外部班组信息
	 * <p>Title: saveTeamMaster</p>  
	 * <p>Description: </p>  
	 * @author duanfen  
	 * @date 2019年7月22日
	 */
	void saveTeamMaster(TeamMaster teamMaster);

	/**
	 * 接收外项目人员关系信息
	 * <p>Title: saveProjectPersonnel</p>  
	 * <p>Description: </p>  
	 * @author duanfen  
	 * @date 2019年7月22日
	 */
	String saveProjectPersonnel(PorjectWorkerDto projectPerson);

	/**
	 * 接收外部设备信息
	 * <p>Title: saveDevice</p>  
	 * <p>Description: </p>  
	 * @author duanfen  
	 * @date 2019年7月22日
	 */
	void saveDevice(Device device);

	/**
	 * 接收外部考勤记录
	 * <p>Title: saveDeviceRecord</p>  
	 * <p>Description: </p>  
	 * @author duanfen  
	 * @date 2019年7月22日
	 */
	void saveDeviceRecord(DeviceRecord deviceRecords);
}
