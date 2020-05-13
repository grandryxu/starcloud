package com.xywg.admin.modular.smz.model;

import lombok.Data;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/7/9 9:53
 */
@Data
public class TeamMo {
    private String name; //名称
    private Long projectId; //项目id
    private Long lablorId; //主键
    private String projectCode;  //项目编号
    private String orgCode;  //企业组织机构代码
    private String teamLeader;//班组领导
    private String contract;//联系手机号
    private String teamIdNumber;//班组长身份证号
    private String responseIdNumber;//责任人身份证号码
    private String note;//备注
    private String conCode;   //承包方组织机构代码
    private Long option1;  //质量评价
    private Long option2;  //进度评价
    private Long option3;  //安全评价
    private Long option4;  //预留星级评价
    private Long reviewId;
}
