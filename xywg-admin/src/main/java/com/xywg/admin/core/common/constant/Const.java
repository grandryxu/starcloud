package com.xywg.admin.core.common.constant;

import com.xywg.admin.core.util.MD5Util;

/**
 * 系统常量
 *
 * @author wangcw
 * @date 2017年2月12日 下午9:42:53
 */
public interface Const {

    /**
     * 系统默认的管理员密码
     */
    String DEFAULT_PWD = MD5Util.encrypt("123456");

    /**
     * 管理员角色的名字
     */
    String ADMIN_NAME = "administrator";

    /**
     * 管理员id
     */
    Integer ADMIN_ID = 1;

    /**
     * 超级管理员角色id
     */
    Integer ADMIN_ROLE_ID = 1;

    /**
     * 接口文档的菜单名
     */
    String API_MENU_NAME = "接口文档";

    /**
     * 验证码有效时间(分钟)
     */
    Integer SIGN_CODE_VALID_TIME = 5;

}
