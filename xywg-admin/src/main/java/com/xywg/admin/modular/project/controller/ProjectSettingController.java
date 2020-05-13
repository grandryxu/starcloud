package com.xywg.admin.modular.project.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import com.xywg.admin.modular.project.service.IProjectMasterService;
import com.xywg.admin.modular.project.wrapper.ProjectMasterWrapper;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 項目設置
 * @author hh cao
 * @date 2019/4/17
 */
@RestController
@RequestMapping("/projectSetting")
public class ProjectSettingController extends BaseController {
    private String PREFIX = "/project/projectSetting/";

    @Autowired
    private IProjectMasterService projectMasterService;
    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;

    /**
     * 跳轉到項目設置頁面
     * @return
     */
    @GetMapping("")
    public ModelAndView jump() {
        ModelAndView view = new ModelAndView(PREFIX + "projectSetting.html");
        view.addObject("project", cooperationProjectMasterService.getList(new HashMap<>()));
        return view;
    }

    /**
     * 獲取項目列表
     * @return
     */
    @PostMapping("/list")
    public Object list(@RequestParam Map<String, Object> params) {
        Page<Map<String, Object>> page = new PageFactory<Map<String,Object>>().defaultPage();
        List<Map<String, Object>> list = projectMasterService.getListForSetting(params, page);
        page.setRecords(list);
        return super.packForBT(page);
    }

    /**
     * 保存修改
     * @param params
     * @return
     */
    @PutMapping("/save")
    public Object save(@RequestParam Map<String, Object> params) {
        projectMasterService.saveForSetting(params);
        return SUCCESS_TIP;
    }
}
