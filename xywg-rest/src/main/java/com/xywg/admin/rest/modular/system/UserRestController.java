package com.xywg.admin.rest.modular.system;

import com.xywg.admin.modular.system.model.AppPersonnelRegister;
import com.xywg.admin.modular.system.service.IUserService;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;
import com.xywg.admin.rest.common.persistence.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author hjy
 * @date 2018/5/29
 */
@RestController
@RequestMapping("appPersonnel")
@Api(description = "用户人员")
public class UserRestController {
    @Autowired
    private IWorkerMasterService iWorkerMasterService;
    @Autowired
    private IUserService userService;


    @ApiOperation(value = "更新工人头像", notes = "更新工人头像")
    @RequestMapping(value = "/updateImage", method = RequestMethod.POST)
    public R updateImage(@RequestBody WorkerMaster workerMaster) {

        iWorkerMasterService.updateByIdCardTypeAndIdCardNumber(workerMaster);
        return R.ok();
    }

    @ApiOperation(value = "更新密码", notes = "更新密码")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public R updatePassword(@RequestBody AppPersonnelRegister appPersonnelRegister) {
        userService.updatePasswordBySign(appPersonnelRegister);
        return R.ok();
    }


    @ApiOperation(value = "修改工人设备id", notes = "修改工人设备id")
    @RequestMapping(value = "/updateEquipment", method = RequestMethod.POST)
    public R updateEquipment(@RequestBody Map<String,String> map ) {
        iWorkerMasterService.updateEquipmentById(map);
        return R.ok();
    }

    /**
     * 根据证件类型修改手机号码,手机号码也是登录账号
     */
    @ApiOperation(value = "修改工人手机号码和登录账号", notes = "修改工人手机号码和登录账号")
    @RequestMapping(value = "/updateAccountInfoByIdCard", method = RequestMethod.POST)
    public R updateAccountInfoByIdCard(@RequestBody AppPersonnelRegister accountInfo ) {
        userService.updateAccountInfoByIdCard(accountInfo);
        return R.ok();
    }


    /**
     * 手机号码也是登录账号
     */
    @ApiOperation(value = "修改工人手机号码", notes = "修改工人手机号码")
    @RequestMapping(value = "/updateAccountInfoByOldAccount", method = RequestMethod.POST)
    public R updateAccountInfoByOldAccount(@RequestBody AppPersonnelRegister accountInfo ) {
        userService.updateAccountInfoByOldAccount(accountInfo);
        return R.ok();
    }

}
