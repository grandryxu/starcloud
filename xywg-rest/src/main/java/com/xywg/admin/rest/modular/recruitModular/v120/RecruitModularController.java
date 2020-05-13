package com.xywg.admin.rest.modular.recruitModular.v120;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xywg.admin.modular.recruitModular.model.RecruitModularParams;
import com.xywg.admin.modular.recruitModular.service.IRecruitModularService;
import com.xywg.admin.rest.common.persistence.model.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @description 招聘模块(部分)
 * 
 * @author chupp
 *
 * @date 2018年8月20日
 * 
 */
@RestController
@RequestMapping("/v120/recruitModularRest")
@Api(description = "招聘模块")
public class RecruitModularController {

	@Autowired
    private IRecruitModularService recruitModularService;

	/**
	 * 
	 * @description 获取岗位列表
	 * @author chupp
	 * @date 2018年8月20日
	 * @param params
	 * @return
	 *
	 */
	@ApiOperation(value="获取岗位列表", notes="")
    @RequestMapping(value = "getRecruitList",method = RequestMethod.POST)
    public R getRecruitList(@RequestBody RecruitModularParams params) {
    	return R.ok(recruitModularService.getRecruitList(params));
    }
	
	/**
	 * 
	 * @description 获取岗位详情
	 * @author chupp
	 * @date 2018年8月21日
	 * @param params
	 * @return
	 *
	 */
	@ApiOperation(value="获取岗位详情", notes="")
    @RequestMapping(value = "getRecruitDetail",method = RequestMethod.POST)
    public R getRecruitDetail(@RequestBody RecruitModularParams params) {
    	return R.ok(recruitModularService.getRecruitDetail(params));
    }
	
	/**
	 * 
	 * @description 获取公司基本信息
	 * @author chupp
	 * @date 2018年8月21日
	 * @param id
	 * @return
	 *
	 */
	@ApiOperation(value="获取公司基本信息", notes="")
    @RequestMapping(value = "getCompanyBaseData",method = RequestMethod.POST)
    public R getCompanyBaseData(@RequestBody RecruitModularParams params) {
    	return R.ok(recruitModularService.getCompanyBaseData(params.getId()));
    }
	
	/**
	 * 
	 * @description 获取评价列表
	 * @author chupp
	 * @date 2018年8月21日
	 * @param params
	 * @return
	 *
	 */
	@ApiOperation(value="获取评价列表", notes="")
    @RequestMapping(value = "getRecruitEvaluateList",method = RequestMethod.POST)
    public R getRecruitEvaluateList(@RequestBody RecruitModularParams params) {
    	return R.ok(recruitModularService.getRecruitEvaluateList(params));
    }
	
	/**
	 * 
	 * @description 评价接口
	 * @author chupp
	 * @date 2018年8月21日
	 * @param params
	 * @return
	 *
	 */
	@ApiOperation(value="评价接口", notes="")
    @RequestMapping(value = "saveRecruitEvaluate",method = RequestMethod.POST)
    public R saveRecruitEvaluate(@RequestBody RecruitModularParams params) {
		recruitModularService.saveRecruitEvaluate(params);
    	return R.ok();
    }
	
	/**
	 * 
	 * @description 获取个人简历详情
	 * @author chupp
	 * @date 2018年8月21日
	 * @param params
	 * @return
	 *
	 */
	@ApiOperation(value="获取个人简历详情", notes="")
    @RequestMapping(value = "getPersonRecruitResume",method = RequestMethod.POST)
    public R getPersonRecruitResume(@RequestBody RecruitModularParams params) {
    	return R.ok(recruitModularService.getPersonRecruitResume(params));
    }
	
	/**
	 * 
	 * @description 保存个人简历
	 * @author chupp
	 * @date 2018年8月22日
	 * @param params
	 * @return
	 *
	 */
	@ApiOperation(value="保存个人简历", notes="")
    @RequestMapping(value = "saveRecruitResumeWorker",method = RequestMethod.POST)
    public R saveRecruitResumeWorker(@RequestBody RecruitModularParams params) {
    	return R.ok(recruitModularService.saveRecruitResumeWorker(params));
    }
	
	/**
	 * 
	 * @description 申诉保存
	 * @author chupp
	 * @date 2018年8月22日
	 * @param params
	 * @return
	 *
	 */
	@ApiOperation(value="申诉保存", notes="")
    @RequestMapping(value = "saveRecruitAppeal",method = RequestMethod.POST)
    public R saveRecruitAppeal(@RequestBody RecruitModularParams params) {
		recruitModularService.saveRecruitAppeal(params);
    	return R.ok();
    }
	
	/**
	 * 
	 * @description 查询电话记录
	 * @author chupp
	 * @date 2018年8月22日
	 * @param params
	 * @return
	 *
	 */
	@ApiOperation(value="查询电话记录", notes="")
    @RequestMapping(value = "getRecruitTelegram",method = RequestMethod.POST)
    public R getRecruitTelegram(@RequestBody RecruitModularParams params) {
    	return R.ok(recruitModularService.getRecruitTelegram(params));
    }
}
