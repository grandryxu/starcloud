package com.xywg.admin.modular.led.model;

import java.util.Date;

import lombok.Data;
/**
 * 上传人员实体类（过滤用）
 * @ClassName:  UploadUser   
 * @Description:TODO(上传人员实体类（过滤用）)   
 * @author: wangshibo 
 * @date:   2018年7月17日 下午2:20:13   
 *     
 * @Copyright: 2018 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏星云网格信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
public class UploadUser {
	/**
	 * 工人id
	 */
	private Integer workId;
	/**
	 * 录入时间
	 */
	private Date createDate;
	/**
	 * 备注
	 */
	private String memo;
	
	public UploadUser(Integer workId, Date createDate, String memo) {
		super();
		this.workId = workId;
		this.createDate = createDate;
		this.memo = memo;
	}


	public UploadUser() {
		super();
	}
	
}
