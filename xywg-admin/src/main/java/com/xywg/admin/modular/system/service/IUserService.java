package com.xywg.admin.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.core.datascope.DataScope;
import com.xywg.admin.modular.system.model.AppPersonnelRegister;
import com.xywg.admin.modular.system.model.Dept;
import com.xywg.admin.modular.system.model.User;
import com.xywg.admin.modular.system.transfer.UserDto;
import com.xywg.admin.oauth.domain.oauth.AccessToken;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员表 服务类
 * </p>
 *
 * @author wangcw
 * @since 2018-02-22
 */
public interface IUserService extends IService<User> {

    /**
     * 修改用户状态
     */
    int setStatus(@Param("userId") Integer userId, @Param("status") int status);

    /**
     * 修改密码
     */
    int changePwd(@Param("userId") Integer userId, @Param("pwd") String pwd);

    /**
     * 根据条件查询用户列表
     */
    List<Map<String, Object>> selectUsers(@Param("dataScope") DataScope dataScope, @Param("name") String name, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("deptid") Integer deptid);

    /**
     * 设置用户的角色
     */
    int setRoles(@Param("userId") Integer userId, @Param("roleIds") String roleIds);

    /**
     * 通过账号获取用户
     */
    User getByAccount(@Param("account") String account);

    /**
     * 通过账号获取用户验重复
     */
    User getByAccountAndId(@Param("account") String account, @Param("id") String id);

    /**
     * 通过账号获取用户
     */
    User getByAccountAPP(@Param("account") String account, @Param("userType") String userType);

    /**
     * 通过证件查询用户
     * @param idCardType
     * @param idCardNumber
     * @return
     */
    User getUserInfoByIdCard(@Param("account") Integer idCardType,@Param("account") String idCardNumber);
    /**
     * 移动端添加工人登录信息
     */
    void addWorkerAccount(AppPersonnelRegister appPersonnelRegister);

    /**
     * PC端添加工人信息时,添加工人登录信息,以手机号作为登录账号,默认密码123456
     *
     * @param mobile 手机号
     */
    void addWorkerAccountPC(String mobile, Integer idCardType, String idCardNumber);


    Map<String, Object> getUserInfo(@Param("account") String account, @Param("type") Integer type);


    void updateUser(User user);

    /**
     * 根据短信验证码修改密码
     */
    void updatePasswordBySign(AppPersonnelRegister appPersonnelRegister);

    /**
     * 移动端登录
     *
     * @param user
     * @return
     */
    Map<String, Object> loginCheck(User user);


    void deleteUserByPhone(String phone);

    /**
     * web端添加管理级账号
     *
     * @param user
     */
    void saveUserInfo(UserDto user);

    void updateAccount(String newPhone, String oldPhone);

    /**
     * 根据证件修改用户信息
     * @param user
     */
    void updateAccountInfoByIdCard(User user);

    /**
     * 根据证件修改用户信息
     * @param user
     */
    void updateAccountInfoByIdCard(AppPersonnelRegister user);


    /**
     * 根据老账号修改新账号
     * @param user
     */
    void updateAccountInfoByOldAccount(AppPersonnelRegister user);

    /**
     * 根据账号,证件类型,证件编号删除数据
     */
    void deleteUserByAccount(String account);



    /**
     * 根据TokenId查找AccessToken
     * @param tokenId
     * @return
     */
    AccessToken findAccessTokenByTokenId(String tokenId);

    /**
     * 账号定时过期
     * @return
     */
    Integer userExpire();

    /**
     * 根据部门设置有效期
     * @param dept
     */
    void setTimeByDept(Dept dept);

    void updateRegStatus(User usr);

    Map<String, Object> selectAccount(Object organizationCode);

    Integer power(String account);
}
