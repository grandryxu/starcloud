package com.xywg.admin.modular.longxin.model;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Data;

import java.io.Serializable;


@Data
public class BidPingbiao extends Model<BidPingbiao> {

    private Long id;

    private String bidId;

    private String content;
	@Override
	protected Serializable pkVal() {
		 return this.id;
	}



}
