package com.xywg.admin.modular.device.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.base.tips.ErrorTip;
import com.xywg.admin.core.base.tips.SuccessTip;
import com.xywg.admin.core.base.tips.Tip;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.device.dao.DeviceQrMapper;
import com.xywg.admin.modular.device.model.DeviceCommand;
import com.xywg.admin.modular.device.model.DeviceQr;
import com.xywg.admin.modular.device.service.IDeviceQrService;
import com.xywg.admin.modular.system.service.AccountProjectService;

/**
 * <p>
 * 二维码设备维护 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-30
 */
@Service
public class DeviceQrServiceImpl extends ServiceImpl<DeviceQrMapper, DeviceQr> implements IDeviceQrService {
	private static Logger log = LoggerFactory.getLogger(DeviceQrServiceImpl.class);
    @Resource
    private DeviceQrMapper deviceQrMapper;
    @Autowired
    private AccountProjectService accountProjectService;

    @Override
    public List<Map<String, Object>> list(Map<String, Object> map, Page page) {
        map.put("projectCodes", accountProjectService.getProjectCodes());
        map.put("deptId", ShiroKit.getUser().getDeptId());
        return deviceQrMapper.list(map, page);
    }

    @Override
    public int saveDeviceQr(DeviceQr deviceQr) {
        if (deviceQrMapper.checkDeviceSn(deviceQr.getSn()).size() == 0) {
            deviceQrMapper.saveDeviceQr(deviceQr);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public int updateDeviceQr(DeviceQr deviceQr) {
        //先判重
        List<DeviceQr> list = deviceQrMapper.checkBySn(deviceQr);
        if (list.size() > 0) {
            return 0;
        } else {
            deviceQrMapper.updateDeviceQr(deviceQr);
            return 1;
        }

    }

    @Override
    public void deleteByIds(Map<String, Object> map) {
        map.put("updateUser", ShiroKit.getUser().getName());
        deviceQrMapper.deleteByIds(map);
    }

    /**
	 * 
	 * @description 终端代码升级
	 * @author chupp
	 * @date 2018年8月6日
	 * @param map
	 * @return
	 *
	 */
	@Override
	public Tip upCode(Map<String,Object> map) {
		Socket socket = null;
		DataOutputStream dos = null;
		try {
			List<String> snList = new ArrayList<>();
			String[] split = ((String) map.get("sns")).split(",");
			for(String sn : split) {
				if(deviceQrMapper.getUpStatus(sn) == 0) snList.add(sn);
			}
			if(snList.size() > 0) {
				byte[] bytes = this.getSendMessageDetail((String) map.get("version"),snList);
				socket = new Socket("192.168.1.158", 9090);
				dos = new DataOutputStream(socket.getOutputStream()); 
			    dos.write(bytes, 0, bytes.length); 
			}
			return new SuccessTip();
		}catch(Exception e) {
			log.error(e.getMessage());
			return new ErrorTip(660, "升级失败!");
		}finally {
			if(dos != null) {
				try {
					dos.close();
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}
			if(socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}
		}
	}
	
	/**
	 * 
	 * @description 获取报文byte[]
	 * @author chupp
	 * @date 2018年8月7日
	 * @param version
	 * @param snList
	 * @return
	 * @throws IOException
	 *
	 */
	public byte[] getSendMessageDetail(String version,List<String> snList) throws IOException {
		String urlPath = "http://labor.jsxywg.cn/labor" + deviceQrMapper.getVersionPath(version,1);
		byte[] versionByteArray = version.getBytes("UTF-8");
		byte[] snListByteArray = snList.toString().getBytes("UTF-8");
		byte[] fileByteArray = this.getByteArrayFromHttpFile(urlPath);
		int versionLength = versionByteArray.length;
		int snListLength = snListByteArray.length;
		int fileLength = fileByteArray.length;
		int tolalLength = 19 + versionLength + snListLength + fileLength;
		byte[] byteArrayPrefix = {(byte)0xFF,(byte)0xFF,(byte)((tolalLength >> 16) & 0xFF),(byte)0x01,(byte)0x00,(byte)0xFF,
				(byte)0x01,(byte) ((tolalLength >> 8) & 0xFF),(byte) (tolalLength & 0xFF),(byte) (((fileLength + 3) >> 16) & 0xFF)};
		byte[] bytes = new byte[tolalLength];
		System.arraycopy(byteArrayPrefix, 0, bytes, 0, 10);
		System.arraycopy(new byte[] {(byte)0,(byte)(versionLength + 3),(byte)0x01}, 0, bytes, 10, 3);
		System.arraycopy(versionByteArray, 0, bytes, 13, versionLength);
		System.arraycopy(new byte[] {(byte) (((snListLength + 3) >> 8) & 0xFF),(byte) ((snListLength + 3) & 0xFF),(byte)0x02}, 0, bytes, versionLength + 13, 3);
		System.arraycopy(snListByteArray, 0, bytes, versionLength + 16, snListLength);
		System.arraycopy(new byte[] {(byte) (((fileLength + 3) >> 8) & 0xFF),(byte) ((fileLength + 3) & 0xFF),(byte)0x03}, 0, bytes, versionLength + snListLength + 16, 3);
		System.arraycopy(fileByteArray, 0, bytes, versionLength + 19 + snListLength, fileLength);
		return bytes;
	}
	
	@Override
	public Tip remoteRestart(Map<String, Object> map) {
		Socket socket = null;
		DataOutputStream dos = null;
		try {
			String[] split = ((String) map.get("sns")).split(",");
			List<String> snList = new ArrayList<>(Arrays.asList(split));
			if(snList.size() > 0) {
				byte[] bytes = this.sendRemoteRestart(snList);
				socket = new Socket("192.168.1.158", 9090);
				dos = new DataOutputStream(socket.getOutputStream()); 
			    dos.write(bytes, 0, bytes.length); 
			}
			return new SuccessTip();
		}catch(Exception e) {
			log.error(e.getMessage());
			return new ErrorTip(660, "重启失败!");
		}finally {
			if(dos != null) {
				try {
					dos.close();
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}
			if(socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}
		}
	}
	
	private byte[] sendRemoteRestart(List<String> snList) throws UnsupportedEncodingException {
		byte[] snListByteArray = snList.toString().getBytes("UTF-8");
		int snListLength = snListByteArray.length;
		int tolalLength = 13 + snListLength;
		byte[] byteArrayPrefix = {(byte)0xFF,(byte)0xFF,(byte)0xFE,(byte)0x01,(byte)0x00,(byte)0xFE,
				(byte)0x01,(byte) ((tolalLength >> 8) & 0xFF),(byte) (tolalLength & 0xFF),(byte)0x00};
		byte[] bytes = new byte[tolalLength];
		System.arraycopy(byteArrayPrefix, 0, bytes, 0, 10);
		System.arraycopy(new byte[] {(byte) (((snListLength + 3) >> 8) & 0xFF),(byte) ((snListLength + 3) & 0xFF),(byte)0x01}, 0, bytes, 10, 3);
		System.arraycopy(snListByteArray, 0, bytes, 13, snListLength);
		return bytes;
	}

	/**
	 * 
	 * @description 获取文件byte[]
	 * @author chupp
	 * @date 2018年8月7日
	 * @param urlPath
	 * @return
	 * @throws IOException
	 *
	 */
	public byte[] getByteArrayFromHttpFile(String urlPath) throws IOException {
		URL url = new URL(urlPath);    
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();    
	    conn.setRequestMethod("GET");    
	    conn.setConnectTimeout(5 * 1000);    
	    InputStream inStream = conn.getInputStream();
	    ByteArrayOutputStream outStream = new ByteArrayOutputStream();    
	    byte[] buffer = new byte[1024];    
	    int len = 0;    
	    while((len = inStream.read(buffer)) != -1 ){    
	        outStream.write(buffer, 0, len);    
	    }    
	    inStream.close();    
	    return outStream.toByteArray();
	}

    @Override
    @Transactional(readOnly = false)
    public void upload(Map<String, String> map) {
	    String name=deviceQrMapper.selectByName(map.get("name"));
        if(name!=null||"".equals(name)){
            deviceQrMapper.deleteByName(name);
        }
       deviceQrMapper.upload(map);
    }

    @Override
    public List<Map<String,String>> selectVersions() {
        return deviceQrMapper.selectVersions();
    }

	@Override
	public DeviceQr getOneById(Long id) {
		return deviceQrMapper.getOneById(id);
	}

	@Override
	public void addDeviceQr(List<Object> addList) {
		for (Object o : addList) {
			DeviceQr deviceQr = new DeviceQr();
			stringToDateException();
			try {
				BeanUtils.copyProperties(deviceQr, o);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			Map<String,Object>deviceQrMap=baseMapper.selectIdByProjectCodeAndSn(deviceQr.getProjectCode(),deviceQr.getSn());
			if (deviceQrMap.get("id")==null){
				deviceQr.setId(null);
				insert(deviceQr);
			}

		}
	}


	//解决 BeanUtils.copyProperties()的string转date异常
	private void stringToDateException() {
		ConvertUtils.register(new Converter() {

			@Override
			public Object convert(Class type, Object value) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
				    if ("".equals(value.toString())){
                        return null;
                    }
					return simpleDateFormat.parse(value.toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return null;
			}
		}, java.util.Date.class);
	}
}
