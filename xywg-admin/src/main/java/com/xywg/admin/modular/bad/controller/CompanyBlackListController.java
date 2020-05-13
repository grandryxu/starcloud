package com.xywg.admin.modular.bad.controller;

import java.util.List;
import java.util.Map;

import com.xywg.admin.modular.system.service.AccountProjectService;
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
import com.xywg.admin.modular.bad.model.WorkerBlackList;
import com.xywg.admin.modular.bad.model.WorkerBlackListVO;
import com.xywg.admin.modular.bad.service.IWorkerBlackListService;
import com.xywg.admin.modular.bad.warpper.WorkerBlackListWarpper;
import com.xywg.admin.modular.system.service.IDictService;

/**
 * 企业黑名单记录控制器
 *
 * @author shily
 * @Date 2018-06-06 08:57:30
 */
@Controller
@RequestMapping("/companyBlackList")
public class CompanyBlackListController extends BaseController {

    private String PREFIX = "/bad/companyBlackList/";

    @Autowired
    private IWorkerBlackListService workerBlackListService;
    
    @Autowired
    private IDictService dictService;

    @Autowired
    private AccountProjectService accountProjectService;
    
    /**
     * 跳转到企业黑名单记录首页
     */
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("switchType",accountProjectService.getSwitchType());
        return PREFIX + "companyBlackList.html";
    }

    /**
     * 跳转到添加企业黑名单记录
     */
    @RequestMapping("/companyBlackList_add")
    public String companyBlackListAdd(Model model) {
        model.addAttribute("projects",workerBlackListService.getProjects());
    	model.addAttribute("dictValidStatus", dictService.selectByName("时间类型"));
        return PREFIX + "companyBlackList_add.html";
    }

    /**
     * 跳转到修改企业黑名单记录
     */
    @RequestMapping("/companyBlackList_update/{companyBlackListId}")
    public String workerBlackListUpdate(@PathVariable Integer companyBlackListId, Model model) {
        WorkerBlackListVO workerBlackList = workerBlackListService.selectCompanyBlackListById(companyBlackListId);
        model.addAttribute("dictValidStatus", dictService.selectByName("时间类型"));
        model.addAttribute("item",workerBlackList);
        LogObjectHolder.me().set(workerBlackList);
        return PREFIX + "companyBlackList_edit.html";
    }
    
    /**
     * 查看详情
     */
    @RequestMapping("/companyBlackList_view/{companyBlackListId}")
    public String companyBlackListView(@PathVariable Integer companyBlackListId, Model model) {
    	WorkerBlackListVO workerBlackList = workerBlackListService.selectCompanyBlackListById(companyBlackListId);
    	model.addAttribute("dictValidStatus", dictService.selectByName("时间类型"));
        model.addAttribute("item",workerBlackList);
        LogObjectHolder.me().set(workerBlackList);
        return PREFIX + "companyBlackList_info.html";
    }

    /**
     * 获取企业黑名单记录列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {
    	 Page<WorkerBlackList> page = new PageFactory<WorkerBlackList>().defaultPage();
         List<Map<String, Object>> WorkerBlackList = workerBlackListService.selectCompanyBlackList(page, map);
         page.setRecords((List<WorkerBlackList>) new WorkerBlackListWarpper(WorkerBlackList).warp());
         return super.packForBT(page);
    }

    /**
     * 新增企业黑名单记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(WorkerBlackList workerBlackList) {
    	workerBlackList.setIsValid(1);
        workerBlackListService.insert(workerBlackList);
        return SUCCESS_TIP;
    }

    /**
     * 删除企业黑名单记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long companyBlackListId) {
        workerBlackListService.deleteById(companyBlackListId);
        return SUCCESS_TIP;
    }

    /**
     * 修改企业黑名单记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(WorkerBlackList workerBlackList) {
        workerBlackListService.updateById(workerBlackList);
        return SUCCESS_TIP;
    }

    /**
     * 工人黑名单记录详情
     */
    @RequestMapping(value = "/detail/{companyBlackListId}")
    @ResponseBody
    public Object detail(@PathVariable("companyBlackListId") Integer companyBlackListId) {
        return workerBlackListService.selectById(companyBlackListId);
    }
    
    /**
     * 公司名称下拉框onchange事件
     */
    @RequestMapping(value = "/companyDetail/{organizationCode}")
    @ResponseBody
    public Object companyDetail(@PathVariable("organizationCode") String organizationCode) {
        return workerBlackListService.selectCompanyInfoById(organizationCode);
    }
    
}
