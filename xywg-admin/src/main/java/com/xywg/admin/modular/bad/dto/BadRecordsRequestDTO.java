package com.xywg.admin.modular.bad.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 不良记录查询传输DTO
 * @shily
 * @date 2018年6月8日
 */
@Data
public class BadRecordsRequestDTO{
	
	/**
	 * 分页第几页
	 */
	@ApiModelProperty(value="主键", required=true)
	private Long id;
	
	/**
	 * 每页数量
	 */
	@ApiModelProperty(value="审核状态", required=true)
	private Integer isAudit;

}
