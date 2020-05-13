package com.xywg.admin.rest.modular.device.v116;

import com.xywg.admin.modular.device.model.DeviceRecord;
import com.xywg.admin.modular.device.model.dto.DeviceRecordDto2;
import com.xywg.admin.modular.device.model.dto.ParamDto;
import com.xywg.admin.modular.device.service.IDeviceRecordService;
import com.xywg.admin.modular.project.model.AppProjectListByPersonVo;
import com.xywg.admin.modular.project.service.IProjectWorkerService;
import com.xywg.admin.modular.system.model.FileInfo;
import com.xywg.admin.modular.system.service.IFileInfoService;
import com.xywg.admin.rest.common.persistence.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RequestMapping("/v116/deviceRest")
@Api(description = "考勤控制")
public class V116DeviceRestController {

	@Autowired
    private IDeviceRecordService deviceRecordService;



	/**
	 * 
	 * @description 查询工人某天考勤列表
	 * @author cw
	 * @date 2018年9月17日
	 * @param deviceRecord
	 * @return
	 *
	 */
	@ApiOperation(value="v116查询工人某天考勤列表", notes="")
    @RequestMapping(value = "v116GetDayRecord",method = RequestMethod.POST)
    public R v116GetDayRecord(@RequestBody DeviceRecordDto2 deviceRecord) {
    	return R.ok(deviceRecordService.v116GetDayRecord(deviceRecord,deviceRecord.getRemark()));
    }

	/**
	 *
	 * @description 查询工人在某个项目里的月考勤列表
	 * @author cw
	 * @date 2018年9月17日
	 * @param deviceRecord
	 * @return
	 *
	 */
	@ApiOperation(value="v116查询工人在某个项目里的月考勤列表", notes="")
	@RequestMapping(value = "v116GetMonthRecords",method = RequestMethod.POST)
	public R v116GetMonthRecords(@RequestBody DeviceRecordDto2 deviceRecord) {
		return R.ok(deviceRecordService.v116GetMonthRecords(deviceRecord,deviceRecord.getRemark()));
	}
	
	/**
	 * 
	 * @description 获取班组指定人员历史考勤记录
	 * @author chupp
	 * @date 2018年9月25日
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
    		@RequestParam String teamMemeberIdCardType,@RequestParam String teamMemeberIdCardNumber,
    		@RequestParam Integer pageSize,@RequestParam Integer pageNo) {
    	return R.ok(deviceRecordService.getByClassMemeberV116(idCardType,idCardNumber,teamMemeberIdCardType,
    			teamMemeberIdCardNumber,pageSize,pageNo));
    }
	
	/**
	 * 
	 * @description 获取班组人员考勤记录
	 * @author chupp
	 * @date 2018年9月25日
	 * @param idCardType
	 * @param idCardNumber
	 * @param teamMemeber
	 * @return
	 *
	 */
	@ApiOperation(value="获取班组人员考勤记录", notes="")
    @RequestMapping(value = "getByClass",method = RequestMethod.GET)
    public R getByClass(@RequestParam String idCardType,@RequestParam String idCardNumber,
    		@RequestParam(required = false) String teamMemeber,@RequestParam Integer pageSize,
    		@RequestParam Integer pageNo) {
    	return R.ok(deviceRecordService.getByClassV116(idCardType,idCardNumber,teamMemeber,pageSize,pageNo));
    }
	
	/**
	 * 
	 * @description 查询工人在某个项目里的月考勤列表
	 * @author chupp
	 * @date 2018年9月25日
	 * @param deviceRecord
	 * @return
	 *
	 */
	@ApiOperation(value="查询工人在某个项目里的月考勤列表", notes="")
    @RequestMapping(value = "getMonthRecords",method = RequestMethod.POST)
    public R getMonthRecords(@RequestBody DeviceRecordDto2 deviceRecord) {
    	return R.ok(deviceRecordService.getMonthRecordsV116(deviceRecord,deviceRecord.getRemark(),deviceRecord.getPageSize(),deviceRecord.getPageNo()));
    }
	
	/**
	 * 
	 * @description 查询工人某天考勤列表
	 * @author chupp
	 * @date 2018年9月25日
	 * @param deviceRecord
	 * @return
	 *
	 */
	@ApiOperation(value="查询工人某天考勤列表", notes="")
    @RequestMapping(value = "getDayRecord",method = RequestMethod.POST)
    public R getDayRecord(@RequestBody DeviceRecordDto2 deviceRecord) {
    	return R.ok(deviceRecordService.getDayRecordV116(deviceRecord,deviceRecord.getRemark(),deviceRecord.getPageSize(),deviceRecord.getPageNo()));
    }
	
	/**
	 * 
	 * @description 查询工人历史考勤记录
	 * @author chupp
	 * @date 2018年9月25日
	 * @param deviceRecord
	 * @return
	 *
	 */
	@ApiOperation(value="查询工人历史考勤记录", notes="")
    @RequestMapping(value = "getRecordsByAll",method = RequestMethod.POST)
    public R getRecordsByAll(@RequestBody DeviceRecordDto2 deviceRecord) {
    	return R.ok(deviceRecordService.getRecordsByAllV116(deviceRecord));
    }
}

