package com.xywg.admin.modular.longxin.model;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
@Data
public class PriceTemp {

    private  String name;
    private String type;
    private String desc;
    private String price;

}
