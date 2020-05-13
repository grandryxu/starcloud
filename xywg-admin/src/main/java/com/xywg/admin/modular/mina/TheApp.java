package com.xywg.admin.modular.mina;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xywg.admin.modular.device.model.DeviceRecord;

public class TheApp {
	private static Logger logger = LoggerFactory.getLogger(TheApp.class);

	private static RecordProcessor recordProcessor = new RecordProcessor();

//	private static ThreadLocal<UserTpm> currentLoginUser = new ThreadLocal();

	private static Map<Long, Boolean> deviceCache = new HashMap();

	private static Object syncDevice = new Object();
	private static Object syncRecord = new Object();
	private static String webPath;
	private static String webAddress;
	private static int sqlBatch = 500;

	private static int deviceOfflineInterval = 5;

	private static int deviceCommandValidDay = 3;

	private static String deviceUpgradePath = "devices";

	private static String deviceRecordPath = "records";

	private static String userPhotoPath = "users";

	private static Date systemInitDay;
	private static String announcementPath = "announcement";
	private static String trainAttementPath = "trains";
	private static String trainRecordAttementPath = "trecords";
	private static String salaryInfoPath = "salaryinfo";
	private static String injuryAttementPath = "injurys";
	private static int maxDeviceCount = -1;
	private static int systemState = -1;
	private static int systemDeadLine;

	private static String projectUpgradePath = "projects";
	private static String contractUpgradePath = "contracts"; //劳动合同上传
	private static String rootPath;
	private static String deployUpgradePath = "war";

	public static void initialize(String webPath) throws Exception {
		logger.debug(new StringBuilder().append("webPath=").append(webPath)
				.toString());
		TheApp.webPath = webPath;
		InputStream is = TheApp.class.getClassLoader().getResourceAsStream(
				"app.properties");
		if (is == null) {
			return;
		}

		Properties p = new Properties();
		p.load(is);
		is.close();

		webAddress = p.getProperty("server.url");
		deviceUpgradePath = p.getProperty("device.upgrade.path", deviceUpgradePath);
		deviceRecordPath = p.getProperty("device.record.path", deviceRecordPath);
		userPhotoPath = p.getProperty("user.photo.path", userPhotoPath);
		announcementPath = p.getProperty("system.announcement.path",announcementPath);
		sqlBatch = getUnsignedIntegerValue(p, "sql.batch", sqlBatch);
		projectUpgradePath = p.getProperty("project.upgrade.path",projectUpgradePath);
		contractUpgradePath = p.getProperty("contract.upgrade.path",contractUpgradePath);	//劳动合同上传
		trainAttementPath = p.getProperty("train.attement.path",trainAttementPath);
		trainRecordAttementPath = p.getProperty("train.record.path",trainRecordAttementPath);
		salaryInfoPath = p.getProperty("salary.record.path", salaryInfoPath);
		injuryAttementPath = p.getProperty("injury.record.path",injuryAttementPath);
		rootPath = p.getProperty("server.path",webPath);
		deployUpgradePath = p.getProperty("deploy.upgrade.path", deployUpgradePath);
	}

//	public static void updateParameter(ParameterTpm parameter) {
//		if (parameter.getDeviceOfflineInterval() != null) {
//			deviceOfflineInterval = parameter.getDeviceOfflineInterval()
//					.intValue();
//		}
//
//		if (parameter.getCommandValid() != null) {
//			deviceCommandValidDay = parameter.getCommandValid().intValue();
//		}
//
//		systemInitDay = parameter.getSystemInitDay();
//		if (systemInitDay == null)
//			systemInitDay = new Date();
//	}

//	public static void setLoginUser(UserTpm user) {
//		currentLoginUser.set(user);
//	}
//
//	public static UserTpm getLoginUser() throws Exception {
//		UserTpm user = (UserTpm) currentLoginUser.get();
//		if (user == null) {
//			throw new Exception("会话超时，请重新登录");
//		}
//		return user;
//	}
//
//	public static void clearLoginUser() {
//		currentLoginUser.remove();
//	}

	public static String getWebPath() {
		return webPath;
	}
	
	public static String getRootPath() {
		return "rootPath";
	}

	public static String getWebAddress() {
		return webAddress;
	}

	public static int getSqlBatch() {
		return sqlBatch;
	}

	public static int getDeviceOfflineInterval() {
		return deviceOfflineInterval;
	}

	public static void setDeviceOfflineInterval(Integer interval) {
		if (interval == null) {
			return;
		}
		deviceOfflineInterval = interval.intValue();
	}

	public static int getDeviceCommandValidDay() {
		return deviceCommandValidDay;
	}

	public static void setDeviceCommandValidDay(Integer day) {
		if (day == null) {
			return;
		}
		deviceCommandValidDay = day.intValue();
	}

	public static String getProjectUpgradePath() {
		return projectUpgradePath;
	}

	//劳动合同上传
	public static String getContractUpgradePath() {
		return contractUpgradePath;
	}

	public static String getDeviceRecordPath() {
		return deviceRecordPath;
	}

	public static String getUserPhotoPath() {
		return userPhotoPath;
	}

	public static String getAnnouncementPath() {
		return announcementPath;
	}

	public static String getDeviceUpgradePath() {
		return deviceUpgradePath;
	}

	public static Date getSystemInitDay() {
		return systemInitDay != null ? systemInitDay : new Date();
	}

	public static int getSystemState() {
		if (systemState != 99) {
			return systemState;
		}

		if (systemDeadLine < 0) {
			return systemState;
		}

		Calendar cal = Calendar.getInstance();
		cal.set(1, 2030);
		cal.set(6, systemDeadLine);
		if (cal.getTime().after(new Date())) {
			return systemState;
		}

		return 0;
	}

	public static void setSystemState(int state) {
		systemState = state;
		System.out.println(new StringBuilder().append("系统状态：")
				.append(state < 0 ? "未授权" : state == 99 ? "正常" : "已过期")
				.toString());
	}

	public static void setSystemDeadLine(int s) {
		systemDeadLine = s;
	}

	public static String getRootPath(String path) {
		StringBuilder rootPath = new StringBuilder(getRootPath()).append(
				File.separator).append(path);

		return rootPath.toString();
	}

	private static int getUnsignedIntegerValue(Properties properties,
			String key, int defaultValue) {
		String threshold = properties.getProperty(key);
		if ((threshold != null) && (threshold.matches("\\d+"))) {
			int th = Integer.parseInt(threshold);
			if (th > 0) {
				return th;
			}
		}

		return defaultValue;
	}

	public static void cacheDevice(Long deviceId) {
		synchronized (syncDevice) {
			if (deviceCache.get(deviceId) == null)
				deviceCache.put(deviceId, Boolean.valueOf(true));
		}
	}

	public static List<Long> getCacheDevice() {
		synchronized (syncDevice) {
			if (deviceCache.isEmpty()) {
				return null;
			}
			List<java.lang.Long> result = new ArrayList<java.lang.Long>();
			result.addAll(deviceCache.keySet());
			deviceCache.clear();
			return result;
		}
	}

	public static boolean cacheRecord(DeviceRecord record) {
		synchronized (syncRecord) {
			return recordProcessor.cacheRecord(record);
		}
	}

	public static boolean cacheRecord(List<DeviceRecord> record) {
		synchronized (syncRecord) {
			return recordProcessor.cacheRecord(record);
		}
	}

	public static List<DeviceRecord> getCacheRecord(long endline) {
		synchronized (syncRecord) {
			return recordProcessor.getCacheRecord(endline);
		}
	}

	public static int getMaxDeviceCount() {
		return maxDeviceCount;
	}

	public static void setMaxDeviceCount(int count) {
		maxDeviceCount = count;
		System.out.println(new StringBuilder().append("设备授权数量：")
				.append(count == -1 ? "无限制" : Integer.valueOf(count))
				.toString());
	}

	public static void cleanRecordLock() {
		recordProcessor.cleanRecordLock();
	}

	public static String getTrainAttementPath() {
		return trainAttementPath;
	}

	public static String getTrainRecordAttementPath() {
		return trainRecordAttementPath;
	}

	public static String getSalaryInfoPath() {
		return salaryInfoPath;
	}

	public static String getInjuryAttementPath() {
		return injuryAttementPath;
	}

	public static String getDeployUpgradePath() {
		return deployUpgradePath;
	}

	public static void setDeployUpgradePath(String deployUpgradePath) {
		TheApp.deployUpgradePath = deployUpgradePath;
	}
	
}
