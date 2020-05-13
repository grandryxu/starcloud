package com.xywg.admin.rest.modular.bad.v116;

import com.xywg.admin.modular.bad.dto.BadRecordsRequestDTO;
import com.xywg.admin.modular.bad.dto.BadRecordsResultVO;
import com.xywg.admin.modular.bad.model.WorkerBadRecords;
import com.xywg.admin.modular.bad.model.WorkerBadRecordsVO;
import com.xywg.admin.modular.bad.service.IWorkerBadRecordsService;
import com.xywg.admin.rest.common.persistence.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 不良记录接口控制类
 * 
 * @author shily
 * @date 2018年6月8日
 */
@RestController
@Api(description = "不良记录接口")
@RequestMapping("/v116/badRecords")
public class V116BadRecordsRestController {

	 @Autowired
	 private IWorkerBadRecordsService workerBadRecordsService;

	/**
	 * 获取不良记录信息列表
	 * 
	 * @param type
	 * @return
	 * @author cw
	 */
	@ApiOperation(value = "v116获取不良记录信息列表")
	@GetMapping(value = "/v116GetBadRecordsList")
	public R v116GetBadRecordsList(@RequestParam(required = true) Integer type ,@RequestParam Integer pageNo , @RequestParam Integer pageSize
	){
		List<WorkerBadRecordsVO> list=workerBadRecordsService.v116GetPageList(type,pageNo,pageSize);
		return R.ok(list);
	}

	/**
	 * 获取待审核不良记录信息列表
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "v116获取待审核不良记录信息列表")
	@GetMapping(value = "/v116GetToAuditBadRecordsList")
	public R v116GetToAuditBadRecordsList(@RequestParam(required = true) Long id ,@RequestParam Integer pageNo , @RequestParam Integer pageSize){
		List<WorkerBadRecordsVO> list=workerBadRecordsService.v116GetToAuditBadRecordsList(id,pageNo,pageSize);
		return R.ok(list);
	}

}
