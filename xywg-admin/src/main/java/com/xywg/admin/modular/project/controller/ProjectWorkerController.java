package com.xywg.admin.modular.project.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.modular.project.model.ProjectWorker;
import com.xywg.admin.modular.project.service.IProjectWorkerService;
import com.xywg.admin.modular.project.wrapper.ProjectTrainWorkerWarpper;
import com.xywg.admin.modular.project.wrapper.ProjectWorkerWrapper;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.modular.team.service.ITeamMasterService;

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
 * 项目工人控制器
 *
 * @author wangcw
 * @Date 2018-05-28 17:09:16
 */
@Controller
@RequestMapping("/projectWorker")
public class ProjectWorkerController extends BaseController {

    private String PREFIX = "/project/projectWorker/";

    @Autowired
    private IProjectWorkerService projectWorkerService;
    
    @Autowired
    private ITeamMasterService teamMasterService;
    
    @Autowired
    private IDictService dictService;

    /**
     * 跳转到项目工人首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "show_pdf.html";
    }

    /**
     * 跳转到添加项目工人
     */
    @RequestMapping("/projectWorker_add")
    public String projectWorkerAdd() {
        return PREFIX + "projectWorker_add.html";
    }


    /**
     * 弹出工人搜索列表框
     *
     * @author wangcw
     */
    @RequestMapping("/projectWorker_table_dialog")
    public String workerMasterTableDialog(Model model) {
        return PREFIX + "projectWorker_table_dialog.html";
    }

    /**
     * 跳转到修改项目工人
     */
    @RequestMapping("/projectWorker_update/{projectWorkerId}")
    public String projectWorkerUpdate(@PathVariable Integer projectWorkerId, Model model) {
        ProjectWorker projectWorker = projectWorkerService.selectById(projectWorkerId);
        model.addAttribute("item", projectWorker);
        LogObjectHolder.me().set(projectWorker);
        return PREFIX + "projectWorker_edit.html";
    }

    /**
     * 获取项目工人列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {

        return projectWorkerService.selectList(null);
    }

    /**
     * 获取工作履历列表(tab页)
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getListByIdCard")
    @ResponseBody
    public Object getListByIdCard(@RequestParam Map<String, Object> map) {
        Page<ProjectWorker> page = new PageFactory<ProjectWorker>().defaultPage();
        List<Map<String, Object>> workerExperience = projectWorkerService.getListByIdCard(page, map);
        page.setRecords((List<ProjectWorker>) new ProjectWorkerWrapper(workerExperience).warp());
        return super.packForBT(page);
    }

    /**
     * 新增项目工人
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProjectWorker projectWorker) {
        projectWorkerService.insert(projectWorker);
        return SUCCESS_TIP;
    }

    /**
     * 绑定安全帽
     */
    @RequestMapping(value = "/bindSafetyHelmet")
    @ResponseBody
    public Object bindSafetyHelmet(ProjectWorker projectWorker) {
        projectWorkerService.bindSafetyHelmet(projectWorker);
        return SUCCESS_TIP;
    }

    /**
     * 删除项目工人
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer projectWorkerId) {
        projectWorkerService.deleteById(projectWorkerId);
        return SUCCESS_TIP;
    }

    /**
     * 修改项目工人
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ProjectWorker projectWorker) {
        projectWorkerService.updateById(projectWorker);
        return SUCCESS_TIP;
    }

    /**
     * 项目工人详情
     */
    @RequestMapping(value = "/detail/{projectWorkerId}")
    @ResponseBody
    public Object detail(@PathVariable("projectWorkerId") Integer projectWorkerId) {
        return projectWorkerService.selectById(projectWorkerId);
    }


    /**
     * 跳转页面
     *
     * @author: duanfen
     * @version: 2018年6月16日 上午9:48:20
     */
    @RequestMapping(value = "/skippage")
    public String skipPage(@RequestParam String projectCode, @RequestParam int type, @RequestParam String deviceSns, Model model) {
        model.addAttribute("projectCode", projectCode);
        model.addAttribute("type", type);
        model.addAttribute("deviceSns", deviceSns);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectCode", projectCode);
        model.addAttribute("team", teamMasterService.getList(map));
        model.addAttribute("workKind", dictService.selectByName("工种字典数据"));
        return PREFIX + "projectWorker_table_dialog.html";
    }

    /**
     * 查询项目下面的工人身份证号
     *
     * @author: duanfen
     * @version: 2018年6月16日 上午9:48:20
     */
    @RequestMapping(value = "/byproject")
    @ResponseBody
    public Object queryByProject(@RequestParam Map<String, Object> map) {
    	String projectCode = (String) map.get("projectCode");
    	String team = (String) map.get("team");
    	String workKind = (String) map.get("workKind");
    	String keys = (String) map.get("keys");
        return projectWorkerService.queryByProject(projectCode,team,workKind, keys);
    }

    /**
     * 查询项目下所有工人 xieshuaishuai
     */
    @RequestMapping(value = "/getWorkerListByProject")
    @ResponseBody
    public Object getWorkerListByProject(@RequestParam Map map) {
        Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
        List<Map<String, Object>> list = projectWorkerService.getWorkerListByProject(map, page);
        page.setRecords((List<Map<String, Object>>) new ProjectTrainWorkerWarpper(list).warp());
        return super.packForBT(page);
    }

    /**
     * 修改安全帽
     * 2018年6月23日
     * 下午9:37:56
     *
     * @author wangshibo
     */
    @RequestMapping(value = "/updateShImei")
    @ResponseBody
    public Object updateShImei(@RequestParam String projectCode, @RequestParam String helmetCode, @RequestParam Integer workerId) {
        projectWorkerService.updateShImei(projectCode, helmetCode, workerId);
        return SUCCESS_TIP;
    }

    /**
     * 根据用户信息获取工人项目详情
     * 2018年6月23日
     * 下午9:37:56
     *
     * @author wangshibo
     */
    @RequestMapping(value = "/getProjectWorkerByUserInfo")
    @ResponseBody
    public Object getProjectWorkerByUserInfo(@RequestParam String projectCode, @RequestParam Integer idCardType, @RequestParam String idCardNumber) {
        return projectWorkerService.getProjectWorkerByUserInfo(projectCode, idCardType, idCardNumber);
    }


    /**
     * 修改门禁卡号
     * @param pwId
     * @param cardNumber
     * @return
     */
    @RequestMapping(value = "/updateCardNumber")
    @ResponseBody
    public Object updateCardNumber(@RequestParam Long id, @RequestParam String cardNumber) {
        projectWorkerService.updateCardNumber(id, cardNumber);
        return SUCCESS_TIP;
    }

}
