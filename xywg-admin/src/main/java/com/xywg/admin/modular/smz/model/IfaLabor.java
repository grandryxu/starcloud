package com.xywg.admin.modular.smz.model;

import lombok.Data;

/**
 * @Description: 与实名制对接实体类
 * @Author xieshuaishuai
 * @Date Create in 2018/7/3 17:54
 */
@Data
public class IfaLabor {
	
    private String tableName;
    
    private Long id;
    
    public IfaLabor() {
    	super();
    }
    
    public IfaLabor(String tableName,Long id){
        this.tableName=tableName;
        this.id=id;
    }
}
