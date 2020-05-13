package com.xywg.admin.modular.smz.model;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/7/4 14:58
 */
@Data
public class ProjectMo {
    private Long comId;

    /**
     * 项目主键
     */
    private Long projectId;
    /**
     * 项目名称
     */
    private String name;

    /**
     * 施工地址
     */
    private String address;
    /**
     * 合同开始时间
     */
    private Date beginDate;

    /**
     * 合同竣工时间
     */
    private Date endDate;

    /**
     * 开工时间
     */
    private Date start;

    /**
     * 竣工时间
     */
    private Date end;

    /**
     * 工程面积
     */
    private Float buildSize;

    /**
     * 工程造价
     */
    private Double buildCost;

    /**
     * 项目经理
     */
    private String projectManager;


    /**
     * 施工状态
     */
    private String projectProgess;
    /**
     * 联系方式
     */
    private String manPhone;
    /**
     * 施工内容
     */
    private String content;

    /**
     * 类型
     */
    protected Integer type;

    /**
     * 区域
     */
    private String area;

    /**
     * 经纬度
     */
    private String placePoint;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 经度
     */
    private Double lng;
    /**
     * 纬度
     */
    private Double lat;
    /*
	 * 项目编号
	 */
    private String projectCode;
    /*
     * 建设项目编码
     */
    private String buildProjectCode;
    /*
     * 承包单位组织机构代码
     */
    private String contractorOrgcode;
    /*
     * 承包单位编号
     */
    private String generalContractorCode;
    /*
     * 项目活动类型
     */
    private String projectActivityType;
    /*
     * 项目简介
     */
    private String projectDescription;
    /*
     * 重点项目
     */
    private Integer isMajaorProject;
    /*
     * 参建类型
     */
    private Integer contractorType;
    /*
     * 进场时间
     */
    private Date entryTime;
    /*
     * 退场时间
     */
    private Date exitTime;
    /*
     * 发放工资的银行名称
     */
    private String bankName;
    /*
     * 发放工资的共管银行账号
     */
    private String bankLinkNumber;
    /*
     * 工资发放模式
     */
    private Integer payMode;
    /*
     * 身份类型
     */
    private Integer pmIdcardType;
    /*
     * 身份证号码
     */
    private String pmIdcardNumber;
    
    /**
     * 建设单位联系人
     */
    private String buildeContractor;
    
    /**
     * 建设单位联系人电话
     */
    private String buildeContractorphone;
    
    /**
     * 企业分管领导
     */
    private String leader;
    
    /**
     * 企业分管领导电话
     */
    private String leaderPhone;
    
    /**
     * 项目总监
     */
    private String manager;
    
    /**
     * 项目总监电话
     */
    private String managerPhone;
    
    /**
     * 是否文明工地 0否 1是
     */
    private Integer  civilizedProject;
    
    /**
     * 是否申报优质工程 0否1是
     */
    private Integer goodProject;
    
    /**
     * 是否政府工程 0否 1是
     */
    private Integer governmentProject;

    /**
     * 是否是龙信公司机构代码 1：是 0：否   默认 null
     */
    private String isRealcode;
}
