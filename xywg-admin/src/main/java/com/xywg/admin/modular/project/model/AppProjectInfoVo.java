package com.xywg.admin.modular.project.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
/**
 * app项目信息实体类
 * @author wangshibo
 *	2018年6月6日
 * 下午5:55:40
 */
@Data
public class AppProjectInfoVo {
	
	 /**
     * 项目编号
     */
    private String projectCode;
    
    /**
     * 项目分类
     */
    private String projectCategory;
	
    /**
     * 施工许可证编号
     */
    private String builderLicenceNum;
    /**
     * 项目行政区划,参见地区字典数据
     */
    private String area;
    
    /**
     * 承包合同额 单位 万元
     */
    private BigDecimal totalContractAmt;
    /**
     * 建筑面积 单位 平方米
     */
    private BigDecimal buildingArea;
	 /**
     * 开工日期 精确到天，格式：yyyy-mm-dd
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    /**
     * 竣工日期 精确到天，格式：yyyy-mm-dd
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date completeDate;
}
