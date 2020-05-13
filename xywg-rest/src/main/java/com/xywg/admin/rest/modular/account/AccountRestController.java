package com.xywg.admin.rest.modular.account;

import com.xywg.admin.modular.wages.model.AccountDetail;
import com.xywg.admin.modular.wages.model.AccountVo;
import com.xywg.admin.modular.wages.service.IAccountDetailService;
import com.xywg.admin.modular.wages.service.IAccountService;
import com.xywg.admin.rest.common.persistence.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/6/7 17:50
 */
@RestController
@RequestMapping("/appAccountDetail")
@Api(description = "控制")
public class AccountRestController {

    @Autowired
    private IAccountDetailService accountDetailService;
    @Autowired
    private IAccountService accountService;

    /**
     * 添加记工单
     * xieshuaishuai
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "添加记工单")
    public R addAccount(@RequestBody AccountVo accountVo) {
        accountDetailService.addAccountAndAccountDetail(accountVo);
        return R.ok();
    }

    /**
     * 根据accountId获取记工单详情
     */
    @RequestMapping(value = "/getAccountDetail", method = RequestMethod.GET)
    @ApiOperation(value = "/根据accountId获取记工单详情")
    public R getAccountDetail(@RequestParam String ids) {
        return R.ok(accountDetailService.getAccountDetailByAccountIds(ids));
    }

    /**
     * 根据项目编码和状态查询记工单列表
     */
    @RequestMapping(value = "/getAccountListByProjectCodeAndIsSign", method = RequestMethod.GET)
    @ApiOperation(value = "/根据项目编码和状态查询记工单列表")
    public R getAccountListByProjectCodeAndIsSign(@RequestParam Integer id,@RequestParam(required=false) Integer teamSysNo,@RequestParam(required=false) Integer isSign) {
        return R.ok(accountService.getAccountListByProjectCodeAndIsSign(id,teamSysNo,isSign));
    }

    /**
     * 记工单签字
     */
    @RequestMapping(value = "/accountSign",method = RequestMethod.POST)
    @ApiOperation(value = "/记工单签字")
    public R accountSign(@RequestBody AccountDetail accountDetail){
        accountDetailService.accountSign(accountDetail);
        return R.ok() ;
    }

    /**
     * 获取工友 xieshuaishuai
     */
    @RequestMapping(value = "/getWorkerMateList",method = RequestMethod.GET)
    @ApiOperation(value = "获取工友")
    public R getWorkerMateList(@RequestParam String teamSysNo, @RequestParam(required = false) String closingTime,@RequestParam(required = false)String payMonth ){
        return R.ok(accountService.getWorkerMateList(teamSysNo,closingTime,payMonth));
    }

    /**
     * 获取单位
     */
    @RequestMapping(value = "/getUnit",method = RequestMethod.GET)
    @ApiOperation(value = "获取单位")
    public R getUnit(@RequestParam String name){
        return R.ok(accountService.getUnit(name));
    }

    /**
     * 记工单选择列表
     */
    @RequestMapping(value = "/getAccountSelectList",method = RequestMethod.GET)
    @ApiOperation(value = "记工单选择列表")
    public R getAccountSelectList(@RequestParam Integer teamSysNo){
        return R.ok(accountService.getAccountSelectList(teamSysNo));
    }
}
