package com.xywg.admin.modular.device.utils;

import java.math.BigDecimal;

/**
 * 
 * @description 考勤定位判断
 * 
 * @author chupp
 *
 * @date 2018年5月30日
 *
 */
public class MathRadius {

	private static final double EARTH_RADIUS = 6378.137;

	public static boolean isRecord(BigDecimal lat, BigDecimal lng, 
			BigDecimal standardLat, BigDecimal standardLng, Double standardRadius) {
		boolean flag = false;
		// 计算考勤点到中心点的距离
		double radius = getDistance(standardLat, standardLng, lat, lng);
		BigDecimal recordDistance = BigDecimal.valueOf(radius);
		BigDecimal projectRadius = new BigDecimal(standardRadius);
		// 判断考勤点是否在项目内
		if (recordDistance.compareTo(projectRadius) <= 0) {
			flag = true;
		}
		return flag;
	}
	/**
	 * 计算距离
	 * 
	 * @param lat1
	 *            电子围栏纬度
	 * @param lng1
	 *            电子围栏经度
	 * @param lat2
	 *            考勤定位纬度
	 * @param lng2
	 *            考勤定位经度
	 * @return double 相隔距离
	 * @author duanfen
	 */
	public static double getDistance(BigDecimal standardLat, BigDecimal standardLng, BigDecimal lat, BigDecimal lng) {
		double staLat = radian(standardLat);
		double radLat = radian(lat);
		double a = staLat - radLat;
		double b = radian(standardLng) - radian(lng);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(staLat) * Math.cos(radLat) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000d) / 10000d;
		s = s * 1000;
		return s;
	}

	private static double radian(BigDecimal d) {
		return d.doubleValue() * Math.PI / 180.0;
	}

}
