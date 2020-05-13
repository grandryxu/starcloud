package com.xywg.admin.modular.company.controller;

import com.xywg.admin.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.xywg.admin.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.xywg.admin.modular.company.model.EntryExitHistory;
import com.xywg.admin.modular.company.service.IEntryExitHistoryService;

/**
 * 进退场控制器
 *
 * @author wangcw
 * @Date 2018-05-28 16:51:36
 */
@Controller
@RequestMapping("/entryExitHistory")
public class EntryExitHistoryController extends BaseController {

    private String PREFIX = "/company/entryExitHistory/";

    @Autowired
    private IEntryExitHistoryService entryExitHistoryService;

    /**
     * 跳转到进退场首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "entryExitHistory.html";
    }

    /**
     * 跳转到添加进退场
     */
    @RequestMapping("/entryExitHistory_add")
    public String entryExitHistoryAdd() {
        return PREFIX + "entryExitHistory_add.html";
    }

    /**
     * 跳转到修改进退场
     */
    @RequestMapping("/entryExitHistory_update/{entryExitHistoryId}")
    public String entryExitHistoryUpdate(@PathVariable Integer entryExitHistoryId, Model model) {
        EntryExitHistory entryExitHistory = entryExitHistoryService.selectById(entryExitHistoryId);
        model.addAttribute("item",entryExitHistory);
        LogObjectHolder.me().set(entryExitHistory);
        return PREFIX + "entryExitHistory_edit.html";
    }

    /**
     * 获取进退场列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return entryExitHistoryService.selectList(null);
    }

    /**
     * 新增进退场
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(EntryExitHistory entryExitHistory) {
        entryExitHistoryService.insert(entryExitHistory);
        return SUCCESS_TIP;
    }

    /**
     * 删除进退场
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer entryExitHistoryId) {
        entryExitHistoryService.deleteById(entryExitHistoryId);
        return SUCCESS_TIP;
    }

    /**
     * 修改进退场
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(EntryExitHistory entryExitHistory) {
        entryExitHistoryService.updateById(entryExitHistory);
        return SUCCESS_TIP;
    }

    /**
     * 进退场详情
     */
    @RequestMapping(value = "/detail/{entryExitHistoryId}")
    @ResponseBody
    public Object detail(@PathVariable("entryExitHistoryId") Integer entryExitHistoryId) {
        return entryExitHistoryService.selectById(entryExitHistoryId);
    }
}
