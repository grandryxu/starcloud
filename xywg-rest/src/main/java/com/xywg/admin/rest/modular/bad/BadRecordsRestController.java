package com.xywg.admin.rest.modular.bad;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xywg.admin.modular.bad.dto.BadRecordsRequestDTO;
import com.xywg.admin.modular.bad.dto.BadRecordsResultVO;
import com.xywg.admin.modular.bad.model.WorkerBadRecords;
import com.xywg.admin.modular.bad.model.WorkerBadRecordsVO;
import com.xywg.admin.modular.bad.service.IWorkerBadRecordsService;
import com.xywg.admin.rest.common.persistence.model.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 不良记录接口控制类
 * 
 * @author shily
 * @date 2018年6月8日
 */
@RestController
@Api(description = "不良记录接口")
@RequestMapping("/badRecords")
public class BadRecordsRestController {

	 @Autowired
	 private IWorkerBadRecordsService workerBadRecordsService;

	/**
	 * 获取不良记录信息列表
	 * 
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "获取不良记录信息列表")
	@GetMapping(value = "/getBadRecordsList")
	public R getBadRecordsList(@RequestParam(required = true) Integer type){
		List<WorkerBadRecordsVO> list=workerBadRecordsService.getPageList(type);
		/*List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for(WorkerBadRecordsVO item : list){
			Map<String, Object> map = (Map<String, Object>) JSON.toJSON(item);
			int code = 0;
			if(StrUtil.hasBlank(map.get("badRecordTypeCode")+"")){
				code = Integer.parseInt(map.get("badRecordTypeCode")+"");
			}
			map.put("badRecordType",code);
			resultList.add(map);
		}*/
		return R.ok(list);
	}
	
	/**
	 * 获取待审核不良记录信息列表
	 * 
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "获取待审核不良记录信息列表")
	@GetMapping(value = "/getToAuditBadRecordsList")
	public R getToAuditBadRecordsList(@RequestParam(required = true) Long id){
		List<WorkerBadRecordsVO> list=workerBadRecordsService.getToAuditPageList(id);
		/*List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for(WorkerBadRecordsVO item : list){
			Map<String, Object> map = (Map<String, Object>) JSON.toJSON(item);
			int code = 0;
			if(StrUtil.hasBlank(map.get("badRecordType")+"")){
				code = Integer.parseInt(map.get("badRecordType")+"");
			}
			map.put("badRecordType",code);
			resultList.add(map);
		}*/
		return R.ok(list);
	}
	
	/**
	 * 新增不良记录
	 * 
	 * @return
	 */
	@ApiOperation(value = "新增不良记录")
	@PostMapping(value = "/addBadRecords")
	public R addBadRecords(@RequestBody WorkerBadRecords workerBadRecords){
		workerBadRecords.setType(1);
		workerBadRecords.setIsAudit(1);
		Boolean result = workerBadRecordsService.insertWorkerBadRecords(workerBadRecords);
		if (result) {
			return R.ok();
		}else {
			return R.error("该工人不属于此项目");
		}
		
	}
	
	/**
	 * 获取不良记录待审核的事件总个数
	 * @return
	 */
	@ApiOperation(value = "获取待审核的事件总个数")
	@GetMapping(value = "/getToAuditCount")
	public R getToAuditCount(@RequestParam(required = true) Long id){
		BadRecordsResultVO resultVo = workerBadRecordsService.getToAuditBadRecordsCount(id);
		return R.ok(resultVo);
	}
	
	/**
	 * 审核不良记录
	 * @param id
	 * @param isAudit
	 * @return
	 */
	@ApiOperation(value = "审核不良记录")
	@PostMapping(value = "/auditBadRecords")
	public R auditBadRecords(@RequestBody BadRecordsRequestDTO request){
		workerBadRecordsService.auditBadRecords(request.getId(), request.getIsAudit());
		return R.ok();
	}

}
