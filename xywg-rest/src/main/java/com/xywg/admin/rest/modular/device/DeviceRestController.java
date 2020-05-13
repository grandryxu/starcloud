package com.xywg.admin.rest.modular.device;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xywg.admin.modular.device.model.DeviceRecord;
import com.xywg.admin.modular.device.model.dto.ParamDto;
import com.xywg.admin.modular.device.service.IDeviceRecordService;
import com.xywg.admin.modular.project.model.AppProjectListByPersonVo;
import com.xywg.admin.modular.project.service.IProjectWorkerService;
import com.xywg.admin.modular.system.model.FileInfo;
import com.xywg.admin.modular.system.service.IFileInfoService;
import com.xywg.admin.rest.common.persistence.model.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @description 考勤控制类
 * 
 * @author chupp
 *
 * @date 2018年5月30日
 *
 */
@RestController
@RequestMapping("/deviceRest")
@Api(description = "考勤控制")
public class DeviceRestController {

	@Autowired
    private IDeviceRecordService deviceRecordService;
	
	@Autowired
    private IProjectWorkerService projectWorkerService;
	
	@Autowired
    private IFileInfoService fileInfoService;

	/**
	 * 
	 * @description 获取工人在建项目列表
	 * @author chupp
	 * @date 2018年5月30日
	 * @param idCardNumber
	 * @param idCardType
	 * @return
	 *
	 */
	@ApiOperation(value="获取工人在建项目列表", notes="")
    @RequestMapping(value = "getProjectWorkerList",method = RequestMethod.GET)
    public R getProjectWorkerList(@RequestParam String idCardNumber,@RequestParam String idCardType) {
    	return R.ok(projectWorkerService.getProjectWorkerList(idCardNumber,idCardType));
    }
	
	/**
	 * 
	 * @description 手机考勤
	 * @author chupp
	 * @date 2018年5月30日
	 * @param deviceRecord
	 * @return
	 *
	 */
	@ApiOperation(value="手机考勤", notes="")
    @RequestMapping(value = "record",method = RequestMethod.POST)
    public R record(@RequestBody DeviceRecord deviceRecord)throws IOException {
		deviceRecordService.record(deviceRecord);
    	return R.ok();
    }
	
	/**
	 * 
	 * @description 查询工人某天考勤列表
	 * @author chupp
	 * @date 2018年5月31日
	 * @param deviceRecord
	 * @return
	 *
	 */
	@ApiOperation(value="查询工人某天考勤列表", notes="")
    @RequestMapping(value = "getDayRecord",method = RequestMethod.POST)
    public R getDayRecord(@RequestBody DeviceRecord deviceRecord) {
    	return R.ok(deviceRecordService.getDayRecord(deviceRecord,deviceRecord.getRemark()));
    }
	
	/**
	 * 
	 * @description 查询工人历史考勤记录
	 * @author chupp
	 * @date 2018年5月31日
	 * @param deviceRecord
	 * @return
	 *
	 */
	@ApiOperation(value="查询工人历史考勤记录", notes="")
    @RequestMapping(value = "getRecordsByAll",method = RequestMethod.POST)
    public R getRecordsByAll(@RequestBody DeviceRecord deviceRecord) {
    	return R.ok(deviceRecordService.getRecordsByAll(deviceRecord));
    }
	
	/**
	 * 
	 * @description 获取某人在项目下总共出勤天数
	 * @author chupp
	 * @date 2018年5月31日
	 * @param deviceRecord
	 * @return
	 *
	 */
	@ApiOperation(value="获取某人在项目下总共出勤天数", notes="")
    @RequestMapping(value = "getAllRecordByProject",method = RequestMethod.POST)
    public R getAllRecordByProject(@RequestBody DeviceRecord deviceRecord) {
    	return R.ok(deviceRecordService.getAllRecordByProject(deviceRecord));
    }
	
	/**
	 * 
	 * @description 查询工人在某个项目里的月考勤列表
	 * @author chupp
	 * @date 2018年5月31日
	 * @param deviceRecord
	 * @return
	 *
	 */
	@ApiOperation(value="查询工人在某个项目里的月考勤列表", notes="")
    @RequestMapping(value = "getMonthRecords",method = RequestMethod.POST)
    public R getMonthRecords(@RequestBody DeviceRecord deviceRecord) {
    	return R.ok(deviceRecordService.getMonthRecords(deviceRecord,deviceRecord.getRemark()));
    }
	
	/**
	 * 
	 * @description 获取公司的某项目的今日考勤人数
	 * @author chupp
	 * @date 2018年5月31日
	 * @param deviceRecord
	 * @return
	 *
	 */
	@ApiOperation(value="获取公司的某项目的今日考勤人数", notes="")
    @RequestMapping(value = "getWorkerTotalCount",method = RequestMethod.POST)
    public R getWorkerTotalCount(@RequestBody DeviceRecord deviceRecord) {
    	return R.ok(deviceRecordService.getWorkerTotalCountFromPhone(deviceRecord));
    }
	
	/**
	 * 
	 * @description 根据考勤记录ID更新是否有效
	 * @author chupp
	 * @date 2018年6月4日
	 * @param deviceRecord
	 * @return
	 *
	 */
	@ApiOperation(value="根据考勤记录ID更新是否有效", notes="")
    @RequestMapping(value = "updateRecordById",method = RequestMethod.POST)
    public R updateRecordById(@RequestBody DeviceRecord deviceRecord) {
		deviceRecordService.updateRecordById(deviceRecord.getId(),Integer.parseInt(deviceRecord.getRemark()));
    	return R.ok();
    }
	
	/**
	 * 
	 * @description 获取日期区间考勤天数
	 * @author chupp
	 * @date 2018年6月4日
	 * @param workerList
	 * @param projectCode
	 * @param endDate
	 * @return
	 *
	 */
	@ApiOperation(value="获取日期区间考勤天数", notes="")
    @RequestMapping(value = "getDaySectionRecord",method = RequestMethod.POST)
    public R getDaySectionRecord(@RequestBody ParamDto paramDto) {
    	return R.ok(deviceRecordService.getDaySectionRecord(paramDto.getWorkerList(),paramDto.getProjectCode(),paramDto.getEndDate()));
    }
	
	/**
	 * 
	 * @description 获取公司的某项目的最近7天出勤人数
	 * @author chupp
	 * @date 2018年6月4日
	 * @param organizationCode
	 * @param projectCode
	 * @return
	 *
	 */
	@ApiOperation(value="获取公司的某项目的最近7天出勤人数", notes="")
    @RequestMapping(value = "getTotalNumFromSevenDays",method = RequestMethod.GET)
    public R getTotalNumFromSevenDays(@RequestParam String organizationCode,@RequestParam String projectCode) {
    	return R.ok(deviceRecordService.getTotalNumFromSevenDays(organizationCode,projectCode));
    }
	
	/**
	 * 
	 * @description 获取班组人员考勤记录
	 * @author chupp
	 * @date 2018年6月4日
	 * @param idCardType
	 * @param idCardNumber
	 * @param teamMemeber
	 * @return
	 *
	 */
	@ApiOperation(value="获取班组人员考勤记录", notes="")
    @RequestMapping(value = "getByClass",method = RequestMethod.GET)
    public R getByClass(@RequestParam String idCardType,@RequestParam String idCardNumber,
    		@RequestParam(required = false) String teamMemeber) {
    	return R.ok(deviceRecordService.getByClass(idCardType,idCardNumber,teamMemeber));
    }
	
	/**
	 * 
	 * @description 获取班组指定人员历史考勤记录
	 * @author chupp
	 * @date 2018年6月4日
	 * @param idCardType
	 * @param idCardNumber
	 * @param teamMemeberIdCardType
	 * @param teamMemeberIdCardNumber
	 * @return
	 *
	 */
	@ApiOperation(value="获取班组指定人员历史考勤记录", notes="")
    @RequestMapping(value = "getByClassMemeber",method = RequestMethod.GET)
    public R getByClassMemeber(@RequestParam String idCardType,@RequestParam String idCardNumber,
    		@RequestParam String teamMemeberIdCardType,@RequestParam String teamMemeberIdCardNumber) {
    	return R.ok(deviceRecordService.getByClassMemeber(idCardType,idCardNumber,teamMemeberIdCardType,
    			teamMemeberIdCardNumber));
    }
	
	@ApiOperation(value="根据登录人获取项目列表")
	@GetMapping(value="/getProjectsByUserId")
	 public R getProjectsByUserId(@RequestParam Integer userId) {
		List<AppProjectListByPersonVo> list =	deviceRecordService.getProjectsByUserId(userId);
	    	return R.ok(list);
	    }
	
	
	/**
	 * 
	 * @description 二维码考勤图片地址上传获取ID
	 * @author chupp
	 * @date 2018年6月27日
	 * @param fileInfo
	 * @return
	 *
	 */
	@ApiOperation(value="二维码考勤图片地址上传获取ID", notes="")
    @RequestMapping(value = "getIdFromImagePath",method = RequestMethod.POST)
    public R getIdFromImagePath(@RequestBody FileInfo fileInfo) {
    	return R.ok((Object)fileInfoService.getIdFromImagePath(fileInfo));
    }
}
