package com.xywg.admin.rest.modular.account.v116;

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
@RequestMapping("/v116/appAccountDetail")
@Api(description = "控制")
public class V116AccountRestController {

    @Autowired
    private IAccountDetailService accountDetailService;
    @Autowired
    private IAccountService accountService;

    /**
     * 获取工友 xieshuaishuai
     */
    @RequestMapping(value = "/v116GetWorkerMateList",method = RequestMethod.GET)
    @ApiOperation(value = "v116获取工友")
    public R v116GetWorkerMateList(@RequestParam String teamSysNo, @RequestParam(required = false) String closingTime,@RequestParam(required = false)String payMonth ,@RequestParam Integer pageNo , @RequestParam Integer pageSize){
        return R.ok(accountService.v116GetWorkerMateList(teamSysNo,closingTime,payMonth,pageNo,pageSize));
    }

    /**
     * 记工单选择列表
     */
    @RequestMapping(value = "/v116GetAccountSelectList",method = RequestMethod.GET)
    @ApiOperation(value = "记工单选择列表")
    public R getAccountSelectList(@RequestParam Integer teamSysNo ,@RequestParam Integer pageNo , @RequestParam Integer pageSize){
        return R.ok(accountService.v116GetAccountSelectList(teamSysNo,pageNo,pageSize));
    }
    
    @RequestMapping(value = "/getAccountListByProjectCodeAndIsSign", method = RequestMethod.GET)
    @ApiOperation(value = "/根据项目编码和状态查询记工单列表")
    public R getAccountListByProjectCodeAndIsSign(@RequestParam Integer id,@RequestParam(required=false) Integer teamSysNo,@RequestParam(required=false) Integer isSign,
    		@RequestParam Integer pageNo,@RequestParam Integer pageSize) {
        return R.ok(accountService.getAccountListByProjectCodeAndIsSignV116(id,teamSysNo,isSign,pageNo,pageSize));
    }

    /**
     * 根据accountId获取记工单详情
     */
    @RequestMapping(value = "/getAccountDetail", method = RequestMethod.GET)
    @ApiOperation(value = "/根据accountId获取记工单详情")
    public R getAccountDetail(@RequestParam String ids ,@RequestParam Integer pageNo , @RequestParam Integer pageSize) {
        return R.ok(accountDetailService.v116GetAccountDetailByAccountIds(ids,pageNo,pageSize));
    }

}
