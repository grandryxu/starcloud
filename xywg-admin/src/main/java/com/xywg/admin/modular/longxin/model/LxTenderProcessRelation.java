package com.xywg.admin.modular.longxin.model;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 企业录入表
 * </p>
 *
 * @author xuehao.shi
 * @since 2019-07-10
 */
@Data
public class LxTenderProcessRelation extends Model<LxTenderProcessRelation> {

    private Long id;
    private String processId ;
    private String bussId;

    private Integer type;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
