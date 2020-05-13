package com.xywg.admin.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hjy
 * @date 2018/5/29
 * 鸡汤实体类
 */
@Data
@TableName("buss_banner")
public class Banner extends Model<Banner> {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("organization_code")
    private String organizationCode;
    private String name;

    @TableField("file_name")
    private String fileName;
    private String url;
    private Integer link;
    private Integer status;
    /**
     * 备注
     */
    private String note;
    private Integer type;
    private Integer order;
    @TableField("choose_type")
    private Integer chooseType;
    /**
     * 名人警句
     */
    private String motto;
    @TableField("is_del")
    private Integer isDel;
    /**
     * 添加人
     */
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;
    /**
     * 添加时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private String createUser;
    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;
    /**
     * 修改人
     */
    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    private String updateUser;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
