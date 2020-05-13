package com.xywg.admin.modular.device.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.device.dao.DeviceRecordMapper;
import com.xywg.admin.modular.device.model.DeviceRecord;
import com.xywg.admin.modular.device.model.DeviceRecordExceptionData;
import com.xywg.admin.modular.device.dao.DeviceRecordExceptionDataMapper;
import com.xywg.admin.modular.device.service.IDeviceRecordExceptionDataService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xywg.admin.modular.project.dao.ProjectWorkerMapper;
import com.xywg.admin.modular.project.model.ProjectWorker;
import com.xywg.admin.modular.system.model.Banner;
import com.xywg.admin.modular.system.service.AccountProjectService;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.worker.dao.WorkerMasterMapper;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.tools.ant.Project;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 异常考勤记录表 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-12
 */
@Service
public class DeviceRecordExceptionDataServiceImpl extends ServiceImpl<DeviceRecordExceptionDataMapper, DeviceRecordExceptionData> implements IDeviceRecordExceptionDataService {
	@Resource
	private IDeptService deptService;
    @Resource
    private ProjectWorkerMapper projectMapper;
    @Resource
    WorkerMasterMapper workerMasterMapper;
	@Resource
	private DeviceRecordExceptionDataMapper deviceRecordExceptionDataMapper;
	@Resource
	private DeviceRecordMapper deviceRecordMapper;
	@Autowired
	private AccountProjectService accountProjectService;
	@Override
	public void saveDisabledRecord(List<DeviceRecordExceptionData> recordExceptionDataList) {
		deviceRecordExceptionDataMapper.saveDisabledRecord(recordExceptionDataList);
	}

	@Override
	public List<Map<String, Object>> selectDeviceExceptions(Page<Banner> page, Map<String, Object> map) {
		map.put("depts", deptService.getUserDeptAndSubdivisionOrganizationCode());
		//项目切换获取ProejctCode projectName
		Object toggleProjectCode = ShiroKit.getSession().getAttribute("toggleProjectCode");
		if( toggleProjectCode!=null && !"".equals(toggleProjectCode.toString())  ){
			//项目切换的projectCode
			map.put("projectCode",toggleProjectCode.toString());
		}
		return this.baseMapper.selectDeviceExceptions(page,map,accountProjectService.getSwitchType());
	}

	@Override
	public Map<String,Object> getExceptionDeviceById(Integer id) {
		Map<String,Object> map= this.baseMapper.getExceptionDeviceById(id);
		map.put("idCardTypeName", ConstantFactory.me().getDictsByName("人员证件类型", (Integer) map.get("idCardType")));
		if (map.get("exceptionType") != null) {
			if (map.get("exceptionType").equals("1")) {
				map.put("exceptionTypeName", "注册人不在项目中");
			} else if (map.get("exceptionType").equals("2")) {
				map.put("exceptionTypeName", "考勤人不在项目中");
			} else if (map.get("exceptionType").equals("3")) {
				map.put("exceptionTypeName", "禁用时考勤数据");
			}
		}
		if (map.get("source") != null) {
			if (map.get("source").equals("1")) {
				map.put("sourceName", "手环");
			} else if (map.get("source").equals("2")) {
				map.put("sourceName", "安全帽");
			} else if (map.get("source").equals("3")) {
				map.put("sourceName", "考勤机");
			} else if (map.get("source").equals("4")) {
				map.put("sourceName", "手机考勤");
			}
		}
		if (map.get("type") != null) {
			map.put("typeName", (Integer) map.get("type") == 1 ? "正常" : "补考勤");
		}
		if (map.get("deviceType") != null) {
			map.put("deviceTypeName", (Integer) map.get("deviceType") == 3 ? "上班" : "下班");
		}
		return map;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void action(Map<String, Object> map) {
		String updateUser= ShiroKit.getUser().getName();
	    String idCardNumber=map.get("idCardNumber").toString();
	    Integer idCardType=Integer.valueOf(map.get("idCardType").toString());
        String exceptionIdCardNumber=map.get("exceptionIdCardNumber").toString();
        Integer exceptionIdCardType=Integer.valueOf(map.get("exceptionIdCardType").toString());
        String projectCode=map.get("projectCode").toString();
        List<Map<String, Object>> workerMaster=workerMasterMapper.searchWorker(idCardNumber,idCardType);
        if(workerMaster==null||workerMaster.size()==0){
            throw new XywgException(600,"现有人员不存在");
        }

        List<ProjectWorker> projectWorkers=projectMapper.isInProject(projectCode,idCardType,idCardNumber);
        if(projectWorkers==null||projectWorkers.size()==0){
            throw new XywgException(600,"现有人员不在此项目中");
        }

		List<DeviceRecordExceptionData> exceptionDatas=this.baseMapper.selectByIdCard(idCardType,idCardNumber,exceptionIdCardType,exceptionIdCardNumber);
		if(exceptionDatas==null||exceptionDatas.size()==0){
			throw new XywgException(600,"异常考勤不存在");
		}
		List<DeviceRecord> deviceRecords=new ArrayList<DeviceRecord>();
		for(int i=0;i<exceptionDatas.size();i++){
			DeviceRecord deviceRecord=new DeviceRecord();
			BeanUtils.copyProperties(exceptionDatas.get(i),deviceRecord);
			deviceRecord.setCreateUser(exceptionDatas.get(i).getUpdateUser());
			deviceRecord.setTeamSysNo(projectWorkers.get(0).getTeamSysNo());
			deviceRecord.setIsValid(1);
			deviceRecords.add(deviceRecord);
		}
		//将异常表的数据复制到考勤表
		for(int i=0;i<deviceRecords.size();i++){
			deviceRecordMapper.insertDeviceRecord(deviceRecords.get(i));
		}

		this.baseMapper.deleteExceptionDevices(exceptionIdCardType,exceptionIdCardNumber,updateUser);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void changeStatus(Integer id) {
	    String updateUser= ShiroKit.getUser().getName();

		DeviceRecordExceptionData deviceRecordExceptionData=this.baseMapper.selectById(id);
		deviceRecordExceptionData.setCreateDate(null);
		deviceRecordExceptionData.setCreateUser(null);
		DeviceRecord deviceRecord=new DeviceRecord();
		BeanUtils.copyProperties(deviceRecordExceptionData,deviceRecord);
		deviceRecord.setCreateUser(deviceRecordExceptionData.getUpdateUser());
		if(deviceRecordMapper.insertDeviceRecord(deviceRecord)>0){//启用该条考勤记录
			this.baseMapper.deleteExceptionDeviceById(id,updateUser);
		}

	}

    @Override
    public void deleteByIds(Map<String, Object> map) {
        map.put("updateUser", ShiroKit.getUser().getName());
        this.baseMapper.deleteByIds(map);
    }

	@Override
	public void addDeviceRecordExceptionData(List<Object> addList) {
		for (Object o : addList) {
			DeviceRecordExceptionData deviceRecordExceptionData = new DeviceRecordExceptionData();
			stringToDateException();
			try {
				org.apache.commons.beanutils.BeanUtils.copyProperties(deviceRecordExceptionData, o);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			deviceRecordExceptionData.setId(null);
			if ("".equals(deviceRecordExceptionData.getExceptionType())){
				deviceRecordExceptionData.setExceptionType(null);
			}
			insert(deviceRecordExceptionData);
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
