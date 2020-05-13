package com.xywg.admin.modular.longxin.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * lx_company_evaluate
 * @author 
 */
@Data
@TableName("lx_company_evaluate")
public class LxCompanyEvaluate implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 公司名称
     */
    @TableField("company_name")
    private String companyName;

    /**
     * 公司id
     */
    @TableField("company_id")
    private Long companyId;

    /**
     * 分数
     */
    @TableField("score")
    private Long score;

    /**
     * 等级
     */
    @TableField("grade")
    private String grade;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    private static final long serialVersionUID = 1L;


}