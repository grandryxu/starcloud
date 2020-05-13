package com.xywg.admin.rest.modular.health.v120;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xywg.admin.modular.health.service.IWorkerHealthService;
import com.xywg.admin.rest.common.persistence.model.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @description 健康模块
 * 
 * @author chupp
 *
 * @date 2018年8月20日
 *
 */
@RestController
@RequestMapping("/v120/healthRest")
@Api(description = "健康模块")
public class HealthController {

	@Autowired
    private IWorkerHealthService workerHealthService;

	/**
	 * 
	 * @description 查询当前登录工人最新的健康信息
	 * @author chupp
	 * @date 2018年8月20日
	 * @param idCardType
	 * @param idCardNumber
	 * @return
	 *
	 */
	@ApiOperation(value="查询当前登录工人最新的健康信息", notes="")
    @RequestMapping(value = "getNowHealth",method = RequestMethod.GET)
    public R getNowHealth(@RequestParam String idCardType,@RequestParam String idCardNumber) {
    	return R.ok(workerHealthService.getNowHealth(idCardType,idCardNumber));
    }
	
	/**
	 * 
	 * @description 根据日期查询工人的历史健康信息
	 * @author chupp
	 * @date 2018年8月20日
	 * @param idCardType
	 * @param idCardNumber
	 * @param nowDate
	 * @param pageNo
	 * @param pageSize
	 * @return
	 *
	 */
	@ApiOperation(value="查询当前登录工人最新的健康信息", notes="")
    @RequestMapping(value = "getHealthListByDate",method = RequestMethod.GET)
    public R getHealthListByDate(@RequestParam String idCardType,@RequestParam String idCardNumber,
    		@RequestParam String nowDate,@RequestParam Integer pageNo,@RequestParam Integer pageSize) {
    	return R.ok(workerHealthService.getHealthListByDate(idCardType,idCardNumber,nowDate,pageNo,pageSize));
    }
}
