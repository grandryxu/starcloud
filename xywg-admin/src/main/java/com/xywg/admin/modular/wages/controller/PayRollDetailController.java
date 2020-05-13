package com.xywg.admin.modular.wages.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.modular.wages.model.*;
import com.xywg.admin.modular.wages.service.IPayRollDetailService;
import com.xywg.admin.modular.wages.service.IPayRollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 工资详情控制器
 *
 * @author wangcw
 * @Date 2018-06-01 10:14:30
 */
@Controller
@RequestMapping("/payRollDetail")
public class PayRollDetailController extends BaseController {

    private String PREFIX = "/wages/payRollDetail/";

    @Autowired
    private IDictService dictService;
    @Autowired
    private IPayRollDetailService payRollDetailService;
    @Autowired
    private IPayRollService payRollService;
    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;
    /**
     * 跳转到工资详情首页
     */
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("project",cooperationProjectMasterService.getList(new HashMap<>()));
        model.addAttribute("workerType",dictService.selectByName("工种字典数据"));

        model.addAttribute("isEnterprise",ShiroKit.getUser().getIsEnterprise());
        return PREFIX + "payRollDetail.html";
    }

    /**
     * 跳转到添加工资详情
     */
    @RequestMapping("/payRollDetail_add")
    public String payRollDetailAdd(Model model) {
        model.addAttribute("type", dictService.selectByName("工资类型"));
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        return PREFIX + "payRollDetail_add.html";
    }

    /**
     * 跳转到修改工资详情
     */
    @RequestMapping("/payRollDetail_update")
    public String payRollDetailUpdate(@RequestParam String payRollCode,@RequestParam String type,@RequestParam String saveStatus, Model model) {
        model.addAttribute("payRollCode",payRollCode);
        model.addAttribute("type",type);
        model.addAttribute("workerType",dictService.selectByName("工种字典数据"));
        model.addAttribute("id",payRollService.selectByPayRollCode(payRollCode).getId());
        return PREFIX + "payRollDetail_edit.html";
    }

    /**
     * 双击查看
     */
    @RequestMapping("/payRollDetail_view")
    public String payRollView(@RequestParam String payRollCode,@RequestParam String type, Model model) {
        model.addAttribute("payRollCode",payRollCode);
        model.addAttribute("workerType",dictService.selectByName("工种字典数据"));
        model.addAttribute("type",type);
        return PREFIX + "payRollDetail_info.html";
    }

    /**
     * 跳转到选择工人页面
     * @return
     */
    @RequestMapping("/payRoll_workerList")
    public String payRollWorkerList(@RequestParam String projectCode, @RequestParam String teamSysNo,Model model){
         model.addAttribute("projectCode",projectCode);
         model.addAttribute("teamSysNo",teamSysNo);
         return "/wages/payRollDetail/payRoll_workerList.html";
    }
    /**
     * 获取工资详情列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String , Object> map) {
        Page<PayRollDetailVo> page = new PageFactory<PayRollDetailVo>().defaultPage();
        List<PayRollDetailVo> payRollDetails = payRollDetailService.getDetailList(page, map);
        page.setRecords(payRollDetails);
        return super.packForBT(page);
    }

    /**
     * 新增工资详情
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PayRollDetail payRollDetail) {
        payRollDetailService.insert(payRollDetail);
        return SUCCESS_TIP;
    }

    /**
     * 删除工资详情
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer payRollDetailId) {
        payRollDetailService.deleteById(payRollDetailId);
        return SUCCESS_TIP;
    }

    /**
     * 修改工资详情
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PayRollDetail payRollDetail) {
        payRollDetailService.updateById(payRollDetail);
        return SUCCESS_TIP;
    }

    /**
     * 批量修改工资详情
     */
    @RequestMapping(value = "/updates")
    @ResponseBody
    public Object updates(PayRollVo payRollVo) {
        payRollDetailService.updates(payRollVo.getPayRollDetailList());
        return SUCCESS_TIP;
    }

    /**
     * 工资详情详情
     */
    @RequestMapping(value = "/detail/{payRollDetailId}")
    @ResponseBody
    public Object detail(@PathVariable("payRollDetailId") Integer payRollDetailId) {
        return payRollDetailService.selectById(payRollDetailId);
    }

    /**
     *
     * @param request
     * @param binder
     */
    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    /**
     * 根据用户idcard number 项目 班组 获取用户考勤及其基本信息
     */
    @RequestMapping(value = "/getSalaryDetailByWorkerInfo")
    @ResponseBody
    public Object getSalaryDetailByWorkerInfo(ParamsToFindSalaryDetail paramsToFindSalaryDetail) {
        return payRollDetailService.getSalaryDetailByWorkerInfo(paramsToFindSalaryDetail);
    }

    /**
     * 根据用户idcard number 项目 班组 获取用户考勤及其基本信息
     */
    @RequestMapping(value = "/getSalaryDetailByWorkerInfoPages")
    @ResponseBody
    public Object getSalaryDetailByWorkerInfoPages(ParamsToFindSalaryDetail paramsToFindSalaryDetail) {
        Page<PayRollDetailVo> page = new PageFactory<PayRollDetailVo>().defaultPage();
        if(paramsToFindSalaryDetail.getTeamWorkers()==null){
            return super.packForBT(page);
        }
        List<PayRollDetailVo> payRollDetails = payRollDetailService.selectList(page, paramsToFindSalaryDetail);
        page.setRecords(payRollDetails);
        return super.packForBT(page);
    }

    /**
     * 根据用户idcard number 项目 班组 获取用户考勤及其基本信息
     */
    @RequestMapping(value = "/getSalaryDetailByWorkerInfoNoPages")
    @ResponseBody
    public Object getSalaryDetailByWorkerInfoNoPages(ParamsToFindSalaryDetail paramsToFindSalaryDetail) {
        if(paramsToFindSalaryDetail.getTeamWorkers()==null){
            return new ArrayList<Object>();
        }
        List<PayRollDetailVo> payRollDetails = payRollDetailService.getSalaryDetailByWorkerInfoNoPages(paramsToFindSalaryDetail);
        return payRollDetails;
    }

    /**
     * 保存计工单及其详情
     */
    @RequestMapping(value = "/addPayRollAndDetail")
    @ResponseBody
    public Object addPayRollAndDetail(PayRollVo payRollVo) {
        return payRollDetailService.addPayRollAndDetail(payRollVo);
    }

    /**
     * 跳转到审核页面
     */
    @RequestMapping("/toToggleStatus")
    public String toToggleStatus(@RequestParam String ids, @RequestParam Integer status, Model model) {
        model.addAttribute("ids", ids);
        model.addAttribute("status", status);
        return PREFIX + "review.html";
    }

    /**
     * 审核
     */
    @RequestMapping("/toggleStatus")
    @ResponseBody
    public Object toggleStatus(@RequestParam String ids, @RequestParam Integer status, Model model) {
        return payRollDetailService.toggleStatus(ids,status);
    }


    /**
     * 导出
     *
     * @param res
     * @param req
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ResponseBody
    public void export(HttpServletResponse res, HttpServletRequest req, @RequestParam Map<String, Object> params) {
        payRollDetailService.export(res,req,params);
    }

    /**
     * 根据工种工资单导入
     */
    @RequestMapping(method = RequestMethod.POST, path = "/excelUploadByWorkerType")
    @ResponseBody
    public Object excelUploadByWorkerType(@RequestPart("file") MultipartFile excelFile, HttpServletRequest request) throws Exception{
        return payRollDetailService.importByWorkerType(excelFile,request);
    }


    /**
     * 根据计工单导入
     */
    @RequestMapping(method = RequestMethod.POST, path = "/excelUploadByOrder")
    @ResponseBody
    public Object excelUploadByOrder(@RequestPart("file") MultipartFile excelFile, HttpServletRequest request) throws Exception {
        return payRollDetailService.importByOrder(excelFile,request);
    }

}
