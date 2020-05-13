package com.xywg.admin.modular.longxin.model;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xuehao.shi
 * 2019/07/11
 */


@Data
public class FixBid extends Model<FixBid> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6692188727805222947L;

	/**
     * 招标信息id
     */
    private String id ;

    /**
     * 项目名称
     */
    private String projectName;
    
    /**
     * 招标信息name
     */
    private String tenderName;


    /**
     * 招标信息简述
     */
    private String tenderDes;
    
    /**
     * 状态
     */
    private Integer status;
    

    /**
     * 流程状态
     */
    private Integer flowStatus;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
