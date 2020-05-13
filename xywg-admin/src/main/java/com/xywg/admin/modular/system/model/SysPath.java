package com.xywg.admin.modular.system.model;

import lombok.Data;

/**
 * * @Package com.xywg.admin.modular.system.model
 * * @Description: app请求地址
 * * @author caiwei
 * * @date 2018/10/30
 **/
@Data
public class SysPath {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 请求地址
     */
    private String path;

    /**
     * 是否启用 0否 1是
     */
    private Integer status;

    /**
     * key
     */
    private String key;
}
