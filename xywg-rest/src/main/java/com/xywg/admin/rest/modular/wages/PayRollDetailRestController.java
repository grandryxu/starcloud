package com.xywg.admin.rest.modular.wages;

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
@RequestMapping("/appPayDetailRoll")
@Api(value = "appPayDetailRoll", description = "工资详情控制类")
public class PayRollDetailRestController extends BaseController {

    @Autowired
    private IPayRollDetailService payRollDetailService;


    /**
     * 获取工资单详情
     * 2018年6月7日
     *
     * @author caiwei
     */
    @ApiOperation(value = "根据工资单id获取工资单", notes = "")
    @RequestMapping(value = "/getPayRollDetailById", method = RequestMethod.GET)
    public R getPayRollDetailById(@RequestParam Long id) {
        return R.ok(payRollDetailService.getPayRollDetailById(id));
    }

    /**
     * 工资单签字
     * 2018年6月7日
     *
     * @author caiwei
     */
    @ApiOperation(value = "工资单签字", notes = "")
    @RequestMapping(value = "/payRollDetailSign", method = RequestMethod.POST)
    public R payRollDetailSign(@RequestBody PayRollDetailDto payRollDetailDto) {
        payRollDetailService.payRollDetailSign(payRollDetailDto);
        return R.ok();
    }

    /**
     * 获取工人在某班组的工资剩余列表
     * 2018年6月7日
     *
     * @author caiwei
     */
    @ApiOperation(value = "获取工人在某班组的工资剩余列表", notes = "")
    @RequestMapping(value = "/getPayRollDetailListByWorkerListAndTeamSysNo", method = RequestMethod.POST)
    public R getPayRollDetailListByWorkerListAndTeamSysNo(@RequestBody PayRollDto PayRollDto) {
        return R.ok(payRollDetailService.getPayRollDetailListByWorkerListAndTeamSysNo(PayRollDto));
    }

    /**
     * 根据项目获取班组长手下工人列表
     * 2018年6月8日
     *
     * @author caiwei
     */
    @ApiOperation(value = "根据项目获取班组长手下工人列表", notes = "")
    @RequestMapping(value = "/getPayRollFlowDetailListByProjectCodeAndTeamSysNo", method = RequestMethod.GET)
    public R getPayRollFlowDetailListByProjectCodeAndTeamSysNo(@RequestParam(required = false) String projectCode, @RequestParam Long id) {
        return R.ok(payRollDetailService.getPayRollFlowDetailListByProjectCodeAndTeamSysNo(projectCode, id));
    }

    /**
     * 获取指定工人在该班组下的工资，结算流水
     * 2018年6月8日
     *
     * @author caiwei
     */
    @ApiOperation(value = "获取指定工人在该班组下的工资，结算流水", notes = "")
    @RequestMapping(value = "/getPayRollFlowDetailListByWorkerAndTeamSysNo", method = RequestMethod.GET)
    public R getPayRollFlowDetailListByWorkerAndTeamSysNo(@RequestParam String teamSysNo, @RequestParam String idCardType, @RequestParam String idCardNumber) {
        return R.ok(payRollDetailService.getPayRollFlowDetailListByWorkerAndTeamSysNo(teamSysNo, idCardType, idCardNumber));
    }

    /**
     * 获取最新工资单
     */
    @ApiOperation(value = "获取最新工资单")
    @RequestMapping(value = "/getLastPayRoll", method = RequestMethod.GET)
    public R getLastPayRoll(@RequestParam String idCardNumber, @RequestParam Integer idCardType) {
        return R.ok(payRollDetailService.getLastPayRoll(idCardNumber, idCardType));
    }

    /**
     * 根据项目编号和工人信息获取工资和结算单详细
     * 2018年6月13日
     *
     * @author hujignyun
     */
    @ApiOperation(value = "根据项目编号和工人信息获取工资和结算单详细", notes = "根据项目编号和工人信息获取工资和结算单详细")
    @RequestMapping(value = "/getPayrollAndSettlementDetailListByWorkerAndProjectCode", method = RequestMethod.GET)
    public R getPayrollAndSettlementDetailListByWorkerAndProjectCode(@RequestParam String projectCode, @RequestParam String idCardType, @RequestParam String idCardNumber) {
        return R.ok(payRollDetailService.getPayrollAndSettlementDetailListByWorkerAndProjectCode(projectCode, idCardType, idCardNumber));
    }

}
