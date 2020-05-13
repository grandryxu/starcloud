package com.xywg.admin.modular.system.model.dto;

import com.xywg.admin.modular.system.model.Appeal;
import lombok.Data;

/**
 * * @Package com.xywg.admin.modular.system.model.dto
 * * @Description: 申诉实体扩展
 * * @author caiwei
 * * @date 2018/9/10
 **/
@Data
public class AppealDto extends Appeal{

    /**
     * 通过或者驳回的ids
     */
    private String ids;
}
