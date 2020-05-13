package com.xywg.admin.core.shiro;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息
 *
 * @author wangcw
 * @date 2016年12月5日 上午10:26:43
 */
@Data
public class ShiroUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String account;
    private String name;
    private Integer deptId;
    private List<Integer> roleList;
    /**
     * 是否是企业级账号
     */
    private Integer isEnterprise;
    private String deptName;
    private List<String> roleNames;



}
