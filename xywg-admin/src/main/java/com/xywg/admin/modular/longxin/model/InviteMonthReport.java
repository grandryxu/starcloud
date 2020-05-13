package com.xywg.admin.modular.longxin.model;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
public class InviteMonthReport extends Model<InviteMonthReport> {

    // 月份
    private String mon;

    // 数量
    private String cou;

    @Override
    protected Serializable pkVal() {
        return this.mon;
    }
}
