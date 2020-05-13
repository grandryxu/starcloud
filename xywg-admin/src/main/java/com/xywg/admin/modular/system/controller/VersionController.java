package com.xywg.admin.modular.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.system.model.VersionVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.xywg.admin.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.xywg.admin.modular.system.model.Version;
import com.xywg.admin.modular.system.service.IVersionService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 版本管理控制器
 *
 * @author wangcw
 * @Date 2018-08-21 16:55:55
 */
@Controller
@RequestMapping("/version")
public class VersionController extends BaseController {

    private String PREFIX = "/system/version/";

    @Autowired
    private IVersionService versionService;

    /**
     * 跳转到版本管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "version.html";
    }

    /**
     * 跳转到添加版本管理
     */
    @RequestMapping("/version_add")
    public String versionAdd() {
        return PREFIX + "version_add.html";
    }

    /**
     * 跳转到修改版本管理
     */
    @RequestMapping("/version_update/{versionId}")
    public String versionUpdate(@PathVariable Integer versionId, Model model) {
        Version version = versionService.selectById(versionId);
        model.addAttribute("item",version);
        LogObjectHolder.me().set(version);
        return PREFIX + "version_edit.html";
    }

    /**
     * 获取版本管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String,Object> map) {
        Page<VersionVo> page = new PageFactory<VersionVo>().defaultPage();
        List<VersionVo> versionList= this.versionService.selectList(page ,map);
        page.setRecords(versionList);
        return super.packForBT(page);
    }

    /**
     * 新增版本管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Version version) {
        Date date = new Date();
        version.setCreateDate(date);
        version.setCreateUser(ShiroKit.getUser().getName());
        version.setUpdateDate(date);
        version.setUpdateUser(ShiroKit.getUser().getName());
        versionService.insert(version);
        return SUCCESS_TIP;
    }

    /**
     * 删除版本管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long versionId) {
        versionService.deleteById(versionId);
        return SUCCESS_TIP;
    }

    /**
     * 修改版本管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Version version) {
        version.setUpdateDate(new Date());
        version.setUpdateUser(ShiroKit.getUser().getName());
        versionService.updateById(version);
        return SUCCESS_TIP;
    }

    /**
     * 版本管理详情
     */
    @RequestMapping(value = "/detail/{versionId}")
    @ResponseBody
    public Object detail(@PathVariable("versionId") Integer versionId) {
        return versionService.selectById(versionId);
    }
}
