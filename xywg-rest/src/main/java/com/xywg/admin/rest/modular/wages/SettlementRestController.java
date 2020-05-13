package com.xywg.admin.rest.modular.wages;

import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.modular.wages.model.dto.SettlementDetailDto;
import com.xywg.admin.modular.wages.model.dto.SettlementDto;
import com.xywg.admin.modular.wages.service.SettlementService;
import com.xywg.admin.rest.common.persistence.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appSettlement")
@Api(value = "appSettlement",description = "结算单控制类")
public class SettlementRestController extends BaseController {

    @Autowired
    private SettlementService settlementService;

    /**
     * 根据项目，工资单类型获取工资单列表
     * 2018年6月7日
     *@author caiwei
     *
     */
    @ApiOperation(value = "根据项目获取结算单列表", notes = "")
    @RequestMapping(value = "/getSettlementListByProjectCodeAndSysTeamNo", method = RequestMethod.GET)
    public R getSettlementListByProjectCodeAndTeamSysNo(@RequestParam(required = false) String projectCode ,@RequestParam(required = false) Integer id) {
        return R.ok(settlementService.getSettlementListByProjectCodeAndTeamSysNo(projectCode,id));
    }

    /**
     * 根据项目，工资单类型获取工资单列表
     * 2018年6月8日
     *@author caiwei
     *
     */
    @ApiOperation(value = "根据结算单详情id获取结算单详情", notes = "")
    @RequestMapping(value = "/getSettlementDetailById", method = RequestMethod.GET)
    public R getSettlementDetailById(@RequestParam Long id) {
        return R.ok(settlementService.getSettlementDetailById(id));
    }

    /**
     * 结算单签字
     * 2018年6月8日
     *@author caiwei
     *
     */
    @ApiOperation(value = "结算单签字", notes = "")
    @RequestMapping(value = "/settlementDetailDtoSign", method = RequestMethod.POST)
    public R settlementDetailDtoSign(@RequestBody SettlementDetailDto settlementDetailDto) {
        settlementService.settlementDetailDtoSign(settlementDetailDto);
        return R.ok();
    }

    /**
     * 新增结算单
     * 2018年6月8日
     *@author caiwei
     *
     */
    @ApiOperation(value = "新增结算单", notes = "")
    @RequestMapping(value = "/addSettlementDto", method = RequestMethod.POST)
    public R addSettlementDto(@RequestBody SettlementDto settlementDto) {
        return R.ok(settlementService.addSettlementDto(settlementDto));
    }

    /**
     * 获取待我审批的结算单列表
     * 2018年6月10日
     *@author caiwei
     *
     */
    @ApiOperation(value = "获取待我审批的结算单列表", notes = "")
    @RequestMapping(value = "/getSettlementDtoByOrganizationCode", method = RequestMethod.GET)
    public R getPayRollByOrganizationCode(@RequestParam String organizationCode) {
        return R.ok(settlementService.getSettlementDtoByOrganizationCode(organizationCode));
    }

    /**
     * 审核结算单
     * 2018年6月10日
     *@author caiwei
     *
     */
    @ApiOperation(value = "审核结算单", notes = "")
    @RequestMapping(value = "/examineSettlementDto", method = RequestMethod.POST)
    public R examineSettlementDto(@RequestBody SettlementDto settlementDto) {
        return R.ok(settlementService.examineSettlementDto(settlementDto));
    }
}
