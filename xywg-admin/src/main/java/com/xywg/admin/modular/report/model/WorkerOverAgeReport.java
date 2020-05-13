package com.xywg.admin.modular.report.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xywg.admin.core.excel.annotation.ExcelField;
import com.xywg.admin.modular.project.model.Injury;

import lombok.Data;

/**
 * 导出超龄工人
 * @author duanfen
 * 2019-06-11
 */
@Data
public class WorkerOverAgeReport  extends Model<WorkerOverAgeReport> {

	/**
     * 班组
     */
    @ExcelField(title = "班组", order = 1)
    private String teamName;
    /**
     * 工人名称
     */
    @ExcelField(title = "姓名", order = 2)
    private String workerName;

    /**
     * 手机号码
     */
    @ExcelField(title = "证件编号", order = 3)
    private String idCardNumber;
    
    


    /**
     * 身份证类型
     */
    private String idCardType;

    /**
     * 项目个数
     */
    @ExcelField(title = "生日", order = 4)
    private String birthday;

    /**
     * 不良记录数
     */
    @ExcelField(title = "年龄", order = 5)
    private Integer age;

	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stubd
		return null;
	}


  
}
