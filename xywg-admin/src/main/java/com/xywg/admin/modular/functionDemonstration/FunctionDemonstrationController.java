package com.xywg.admin.modular.functionDemonstration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * * @Package com.xywg.admin.modular.functionDemonstration
 * * @Description: 功能演示
 * * @author caiwei
 * * @date 2019/2/14
 **/
@Controller
@RequestMapping("/functionDemonstration")
public class FunctionDemonstrationController {

    private String PREFIX = "/functionDemonstration/";

    @RequestMapping("/timeSet")
    public String timeSetPage(){
        return PREFIX+"timeSet.html";
    }

    @RequestMapping("/deviceCount")
    public String deviceCountPage(){
        return PREFIX+"deviceCount.html";
    }

    @RequestMapping("/workerManager")
    public String workerManagePage(){
        return PREFIX+"workerManager.html";
    }

    @RequestMapping("/payRoll")
    public String payRollPage(){
        return PREFIX+"payRoll.html";
    }
}
