package com.xywg.admin.rest.modular.wages.v116;

import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.modular.wages.model.dto.PayRollDetailDto;
import com.xywg.admin.modular.wages.model.dto.PayRollDto;
import com.xywg.admin.modular.wages.service.IPayRollDetailService;
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
@RequestMapping("/v116/appPayDetailRoll")
@Api(description = "工资详情控制类")
public class V116PayRollDetailRestController extends BaseController {

    @Autowired
    private IPayRollDetailService payRollDetailService;

    /**
     * 获取工人在某班组的工资剩余列表
     * 2018年6月7日
     *
     * @author caiwei
     */
    @ApiOperation(value = "v116获取工人在某班组的工资剩余列表", notes = "")
    @RequestMapping(value = "/v116GetPayRollDetailListByWorkerListAndTeamSysNo", method = RequestMethod.POST)
    public R v116GetPayRollDetailListByWorkerListAndTeamSysNo(@RequestBody PayRollDto PayRollDto) {
        return R.ok(payRollDetailService.v116GetPayRollDetailListByWorkerListAndTeamSysNo(PayRollDto));
    }
    
    @ApiOperation(value = "根据项目获取班组长手下工人列表", notes = "")
    @RequestMapping(value = "/getPayRollFlowDetailListByProjectCodeAndTeamSysNo", method = RequestMethod.GET)
    public R getPayRollFlowDetailListByProjectCodeAndTeamSysNo(@RequestParam(required = false) String projectCode, @RequestParam Long id,
    		@RequestParam Integer pageNo,@RequestParam Integer pageSize) {
        return R.ok(payRollDetailService.getPayRollFlowDetailListByProjectCodeAndTeamSysNoV116(projectCode, id, pageNo, pageSize));
    }

    /**
     * 根据项目编号和工人信息获取工资和结算单详细
     * 2018年6月13日
     *
     * @author hujignyun
     */
    @ApiOperation(value = "根据项目编号和工人信息获取工资和结算单详细", notes = "根据项目编号和工人信息获取工资和结算单详细")
    @RequestMapping(value = "/getPayrollAndSettlementDetailListByWorkerAndProjectCode", method = RequestMethod.GET)
    public R getPayrollAndSettlementDetailListByWorkerAndProjectCode(@RequestParam String projectCode, @RequestParam String idCardType, @RequestParam String idCardNumber ,@RequestParam Integer pageNo , @RequestParam Integer pageSize) {
        return R.ok(payRollDetailService.v116GetPayrollAndSettlementDetailListByWorkerAndProjectCode(projectCode, idCardType, idCardNumber,pageNo,pageSize));
    }

    /**
     * 获取工资单详情
     * 2018年9月27日
     *
     * @author caiwei
     */
    @ApiOperation(value = "根据工资单id获取工资单", notes = "")
    @RequestMapping(value = "/getPayRollDetailById", method = RequestMethod.GET)
    public R getPayRollDetailById(@RequestParam Long id ,@RequestParam Integer pageNo , @RequestParam Integer pageSize) {
        return R.ok(payRollDetailService.v116GetPayRollDetailById(id,pageNo,pageSize));
    }
}
