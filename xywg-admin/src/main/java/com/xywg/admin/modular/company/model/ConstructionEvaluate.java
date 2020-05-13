package com.xywg.admin.modular.company.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 参建单位评价
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-28
 */
@TableName("buss_construction_evaluate")
@Data
public class ConstructionEvaluate extends Model<ConstructionEvaluate> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 参建公司组织机构编码
     */
    @TableField("organization_code")
    private String organizationCode;
    /**
     * 证件类型 参见人员证件类型字典表
     */
    @TableField("id_card_type")
    private String idCardType;
    /**
     * 证件编号
     */
    @TableField("id_card_number")
    private String idCardNumber;
    /**
     * 质量评价星级
     */
    @TableField("mass_review")
    private Integer massReview;
    /**
     * 进度评价星级
     */
    @TableField("pace_review")
    private Integer paceReview;
    /**
     * 安全评价星级
     */
    @TableField("safe_review")
    private Integer safeReview;
    /**
     * 评价说明
     */
    private String note;
    /**
     * 添加时间
     */
    @TableField("create_date")
    private Date createDate;
    /**
     * 修改人
     */
    @TableField("update_user")
    private String updateUser;
    /**
     * 添加人
     */
    @TableField("create_user")
    private String createUser;
    /**
     * 修改时间
     */
    @TableField("update_date")
    private Date updateDate;

    @TableField("score")
    private Long score;


    @TableField("grade")
    private String grade;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
