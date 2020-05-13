package com.xywg.admin.modular.system.model;

import lombok.Data;

/**
 * * @Package com.xywg.admin.modular.system.model
 * * @Description: ${todo}
 * * @author caiwei
 * * @date 2018/8/21
 **/
@Data
public class VersionVo extends Version {

    /**
     * 0最新版本 1非最新版本
     */
    private Integer isNew;
}
