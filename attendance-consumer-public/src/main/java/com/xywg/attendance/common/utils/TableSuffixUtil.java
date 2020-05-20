package com.xywg.attendance.common.utils;

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
	
	public static SimpleDateFormat df = new SimpleDateFormat("yyyy_MM");//设置日期格式
	public static SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM");//设置日期格式
	public static SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
	/**
	 *	 获取当前分表后缀
	 *  @return
	 */
	public static String getTableSuffix() {
		synchronized(df) {
			return df.format(new Date());// new Date()为获取当前系统时间
		}
		
	}

	public static String getTableSuffix(String time) {
		synchronized(df) {
			try {
				return df.format(df1.parse(time));
			} catch (ParseException e) {
				try {
					Date date = df2.parse(time);
					return df.format(date);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
			return df.format(new Date());// new Date()为获取当前系统时间
		}
		
	}
	
	

	
}
