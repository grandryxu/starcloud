package com.xywg.admin.modular.wages.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/6/7 20:17
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDetailDto extends AccountDetail{
    private String workerName;
    private String headImage;
    private String workerType;
    private Date lastTime;
    private Date closingTime;
    private String teamSysNo;
    private String organizationCode;

}
