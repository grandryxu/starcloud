package com.xywg.admin.modular.longxin.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.xywg.admin.flow.entity.Order;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by tcw on 2019/7/9.
 */


@Data
public class LxOrder<T> extends Model<LxOrder> {

    private String id;

    private Order order;


    private T  business;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
