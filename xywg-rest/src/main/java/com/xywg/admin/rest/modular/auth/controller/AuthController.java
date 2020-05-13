package com.xywg.admin.rest.modular.auth.controller;

import com.xywg.admin.core.support.HttpKit;
import com.xywg.admin.modular.system.service.IUserService;
import com.xywg.admin.rest.common.persistence.model.R;
import com.xywg.admin.rest.modular.auth.util.JwtTokenUtil;
import com.xywg.admin.rest.modular.auth.validator.IReqValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 请求验证的
 *
 * @author wangcw
 * @Date 2017/8/24 14:22
 */
@RestController
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Resource(name = "dbValidator")
    private IReqValidator reqValidator;

    @Autowired
    private IUserService userService;

//    @ApiOperation("用户登录")
//    @RequestMapping(value = "${jwt.auth-path}",method = RequestMethod.POST)
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) {
//        boolean validate = reqValidator.validate(user);
//        if (validate) {
//            final String randomKey = jwtTokenUtil.getRandomKey();
//            final String token = jwtTokenUtil.generateToken(user.getAccount(), randomKey);
//            HttpKit.getRequest().getSession().setAttribute("token",user.getAccount());
//            return ResponseEntity.ok(new AuthResponse(token, randomKey));
//        } else {
//            throw new XywgException(BizExceptionEnum.AUTH_REQUEST_ERROR);
//        }
//
//    }

    @ApiOperation("用户登录")
    @RequestMapping(value = "auth2",method = RequestMethod.GET)
    public R createAuthenticationToken() {
        HttpKit.getRequest().getHeader("Authorization");
        HttpKit.getRequest().getSession().setAttribute("name","张三");
        Object name = HttpKit.getRequest().getSession().getAttribute("name");
         return R.ok().put("name",name);
    }
}
