package com.xywg.admin.modular.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.modular.system.model.TimeSet;
import com.xywg.admin.modular.system.model.TimeSetVO;
import com.xywg.admin.modular.system.service.ITimeSetService;
import com.xywg.admin.modular.system.warpper.TimeSetWarpper;

/**
 * 时间设置控制器
 *
 * @author shily
 * @Date 2018-06-22 08:52:56
 */
@Controller
@RequestMapping("/timeSet")
public class TimeSetController extends BaseController {

    private String PREFIX = "/system/timeSet/";

    @Autowired
    private ITimeSetService timeSetService;

    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;

    /**
     * 跳转到时间设置首页
     */
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
    	model.addAttribute("projects",timeSetService.getProjects());
        return PREFIX + "timeSet.html";
    }

    /**
     * 跳转到修改时间设置
     */
    @RequestMapping("/timeSet_update/{timeSetId}")
    public String timeSetUpdate(@PathVariable Integer timeSetId, Model model) {
    	TimeSetVO timeSetVO = timeSetService.selectTimeSetById(timeSetId);
        model.addAttribute("item",timeSetVO);
        LogObjectHolder.me().set(timeSetVO);
        return PREFIX + "timeSet_edit.html";
    }
    
    /**
     * 跳转到时间设置详情
     */
    @RequestMapping("/timeSet_view/{timeSetId}")
    public String timeSetView(@PathVariable Integer timeSetId, Model model) {
    	TimeSetVO timeSetVO = timeSetService.selectTimeSetById(timeSetId);
        model.addAttribute("item", timeSetVO);
        LogObjectHolder.me().set(timeSetVO);
        return PREFIX + "timeSet_info.html";
    }

    /**
     * 获取时间设置列表
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
   	@RequestMapping(value = "/list")
       @ResponseBody
       public Object list(@RequestParam Map<String, Object> map) {
       	    Page<TimeSetVO> page = new PageFactory<TimeSetVO>().defaultPage();
            List<Map<String, Object>> timeSetVO = timeSetService.selectTimeSetList(page, map);
            page.setRecords((List<TimeSetVO>) new TimeSetWarpper(timeSetVO).warp());
            return super.packForBT(page);
       }

    /**
     * 启用
     */
    @RequestMapping(value = "/enable")
    @ResponseBody
    public Object enable(@RequestParam Long timeSetId) {
        timeSetService.enable(timeSetId);
        return SUCCESS_TIP;
    }
    
    /**
     * 启用
     */
    @RequestMapping(value = "/disable")
    @ResponseBody
    public Object disable(@RequestParam Map<String,Object> map) {
        timeSetService.disable(map);
        return SUCCESS_TIP;
    }

    /**
     * 修改时间设置
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(TimeSet timeSet) {
        timeSetService.updateTimeSet(timeSet);
        return SUCCESS_TIP;
    }
    
    /**
     * 查询打卡时间区间
     */
    @RequestMapping(value = "/getTime")
    @ResponseBody
    public Object getTime(TimeSet timeSet) {
        TimeSetVO timeSetVO= timeSetService.getTime(timeSet);
        return timeSetVO;
    }

    /**
     * 时间设置详情
     */
    @RequestMapping(value = "/detail/{timeSetId}")
    @ResponseBody
    public Object detail(@PathVariable("timeSetId") Integer timeSetId) {
        return timeSetService.selectById(timeSetId);
    }
}
