package com.xywg.admin.modular.mina;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xywg.admin.modular.device.model.Device;
import com.xywg.admin.modular.device.model.DeviceRecord;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.worker.model.WorkerMaster;

public class RecordProcessor {
	private static final String RECORD_SEPARATOR = "##";
	private static final String RECORD_DIR = ".record";
	private static final String FILE_LOCK = ".lock";
	private static final String TRASH_FILE = "trash.txt";
	private static final Pattern FILE_PATTERN = Pattern.compile("\\d+");
	private File lockFile;
	private File writterFile;
	private int count;
	private static  Logger logger = LoggerFactory.getLogger(RecordProcessor.class);
	private PrintWriter writer;
	private String rootDir;

	public boolean cacheRecord(DeviceRecord record) {
		try {
			makeWriter();
		} catch (Exception e) {
			this.logger.error(new StringBuilder().append("创建Writer失败：").append(e.getMessage()).toString());
			logger.error(e.getMessage());
			tryToResetWriter(true);
			return false;
		}
		saveRecord(record);
		tryToResetWriter(false);
		return true;
	}

	public boolean cacheRecord(List<DeviceRecord> record) {
		try {
			makeWriter();
		} catch (Exception e) {
			this.logger.error(new StringBuilder().append("创建Writer失败：").append(e.getMessage()).toString());
			logger.error(e.getMessage());
			tryToResetWriter(true);
			return false;
		}
		for (DeviceRecord r : record) {
			saveRecord(r);
		}
		tryToResetWriter(false);
		return true;
	}

	public List<DeviceRecord> getCacheRecord(long endline) {
		File dir = getRootDir();
		String[] files = dir.list();
		if (files == null) {
			return null;
		}

		for (String name : files) {
			if (FILE_PATTERN.matcher(name).matches()) {
				if (!new File(dir, new StringBuilder().append(name).append(".lock").toString()).exists()) {
					if (Long.parseLong(name) <= endline) {
						File file = new File(dir, name);
						List record;
						try {
							record = readRecord(file);
						} catch (Exception e) {
							this.logger.error(new StringBuilder().append("解析记录文件失败：").append(name).append(";ex=")
									.append(e.getMessage()).toString());
							logger.error(e.getMessage());
							continue;
						}
						if (!Utils.isEmpty(record))
							return record;
					}
				}
			}
		}
		if (this.count == 0) {
			return null;
		}
		tryToResetWriter(true);
		List record;
		try {
			record = readRecord(this.writterFile);
			this.writterFile = null;
		} catch (Exception e) {
			this.logger.error(new StringBuilder().append("解析记录文件失败：").append(e.getMessage()).toString());
			logger.error(e.getMessage());
			return null;
		}

		return record;
	}

	private void makeWriter() throws Exception {
		if (this.writer != null) {
			return;
		}
		File dir = getRootDir();
		Long name = Long.valueOf(System.currentTimeMillis());
		File file;
		while (true) {
			file = new File(dir, name.toString());
			if (!file.exists()) {
				break;
			}
			name = Long.valueOf(name.longValue() - 10L);
		}
		this.writterFile = file;
		this.writer = new PrintWriter(file);
		this.lockFile = new File(dir, new StringBuilder().append(name.toString()).append(".lock").toString());
		boolean createNewFile = this.lockFile.createNewFile();
		if (!createNewFile) {
			logger.info("" + createNewFile);
		}
		this.count = 0;
	}

	private void saveRecord(DeviceRecord record) {
		try {
			StringBuilder builder = new StringBuilder();
			if (record.getDevice().getProject() != null) {
				builder.append(record.getDevice().getId()).append("##").append(record.getDevice().getProject().getId())
						.append("##").append(record.getPerson().getIdCardNumber()).append("##")
						.append(record.getTime().getTime()).append("##")
						.append(record.getPerson().getWorkerName() != null ? record.getPerson().getWorkerName() : "")
						.append("##").append(record.getDevice().getState());
			} else {
				builder.append(record.getDevice().getId()).append("##").append(-1).append("##")
						.append(record.getPerson().getIdCardNumber()).append("##").append(record.getTime().getTime())
						.append("##")
						.append(record.getPerson().getWorkerName() != null ? record.getPerson().getWorkerName() : "")
						.append("##").append(record.getDevice().getState());
			}

			if (!Utils.isEmpty(record.getPhoto())) {
				builder.append("##").append(record.getPhoto());
			}

			this.writer.println(builder);
			this.writer.flush();
			this.count += 1;
		} catch (Exception e) {
			this.logger.error(new StringBuilder().append("创建record失败：未找到记录对应的项目").append(e.getMessage()).toString());
			logger.error(e.getMessage());
		}

	}

	private void tryToResetWriter(boolean force) {
		if ((!force) && (this.count < TheApp.getSqlBatch())) {
			return;
		}

		this.count = 0;
		if (this.writer == null) {
			return;
		}
		try {
			this.writer.close();
		} catch (Exception e) {
			this.logger.error(new StringBuilder().append("关闭记录文件失败：").append(e.getMessage()).toString());
		}
		this.writer = null;

		if (this.lockFile == null)
			return;
		try {
			boolean delete = this.lockFile.delete();
			if (!delete) logger.info("" + delete);
		} catch (Exception e) {
			this.logger.error(new StringBuilder().append("删除锁文件失败：").append(e.getMessage()).toString());
			logger.error(e.getMessage());
		}
		this.lockFile = null;
	}

	private List<DeviceRecord> readRecord(File file) throws Exception {
		if (file == null) {
			return null;
		}
		BufferedReader reader = null;
		List<DeviceRecord> record = new ArrayList<DeviceRecord>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String data;
			while ((data = reader.readLine()) != null) {
				String[] array = data.split("##");
				if ((array == null) || (array.length < 5)) {
					this.logger.error(new StringBuilder().append("不完整的考勤数据：").append(data).toString());
					saveTrash(data);
				} else {
					DeviceRecord r = new DeviceRecord();
					Device device = new Device();
					r.setDevice(device);
					try {
						device.setId(Long.valueOf(Long.parseLong(array[0])));
					} catch (Exception e) {
						this.logger.error(new StringBuilder().append("解析设备ID失败：").append(data).toString());
						saveTrash(data);
						continue;
					}

					ProjectMaster project = new ProjectMaster();
					try {
						project.setId(Long.valueOf(Long.parseLong(array[1])));
					} catch (Exception e) {
						this.logger.error(new StringBuilder().append("解析项目ID失败：").append(data).toString());
						saveTrash(data);
						continue;
					}

					device.setProject(project);
					WorkerMaster personel = new WorkerMaster();
					// personel.setId(Long.valueOf(array[2]));
					personel.setIdCardNumber(array[2]);

					// r.setUserId(array[2]);
					try {
						r.setTime(new Timestamp(Long.parseLong(array[3])));
					} catch (Exception e) {
						this.logger.error(new StringBuilder().append("解析考勤时间失败：").append(data).toString());
						saveTrash(data);
						continue;
					}
					personel.setWorkerName(array[4]);
					// r.setUserName(array[4]);
					r.setPerson(personel);

					try {
						r.getDevice().setState(Integer.parseInt(array[5]));
					} catch (Exception e) {
						this.logger.error(new StringBuilder().append("解析考勤设备状态失败：").append(data).toString());
						saveTrash(data);
						continue;
					}

					if (array.length > 6) {
						r.setPhoto(array[6]);
					}
					record.add(r);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (reader != null) {
				reader.close();
				reader = null;
			}
		}
		boolean delete = file.delete();
		if(!delete) {
			logger.info("" + delete);
		}
		return record;
	}

	private void saveTrash(String data) {
		PrintWriter trashWriter = null;
		try {
			File dir = getRootDir();
			trashWriter = new PrintWriter(new FileOutputStream(new File(dir, "trash.txt"), true));
			trashWriter.println(data);
			trashWriter.flush();
			// trashWriter.close();
		} catch (Exception e) {
			this.logger.error(new StringBuilder().append("保存不完整考勤数据失败：").append(e.getMessage()).toString());
			logger.error(e.getMessage());
		} finally {
			if (trashWriter != null) {
				trashWriter.close();
				trashWriter = null;
			}
		}
	}

	private File getRootDir() {
		if (this.rootDir == null) {
			StringBuilder builder = new StringBuilder(TheApp.getDeviceRecordPath()).append(File.separator)
					.append(".record");

			this.rootDir = builder.toString();
		}

		String path = TheApp.getRootPath(this.rootDir);
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	public void cleanRecordLock() {
		File dir = getRootDir();
		String[] files = dir.list();
		if (files == null) {
			return;
		}
		for (String name : files)
			if (name.endsWith(".lock"))
				try {
					boolean delete = new File(dir, name).delete();
					if(!delete)logger.info(""+delete);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
	}
}
