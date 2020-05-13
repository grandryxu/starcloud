package com.xywg.admin.modular.device.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.base.tips.ErrorTip;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.device.model.SafetyHelmet;
import com.xywg.admin.modular.device.service.ISafetyHelmetService;
import com.xywg.admin.modular.device.wrapper.SafetyHelmetWarpper;
import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 安全帽管理控制器
 *
 * @author wangcw
 * @Date 2018-05-29 10:56:31
 */
@Controller
@RequestMapping("/safetyHelmet")
public class SafetyHelmetController extends BaseController {

    private String PREFIX = "/device/safetyHelmet/";

    @Autowired
    private ISafetyHelmetService safetyHelmetService;

    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;


    /**
     * 跳转到安全帽管理首页
     */
    @RequestMapping("")
    public String index(@RequestParam Map<String,Object> map,Model model) {
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        model.addAttribute("projectCode",map.get("projectCode"));
        model.addAttribute("projectName",map.get("projectName"));
        model.addAttribute("projectStatus",map.get("projectStatus"));
        return PREFIX + "safetyHelmet.html";
    }

    /**
     * 跳转到添加安全帽管理
     */
    @RequestMapping("/safetyHelmet_add")
    public String safetyHelmetAdd(@RequestParam Map<String,Object> map,Model model) {
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        model.addAttribute("projectCode",map.get("projectCode"));
        model.addAttribute("projectName",map.get("projectName"));
        return PREFIX + "safetyHelmet_add.html";
    }

    /**
     * 跳转到修改安全帽管理
     */
    @RequestMapping("/safetyHelmet_update")
    public String safetyHelmetUpdate(@RequestParam Map<String,Object> map, Model model) {
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        SafetyHelmet safetyHelmet = safetyHelmetService.selectById(Integer.parseInt(map.get("id").toString()));
        model.addAttribute("item",safetyHelmet);
        model.addAttribute("projectName",map.get("projectName"));
        LogObjectHolder.me().set(safetyHelmet);
        return PREFIX + "safetyHelmet_edit.html";
    }

    /**
     * 双击查看
     */
    @RequestMapping("/safetyHelmet_view")
    public String safetyHelmetView(@RequestParam Map<String,Object> map, Model model) {
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        SafetyHelmet safetyHelmet = safetyHelmetService.selectById(Integer.parseInt(map.get("id").toString()));
        model.addAttribute("item",safetyHelmet);
        model.addAttribute("projectName",map.get("projectName"));
        LogObjectHolder.me().set(safetyHelmet);
        return PREFIX + "safetyHelmet_info.html";
    }

    /**
     * 获取安全帽管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String,Object> map) {
        Page<SafetyHelmet> page = new PageFactory<SafetyHelmet>().defaultPage();
        List<Map<String,Object>> safetyHelmets= safetyHelmetService.selectList(page ,map);
        page.setRecords((List<SafetyHelmet>) new SafetyHelmetWarpper(safetyHelmets).warp());
        return super.packForBT(page);
    }

    /**
     * 获取项目下未使用的安全帽
     */
    @RequestMapping(value = "/getUnusedSafetyHelmet")
    @ResponseBody
    public Object getUnusedSafetyHelmet(@RequestParam String projectCode) {
        return this.safetyHelmetService.getUnusedSafetyHelmet(projectCode);
    }

    /**
     * 新增安全帽管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SafetyHelmet safetyHelmet) {
        safetyHelmet.setCreateUser(ShiroKit.getUser().getName());
        safetyHelmetService.insert(safetyHelmet);
        return SUCCESS_TIP;
    }

    /**
     * 删除安全帽管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long safetyHelmetId) {
        safetyHelmetService.deleteById(safetyHelmetId);
        return SUCCESS_TIP;
    }

    /**
     * 批量删除安全帽管理
     */
    @RequestMapping(value = "/deleteSafetyHelmets")
    @ResponseBody
    public Object deleteSafetyHelmets(@RequestParam String ids) {
        safetyHelmetService.deleteSafetyHelmets(ids);
        return SUCCESS_TIP;
    }


    /**
     * 修改安全帽管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SafetyHelmet safetyHelmet) {
        if(safetyHelmetService.updateSafety(safetyHelmet)==0){
            return new ErrorTip(600,"该序列号已存在！");
        }else{
        return SUCCESS_TIP;
    }}

    /**
     * 安全帽管理详情
     */
    @RequestMapping(value = "/detail/{safetyHelmetId}")
    @ResponseBody
    public Object detail(@PathVariable("safetyHelmetId") Integer safetyHelmetId) {
        return safetyHelmetService.selectById(safetyHelmetId);
    }

    /**
     * 安全帽管理详情
     */
    @RequestMapping(value = "/getUnusedHelmetsByProjectCode")
    @ResponseBody
    public Object getUnusedHelmetsByProjectCode(@RequestParam Map<String,Object> map) {
        return safetyHelmetService.getUnusedHelmetsByProjectCode(map);
    }

}
