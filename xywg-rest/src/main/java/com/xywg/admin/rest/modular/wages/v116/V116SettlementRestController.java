package com.xywg.admin.rest.modular.wages.v116;

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
@RequestMapping("/v116/appSettlement")
@Api(description = "结算单控制类")
public class V116SettlementRestController extends BaseController {

    @Autowired
    private SettlementService settlementService;

    /**
     * 获取待我审批的结算单列表
     * 2018年6月10日
     *@author caiwei
     *
     */
    @ApiOperation(value = "v116获取待我审批的结算单列表", notes = "")
    @RequestMapping(value = "/v116GetSettlementDtoByOrganizationCode", method = RequestMethod.GET)
    public R v116GetSettlementDtoByOrganizationCode(@RequestParam String organizationCode ,@RequestParam Integer pageNo , @RequestParam Integer pageSize) {
        return R.ok(settlementService.v116GetSettlementDtoByOrganizationCode(organizationCode,pageNo,pageSize));
    }

    /**
     * 根据项目，工资单类型获取工资单列表
     * 2018年6月7日
     *@author caiwei
     *
     */
    @ApiOperation(value = "v116根据项目获取结算单列表", notes = "")
    @RequestMapping(value = "/v116GetSettlementListByProjectCodeAndSysTeamNo", method = RequestMethod.GET)
    public R v116GetSettlementListByProjectCodeAndSysTeamNo(@RequestParam(required = false) String projectCode ,@RequestParam(required = false) Integer id ,@RequestParam Integer pageNo , @RequestParam Integer pageSize) {
        return R.ok(settlementService.v116GetSettlementListByProjectCodeAndSysTeamNo(projectCode,id,pageNo,pageSize));
    }
    
    @ApiOperation(value = "v116根据结算单详情id获取结算单详情", notes = "")
    @RequestMapping(value = "/getSettlementDetailById", method = RequestMethod.GET)
    public R getSettlementDetailById(@RequestParam Long id,@RequestParam Long uid) {
        return R.ok(settlementService.getSettlementDetailById2(id,uid));
    }

}
