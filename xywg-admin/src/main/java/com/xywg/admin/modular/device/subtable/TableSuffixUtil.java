package com.xywg.admin.modular.device.subtable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @description 分表工具类
 * @author xuehao.shi
 * @date
 *
 */

public class TableSuffixUtil {
	
	public static SimpleDateFormat df = new SimpleDateFormat("yyyy_MM");//璁剧疆鏃ユ湡鏍煎紡
	public static SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM");//璁剧疆鏃ユ湡鏍煎紡
	/**
	 *	 获取当前分表后缀
	 *  @return
	 */
	public static String getTableSuffix() {
		return df.format(new Date());// new Date()为获取当前系统时间
	}
	
	
	public static String getTableSuffix(String time) {
		try {
			return df.format(df1.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return df.format(new Date());
		
	}
	
	

	
}
