package com.xywg.admin.modular.smz.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 
 * @description 劳务通将每个项目对应的最大值返回给实名制实体类
 * 
 * @author jln
 *
 * @date 2019-04-19
 *
 */
@Data
public class SmzCallBack implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3705751532722230544L;
	private String maxId;
	private String projectId;
}
