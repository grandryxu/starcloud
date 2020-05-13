package com.xywg.admin.modular.recruit.model;

import lombok.Data;

import java.util.Date;

/**
 * @Auther: wangshibo
 * @Date: 2018/9/4/004 13:41
 * @Description:
 */
@Data
public class RecruitInfoVo {
    private String account;
    private Integer recruitType;
    private String recruitStation;
    private String projectType;
    private String workNameSets;
    private String recruitNumber;
    private String wealContent;
    private Integer ageStart;
    private Integer ageEnd;
    private String claim;
    private Integer salaryType;
    private String salaryContent;
    private String salaryText;
    private String name;
    private String phone;
    private String email;
    private String provinces;
    private String cities;
    private String areas;
    private String address;
    private String description;
    private String createUser;
    private Date createDate;
}
