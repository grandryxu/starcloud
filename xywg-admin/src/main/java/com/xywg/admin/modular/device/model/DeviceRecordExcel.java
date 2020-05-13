package com.xywg.admin.modular.device.model;

import com.xywg.admin.core.excel.annotation.ExcelField;

import lombok.Data;

@Data
public class DeviceRecordExcel {

	@ExcelField(title = "项目名称", order = 1)
    private String projectName;
	
	@ExcelField(title = "班组名称", order = 2)
    private String teamName;
	
	@ExcelField(title = "姓名", order = 3)
    private String workerName;
	
	@ExcelField(title = "证件类型", order = 4)
    private String idCardType;
	
	@ExcelField(title = "证件号码", order = 5)
    private String idCardNumber;
	
	@ExcelField(title = "考勤时间", order = 6)
    private String time;
	
	@ExcelField(title = "来源", order = 7)
    private String source;
	
	@ExcelField(title = "是否有效", order = 8)
    private String isValid;
	
	@ExcelField(title = "考勤类型", order = 9)
    private String deviceType;
	
}
