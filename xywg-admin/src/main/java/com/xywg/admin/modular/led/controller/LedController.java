package com.xywg.admin.modular.led.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import com.xywg.admin.modular.project.wrapper.ProjectMasterWrapper;
import com.xywg.admin.modular.system.service.IDictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.xywg.admin.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.xywg.admin.modular.led.model.Led;
import com.xywg.admin.modular.led.service.ILedService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * led管理控制器
 *
 * @author wangcw
 * @Date 2018-09-20 16:19:20
 */
@Controller
@RequestMapping("/led")
public class LedController extends BaseController {

    private String PREFIX = "/led/";

    @Autowired
    private ILedService ledService;
    @Autowired
    private IDictService dictService;
    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;

    /**
     * 跳转到led管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "led.html";
    }

    /**
     * 跳转到添加led管理
     */
    @RequestMapping("/led_add")
    public String ledAdd(Model model) {
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        model.addAttribute("displayType",dictService.selectByName("显示特技"));
        model.addAttribute("fontColor",dictService.selectByName("LED字体颜色"));
        model.addAttribute("status",dictService.selectByName("LED状态"));
        return PREFIX + "led_add.html";
    }

    /**
     * 跳转到修改led管理
     */
    @RequestMapping("/led_update/{ledId}")
    public String ledUpdate(@PathVariable Long ledId, Model model) {
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        model.addAttribute("displayType",dictService.selectByName("显示特技"));
        model.addAttribute("fontColor",dictService.selectByName("LED字体颜色"));
        model.addAttribute("status",dictService.selectByName("LED状态"));
        Led led = ledService.selectById(ledId);
        model.addAttribute("item",led);
        LogObjectHolder.me().set(led);
        return PREFIX + "led_edit.html";
    }

    /**
     * 获取led管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String,Object> map) {
        Page<Led> page = new PageFactory<Led>().defaultPage();
        List<Led> list = this.ledService.selectList(map, page);
        page.setRecords(list);
        return super.packForBT(page);
    }

    /**
     * 新增led管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Led led) {
        ledService.insert(led);
        return SUCCESS_TIP;
    }

    /**
     * 删除led管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long ledId) {
        ledService.deleteById(ledId);
        return SUCCESS_TIP;
    }

    /**
     * 批量删除led管理
     */
    @RequestMapping(value = "/deletes")
    @ResponseBody
    public Object deletes(@RequestParam String ids) {
        ledService.deletes(ids);
        return SUCCESS_TIP;
    }

    /**
     * 修改led管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Led led) {
        ledService.updateById(led);
        return SUCCESS_TIP;
    }

    /**
     * led管理详情
     */
    @RequestMapping(value = "/detail/{ledId}")
    @ResponseBody
    public Object detail(@PathVariable("ledId") Integer ledId) {
        return ledService.selectById(ledId);
    }
}
