package com.xywg.admin.modular.longxin.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;
import java.util.Date;

/**
 * lx_evaluate
 * @author 
 */
@Data
@TableName("lx_evaluate")
public class LxEvaluate extends Model<LxEvaluate> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 评分项
     */
    @TableField("items")
    private String items;

    /**
     * 分数类型
     */
    @TableField("type")
    private String type ;

    /**
     * 分数上限
     */
    @TableField("max")
    private String max;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /*
    * 参建单位分数
    * */
    @TableField("score")
    private  Integer score;


    private String projectName;
    private String companyName;
    private static final long serialVersionUID = 1L;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}