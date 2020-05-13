package com.xywg.admin.modular.longxin.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.flow.entity.Process;
import com.xywg.admin.flow.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by tcw on 2019/7/9.
 */
@Controller
@RequestMapping("/lxFlow")
public class LxFlowController extends BaseController {

    private String PREFIX = "/longxin/flow/";


    @Autowired
    private ProcessService processService;

    /**
     * 流程管理页
     * @return
     */
    @RequestMapping("/index")
    public String index(){
        System.out.println(1111111);
        return PREFIX + "index.html";
    }


    /**
     * 获取所有流程
     */
//    @RequestMapping("/getAll")
//    @ResponseBody
//    public Object getAll(){
//        Map ma = new HashMap();
//        List l = processService.getAll();
//        if(l!=null){
//            ma.put("rows",l) ;
//        }
//        ma.put("total",0);
//        return ma;
//    }
    /**
     * 获取发布的招标的列表
     */
    @RequestMapping(value = "/getAll")
    @ResponseBody
    public Object list(@RequestParam Map<String,Object> map) {
        Page<Process> page = new PageFactory<Process>().defaultPage();
        List<Process> list =  processService.selectList(map, page);
        page.setRecords(list);
        return super.packForBT(page);
    }

}
