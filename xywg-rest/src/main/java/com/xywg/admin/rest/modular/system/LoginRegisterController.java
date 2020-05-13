package com.xywg.admin.rest.modular.system;

import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.log.LogManager;
import com.xywg.admin.core.log.factory.LogTaskFactory;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.support.HttpKit;
import com.xywg.admin.modular.system.model.AppPersonnelRegister;
import com.xywg.admin.modular.system.model.User;
import com.xywg.admin.modular.system.service.IUserService;
import com.xywg.admin.rest.common.persistence.model.R;
import com.xywg.admin.rest.modular.auth.util.JwtTokenUtil;
import com.xywg.admin.rest.modular.auth.validator.IReqValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author hjy
 * @date 2018/5/29
 */
@RestController
@Api(description = "登录注册")
public class LoginRegisterController extends BaseController {
    @Autowired
    private IUserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Resource(name = "dbValidator")
    private IReqValidator reqValidator;

    /**
     * 登录
     * 入参参数：account，password，userType
     */
    @ApiOperation(value = "登录", notes = "登录")
    @RequestMapping(value = "${jwt.auth-path}",method = RequestMethod.POST)
    public R createAuthenticationToken(@RequestBody(required=false) User user) {
        List<String> validate = reqValidator.validate(user);
        String code="600";
        if (code.equals(validate.get(0))) {
            final String randomKey = jwtTokenUtil.getRandomKey();
            final String token = jwtTokenUtil.generateToken(user.getAccount(), randomKey);
            HttpKit.getRequest().getSession().setAttribute("token",user.getAccount());
            Map<String, Object> map = userService.getUserInfo(user.getAccount(), user.getUserType());
            //i=1招标商，i=2投标商
           Integer i= userService.power(user.getAccount());
           map.put("power",i);
            map.put("userType", user.getUserType());
            return R.ok().put("data",map).put("token",token);
        } else {
            return R.error(Integer.parseInt(validate.get(0)),validate.get(1));
        }
    }

    /**
     * 工人注册
     * 参数:mobile,password,codeId,code
     */
    @ApiOperation(value = "工人注册", notes = "工人注册")
    @RequestMapping(value = "/appPersonnel/register", method = RequestMethod.POST)
    public R appPersonnelRegister(@RequestBody AppPersonnelRegister appPersonnelRegister) {
        userService.addWorkerAccount(appPersonnelRegister);
        return R.ok();
    }


    /**
     *
     * 通用版登陆
     *
     */
    @ApiOperation(value = "通用版登陆", notes = "通用版登陆")
    @RequestMapping(value = "${jwt.standard-login}", method = RequestMethod.POST)
    public R standardLogin(@RequestBody User user) {
        return this.reqValidator.standardLogin(user);
    }

}
