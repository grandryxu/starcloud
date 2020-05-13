package com.xywg.admin.modular.system.controller;

import com.google.code.kaptcha.Constants;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.exception.InvalidKaptchaException;
import com.xywg.admin.core.log.LogManager;
import com.xywg.admin.core.log.factory.LogTaskFactory;
import com.xywg.admin.core.node.MenuNode;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.shiro.ShiroUser;
import com.xywg.admin.core.support.HttpKit;
import com.xywg.admin.core.util.ApiMenuFilter;
import com.xywg.admin.core.util.KaptchaUtil;
import com.xywg.admin.core.util.MD5Util;
import com.xywg.admin.core.util.ToolUtil;
import com.xywg.admin.modular.system.model.SwitchType;
import com.xywg.admin.modular.system.model.User;
import com.xywg.admin.modular.system.service.*;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录控制器
 *
 * @author wangcw
 * @Date 2017年1月10日 下午8:25:24
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private IDeptService deptService;

    private static final Map<String, String> menuMap = new HashMap<String, String>();

    static {
        menuMap.put("MENUS", "流程管理,工人管理,工人管理列表,工人轨迹,企业管理,参建单位,班组管理,从业人员管理,企业资质管理,项目管理,总包项目,参建项目,培训记录," +
                "工伤管理,工资管理,计工单,工资单,结算单,工资流水,奖惩记录,工人不良记录,班组不良记录,企业不良记录,工人奖励记录," +
                "班组奖励记录,企业不良记录审核,工人不良记录审核,班组不良记录审核,设备管理,考勤机,二维码管理,安全帽管理,黑名单," +
                "工人黑名单,企业黑名单,系统管理,用户管理,角色管理,公司管理,菜单管理,字典管理,业务日志,登陆日志,监控管理,通知," +
                "通知管理,轮播图管理,工种管理,统计分析,人员统计,项目区域统计,工种分布,籍贯统计,人员进退场统计,工资统计," +

                "参建单位首页,考勤统计,代码生成,考勤管理,考勤记录列表,考勤异常记录,考勤时间设置,申诉管理,版本管理,LED屏管理,同步查询," +

                "功能演示,工资表发放,项目设置,工人附件,统计管理,考勤统计,招标相关,招标项目,招标管理,分包企业,企业名录,招标邀请,定标管理," +
                "定标信息,待办任务,我的任务,招标文件审批,定标审批,流程列表,评价规则,企业注册审批,招标投标,招标统计,月度统计,招标信息,今日考勤-班组,班组统计,项目数据指纹");



        menuMap.put("TOGGLEMENUS", "项目管理,项目详情,项目参建单位,项目工人管理,考勤记录,培训记录,班组管理,工伤管理,工资管理,计工单," +
                "工资单,结算单,奖惩记录,工人不良记录,班组不良记录,工人奖励记录,班组奖励记录,工人不良记录审核,班组不良记录审核,黑名单,工人黑名单," +
                "设备管理,二维码管理,安全帽管理,考勤机,考勤管理,考勤时间设置,考勤异常记录,企业不良记录,企业不良记录审核,企业黑名单,统计管理,统计分析,"
                + "今日考勤-班组,今日考勤-工种,考勤统计,超龄统计,待办任务,我的任务,评价规则,企业注册审批,班组统计,项目数据指纹");
    }



    @Autowired
    private IDictService dictService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IUserService userService;

    @Autowired
    private AccountProjectService accountProjectService;

    private final long SEVEN_DAY_LONG_TIME = 7*24*60*60*1000L;

    /**
     * 跳转到主页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        //获取菜单列表
        List<Integer> roleList = ShiroKit.getUser().getRoleList();
        if (roleList == null || roleList.size() == 0) {
            ShiroKit.getSubject().logout();
            model.addAttribute("tips", "该用户没有角色，无法登陆");
            return "/login.html";
        }
        String projectCode = ShiroKit.getSessionAttr("toggleProjectCode");
        
        List<MenuNode> menus = new ArrayList<>();
        //判断是否是切换时
        String isSwitch = ShiroKit.getSessionAttr("isSwitch");
        if(isSwitch == null || !isSwitch.equals("1")) {
	        if(ShiroKit.getUser().getIsEnterprise()==1) {
	            menus = menuService.getMenusByRoleIds(roleList, projectCode, menuMap);
	        }else {
	        	//查询默认项目
	        	List<Map<String,String>> project = accountProjectService.getDefaultProject(ShiroKit.getUser().getAccount());
	        	if(project != null && project.size()>0){
	        		//显示默认项目
	        		projectCode = project.get(0).get("project_code");
	        		ShiroKit.setSessionAttr("toggleProjectCode", projectCode);
	            	menus = menuService.getMenusByRoleIds(roleList, projectCode, menuMap);
	            	ShiroKit.setSessionAttr("menuName","项目:"+project.get(0).get("project_name"));
	        	}else {
	        		//显示企业
	        		menus = menuService.getMenusByRoleIds(roleList, projectCode, menuMap);
	        	}
	        	
	        }
	    }else {
	    	menus = menuService.getMenusByRoleIds(roleList, projectCode, menuMap);
	    }
        List<MenuNode> titles = MenuNode.buildTitle(menus);
        titles = ApiMenuFilter.build(titles);

        model.addAttribute("titles", titles);

        //获取用户头像
        Integer id = ShiroKit.getUser().getId();
        model.addAttribute("deptId", ShiroKit.getUser().getDeptId());
        User user = userService.selectById(id);
        String avatar = user.getAvatar();
        model.addAttribute("avatar", avatar);
        // model.addAttribute("imagePath", "http://192.168.1.124:8080/labor/");

        //项目切换 切换 菜单导航
        String menuName = "";
        if (ShiroKit.getSessionAttr("menuName") == null) {
            String loginDeptName = deptService.selectById(ShiroKit.getUser().getDeptId()).getSimplename();
            menuName = "公司:" + loginDeptName;
        } else {
            menuName = ShiroKit.getSessionAttr("menuName");
        }
        model.addAttribute("menuName", menuName);
        SwitchType switchType = accountProjectService.getSwitchType();
        //项目级操作权限 为总包项目 true 能操作 false 不能操作
        model.addAttribute("isGeneralContractor", switchType.getSwitchType() == 0 && switchType.getIsGeneralContractor() == 1);

        //过期提示
        if ((System.currentTimeMillis()+SEVEN_DAY_LONG_TIME) > userService.selectById(ShiroKit.getUser().getId()).getEndTime().getTime()) {
            model.addAttribute("dateTip", "该账号将于7天内过期，请及时关注");
        }
        return "/index.html";
    }

    /**
     * 跳转到登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        if (ShiroKit.isAuthenticated()) {
            return REDIRECT + "/";
        } else {
            return "/login.html";
        }
    }
    /**
     * 跳转到门户页面
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model) {

            return "/home.html";
    }
    /**
     * 跳转到注册页面
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {

        model.addAttribute("subOrganizationType",dictService.selectByName("单位性质"));
        model.addAttribute("subBusinessStatus",dictService.selectByName("经营状态"));
        model.addAttribute("subRepresentativeIdcardType",dictService.selectByName("人员证件类型"));
        model.addAttribute("subCapitalCurrency",dictService.selectByName("币种"));
        model.addAttribute("subEconomicNature",dictService.selectByName("企业经济性质"));
        model.addAttribute("subEnterpriseMarketType",dictService.selectByName("企业市域类别"));

            return "/register.html";
    }
    /**
     * 点击登录执行的动作
     */
    @RequestMapping(value = "/loginReg", method = RequestMethod.POST)
    @ResponseBody
    public boolean loginValiReg(Model model) {

        String username = super.getPara("username").trim();
        String password = super.getPara("password").trim();
        password = MD5Util.encrypt(password);
        //记住密码
        String remember = super.getPara("remember");

        //验证验证码是否正确
        if (KaptchaUtil.getKaptchaOnOff()) {
            String kaptcha = super.getPara("kaptcha").trim();
            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
                throw new InvalidKaptchaException();
            }
        }

        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());

        if ("on".equals(remember)) {
            token.setRememberMe(true);
        } else {
            token.setRememberMe(false);
        }

        currentUser.login(token);

        //账号过期判断
        User loginUser = userService.selectById(ShiroKit.getUser().getId());
        if (loginUser.getEndTime().getTime() < System.currentTimeMillis()) {
            model.addAttribute("tips", "该账号已过期");
            return false;
        }

        ShiroUser shiroUser = ShiroKit.getUser();
        super.getSession().setAttribute("shiroUser", shiroUser);
        super.getSession().setAttribute("username", shiroUser.getAccount());

        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), HttpKit.getIp()));

        ShiroKit.getSession().setAttribute("sessionFlag", true);

        //初始化session
        accountProjectService.initSession();
        return true;
    }
    /**
     * 点击登录执行的动作
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginVali(Model model) {

        String username = super.getPara("username").trim();
        String password = super.getPara("password").trim();
        password = MD5Util.encrypt(password);
        //记住密码
        String remember = super.getPara("remember");

        //验证验证码是否正确
        if (KaptchaUtil.getKaptchaOnOff()) {
            String kaptcha = super.getPara("kaptcha").trim();
            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
                throw new InvalidKaptchaException();
            }
        }

        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());

        if ("on".equals(remember)) {
            token.setRememberMe(true);
        } else {
            token.setRememberMe(false);
        }

        currentUser.login(token);

        //账号过期判断
        User loginUser = userService.selectById(ShiroKit.getUser().getId());
        if (loginUser.getEndTime().getTime() < System.currentTimeMillis()) {
            model.addAttribute("tips", "该账号已过期");
            return "/login.html";
        }

        ShiroUser shiroUser = ShiroKit.getUser();
        super.getSession().setAttribute("shiroUser", shiroUser);
        super.getSession().setAttribute("username", shiroUser.getAccount());

        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), HttpKit.getIp()));

        ShiroKit.getSession().setAttribute("sessionFlag", true);

        //初始化session
        accountProjectService.initSession();
        return REDIRECT + "/";
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut() {
        LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroKit.getUser().getId(), HttpKit.getIp()));
        ShiroKit.getSubject().logout();
        return REDIRECT + "/login";
    }

    /**
     * 切换公司
     *
     * @auth wangcw
     */
    @RequestMapping(value = "/selectCompany", method = RequestMethod.GET)
    public String selectCompany() {
        return "/selectCompany.html";
    }
}
