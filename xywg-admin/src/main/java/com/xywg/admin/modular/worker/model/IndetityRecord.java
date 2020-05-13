package com.xywg.admin.modular.worker.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

@TableName("buss_indetity_record")
@Data
public class IndetityRecord {
 
	@TableId(value = "id", type = IdType.AUTO)
	private Long id ;
	
	@TableField("id_card_number")
	private String idCardNumber;
	
	@TableField("worker_name")
	private String WorkerName;
	
	@TableField("flag")
	private String flag;
	
	@TableField("reason")
	private String reason;
	
	@TableField("create_date")
	private Date createDate;
	
	@TableField("create_user")
	private String createUser;
	
	
	public IndetityRecord(String idCardNumber,String WorkerName,String flag,String reason) {
	}
}
