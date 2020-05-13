package com.xywg.admin.modular.system.service.impl;

import com.xywg.admin.modular.system.model.Dict;
import com.xywg.admin.modular.system.model.WorkKind;
import com.xywg.admin.modular.faceUtils.Constant;
import com.xywg.admin.modular.smz.dao.IfaLaborMapper;
import com.xywg.admin.modular.smz.utils.HttpClientUtils;
import com.xywg.admin.modular.system.dao.WorkKindMapper;
import com.xywg.admin.modular.system.service.IWorkKindService;
import com.xywg.admin.modular.worker.service.impl.WorkerMasterServiceImpl;

import cn.hutool.json.JSONUtil;
import net.sf.json.JSONArray;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <p>
 * 工种表 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-05
 */
@Service
public class WorkKindServiceImpl extends ServiceImpl<WorkKindMapper, WorkKind> implements IWorkKindService {

	private static Properties systemParams = new Properties();
	
    private static final Log LOG = LogFactory.getLog(WorkKindServiceImpl.class);
    
    @Autowired
    private IfaLaborMapper ifaLaborMapper;
	
    @Override
    public List<WorkKind> getWorkKinds() {
        return this.baseMapper.getWorkKinds();
    }

    @Override
    public Integer getNumByName(String name) {
        return this.baseMapper.getNumByName(name);
    }
    
    @Override
    public List<Map<String,Object>> selectWorkKind(Page<WorkKind> page,Map<String, Object> map){
    	return this.baseMapper.selectWorkKind(page, map);
    }
    
    @Override
    public WorkKind selectWorkKindById(Long workKindId) {
    	return this.baseMapper.selectWorkKindById(workKindId);
    }
    /**
     * 加载配置文件
     */
    static {
        try {
            systemParams.load(WorkerMasterServiceImpl.class.getClassLoader().getResourceAsStream("smz.properties"));
        } catch (IOException e) {
            LOG.error("smz.properties" + "配置文件加载失败");
        }
    }
    /**
     * 插入公司工种信息到工种表
     * @param organizationCode
     * @return
     */
    @Override
    public String addWorkKinds(String organizationCode) {
    	//获取工种表里的有效工种集合
    	List<WorkKind> workKindList = this.baseMapper.getWorkKindsNameList();
    	if (workKindList.size() > 0) {
    		for (int i = 0; i < workKindList.size(); i++) {
        		workKindList.get(i).setNum(i);
        		workKindList.get(i).setOrganizationCode(organizationCode);
        	}
        	this.baseMapper.addWorkKind(workKindList);	
    	}
    	return Constant.SUCCESS;
    }
    
    /**
     * 工种字典同步到工种管理表
     * @param dict
     * @return
     */
    @Override
    public String synWorkKinds(Dict dict) {
    	//获取工种表里的企业集合
    	List<WorkKind> list = this.baseMapper.getWorkKindsCompanyList();
    	if (this.baseMapper.deleteWorkKind() > 0) {
    		for (int i = 0; i < list.size(); i++) {
        		WorkKind workKind = list.get(i);
        		workKind.setNum(i);
        		workKind.setName(dict.getName());
        	}
    		this.baseMapper.addWorkKind(list);	
    	}
    	return Constant.SUCCESS;
    }
    

    /**
     * @param key
     * @return
     * @description 获取配置文件具体信息
     * @author chupp
     * @date 2018年4月27日
     */
    protected String getSystemStringParam(String key) {
        return systemParams.getProperty(key);
    }

	@Override
	public boolean getWorkKindSendSMZ(Map<String, String> m ) {
		List<Map<String, Object>> list = baseMapper.getWorkKindSendSMZ();
        Map<String, Object> map = new HashMap<String, Object>();
        String json = JSONArray.fromObject(list).toString();
        map.put("labrWorkKind", json);
        String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefix") + getSystemStringParam("saveWorkKindLabor"), map, m);
        System.out.println(jsonResult);
        Map<String, String> result = JSONUtil.toBean(jsonResult, Map.class);
        if ("-2".equals(result.get("code"))) {
            return false;
        } else {
            return true;
        }
	}

    @Override
    public void addWorkKind(List<Object> addList) {
        for (Object o : addList) {
            WorkKind workKind = new WorkKind();
            stringToDateException();
            try {
                BeanUtils.copyProperties(workKind, o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            Map<String,Long> workKindMap=baseMapper.selectIdByOrgCodeAndNum(workKind.getOrganizationCode(),workKind.getNum());
            if (workKindMap.get("num")==0){
                workKind.setId(null);
                insert(workKind);
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
