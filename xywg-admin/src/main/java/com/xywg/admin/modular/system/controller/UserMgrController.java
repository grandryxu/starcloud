package com.xywg.admin.modular.system.controller;

import com.xywg.admin.config.properties.XywgProperties;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.base.tips.Tip;
import com.xywg.admin.core.common.annotion.BussinessLog;
import com.xywg.admin.core.common.annotion.Permission;
import com.xywg.admin.core.common.constant.Const;
import com.xywg.admin.core.common.constant.dictmap.UserDict;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;
import com.xywg.admin.core.common.constant.state.ManagerStatus;
import com.xywg.admin.core.common.exception.BizExceptionEnum;
import com.xywg.admin.core.datascope.DataScope;
import com.xywg.admin.core.db.Db;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.util.MD5Util;
import com.xywg.admin.core.util.ToolUtil;
import com.xywg.admin.modular.system.dao.UserMapper;
import com.xywg.admin.modular.system.factory.UserFactory;
import com.xywg.admin.modular.system.model.User;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.modular.system.service.IUserService;
import com.xywg.admin.modular.system.service.UploadService;
import com.xywg.admin.modular.system.transfer.UserDto;
import com.xywg.admin.modular.system.warpper.UserNewWarpper;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.NoPermissionException;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统管理员控制器
 *
 * @author wangcw
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("/mgr")
public class UserMgrController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(UserMgrController.class);
    private static String PREFIX = "/system/user/";

    @Autowired
    private XywgProperties gunsProperties;

    @Autowired
    private IUserService userService;

    @Autowired
    private IDictService dictService;
    @Autowired
    private UploadService uploadService;

    /**
     * 跳转到查看管理员列表的页面
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "user.html";
    }

    /**
     * 跳转到查看管理员列表的页面
     */
    @RequestMapping("/user_add")
    public String addView(Model model) {
        model.addAttribute("dictSex", dictService.selectByName("性别"));
        model.addAttribute("dictValidStatus", dictService.selectByName("时间类型"));

        return PREFIX + "user_add.html";
    }



    /**
     * 跳转到角色分配页面
     */
    //@RequiresPermissions("/mgr/role_assign")  //利用shiro自带的权限检查
    @Permission
    @RequestMapping("/role_assign/{userId}")
    public String roleAssign(@PathVariable Integer userId, Model model) {
        if (ToolUtil.isEmpty(userId)) {
            throw new XywgException(BizExceptionEnum.REQUEST_NULL);
        }
        User user = (User) Db.create(UserMapper.class).selectOneByCon("id", userId);
        model.addAttribute("userId", userId);
        model.addAttribute("userAccount", user.getAccount());
        return PREFIX + "user_roleassign.html";
    }

    /**
     * 跳转到项目分配页面
     */
    @RequestMapping("/project_assign/{userId}")
    public String projectAssign(@PathVariable Integer userId, Model model) {
        if (ToolUtil.isEmpty(userId)) {
            throw new XywgException(BizExceptionEnum.REQUEST_NULL);
        }
        User user = (User) Db.create(UserMapper.class).selectOneByCon("id", userId);
        model.addAttribute("user", user);
        return PREFIX + "userProjectAssign.html";
    }


    /**
     * 跳转到编辑管理员页面
     */
    @Permission
    @RequestMapping("/user_edit/{userId}")
    public String userEdit(@PathVariable Integer userId, Model model) {
        if (ToolUtil.isEmpty(userId)) {
            throw new XywgException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        User user = this.userService.selectById(userId);
        model.addAttribute(user);
        model.addAttribute("roleName", ConstantFactory.me().getRoleName(user.getRoleid()));
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(user.getDeptid()));
        model.addAttribute("dictValidStatus", dictService.selectByName("时间类型"));
        LogObjectHolder.me().set(user);
        return PREFIX + "user_edit.html";
    }

    /**
     * 跳转到查看用户详情页面
     */
    @RequestMapping("/user_info")
    public String userInfo(Model model) {
        Integer userId = ShiroKit.getUser().getId();
        if (ToolUtil.isEmpty(userId)) {
            throw new XywgException(BizExceptionEnum.REQUEST_NULL);
        }
        User user = this.userService.selectById(userId);
        model.addAttribute(user);
        model.addAttribute("roleName", ConstantFactory.me().getRoleName(user.getRoleid()));
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(user.getDeptid()));
        LogObjectHolder.me().set(user);
        return PREFIX + "user_view.html";
    }

    /**
     * 跳转到修改密码界面
     */
    @RequestMapping("/user_chpwd")
    public String chPwd() {
        return PREFIX + "user_chpwd.html";
    }

    /**
     * 修改当前用户的密码
     */
    @RequestMapping("/changePwd")
    @ResponseBody
    public Object changePwd(@RequestParam String oldPwd, @RequestParam String newPwd, @RequestParam String rePwd) {
        if (!newPwd.equals(rePwd)) {
            throw new XywgException(BizExceptionEnum.TWO_PWD_NOT_MATCH);
        }
        Integer userId = ShiroKit.getUser().getId();
        User user = userService.selectById(userId);
        String oldMd5 = ShiroKit.md5(MD5Util.encrypt(oldPwd), user.getSalt());
        if (user.getPassword().equals(oldMd5)) {
            newPwd = MD5Util.encrypt(newPwd);
            String newMd5 = ShiroKit.md5(newPwd, user.getSalt());
            user.setPassword(newMd5);
            user.updateById();
            return SUCCESS_TIP;
        } else {
            throw new XywgException(BizExceptionEnum.OLD_PWD_NOT_RIGHT);
        }
    }
    /**
     * 跳转到项目分配页面
     */

    @RequestMapping("/getUserList")
    @ResponseBody
    public Object getUserList(@RequestParam(required = false) String name, @RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) Integer deptid) {
        List<Map<String, Object>> users = userService.selectUsers(null, name, beginTime, endTime, deptid);
        return new UserNewWarpper(users).warp();
    }

    /**
     * 查询管理员列表
     */
    @RequestMapping("/list")
    //@Permission
    @ResponseBody
    public Object list(@RequestParam(required = false) String name, @RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) Integer deptid) {
    	if (ShiroKit.isAdmin()) {
            List<Map<String, Object>> users = userService.selectUsers(null, name, beginTime, endTime, deptid);
            return new UserNewWarpper(users).warp();
        } else {
            DataScope dataScope = new DataScope(ShiroKit.getDeptDataScope());
            List<Map<String, Object>> users = userService.selectUsers(dataScope, name, beginTime, endTime, deptid);
            return new UserNewWarpper(users).warp();
        }
    }

    /**
     * 添加管理员
     */
    @RequestMapping("/add")
    @BussinessLog(value = "添加系统用户", key = "account", dict = UserDict.class)
    //@Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip add(@Valid UserDto user, BindingResult result) {
        if (result.hasErrors()) {
            throw new XywgException(BizExceptionEnum.REQUEST_NULL);
        }

        // 判断账号是否重复
        User theUser = userService.getByAccountAndId(user.getAccount(), "");
        if (theUser != null) {
            throw new XywgException(BizExceptionEnum.USER_ALREADY_REG);
        }
        // 完善账号信息
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(MD5Util.encrypt(user.getPassword()));
        user.setPassword(ShiroKit.md5(user.getPassword(), user.getSalt()));
        user.setStatus(ManagerStatus.OK.getCode());
        user.setUserType(1);
        user.setCreatetime(new Date());
        if(user.getStartTime() == null){
            user.setStartTime(new Date());
        }
        if(user.getEndTime() == null){
            try {
                user.setEndTime(new SimpleDateFormat("yyyy-mm-dd").parse("9999-12-30"));
            } catch (ParseException e) {
            	log.error(e.getMessage());
            }
        }
        userService.saveUserInfo(user);
        return SUCCESS_TIP;
    }

    /**
     * 修改管理员
     *
     * @throws NoPermissionException
     */
    @RequestMapping("/edit")
    @BussinessLog(value = "修改管理员", key = "account", dict = UserDict.class)
    @ResponseBody
    public Tip edit(@Valid UserDto user, BindingResult result) throws NoPermissionException {
        // 判断账号是否重复
        User oldUser = userService.getByAccountAndId("", user.getId() + "");
        User theUser = null;
        if(!oldUser.getAccount().equals(user.getAccount()))//未修改账号
            theUser = userService.getByAccountAndId(user.getAccount(), "");
        if (theUser != null) {
            throw new XywgException(BizExceptionEnum.USER_ALREADY_REG);
        }
        //无时间处理
        if(user.getStartTime() == null){
            user.setStartTime(new Date());
        }
        if(user.getEndTime() == null){
            try {
                user.setEndTime(new SimpleDateFormat("yyyy-mm-dd").parse("9999-12-30"));
            } catch (ParseException e) {
            	log.error(e.getMessage());
            }
        }
        if (result.hasErrors()) {
            throw new XywgException(BizExceptionEnum.REQUEST_NULL);
        }
        if (ShiroKit.hasRole(Const.ADMIN_NAME)) {
            this.userService.updateById(UserFactory.createUser(user));
            return SUCCESS_TIP;
        } else {
            assertAuth(user.getId());
            //ShiroUser shiroUser = ShiroKit.getUser();
            //if (shiroUser.getId().equals(user.getId())) {
            this.userService.updateById(UserFactory.createUser(user));
            return SUCCESS_TIP;
            /*} else {
                throw new XywgException(BizExceptionEnum.NO_PERMITION);
            }*/
        }
    }

    /**
     * 删除管理员（逻辑删除）
     */
    @RequestMapping("/delete")
    @BussinessLog(value = "删除管理员", key = "userId", dict = UserDict.class)
    //@Permission
    @ResponseBody
    public Tip delete(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new XywgException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能删除超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new XywgException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }
        assertAuth(userId);
        this.userService.setStatus(userId, ManagerStatus.DELETED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 查看管理员详情
     */
    @RequestMapping("/view/{userId}")
    @ResponseBody
    public User view(@PathVariable Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new XywgException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        return this.userService.selectById(userId);
    }

    /**
     * 重置管理员的密码
     */
    @RequestMapping("/reset")
    @BussinessLog(value = "重置管理员密码", key = "userId", dict = UserDict.class)
    //@Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip reset(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new XywgException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        User user = this.userService.selectById(userId);
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(ShiroKit.md5(Const.DEFAULT_PWD, user.getSalt()));
        this.userService.updateById(user);
        return SUCCESS_TIP;
    }

    /**
     * 冻结用户
     */
    @RequestMapping("/freeze")
    @BussinessLog(value = "冻结用户", key = "userId", dict = UserDict.class)
    //@Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip freeze(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new XywgException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能冻结超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new XywgException(BizExceptionEnum.CANT_FREEZE_ADMIN);
        }
        assertAuth(userId);
        this.userService.setStatus(userId, ManagerStatus.FREEZED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 解除冻结用户
     */
    @RequestMapping("/unfreeze")
    @BussinessLog(value = "解除冻结用户", key = "userId", dict = UserDict.class)
    //@Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip unfreeze(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new XywgException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        this.userService.setStatus(userId, ManagerStatus.OK.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 分配角色
     */
    @RequestMapping("/setRole")
    @BussinessLog(value = "分配角色", key = "userId,roleIds", dict = UserDict.class)
    //@Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip setRole(@RequestParam("userId") Integer userId, @RequestParam("roleIds") String roleIds) {
        if (ToolUtil.isOneEmpty(userId, roleIds)) {
            throw new XywgException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能修改超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new XywgException(BizExceptionEnum.CANT_CHANGE_ADMIN);
        }
        assertAuth(userId);
        this.userService.setRoles(userId, roleIds);
        return SUCCESS_TIP;
    }

    /**
     * (上传图片(上传到项目的webapp/static/img))
     *
     * 重新定义上传路径,保存到FTP 远程静态服务器上
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public String upload(@RequestPart("file") MultipartFile picture) {
        try {
            /*String fileSavePath = gunsProperties.getFileUploadPath();
            picture.transferTo(new File(fileSavePath + pictureName));*/

            //重新定义上传路径,保存到FTP 远程静态服务器上
            return uploadService.saveFileToFTP(picture);
        } catch (Exception e) {
            throw new XywgException(BizExceptionEnum.UPLOAD_ERROR);
        }
    }

    /**
     * (上传PDF)
     * @auth wangcw
     * 重新定义上传路径,保存到FTP 远程静态服务器上
     */
    @RequestMapping(method = RequestMethod.POST, path = "/uploadFile")
    @ResponseBody
    public Object uploadFile(@RequestPart("file") MultipartFile picture) {
        try {
            //重新定义上传路径,保存到FTP 远程静态服务器上
            String path = uploadService.saveFileToFTP(picture);
            Map map = new HashedMap();
            map.put("path",path);
            return map;
        } catch (Exception e) {
            throw new XywgException(BizExceptionEnum.UPLOAD_ERROR);
        }
    }


    /**
     * 判断当前登录的用户是否有操作这个用户的权限
     */
    private void assertAuth(Integer userId) {
        if (ShiroKit.isAdmin()) {
            return;
        }
        List<Integer> deptDataScope = ShiroKit.getDeptDataScope();
        User user = this.userService.selectById(userId);
        Integer deptid = user.getDeptid();
        if (deptDataScope.contains(deptid)) {
            return;
        } else {
            throw new XywgException(BizExceptionEnum.NO_PERMITION);
        }

    }



}
