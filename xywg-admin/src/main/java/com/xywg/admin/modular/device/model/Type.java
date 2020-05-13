package com.xywg.admin.modular.device.model;

/**
 * 考勤机命令
 * 
 * @author: duanfen
 * @version: 2018年6月15日 上午11:20:32
 */
public enum Type {
	SyncTime(1, "同步时间"), Reboot(2, "重启"), GetInfo(3, "获取设备信息"), Upgrade(4,
			"升级固件"), ClearRecord(5, "清空考勤记录"), ClearUser(6, "清空人员信息"), DispatchUser(
			7, "下发个别人员信息"), DeleteUser(8, "删除个别人员信息");

	private int value;
	private String description;

	private Type(int value, String description) {
		this.value = value;
		this.description = description;
	}

	public int getValue() {
		return this.value;
	}

	public String getDescription() {
		return this.description;
	}

	public static Type valueOf(int value) {
		Type[] vs = values();
		for (Type s : vs) {
			if (s.getValue() == value) {
				return s;
			}
		}
		throw new IndexOutOfBoundsException("Invalid Enum Value");
	}

	public String toString() {
		return this.description;
	}
}
