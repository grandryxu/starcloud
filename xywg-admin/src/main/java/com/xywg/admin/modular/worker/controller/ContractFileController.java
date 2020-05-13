package com.xywg.admin.modular.worker.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.modular.worker.model.Contract;
import com.xywg.admin.modular.worker.model.ContractFile;
import com.xywg.admin.modular.worker.service.IContractFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合同附件控制器
 *
 * @author wangcw
 * @Date 2018-06-08 09:40:11
 */
@Controller
@RequestMapping("/contractFile")
public class ContractFileController extends BaseController {

    private String PREFIX = "/worker/contractFile/";

    @Autowired
    private IContractFileService contractFileService;

    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;
    
    @Autowired
    private IDictService dictService;
   
    /**
     * 跳转到合同附件首页
     */
    @RequestMapping("")
    public String index(Model model) {
		model.addAttribute("project",cooperationProjectMasterService.getList(new HashMap<>()));
        return "/project/contract/contract.html";
    }

    /**
     * 跳转到添加合同附件
     */
    @RequestMapping("/contractFile_add")
    public String contractFileAdd() {
        return PREFIX + "contractFile_add.html";
    }

    /**
     * 跳转到修改合同附件
     */
    @RequestMapping("/contractFile_update/{contractFileId}")
    public String contractFileUpdate(@PathVariable Integer contractFileId, Model model) {
        ContractFile contractFile = contractFileService.selectById(contractFileId);
        model.addAttribute("item",contractFile);
        LogObjectHolder.me().set(contractFile);
        return PREFIX + "contractFile_edit.html";
    }

    /**
     * 批量设置标签值
     * @param ids
     * @param value
     * @return
     */
    @RequestMapping("/contractFile_setting")
    @ResponseBody
    public Object contractFileSetting(String ids, String value) {
        return contractFileService.updateSetting(ids, value);
    }

    /**
     * 获取合同附件列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String,Object> map) {
        Page<ContractFile> page = new PageFactory<ContractFile>().defaultPage();
        List<ContractFile> fileInfos = this.contractFileService.getList(page, map);
        page.setRecords(fileInfos);
        return super.packForBT(page);
    }

    /**
     * 获取所有合同附件列表
     */
    @RequestMapping(value = "/getList")
    @ResponseBody
    public Object getList(@RequestParam Map<String,Object> map) {
        Page<Contract> page = new PageFactory<Contract>().defaultPage();
        List<Contract> fileInfos = this.contractFileService.getContractList(page, map);
        page.setRecords(fileInfos);
        return super.packForBT(page);
    }

    /**
     * 新增合同附件
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ContractFile contractFile) {
        contractFile.setCreateDate(new Date());
        contractFileService.insert(contractFile);
        return SUCCESS_TIP;
    }

    /**
     * 删除合同附件
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer contractFileId) {
        contractFileService.deleteById(contractFileId);
        return SUCCESS_TIP;
    }
    
    

    /**
     * 批量删除附件管理
     */
    @RequestMapping(value = "/deletes")
    @ResponseBody
    public Object delete(@RequestParam String ids) {
        List<String> idList = JSON.parseArray(ids,String.class);
        contractFileService.deleteAccessory(idList);
        return SUCCESS_TIP;
    }


    /**
     * 修改合同附件
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ContractFile contractFile) {
        contractFileService.updateById(contractFile);
        return SUCCESS_TIP;
    }

    /**
     * 合同附件详情
     */
    @RequestMapping(value = "/detail/{contractFileId}")
    @ResponseBody
    public Object detail(@PathVariable("contractFileId") Integer contractFileId) {
        return contractFileService.selectById(contractFileId);
    }

    /**
     * 新增合同
     */
    @RequestMapping(value = "/addContract")
    @ResponseBody
    public Object addContract(@RequestParam("file") MultipartFile file,ContractFile contractFile) {
        return contractFileService.addContract(file,contractFile);
    }

    /**
     * 批量审核
     */
    @RequestMapping(value = "/batchReview")
    @ResponseBody
    public Object batchReview(@RequestParam String ids,@RequestParam Integer status) {
        return contractFileService.batchReview(ids,status);
    }
    
    /**
     * 跳转到合同附件首页
     */
    @RequestMapping("/contractIndex")
    public String contractIndex(Model model) {
		model.addAttribute("project",cooperationProjectMasterService.getList(new HashMap<>()));
        return "/worker/workerContractFile/workerContractFile.html";
    }
    
    /**
     * 获取合同附件列表
     */
    @RequestMapping(value = "/listFiles")
    @ResponseBody
    public Object selectFiles(@RequestParam Map<String,Object> map) {
        Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
        List<Map<String, Object>> fileInfos = this.contractFileService.selectWorkerContractFiles(page, map);
        page.setRecords(fileInfos);
        return super.packForBT(page);
    }
    
    /**
     * 跳转到合同附件首页
     */
    @RequestMapping("/detail")
    public String detail(Model model,@RequestParam(required = false) Long pwId) {
		model.addAttribute("pwId",pwId);
		model.addAttribute("tagType", dictService.selectByName("工人标签"));
        return "/worker/workerContractFile/workerContractFile_info.html";
    }
}
