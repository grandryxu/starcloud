package com.xywg.admin.modular.system.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.base.tips.ErrorTip;
import com.xywg.admin.core.common.annotion.BussinessLog;
import com.xywg.admin.core.common.annotion.Permission;
import com.xywg.admin.core.common.constant.dictmap.DeptDict;
import com.xywg.admin.core.common.constant.dictmap.UserDict;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;
import com.xywg.admin.core.common.exception.BizExceptionEnum;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.core.node.ZTreeNode;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.shiro.ShiroUser;
import com.xywg.admin.core.util.List2Tree;
import com.xywg.admin.core.util.ToolUtil;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.company.service.ISubContractorService;
import com.xywg.admin.modular.system.model.Dept;
import com.xywg.admin.modular.system.model.OrganizationalStructure;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.modular.system.service.IUserService;
import com.xywg.admin.modular.system.warpper.DeptWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门控制器
 *
 * @author wangcw
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController {

    private String PREFIX = "/system/dept/";

    @Autowired
    private IDeptService deptService;

    @Autowired
    private IUserService userService;

    /**
     * 跳转到部门管理首页
     */
    @RequestMapping("")
    public String index(Model model) {
        ShiroUser user=ShiroKit.getUser();
        if(null==user){
            model.addAttribute("deptId", 0);
        }else {
	        Dept dept = deptService.selectById(user.getDeptId());
	        model.addAttribute("deptId", dept.getPid());
        }

        return PREFIX + "dept.html";
    }

    /**
     * 跳转到添加公司
     */
    @RequestMapping("/dept_add")
    public String deptAdd() {

        return PREFIX + "dept_add.html";
    }

    /**
     * 跳转到添加部门
     * @return
     */
    @RequestMapping("/dept_add_data")
    public String deptAdd1() {

        return PREFIX + "dept_add1.html";
    }



    /**
     * 弹出搜索企业弹框
     * @auth wangcw
     */
    @RequestMapping("/company_add_search")
    public String subContractorAddSearch() {
        return PREFIX + "company_add_search.html";
    }

    /**
     * 跳转到修改部门
     */
    @Permission
    @RequestMapping("/dept_update/{deptId}")
    public String deptUpdate(@PathVariable Integer deptId, Model model) {
        Dept dept = deptService.selectById(deptId);
        model.addAttribute(dept);
        model.addAttribute("pName", ConstantFactory.me().getDeptName(dept.getPid()));
        LogObjectHolder.me().set(dept);
        return PREFIX + "dept_edit.html";
    }

    /**
     * 跳转到设置时间
     */
    @RequestMapping("/dept_setTime/{deptId}")
    public String setTime(@PathVariable Integer deptId, Model model) {
        Dept dept = deptService.selectById(deptId);
        model.addAttribute(dept);
        model.addAttribute("pName", ConstantFactory.me().getDeptName(dept.getPid()));
        LogObjectHolder.me().set(dept);
        return PREFIX + "setTime.html";
    }

    /**
     * 根据部门id查询对应的公司id
     */
    @RequestMapping("/selectSubContractorIdByDeptId/{deptId}")
    @ResponseBody
    public Object selectSubContractorIdByDeptId(@PathVariable Integer deptId, Model model) {
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("subContractorId",this.deptService.selectSubContractorIdByDeptId(deptId));
        return result;
    }
    /**
     * 获取登陆用户部门的tree列表
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
    public Object tree() {
        List<ZTreeNode> tree = this.deptService.tree();
        //tree.add(ZTreeNode.createParent());
        JSONArray jsonArray = new JSONArray();
        for (ZTreeNode node:tree){
            jsonArray.add(JSONUtil.parseObj(node));
        }
        return List2Tree.listToTree(jsonArray,"id","pId","nodes");
    }

    /**
     * 获取部门的tree 根据 deptId
     */
    @RequestMapping(value = "/treeByDeptId")
    @ResponseBody
    public Object treeByDeptId(@RequestParam Integer deptId) {
        List<ZTreeNode> tree = this.deptService.treeByDeptId(deptId);
        //tree.add(ZTreeNode.createParent());
        JSONArray jsonArray = new JSONArray();
        for (ZTreeNode node:tree){
            jsonArray.add(JSONUtil.parseObj(node));
        }
        return List2Tree.listToTree(jsonArray,"id","pId","nodes");
    }


    /**
     * 获取部门的tree列表
     */
    @RequestMapping(value = "/treeSelect")
    @ResponseBody
    public Object treeSelect() {
        List<ZTreeNode> tree = this.deptService.tree();
        tree.add(ZTreeNode.createParent());
        JSONArray jsonArray = new JSONArray();
        for (ZTreeNode node:tree){
            jsonArray.add(JSONUtil.parseObj(node));
        }
        return this.deptService.tree();
    }

    /**
     * 新增部门
     */
    @BussinessLog(value = "添加部门", key = "simplename", dict = DeptDict.class)
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(Dept dept) {
        if (ToolUtil.isOneEmpty(dept, dept.getSimplename())) {
            throw new XywgException(BizExceptionEnum.REQUEST_NULL);
        }
        //判断该公司是否存在
        Dept hasDept = deptService.hasDeptWithSocialCreditNumber(dept.getSocialCreditNumber());
        if(hasDept != null){
            //该公司已存在
            Integer num = this.deptService.addPid(hasDept.getId(),dept.getPid());
            //将其所有的子公司绑定父公司
            this.deptService.updateAllGrantChildrenByChildId(hasDept.getId(),dept.getPid());
            return num;
        }else{
            //该公司不存在 新增一条记录
            deptSetPids(dept);
            return this.deptService.insert(dept);
        }
        //完善pids,根据pid拿到pid的pids
    }

    /**
     * 获取所有部门列表
     */
    @RequestMapping(value = "/list")
    @Permission
    @ResponseBody
    public Object list(String condition) {
        ShiroUser user=ShiroKit.getUser();
        List<Map<String, Object>> list = new ArrayList<>();
        if(user!=null){
            list = this.deptService.list(condition, user.getAccount());
        }
        return super.warpObject(new DeptWarpper(list));
    }

    /**
     * 部门详情
     */
    @RequestMapping(value = "/detail/{deptId}")
    @Permission
    @ResponseBody
    public Object detail(@PathVariable("deptId") Integer deptId) {
        return deptService.selectById(deptId);
    }

    /**
     * 修改部门
     */
    @BussinessLog(value = "修改部门", key = "simplename", dict = DeptDict.class)
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(Dept dept) {
        if (ToolUtil.isEmpty(dept) || dept.getId() == null) {
            throw new XywgException(BizExceptionEnum.REQUEST_NULL);
        }
        deptSetPids(dept);
        deptService.updateById(dept);
        return SUCCESS_TIP;
    }

    /**
     * 设置有效期
     */
    @BussinessLog(value = "设置有效期", key = "simplename", dict = DeptDict.class)
    @RequestMapping(value = "/setTime")
    @Permission
    @ResponseBody
    public Object setTime(Dept dept) {
        if (ToolUtil.isEmpty(dept) || dept.getId() == null) {
            throw new XywgException(BizExceptionEnum.REQUEST_NULL);
        }
        deptSetPids(dept);
        deptService.setTime(dept);
        return SUCCESS_TIP;
    }

    /**
     * 删除部门
     * @auth wancw
     */
    @BussinessLog(value = "删除部门", key = "deptId", dict = DeptDict.class)
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Integer deptId) {
        List<OrganizationalStructure> deptChilds = deptService.getDeptChilds(Long.parseLong(deptId+""));
        if(deptChilds!=null&&deptChilds.size()>0){
            return new ErrorTip(600,"请先删除下面的子公司");
        }

        //缓存被删除的部门名称
        LogObjectHolder.me().set(ConstantFactory.me().getDeptName(deptId));

        deptService.deleteDept(deptId);

        return SUCCESS_TIP;
    }

    private void deptSetPids(Dept dept) {
        if (ToolUtil.isEmpty(dept.getPid()) || dept.getPid().equals(0)) {
            dept.setPid(0);
            dept.setPids("[0],");
        } else {
            int pid = dept.getPid();
            Dept temp = deptService.selectById(pid);
            String pids = temp.getPids();
            dept.setPid(pid);
            dept.setPids(pids + "[" + pid + "],");
        }
    }


    /**
     * 跳转到修改部门
     */
    @RequestMapping("/selectCompany")
    public String selectCompany(Model model) {
        model.addAttribute("isEnterprise",userService.getByAccount(ShiroKit.getUser().getAccount()).getIsEnterprise());
        return PREFIX + "selectCompany.html";
    }
}
