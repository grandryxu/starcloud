package com.xywg.admin.modular.smz.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/7/4 8:57
 */
@Data
public class CompanyMo {
    /**
     * 公司主键
     */
    private Long companyId;

    /**
     * 企业名称
     */
    @NotBlank(message="企业名称不能为空")
    private String name;
    /**
     *  企业机构代码
     */
    @NotBlank(message="企业机构代码不能为空")
    private String code;

    /**
     * 工商注册号
     */
    private String registerNo;
    /**
     * 传真
     */
    private String fox;
    /**
     * 企业资质等级1:优秀 2:良好 3：一般 4：差
     */
    private int level;
    /**
     * 法人
     */
    @NotBlank(message="法人代表不能为空")
    private String legal;

    /**
     *  联系人电话
     */
    private String mobile;

    /**
     * 联系人
     */
    @NotBlank(message="联系人不能为空")
    private String contacts;

    /**
     * 企业营业地址
     */
    private String address;

    /**
     * 注册地区编码
     */
    private String areaCode;
    /**
     *法人代表电话
     */
    private String userMobile;
    /**
     * 单位性质
     */
    private String organizationType  ;
    /**
     * 邮政编码
     */
    private String zipCode;
    /**
     * 法定代表人证件类型
     */
    private Integer representativeIdcardType ;

    /**
     * 法定代表人证件号码
     */
    private String representativeIdcardNumber ;

    /**
     * 注册资本(万元)
     */
    private String registrationCapital ;
    /**
     * 注册资本币种
     */
    private String capitalCurrency ;
    /**
     * 成立日期
     */
    private Date establishDate     ;
    /**
     * 企业联系邮箱
     */
    private String email           ;
    /**
     * 企业网址
     */
    private String homewebsiteUrl  ;
    /**
     * 企业经营状态
     */
    private String businessStatus  ;
    /**
     * 企业备注
     */
    private String memo;
    
    /**
     * 企业市域类型
     */
    private Long domainType  ;
    /**
     * 企业经济性质名称
     */
    private Long organizationEconomicNature;
}
