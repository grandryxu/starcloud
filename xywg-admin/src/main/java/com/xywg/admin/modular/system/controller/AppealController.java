package com.xywg.admin.modular.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.wrapper.ProjectMasterWrapper;
import com.xywg.admin.modular.system.model.AppealVo;
import com.xywg.admin.modular.system.model.dto.AppealDto;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.system.service.IDictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.modular.system.model.Appeal;
import com.xywg.admin.modular.system.service.IAppealService;

import java.util.List;
import java.util.Map;

/**
 * 申诉模块控制器
 *
 * @author wangcw
 * @Date 2018-08-20 16:05:26
 */
@Controller
@RequestMapping("/appeal")
public class AppealController extends BaseController {

    private String PREFIX = "/appeal/appeal/";

    @Autowired
    private IAppealService appealService;

    @Autowired
    private IDictService dictService;

    /**
     * 跳转到申诉模块首页
     */
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("type",dictService.selectByName("申诉类型"));
        return PREFIX + "appeal.html";
    }

    /**
     * 跳转到添加申诉模块
     */
    @RequestMapping("/appeal_add")
    public String appealAdd() {
        return PREFIX + "appeal_add.html";
    }

    /**
     * 跳转到修改申诉模块
     */
    @RequestMapping("/appeal_update/{appealId}")
    public String appealUpdate(@PathVariable Integer appealId, Model model) {
        Appeal appeal = appealService.selectById(appealId);
        model.addAttribute("item",appeal);
        LogObjectHolder.me().set(appeal);
        return PREFIX + "appeal_edit.html";
    }

    /**
     * 获取申诉模块列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String,Object> map) {
        Page<AppealVo> page = new PageFactory<AppealVo>().defaultPage();
        List<AppealVo> list = this.appealService.selectList(map,page);
        page.setRecords(list);
        return super.packForBT(page);
    }

    /**
     * 新增申诉模块
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Appeal appeal) {
        appealService.insert(appeal);
        return SUCCESS_TIP;
    }

    /**
     * 删除申诉模块
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer appealId) {
        appealService.deleteById(appealId);
        return SUCCESS_TIP;
    }

    /**
     * 修改申诉模块
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Appeal appeal) {
        appealService.updateById(appeal);
        return SUCCESS_TIP;
    }

    /**
     * 申诉模块详情
     */
    @RequestMapping(value = "/detail/{appealId}")
    @ResponseBody
    public Object detail(@PathVariable("appealId") Integer appealId) {
        return appealService.selectById(appealId);
    }


    /**
     * 通过 驳回
     * @param appealDto
     * @return
     */
    @RequestMapping(value = "/operation")
    @ResponseBody
    public Object detail(AppealDto appealDto) {
        return appealService.operation(appealDto);
    }
}
