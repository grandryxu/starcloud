package com.xywg.admin.modular.longxin.model;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xuehao.shi
 * 2019/07/11
 */


@Data
public class FixMark extends Model<FixMark> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6692188727805222947L;

	/**
     * 招标邀请id
     */
    private String id ;

    /**
     * 邀请表id
     */
    private String inviteId;
    
    /**
     * 竞标价格
     */
    private String bidPrice;

   




    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
