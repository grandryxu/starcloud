package com.xywg.admin.modular.projectSubContractor.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 项目参建企业信息
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-23
 */
@TableName("buss_project_sub_contractor")
@Data
public class AppProjectSubContractorDto extends Model<AppProjectSubContractorDto> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 项目编号
     */
    @TableField("project_code")
    private String projectCode;
    /**
     * 企业组织机构代码
     */
    @TableField("organization_code")
    private String organizationCode;

    private String companyName;
    /**
     * 参建类型 8专业分包；9设备分包；10材料分包；11后勤服务；12特殊设备；13劳务分包；14监理单位；15建设单位；16总承包单位；17勘察单位；18设计单位；67其它；89租赁
     */
    private Integer contractorType;
    private Integer projectType;

    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 行政区名称
     */
    private String area;
    /**
     * 开工日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField("start_date")
    private Date startDate;

    /**
     * 项目经理名称
     */
    @TableField("pm_name")
    private String pmName;

    /**
     * 进场时间
     */
    @TableField("entry_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date entryTime;
    /**
     * 退场时间
     */
    @TableField("exit_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date exitTime;

    /**
     * 身份类型
     */
    private Integer idCardType;

    /**
     * 身份证号码
     */
    private String idCardNumber;
    /**
     * 搜索字段
     */
    private String key;
    /**
     * 是否收藏
     */
    private Integer isCollect;
    /**
     * 地址
     */
    private String address;
    /**
     * 公司项目总数
     */
    private Integer allProjectCount;
    /**
     * 总承包项目数
     */
    private Integer contractorProjectCount;
    /**
     * 参建项目数
     */
    private Integer constructionProjectCount;
    /**
     * 竣工日期
     */
    @TableField("complete_date")
    private Date completeDate;

    private Integer projectStatus;

    private Integer deviceType;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
