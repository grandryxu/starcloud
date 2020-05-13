package com.xywg.admin.modular.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import com.xywg.admin.modular.smz.service.IfaLaborService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 同步管理
 *
 * @author wangcw
 * @Date 2018-08-21 16:55:55
 */
@Controller
@RequestMapping("/synchro")
public class IfaLaborController extends BaseController {

    private String PREFIX = "/system/synchro/";

    @Autowired
    private IfaLaborService ifaLaborService;

    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;
    
    /**
     * 跳转到版本管理首页
     */
    @RequestMapping("")
    public String index(Model model) {
    	model.addAttribute("project", cooperationProjectMasterService.getSynchroList(new HashMap<>()));
        return PREFIX + "synchro.html";
    }


    /**
     * 查询同步情况
     * @author duanfen
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String,Object> map) {
        Page<HashMap<String, Object>> page = new PageFactory<HashMap<String, Object>>().defaultPage();
        List<HashMap<String, Object>> versionList= this.ifaLaborService.findSynchro(page ,map);
        page.setRecords(versionList);
        return super.packForBT(page);
    }

    /**
     * 同步选中的数据,暂时不做
     * @author duanfen
     */
    @RequestMapping(value = "/synchroLabor")
    @ResponseBody
    public Object synchro(@RequestParam Map<String,Object> map) {
    	this.ifaLaborService.synchro(map);
        return SUCCESS_TIP;
    }
}
