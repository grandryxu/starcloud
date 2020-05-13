package com.xywg.admin.modular.longxin.model;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class BidPrice extends Model<BidPrice> {

    private String id ;

    /**
     * 招标信息id
     */
    private String tenderId;
    
    /**
     * 招标信息name
     */
    private String tenderName;

    /**
     * 企业id
     */
    private String companyId;
    
    /**
     * 企业name
     */
    private String companyName;

    private String inviteId;
    /**
             *竞标价格
     */
    private String bidPrice;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
