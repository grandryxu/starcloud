package com.xywg.admin.rest.modular.tv;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.device.model.Device;
import com.xywg.admin.modular.device.model.DeviceRecord;
import com.xywg.admin.modular.project.model.PorjectWorkerDto;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.smz.service.IReceiveDataService;
import com.xywg.admin.modular.team.model.TeamMaster;
import com.xywg.admin.rest.common.persistence.model.R;

import io.swagger.annotations.Api;

/**
 * 接收外部数据接口
 * <p>
 * Title: ReceiveDataController
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author duanfen
 * @date 2019年7月24日
 */
@RestController
@RequestMapping("/dataTv")
@Api(value = "ReceiveDataController", description = "对接外部接口")
public class AppReceiveDataController {
	@Autowired
	private IReceiveDataService receiveDataService;
	
	/**
	 * <p>
	 * Title: saveCompany
	 * </p>
	 * <p>
	 * Description:接收公司信息
	 * </p>
	 * 
	 * @author duanfen
	 * @date 2019年7月24日
	 */
	@RequestMapping(value = "/company", method = RequestMethod.POST)
	@ResponseBody
	public R saveCompany(@RequestBody SubContractor contract) {
		this.receiveDataService.saveCompany(contract);
		return R.ok("添加成功");
	}

	/**
	 * <p>
	 * Title: saveProject
	 * </p>
	 * <p>
	 * Description:接收项目信息
	 * </p>
	 * 
	 * @author duanfen
	 * @date 2019年7月24日
	 */
	@RequestMapping(value = "/project", method = RequestMethod.POST)
	@ResponseBody
	public R saveProject(@RequestBody ProjectMaster project) {
		String projectCode = this.receiveDataService.saveProject(project);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("projectCode", projectCode);
		map.put("message", "添加成功");
		return R.ok(map);
	}

	/**
	 * <p>
	 * Title: saveTeamMaster
	 * </p>
	 * <p>
	 * Description:接收外部班组信息
	 * </p>
	 * 
	 * @author duanfen
	 * @date 2019年7月22日
	 */
	@RequestMapping(value = "/team", method = RequestMethod.POST)
	@ResponseBody
	public R saveTeamMaster(@RequestBody TeamMaster teamMaster) {
		this.receiveDataService.saveTeamMaster(teamMaster);
		return R.ok("添加成功");
	}

	/**
	 * <p>
	 * Title: saveDevice
	 * </p>
	 * <p>
	 * Description:接收外部设备信息
	 * </p>
	 * 
	 * @author duanfen
	 * @date 2019年7月22日
	 */
	@RequestMapping(value = "/device", method = RequestMethod.POST)
	@ResponseBody
	public R saveDevice(@RequestBody Device device) {
		this.receiveDataService.saveDevice(device);
		return R.ok("添加成功");
	}

	/**
	 * <p>
	 * Title: saveProjectPersonnel
	 * </p>
	 * <p>
	 * Description:接收外项目人员关系信息
	 * </p>
	 * 
	 * @author duanfen
	 * @date 2019年7月22日
	 */
	@RequestMapping(value = "/person", method = RequestMethod.POST)
	@ResponseBody
	public R saveProjectPersonnel(@RequestBody PorjectWorkerDto projectPerson) {
		String uuid = this.receiveDataService.saveProjectPersonnel(projectPerson);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("projectWorkerId", uuid);
		map.put("message", "添加成功");
		return R.ok(map);
	}

	/**
	 * <p>
	 * Title: saveDeviceRecord
	 * </p>
	 * <p>
	 * Description:接收外部考勤记录
	 * </p>
	 * 
	 * @author duanfen
	 * @date 2019年7月22日
	 */
	@RequestMapping(value = "/record", method = RequestMethod.POST)
	@ResponseBody
	public R saveDeviceRecord(@RequestBody DeviceRecord record) {
		this.receiveDataService.saveDeviceRecord(record);
		return R.ok("添加成功");
	}

}
