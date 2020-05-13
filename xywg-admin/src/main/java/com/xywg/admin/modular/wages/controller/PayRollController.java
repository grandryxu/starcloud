package com.xywg.admin.modular.wages.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.modular.system.model.FileInfo;
import com.xywg.admin.modular.system.service.IFileInfoService;
import com.xywg.admin.modular.system.service.UploadService;
import com.xywg.admin.modular.wages.model.PayRoll;
import com.xywg.admin.modular.wages.model.PayRollDetailVo;
import com.xywg.admin.modular.wages.service.IPayRollService;
import com.xywg.admin.modular.wages.service.SettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 工资单控制器
 *
 * @author wangcw
 * @Date 2018-06-01 10:14:07
 */
@Controller
@RequestMapping("/payRoll")
public class PayRollController extends BaseController {

    private String PREFIX = "/wages/payRoll/";

    @Autowired
    private IPayRollService payRollService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private IFileInfoService fileInfoService;

    @Autowired
    private SettlementService settlementService;

    /**
     * 跳转到工资单首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "payRoll.html";
    }

    /**
     * 跳转到添加工资单
     */
    @RequestMapping("/payRoll_add")
    public String payRollAdd() {
        return PREFIX + "payRoll_add.html";
    }

    /**
     * 跳转到修改工资单
     */
    @RequestMapping("/payRoll_update/{payRollId}")
    public String payRollUpdate(@PathVariable Integer payRollId, Model model) {
        PayRoll payRoll = payRollService.selectById(payRollId);
        model.addAttribute("item", payRoll);
        LogObjectHolder.me().set(payRoll);
        return PREFIX + "payRoll_edit.html";
    }

    /**
     * 获取工资单列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {
        Page<PayRollDetailVo> page = new PageFactory<PayRollDetailVo>().defaultPage();
        List<PayRollDetailVo> payRollDetails = payRollService.selectList(page, map);
        page.setRecords(payRollDetails);
        return super.packForBT(page);
    }

    /**
     * 新增工资单
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PayRoll payRoll) {
        payRollService.insert(payRoll);
        return SUCCESS_TIP;
    }

    /**
     * 删除工资单
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer payRollId) {
        payRollService.deleteById(payRollId);
        return SUCCESS_TIP;
    }

    /**
     * 批量删除工资单
     */
    @RequestMapping(value = "/deleteIds")
    @ResponseBody
    public Object deleteIds(@RequestParam String ids) {
        this.payRollService.deleteByIdAndUpdateAccount(ids);
        return SUCCESS_TIP;
    }


    /**
     * 修改工资单
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PayRoll payRoll) {
        payRollService.updateById(payRoll);
        return SUCCESS_TIP;
    }

    /**
     * 工资单详情
     */
    @RequestMapping(value = "/detail/{payRollId}")
    @ResponseBody
    public Object detail(@PathVariable("payRollId") Integer payRollId) {
        return payRollService.selectById(payRollId);
    }

    /**
     * 根据工资单查询工资单详情
     */
    @RequestMapping(value = "/selectDetailByPayRollCode")
    @ResponseBody
    public Object selectDetailByPayRollCode(@RequestParam Map<String, Object> map) {
        Page<PayRollDetailVo> page = new PageFactory<PayRollDetailVo>().defaultPage();
        List<PayRollDetailVo> payRollDetails = payRollService.selectDetailByPayRollCode(page, map);
        page.setRecords(payRollDetails);
        return super.packForBT(page);
    }


    /**
     * 根据工资单查询工资单详情
     */
    @RequestMapping(value = "/selectDetailByPayRollCodeNoPage")
    @ResponseBody
    public Object selectDetailByPayRollCodeNoPage(@RequestParam Map<String, Object> map) {
        List<PayRollDetailVo> payRollDetails = payRollService.selectDetailByPayRollCodeNoPage(map);
        return payRollDetails;
    }

    /**
     * 批量提交工资单
     */
    @RequestMapping(value = "/submit")
    @ResponseBody
    public Object submit(@RequestParam String ids) {
        payRollService.submit(ids);
        return SUCCESS_TIP;
    }

    /**
     * 工资发放
     */
    @RequestMapping(value = "/grantPayRoll")
    @ResponseBody
    public Object grantPayRoll(@RequestParam String ids) {
        payRollService.grantPayRoll(ids);
        return SUCCESS_TIP;
    }

    /**
     * 上传附件
     */
    @RequestMapping(value = "/uploadAccessory", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Object upload(@RequestParam("file") MultipartFile[] files, @RequestParam("payRollUploadId") String payRollUploadId) {
        for(MultipartFile file: files) {
            //允许上传的文件类型
            String fileType = "gif,png,bmp,jpeg,jpg,tiff,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw,wmf,webp";
            //允许上传的文件最大大小(10M,单位为byte)
            int maxSize = 10 * 1024 * 1024;
            List<FileInfo> fileInfoList = new ArrayList<>();
            //重新定义上传路径和文件名称,保存到FTP 远程静态服务器上,返回基础文件地址
            Map<String, String> resMap = uploadService.fileHandle(file, fileType, maxSize);
            if (resMap.containsKey("path")) {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setFileName(file.getOriginalFilename());
                fileInfo.setPath(resMap.get("path"));
                fileInfo.setRelevanceId(Long.parseLong(payRollUploadId));
                //buss_pay_roll 工资单表
                fileInfo.setType("buss_pay_roll");
                fileInfoList.add(fileInfo);
                //保存结算单和附件的关联关系到数据库表:buss_file_info
                fileInfoService.insertPatch(fileInfoList);
            } else {
                return resMap;
            }
        }
        return SUCCESS_TIP;
    }

    /**
     * 查看附件图片
     */
    @RequestMapping(value = "/getAccessoryPicture")
    @ResponseBody
    public Object getAccessoryPicture(@RequestParam String id) {
        return settlementService.getAccessoryPicture(Long.parseLong(id),"工资单附件" ,"buss_pay_roll");
    }
}
