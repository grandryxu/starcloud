package com.xywg.admin.rest.config.handler;

import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.rest.common.persistence.model.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author wangcw
 * @date 2016年11月12日 下午3:19:56
 */
@ControllerAdvice
@Order(-1)
public class GlobalExceptionHandler {

    private static  Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 拦截业务异常
     */
    @ExceptionHandler(XywgException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R notFount(XywgException e) {
        R r = new R();
        r.put("code",e.getCode());
        r.put("message",e.getMessage());
        r.put("success",false);
//        r.remove("success");
        return r;
    }

}
