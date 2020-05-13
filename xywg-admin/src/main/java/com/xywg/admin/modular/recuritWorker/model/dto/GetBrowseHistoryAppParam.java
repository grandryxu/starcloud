package com.xywg.admin.modular.recuritWorker.model.dto;

import lombok.Data;

/**
 * * @Package com.xywg.admin.modular.recuritWorker.model.dto
 * * @Description: ${todo}
 * * @author caiwei
 * * @date 2018/8/20
 **/
@Data
public class GetBrowseHistoryAppParam {

    /**
     * 招聘id
     */
    private Long id;

    /**
     * 分页页号
     */
    private Integer pageNo;

    /**
     * 每页展示的个数
     */
    private Integer pageSize;

    /**
     * 每次搜索的索引
     */
    private Integer index;
}
