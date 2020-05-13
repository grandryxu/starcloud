package com.xywg.admin.modular.project.model.vo;

import com.xywg.admin.modular.project.model.ProjectAddress;
import lombok.Data;

/**
 * * @Package com.xywg.admin.modular.project.model.vo
 * * @Description: ProjectAddress实体扩展
 * * @author caiwei
 * * @date 2018/9/20
 **/
@Data
public class ProjectAddressVo extends ProjectAddress{

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 工人个数
     */
    private Integer workerNum;

    /**
     * 参建类型 0总包 1参建
     */
    private String cooType;
}
