package com.xywg.admin.modular.system.model;

import lombok.Data;

/**
 * 
 * @description 二维码实体类
 * 
 * @author chupp
 *
 * @date 2018年7月27日
 *
 */
@Data
public class Qr{
	
	private String messageType;
	private String idCardType;
	private String idCardNubmer;
	
}
