package com.xywg.admin.rest.modular.system;

import com.xywg.admin.modular.system.model.Sign;
import com.xywg.admin.modular.system.service.SignService;
import com.xywg.admin.rest.common.persistence.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hjy
 * @date 2018/5/29
 */
@RestController
@RequestMapping("sign")
@Api(description = "短信验证码")
public class VerificationCodeRestController {
    @Autowired
    private SignService signService;

    /**
     *   参数:mobile,codeType（验证码类型  0注册 1找回密码）
     * @param sign
     * @return
     */
    @ApiOperation(value = "发送短信", notes = "短信")
    @RequestMapping(value = "/sendCode", method = RequestMethod.POST)
    public R hello(@RequestBody Sign sign) {
        return R.ok().put("data",signService.handleSMS(sign));
    }
}
