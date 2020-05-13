package com.xywg.admin.modular.team.model;

import lombok.Data;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/6/12 10:24
 */
@Data
public class AppTeamerDto {
    private Integer idCardType;
    private String idCardNumber;
    private Integer projectStatus;
    private Integer pageNo;
    private Integer pageSize;
    private Integer index;

}
