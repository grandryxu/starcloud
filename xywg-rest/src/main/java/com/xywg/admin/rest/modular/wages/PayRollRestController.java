package com.xywg.admin.rest.modular.wages;

import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.modular.wages.model.dto.PayRollDto;
import com.xywg.admin.modular.wages.service.IPayRollService;
import com.xywg.admin.rest.common.persistence.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 工资单控制器
 *
 * @author wangcw
 * @Date 2018-06-01 10:14:07
 */
@RestController
@RequestMapping("/appPayRoll")
@Api(value = "appPayRoll",description = "工资控制类")
public class PayRollRestController extends BaseController {

    @Autowired
    private IPayRollService payRollService;


    /**
     * 根据项目，工资单类型获取工资单列表
     * 2018年6月7日
     *@author caiwei
     *
     */
    @ApiOperation(value = "根据项目，工资单类型获取工资单列表", notes = "")
    @RequestMapping(value = "/getPayRollListByProjectCodeAndType", method = RequestMethod.GET)
    public R getPayRollListByProjectCodeAndType(@RequestParam(required = false)Integer id, @RequestParam(required=false) String projectCode , @RequestParam(required=false) String teamSysNo, @RequestParam(required=false) Integer type) {
        return R.ok(payRollService.getPayRollListByProjectCodeAndType(id,projectCode ,teamSysNo,type));
    }

    /**
     * 保存工资单及其详情
     * 2018年6月7日
     *@author caiwei
     *
     */
    @ApiOperation(value = "保存工资单及其详情", notes = "")
    @RequestMapping(value = "/savePayRollAndDetail", method = RequestMethod.POST)
    public R savePayRollAndDetail(@RequestBody PayRollDto payRollDto) {
        payRollService.savePayRollAndDetail(payRollDto);
        return R.ok();
    }

    /**
     * 审核工资单
     * 2018年6月10日
     *@author caiwei
     *
     */
    @ApiOperation(value = "审核工资单", notes = "")
    @RequestMapping(value = "/examineSalary", method = RequestMethod.POST)
    public R examineSalary(@RequestBody PayRollDto payRollDto) {
        return R.ok(payRollService.examineSalary(payRollDto));
    }


    /**
     * 获取待我审批的工资单列表
     * 2018年6月10日
     *@author caiwei
     *
     */
    @ApiOperation(value = "获取待我审批的工资单列表", notes = "")
    @RequestMapping(value = "/getPayRollByOrganizationCode", method = RequestMethod.GET)
    public R getPayRollByOrganizationCode(@RequestParam String organizationCode) {
        return R.ok(payRollService.getPayRollByOrganizationCode(organizationCode));
    }

    /**
     * 获取待我审批的工资单列表
     * 2018年6月10日
     *@author caiwei
     *
     */
    @ApiOperation(value = "提交工资单列表", notes = "")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public R submit(@RequestBody PayRollDto payRollDto) {
        return R.ok(payRollService.submit(String.valueOf(payRollDto.getId())));
    }


    /**
     * 工资单发放
     * 2018年6月10日
     *@author caiwei
     *
     */
    @ApiOperation(value = "工资单发放", notes = "")
    @RequestMapping(value = "/grantPayRoll", method = RequestMethod.POST)
    public R grantPayRoll(@RequestBody PayRollDto payRollDto) {
        return R.ok(payRollService.grantPayRollApp(payRollDto));
    }
}
