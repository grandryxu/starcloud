package com.xywg.admin.modular.flow.controller;

import com.xywg.admin.flow.service.ProcessService;
import com.xywg.admin.modular.flow.service.impl.ProcessDesignServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 严鹏
 * @date 2019/7/11
 */
@Controller
@RequestMapping("/processDesign")
public class ProcessDesignController {

    @Autowired
    private ProcessDesignServiceImpl processDesignService;

    @Autowired
    private ProcessService processService;

    @GetMapping("/design")
    public String design(){
        return "/flow/html/design.html";
    }
    @GetMapping("/edit")
    public String design(String id,Model model,String dName){
        model.addAttribute("displayName",dName);

        model.addAttribute("id",id);
        return "/flow/html/edit.html";
    }

    @GetMapping("/getAllUser")
    @ResponseBody
    public Object getAllUser(){
        return processDesignService.getAllUser();
    }

    @GetMapping("/getAllDept")
    @ResponseBody
    public Object getAllDept(){
        return processDesignService.getAllDept();
    }

    @GetMapping("/getAllRole")
    @ResponseBody
    public Object getAllRole(){
        return processDesignService.getAllRole();
    }

    @GetMapping("/getOneDetail")
    @ResponseBody
    public Object getOneDetail(String processId){
        return processService.getOneDetail(processId);
    }

    @PostMapping("insert")
    @ResponseBody
    public Object insert(String displayName,String lists){
       return processDesignService.insert1(displayName,lists);
    }

    @PostMapping("xiugai")
    @ResponseBody
    public Object xiugai(String displayName,String lists,String id){
        return processDesignService.xiugai(displayName,lists,id);
    }

    @RequestMapping("del")
    @ResponseBody
    public Object del( String id){
        return processDesignService.delete(id);
    }
}
