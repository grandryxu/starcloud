package com.xywg.admin.modular.recuritWorker.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * * @Package com.xywg.admin.modular.recuritWorker.model.dto
 * * @Description: ${todo}
 * * @author caiwei
 * * @date 2018/8/20
 **/
@Data
public class GetBrowseHistoryAppResult {

    /**
     * 浏览记录id
     */
    private Long id ;
    /**
     * 工人名称
     */
    private String workName ;
    /**
     * 浏览时间
     */
    private Date createDate ;
    /**
     * 是否收藏
     */
    private Integer isEnshrine ;
    /**
     * 工人手机号
     */
    private String cellPhone ;

    /**
     * 工人头像
     */
    private String headImage;

    /**
     * 证件类型
     */
    private String idCardType;

    /**
     * 证件号
     */
    private String idCardNumber;


}
