package com.xywg.admin.modular.wages.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * * @Package com.xywg.admin.modular.wages.model
 * * @Description: ${todo}
 * * @author caiwei
 * * @date 2018/6/4
 **/
@Data
public class ParamsToFindSalaryDetail {
    /**
     * 项目编号
     */
    private String projectCode ;
    /**
     * 班组编号
     */
    private String teamSysNo ;
    /**
     * 工人
     */
    private List<Map<String,Object>> teamWorkers ;
    /**
     * 项目编号
     */
    private String month ;

}
