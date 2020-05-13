package com.xywg.admin.modular.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.common.constant.Const;
import com.xywg.admin.core.common.constant.state.ManagerStatus;
import com.xywg.admin.core.common.exception.BizExceptionEnum;
import com.xywg.admin.core.datascope.DataScope;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.log.LogManager;
import com.xywg.admin.core.log.factory.LogTaskFactory;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.shiro.ShiroUser;
import com.xywg.admin.core.support.HttpKit;
import com.xywg.admin.modular.project.dao.CooperationProjectMasterMapper;
import com.xywg.admin.modular.system.dao.AccountProjectMapper;
import com.xywg.admin.modular.system.dao.UserMapper;
import com.xywg.admin.modular.system.factory.UserFactory;
import com.xywg.admin.modular.system.model.AppPersonnelRegister;
import com.xywg.admin.modular.system.model.Dept;
import com.xywg.admin.modular.system.model.User;
import com.xywg.admin.modular.system.service.IUserService;
import com.xywg.admin.modular.system.service.SignService;
import com.xywg.admin.modular.system.transfer.UserDto;
import com.xywg.admin.modular.worker.dao.WorkerMasterMapper;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import com.xywg.admin.oauth.domain.oauth.AccessToken;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private IUserService userService;
    @Autowired
    private SignService signService;
    @Autowired
    private CooperationProjectMasterMapper cooperationProjectMasterMapper;
    @Autowired
    private AccountProjectMapper accountProjectMapper;
    @Autowired
    protected WorkerMasterMapper workerMasterMapper;


    @Override
    public int setStatus(Integer userId, int status) {
        return this.baseMapper.setStatus(userId, status);
    }

    @Override
    public int changePwd(Integer userId, String pwd) {
        return this.baseMapper.changePwd(userId, pwd);
    }

    @Override
    public List<Map<String, Object>> selectUsers(DataScope dataScope, String name, String beginTime, String endTime, Integer deptid) {
        return this.baseMapper.selectUsers(dataScope, name, beginTime, endTime, deptid);
    }

    @Override
    public int setRoles(Integer userId, String roleIds) {
        return this.baseMapper.setRoles(userId, roleIds);
    }

    @Override
    public User getByAccount(String account) {
        return this.baseMapper.getByAccount(account);
    }

    /**
     * 通过账号获取用户验重复
     */
    @Override
    public User getByAccountAndId(String account, String id){
        return this.baseMapper.getByAccountAndId(account, id);
    }

    @Override
    public User getByAccountAPP(String account, String userType) {
        return this.baseMapper.getByAccountAPP(account, userType);
    }

    @Override
    public User getUserInfoByIdCard(Integer idCardType, String idCardNumber) {
        return this.baseMapper.getUserInfoByIdCard(idCardType, idCardNumber);
    }


    /**
     * 移动端添加工人登录信息
     * account    登录账号
     * userType   账号类型(0:工人,1:班组长)
     * dataSource 数据来源类型(0:PC,1:移动端)
     */
    @Override
    public void addWorkerAccount(AppPersonnelRegister appPersonnelRegister) {
        String mobile = appPersonnelRegister.getMobile();
        appPersonnelRegister.setAccount(mobile);
        signService.verifySign(appPersonnelRegister);
        UserDto user = new UserDto();
        // 判断账号是否重复
        User theUser = userService.getByAccountAPP(mobile,"0");
        if (theUser != null) {
            throw new XywgException(BizExceptionEnum.USER_ALREADY_REG);
        }
        user.setAccount(mobile);
        // 完善账号信息
        user.setSalt(ShiroKit.getRandomSalt(5));

        if (appPersonnelRegister.getPassword() == null) {
            //设置初始密码
            user.setPassword(ShiroKit.md5(Const.DEFAULT_PWD, user.getSalt()));
        } else {
            //设置密码
            user.setPassword(ShiroKit.md5(appPersonnelRegister.getPassword(), user.getSalt()));
        }
        user.setUserType(0);
        user.setPhone(appPersonnelRegister.getMobile());
        user.setDataSource(1);
        user.setStatus(ManagerStatus.OK.getCode());
        user.setCreatetime(new Date());
        this.userService.insert(UserFactory.createUser(user));
    }


    /**
     * PC添加工人登录信息
     * account    登录账号
     * userType   账号类型(0:工人,1:班组长)
     * dataSource 数据来源类型(0:PC,1:移动端)
     */
    @Override
    public void addWorkerAccountPC(String mobile, Integer idCardType, String idCardNumber) {

        UserDto user = new UserDto();
        // 判断账号是否重复
        User theUser = userService.getByAccount(mobile);
        if (theUser != null) {
            throw new XywgException(BizExceptionEnum.USER_ALREADY_REG);
        }
        user.setAccount(mobile);
        // 完善账号信息
        user.setSalt(ShiroKit.getRandomSalt(5));
        //设置初始密码
        user.setPassword(ShiroKit.md5(Const.DEFAULT_PWD, user.getSalt()));
        user.setUserType(0);
        user.setPhone(mobile);
        user.setIdCardType(idCardType);
        user.setIdCardNumber(idCardNumber);
        user.setDataSource(0);
        user.setStatus(ManagerStatus.OK.getCode());
        user.setCreatetime(new Date());
        this.userService.insert(UserFactory.createUser(user));
    }

    @Override
    public Map<String, Object> getUserInfo(String account, Integer type) {
        return this.baseMapper.getUserInfo(account, type);
    }

    @Override
    public void updateUser(User user) {
        this.baseMapper.updateUser(user);
    }

    /**
     * 根据短信验证码修改密码
     */
    @Override
    public void updatePasswordBySign(AppPersonnelRegister appPersonnelRegister) {
        signService.verifySign(appPersonnelRegister);
        User user = new User();
        user.setAccount(appPersonnelRegister.getAccount());
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(ShiroKit.md5(appPersonnelRegister.getPassword(), user.getSalt()));
        userService.updateUser(user);
    }

    /**
     * 移动端登录
     */
    @Override
    public Map<String, Object> loginCheck(User user) {
        if (StringUtils.isBlank(user.getAccount())) {
            throw new XywgException(600, "用户名不能为空");
        }
        if (StringUtils.isBlank(user.getPassword())) {
            throw new XywgException(600, "密码不能为空");
        }
        if (user.getUserType() != 0 && user.getUserType() != 1) {
            throw new XywgException(600, "用户类型参数不符合要求");
        }
        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(), user.getPassword().toCharArray());
        currentUser.login(token);

        ShiroUser shiroUser = ShiroKit.getUser();
        ShiroKit.getSession().setAttribute("shiroUser", shiroUser);
        ShiroKit.getSession().setAttribute("username", shiroUser.getAccount());

        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), HttpKit.getIp()));

        ShiroKit.getSession().setAttribute("sessionFlag", true);
        Map<String, Object> map = userService.getUserInfo(user.getAccount(), user.getUserType());
        map.put("userType", user.getUserType());

        return map;
    }

    @Override
    public void deleteUserByPhone(String phone) {
        this.baseMapper.deleteUserByPhone(phone);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserInfo(UserDto user) {
        insert(UserFactory.createUser(user));
    }

    @Override
    public void updateAccount(String newPhone, String oldPhone) {
        this.baseMapper.updateAccount(newPhone, oldPhone);

    }


    /**
     * 根据证件类型修改用户信息(该方法修改用户的手机号后不修改工人表手机号码,如果需要请另起事务修改)
     *
     * @param user
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAccountInfoByIdCard(User user) {
        //先校验证件类型和证件编号是否有效<不允许是空串的证件编号和证件类型>
        if (user.getIdCardType() == 0 || user.getIdCardType() == null || StringUtils.isBlank(user.getIdCardNumber())) {
            throw new XywgException(600, "无效的证件信息!");
        }
        if (StringUtils.isBlank(user.getAccount())) {
            return;
        }
        //如果包含了修改登录账号,先判断该账号是否已经数据库中存在
        User oldUser = getByAccount(user.getAccount());
        if (oldUser != null) {
            //如果该账号已存在,或者没有证件信息,直接删除
            if (oldUser.getIdCardType() == null || StringUtils.isBlank(oldUser.getIdCardNumber())) {
                deleteUserByAccount(user.getAccount());
            } else {
                //不是自己时才删除
                if (!oldUser.getIdCardType().equals(user.getIdCardType()) || !oldUser.getIdCardNumber().equals(user.getIdCardNumber())) {
                    deleteUserByAccount(user.getAccount());
                }
            }
        }
        //更新用户信息
        this.baseMapper.updateAccountInfoByIdCard(user);
    }

    /**
     * 修改账号信息以及更新工人基本信息
     *
     * @param appPersonnel
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAccountInfoByIdCard(AppPersonnelRegister appPersonnel) {
        //先校验证件类型和证件编号是否有效<不允许是空串的证件编号和证件类型>
        if (appPersonnel.getIdCardType() == 0 || appPersonnel.getIdCardType() == null || StringUtils.isBlank(appPersonnel.getIdCardNumber())) {
            throw new XywgException(600, "无效的证件信息");
        }
        //判断短信有效性
        signService.verifySign(appPersonnel);

        User dbUser = getUserInfoByIdCard(appPersonnel.getIdCardType(), appPersonnel.getIdCardNumber());
        if (dbUser == null) {
            throw new XywgException(600, "系统中不存在该证件信息");
        }
        //判断密码有效性
        String md5Password = ShiroKit.md5(appPersonnel.getPassword(), dbUser.getSalt());
        if (!md5Password.equals(dbUser.getPassword())) {
            throw new XywgException(600, "密码错误");
        }
        //新的手机号作为新的账号
        User user = new User(appPersonnel.getMobile(), appPersonnel.getMobile(), appPersonnel.getIdCardType(), appPersonnel.getIdCardNumber());
        //更新用户表
        updateAccountInfoByIdCard(user);
        //更新工人表
        workerMasterMapper.updateByIdCardTypeAndIdCardNumber(
                new WorkerMaster(
                        appPersonnel.getMobile(), appPersonnel.getIdCardType(), appPersonnel.getIdCardNumber()
                )
        );
    }

    /**
     * 根据老账号删除更新账号信息(移动端更新手机号码)
     *
     * @param appPersonnel
     */
    @Override
    public void updateAccountInfoByOldAccount(AppPersonnelRegister appPersonnel) {
        if (StringUtils.isBlank(appPersonnel.getAccount())) {
            throw new XywgException(601, "原账号信息不能为空");
        }
        if (StringUtils.isBlank(appPersonnel.getMobile())) {
            throw new XywgException(601, "新账号信息不能为空");
        }
        //判断短信有效性
        signService.verifySign(appPersonnel);
        User dbUser = getByAccountAPP(appPersonnel.getAccount(),"0");
        if (dbUser == null) {
            throw new XywgException(603, "账号不存在");
        }
        //判断密码有效性
        String md5Password = ShiroKit.md5(appPersonnel.getPassword(), dbUser.getSalt());
        if (!md5Password.equals(dbUser.getPassword())) {
            throw new XywgException(603, "账号或密码错误");
        }
        //如果数据库中已经存在新的手机号码信息,那么直接删除
        //User oldUser = getByAccount(appPersonnel.getMobile());
        //if (oldUser != null) {
            deleteUserByAccount(appPersonnel.getMobile());
        //}
        //更新原账号
        this.baseMapper.updateAccount(appPersonnel.getMobile(),appPersonnel.getAccount());
        //将工人表中原手机号更新为新的手机号
        workerMasterMapper.updateWorkerPhone(appPersonnel.getAccount(),appPersonnel.getMobile());
    }


    @Override
    public AccessToken findAccessTokenByTokenId(String tokenId) {
        return this.baseMapper.findAccessTokenByTokenId(tokenId);
    }

    @Override
    public Integer userExpire() {
        return this.baseMapper.userExpire();
    }

    @Override
    public void setTimeByDept(Dept dept) {
        this.baseMapper.setTimeByDept(dept);
    }

    @Override
    public void updateRegStatus(User usr) {
        this.baseMapper.updateRegStatus(usr);
    }

    @Override
    public void deleteUserByAccount(String account) {
        this.baseMapper.deleteUserByAccount(account);
    }

    @Override
    public Map<String, Object> selectAccount(Object organizationCode) {
        return this.baseMapper.selectAccount(organizationCode);
    }

    @Override
    public Integer power(String account) {
       Integer roleId= baseMapper.power(account);
        //1招标商，2投标商
       if (roleId==139){
         return 2;
       }else {
           return 1;
       }
    }
}
