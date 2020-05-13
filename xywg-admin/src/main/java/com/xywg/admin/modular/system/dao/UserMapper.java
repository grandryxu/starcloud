package com.xywg.admin.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.core.datascope.DataScope;
import com.xywg.admin.modular.system.model.Dept;
import com.xywg.admin.modular.system.model.User;
import com.xywg.admin.oauth.domain.oauth.AccessToken;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author wangcw
 * @since 2017-07-11
 */
public interface UserMapper extends BaseMapper<User> {

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
     * @param dataScope
     * @param name
     * @param beginTime
     * @param endTime
     * @param deptid
     * @return
     */
    List<Map<String, Object>> selectUsers(@Param("dataScope") DataScope dataScope,
                                          @Param("name") String name, @Param("beginTime") String beginTime,
                                          @Param("endTime") String endTime, @Param("deptid") Integer deptid);

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
    User getByAccountAndId(@Param("account")String account, @Param("id") String id);

    /**
     * 通过账号获取用户
     */
    User getByAccountAPP(@Param("account") String account,@Param("userType")String userType);

    /**
     * 通过证件获取用户
     * @param idCardType
     * @param idCardNumber
     * @return
     */
    User getUserInfoByIdCard(@Param("idCardType") Integer idCardType,@Param("idCardNumber")  String idCardNumber);

    Map<String,Object> getUserInfo(@Param("account") String account,@Param("type")Integer type);

    /**
     * 更新用户信息
     * @param user
     */
    void  updateUser(User user);

    void deleteUserByPhone(String phone);

    /**
     * 修改账号(pc端管理员给工人修改手机号,此时他的登录账号为新的手机号)
     * @param newPhone
     * @param oldPhone
     */
    void updateAccount(@Param("newPhone") String newPhone,@Param("oldPhone") String oldPhone);

    /**
     * 根据证件类型和证件编号更新用户信息
     * @param user  用户实体数据封装类
     */
    void updateAccountInfoByIdCard(User user);

    /**
     * 根据身份证号查询重复用户
     * @param idCard
     * @return
     */
    User getByIdCard(@Param("idCard") String idCard,@Param("id")Integer id);

    /**
     * 修改身份证
     * @param id
     * @param idCardNumber
     */
    void updateIdCard(@Param("id")Integer id,@Param("idCardNumber") String idCardNumber);

    /**
     * 逻辑删除
     * @param id
     */
    void delById(@Param("id")Integer id);

    /**
     * 根据
     * @param account
     */
    void deleteUserByAccount(@Param("account")String account);

    @Select("select * from oauth_access_token where token_id = #{tokenId}")
    AccessToken findAccessTokenByTokenId(@Param("tokenId") String tokenId);

    Integer userExpire();

    /**
     * 根据部门设置帐号有效期
     * @param dept
     */
    void setTimeByDept(Dept dept);

    User getById(User u);

    void updateRegStatus(User usr);

    Map<String, Object> selectAccount(@Param("organizationCode") Object organizationCode);

    Integer power(@Param("account") String account);
}