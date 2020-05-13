package com.xywg.admin.modular.project.model.vo;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProjectPositionVo{

	private BigDecimal lng;
	private BigDecimal lat;
	private Double radius;
	private String address;
}
