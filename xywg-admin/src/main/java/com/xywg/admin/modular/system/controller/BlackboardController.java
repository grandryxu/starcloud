package com.xywg.admin.modular.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.device.model.DeviceRecord;
import com.xywg.admin.modular.device.service.IDeviceRecordService;
import com.xywg.admin.modular.longxin.model.LxTenderFile;
import com.xywg.admin.modular.longxin.model.TenderResultBean;
import com.xywg.admin.modular.longxin.service.TenderingService;
import com.xywg.admin.modular.project.service.IProjectAddressService;
import com.xywg.admin.modular.project.service.IProjectWorkerService;
import com.xywg.admin.modular.projectSubContractor.service.IProjectSubContractorService;
import com.xywg.admin.modular.system.model.User;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.system.service.IUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 总览信息
 *
 * @author wangcw
 * @Date 2017年3月4日23:05:54
 */
@Controller
@RequestMapping("/blackboard")
public class BlackboardController extends BaseController {
	@Value("${spring.mvc.view.imageLocalPath}")
    private String path;

    @Autowired
    private IProjectSubContractorService projectSubContractorService;

    @Autowired
    private IProjectWorkerService projectWorkerService;

    @Autowired
    private IDeviceRecordService deviceRecordService;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private IProjectAddressService projectAddressService;
    
    @Autowired
    private TenderingService tenderingService;

    @Autowired
    private IUserService userService;


    /**
     * 跳转到项目
     */
    @RequestMapping("")
    public String blackboard(Model model) {
        int totalEntry = projectSubContractorService.getTotalEntry();

        model.addAttribute("getTotalEntry", projectSubContractorService.getTotalEntry());
        model.addAttribute("getTotalExit", projectSubContractorService.getTotalExit());
        model.addAttribute("getTotalJoin", projectWorkerService.getTotalJoin());
        model.addAttribute("getTotalDevice", deviceRecordService.getTotalDevice());

        //model.addAttribute("project",projectAddressService.getProjectAddressByToggleDeptId());

        //项目切换 切换 菜单导航
        String breadCrumb = "";
        if (ShiroKit.getSessionAttr("breadCrumb") == null) {
            String loginDeptName = deptService.selectById(ShiroKit.getUser().getDeptId()).getSimplename();
            breadCrumb = loginDeptName;
        } else {
            breadCrumb = ShiroKit.getSessionAttr("breadCrumb");
        }
        model.addAttribute("breadCrumb", breadCrumb);
        //是否切换项目
        if ("".equals(ShiroKit.getSessionAttr("toggleProjectCode")) || ShiroKit.getSessionAttr("toggleProjectCode") == null) {
            //公司级别
            return "/blackboard.html";
        } else {//项目级别
            //项目班组数
            model.addAttribute("teamCount", projectWorkerService.getTeamCountPC(null, ShiroKit.getSessionAttr("toggleProjectCode")));
            //项目进场人数
            model.addAttribute("personCount", projectWorkerService.getJoinedCountPC(null, ShiroKit.getSessionAttr("toggleProjectCode")));
            //今日考勤人数
            DeviceRecord deviceRecord = new DeviceRecord();
            deviceRecord.setProjectCode(ShiroKit.getSessionAttr("toggleProjectCode"));
            model.addAttribute("todayPersons", deviceRecordService.getWorkerTotalCount(deviceRecord));

            return "/project_index.html";
        }
    }

    /**
     * 跳转到项目
     */
    @RequestMapping("/map")
    public String blackboardMap(Model model) {
        model.addAttribute("project", projectAddressService.getProjectAddressByToggleDeptId());
        return "/blackboard_map.html";
    }


    /**
     * 首页
     */
    @RequestMapping("/dashborad")
    public String dashborad(Model model) {

        Integer id =  ShiroKit.getUser().getDeptId();
        List<Integer> roleList = ShiroKit.getUser().getRoleList();
        User user = userService.getByAccount(ShiroKit.getUser().getAccount());

    	Map<String,Object> map = new HashedMap();
    	List<TenderResultBean> lxs = tenderingService.selectListTender(map,new Page<TenderResultBean>());
    	for (TenderResultBean tenderResultBean : lxs) {
    		List<LxTenderFile> files = tenderingService.getFileById(tenderResultBean.getId());
    		tenderResultBean.setFiles(files);
		}
    	List<TenderResultBean> fixs = tenderingService.selectListFix(map,new Page<TenderResultBean>());
    	for (TenderResultBean fix : fixs) {
    		List<LxTenderFile> files = tenderingService.getFileById(fix.getId());
    		fix.setFiles(files);
		}
    	model.addAttribute("pros", lxs);
    	model.addAttribute("fixs", fixs);
    	model.addAttribute("path", path);
    	model.addAttribute("flowStatus", StringUtils.isNotBlank(user.getFlowStatus()) ? user.getFlowStatus() : "0");
        model.addAttribute("isExternal", CollectionUtils.isNotEmpty(roleList) && roleList.contains(139) ? "1" : "0");

        if(239==id){
            return "/longxin/indexqy.html";
        }else{
            return "/longxin/index.html";
        }
    }
    
    /**
     * 招标信息
     */
    @RequestMapping("/dashborad/tender")
    @ResponseBody
    public Object dashboradTender(@RequestParam Map<String,Object> map) {
    	Page<TenderResultBean> page = new PageFactory<TenderResultBean>().defaultPage();
    	List<TenderResultBean> lxs = tenderingService.selectListTender(map,page);
    	for (TenderResultBean tenderResultBean : lxs) {
    		List<LxTenderFile> files = tenderingService.getFileById(tenderResultBean.getId());
    		tenderResultBean.setFiles(files);
		}
    	page.setRecords(lxs);
        return page;
    }
    
    
    
    /**
     * 招标信息
     */
    @RequestMapping("/dashborad/fix")
    @ResponseBody
    public Object dashboradFix(@RequestParam Map<String,Object> map) {
    	Page<TenderResultBean> page = new PageFactory<TenderResultBean>().defaultPage();
    	List<TenderResultBean> fixs = tenderingService.selectListFix(map,page);
    	for (TenderResultBean fix : fixs) {
    		List<LxTenderFile> files = tenderingService.getFileById(fix.getId());
    		fix.setFiles(files);
		}
    	page.setRecords(fixs);
        return page;
    }
}
