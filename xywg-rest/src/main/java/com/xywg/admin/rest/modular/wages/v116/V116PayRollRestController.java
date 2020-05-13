package com.xywg.admin.rest.modular.wages.v116;

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
@RequestMapping("/v116/appPayRoll")
@Api(description = "工资控制类")
public class V116PayRollRestController extends BaseController {

    @Autowired
    private IPayRollService payRollService;

    /**
     * 获取待我审批的工资单列表
     * 2018年6月10日
     *@author caiwei
     *
     */
    @ApiOperation(value = "v116获取待我审批的工资单列表", notes = "")
    @RequestMapping(value = "/v116GetPayRollByOrganizationCode", method = RequestMethod.GET)
    public R v116GetPayRollByOrganizationCode(@RequestParam String organizationCode ,@RequestParam Integer pageNo , @RequestParam Integer pageSize) {
        return R.ok(payRollService.v116GetPayRollByOrganizationCode(organizationCode,pageNo,pageSize));
    }

    /**
     * 根据项目，工资单类型获取工资单列表
     * 2018年9月18日
     *@author caiwei
     *
     */
    @ApiOperation(value = "v116根据项目，工资单类型获取工资单列表", notes = "")
    @RequestMapping(value = "/v116GetPayRollListByProjectCodeAndType", method = RequestMethod.GET)
    public R v116GetPayRollListByProjectCodeAndType(@RequestParam(required = false)Integer id, @RequestParam(required=false) String projectCode , @RequestParam(required=false) String teamSysNo, @RequestParam(required=false) Integer type ,@RequestParam Integer pageNo , @RequestParam Integer pageSize) {
        return R.ok(payRollService.v116GetPayRollListByProjectCodeAndType(id,projectCode ,teamSysNo,type,pageNo,pageSize));
    }
}
