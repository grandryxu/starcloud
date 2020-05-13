package com.xywg.admin.modular.bad.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BadRecordsResultVO {

	@ApiModelProperty(value="不良记录待审核总数")
	private Integer count;
	
	@ApiModelProperty(value="工人不良记录待审核总数")
	private Integer toAuditBadRecordsCount;
	
	@ApiModelProperty(value="工资单待审核总数")
	private Integer toAuditPayRollCount;
	
	@ApiModelProperty(value="结算单待审核总数")
	private Integer toAuditSettlementCount;
}
