package com.xywg.admin.modular.system.model;

import lombok.Data;

/**
 * * @Package com.xywg.admin.modular.system.model
 * * @Description: 获取当前切换的状态
 * * @author caiwei
 * * @date 2018/8/2
 **/
@Data
public class SwitchType {

    /**
     * 切换的企业级还是项目级 0项目级 1企业级
     */
    private Integer switchType;

    /**
     * 0项目级的情况下 是否总包 0否 1是
     */
    private Integer isGeneralContractor;

}
