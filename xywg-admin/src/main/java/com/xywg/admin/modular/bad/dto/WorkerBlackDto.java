package com.xywg.admin.modular.bad.dto;

import com.xywg.admin.modular.bad.model.WorkerBlackList;
import lombok.Data;

/**
 * * @Package com.xywg.admin.modular.bad.dto
 * * @Description: 黑名单App
 * * @author caiwei
 * * @date 2018/10/24
 **/
@Data
public class WorkerBlackDto extends WorkerBlackList {

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 班组名称
     */
    private String teamName;

    /**
     * 工人名称
     */
    private String workerName;

    /**
     * 工人头像
     */
    private String headImage;
}
