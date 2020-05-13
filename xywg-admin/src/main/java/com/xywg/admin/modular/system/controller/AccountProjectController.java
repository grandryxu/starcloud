package com.xywg.admin.modular.system.controller;

import com.alibaba.fastjson.JSON;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.modular.system.model.AccountProject;
import com.xywg.admin.modular.system.service.AccountProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 账号与项目关联 控制器
 *
 * @author jingyun_hu
 * @date 2018/7/11
 */
@Controller
@RequestMapping("/accountProject")
public class AccountProjectController extends BaseController {
    @Autowired
    private AccountProjectService accountProjectService;

    /**
     * 批量新增关联关系
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@RequestParam String list) {
        List<AccountProject> projectCodeList = JSON.parseArray(list, AccountProject.class);
        accountProjectService.add(projectCodeList);
        return SUCCESS_TIP;
    }

    /**
     * 批量删除关联关系(逻辑删除)
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String list) {
        List<AccountProject> projectCodeList = JSON.parseArray(list, AccountProject.class);
        accountProjectService.delete(projectCodeList);
        return SUCCESS_TIP;
    }

    /**
     * 根据登陆人,社会信用代码 查询其关联的项目
     */
    @RequestMapping(value = "/selectProjectsByAccountAndDeptId/{deptId}")
    @ResponseBody
    public Object selectProjectsByAccountAndDeptId(@PathVariable String deptId) {
        return accountProjectService.selectProjectsByAccountAndDeptId(deptId);
    }

    /**
     * 项目切换 , 将切换后的数据存入session中
     */
    @RequestMapping(value = "/switchProject")
    @ResponseBody
    public Object switchProject(@RequestParam String deptId , @RequestParam String projectCode) {
        accountProjectService.switchProject(deptId , projectCode);
        return SUCCESS_TIP;
    }

    /**
     * 批量删除关联关系(逻辑删除)
     */
    @RequestMapping(value = "/reset")
    @ResponseBody
    public Object reset() {
        accountProjectService.reset();
        return SUCCESS_TIP;
    }
    
    /**
     * 设为默认项目
     */
    @RequestMapping(value = "/setDefault")
    @ResponseBody
    public Object setDefault(@RequestParam Map<String,Object> map) {
        //将账户下关联的所有项目置为非默认
    	accountProjectService.updateNoDefault(map);
    	//判断该项目有没有被关联
    	if(String.valueOf(map.get("isRelation")).equals("1")) {
    		accountProjectService.updateDefault(map);
    	}else {
    		accountProjectService.addRelationAndDefault(map);
    	}
        return SUCCESS_TIP;
    }
}
