package com.xywg.admin.rest.modular.safetyHelmet.v120;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xywg.admin.modular.device.service.ISafetyHelmetService;
import com.xywg.admin.modular.recruitModular.model.RecruitModularParams;
import com.xywg.admin.rest.common.persistence.model.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v120/safetyHelmetRest")
@Api(description = "安全帽模块")
public class SafetyHelmetRestController {

	@Autowired
    private ISafetyHelmetService safetyHelmetService;

	/**
	 * 
	 * @description 添加安全帽
	 * @author chupp
	 * @date 2018年9月10日
	 * @param params
	 * @return
	 *
	 */
	@ApiOperation(value="添加安全帽", notes="")
    @RequestMapping(value = "addSafetyHelmet",method = RequestMethod.POST)
    public R addSafetyHelmet(@RequestBody RecruitModularParams params) {
		safetyHelmetService.addSafetyHelmet(params);
    	return R.ok();
    }
	
	@ApiOperation(value="更新安全帽", notes="")
    @RequestMapping(value = "updateSafetyHelmet",method = RequestMethod.POST)
    public R updateSafetyHelmet(@RequestBody RecruitModularParams params) {
		safetyHelmetService.updateSafetyHelmet(params);
    	return R.ok();
    }
	
	/**
	 * 
	 * @description 绑定安全帽
	 * @author chupp
	 * @date 2018年9月10日
	 * @param params
	 * @return
	 *
	 */
	@ApiOperation(value="绑定安全帽", notes="")
    @RequestMapping(value = "bindSafetyHelmet",method = RequestMethod.POST)
    public R bindSafetyHelmet(@RequestBody RecruitModularParams params) {
		safetyHelmetService.bindSafetyHelmet(params);
    	return R.ok();
    }
}
