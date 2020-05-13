package com.xywg.admin.modular.system.controller;

import com.alibaba.fastjson.JSON;
import com.xywg.admin.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.xywg.admin.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.xywg.admin.modular.system.model.FileInfo;
import com.xywg.admin.modular.system.service.IFileInfoService;

import java.util.List;

/**
 * 附件管理控制器
 *
 * @author wangcw
 * @Date 2018-06-07 15:29:47
 */
@Controller
@RequestMapping("/fileInfo")
public class FileInfoController extends BaseController {

    private String PREFIX = "/system/fileInfo/";

    @Autowired
    private IFileInfoService fileInfoService;

    /**
     * 跳转到附件管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "fileInfo.html";
    }

    /**
     * 跳转到添加附件管理
     */
    @RequestMapping("/fileInfo_add")
    public String fileInfoAdd() {
        return PREFIX + "fileInfo_add.html";
    }

    /**
     * 跳转到修改附件管理
     */
    @RequestMapping("/fileInfo_update/{fileInfoId}")
    public String fileInfoUpdate(@PathVariable Integer fileInfoId, Model model) {
        FileInfo fileInfo = fileInfoService.selectById(fileInfoId);
        model.addAttribute("item",fileInfo);
        LogObjectHolder.me().set(fileInfo);
        return PREFIX + "fileInfo_edit.html";
    }

    /**
     * 获取附件管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return fileInfoService.selectList(null);
    }

    /**
     * 新增附件管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(FileInfo fileInfo) {
        fileInfoService.insert(fileInfo);
        return SUCCESS_TIP;
    }

    /**
     * 删除附件管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer fileInfoId) {
        fileInfoService.deleteById(fileInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 批量删除附件管理
     */
    @RequestMapping(value = "/deletes")
    @ResponseBody
    public Object delete(@RequestParam String ids) {
        List<String> idList = JSON.parseArray(ids,String.class);
        fileInfoService.deleteAccessory(idList);
        return SUCCESS_TIP;
    }

    /**
     * 修改附件管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(FileInfo fileInfo) {
        fileInfoService.updateById(fileInfo);
        return SUCCESS_TIP;
    }

    /**
     * 附件管理详情
     */
    @RequestMapping(value = "/detail/{fileInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("fileInfoId") Integer fileInfoId) {
        return fileInfoService.selectById(fileInfoId);
    }
}
