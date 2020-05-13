package com.xywg.admin.modular.smz.service.imp;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.smz.dao.IfaLaborMapper;
import com.xywg.admin.modular.smz.model.IfaLabor;
import com.xywg.admin.modular.smz.service.IfaLaborService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/7/26 18:35
 */
@Service
public class IfaLaborServiceImpl implements IfaLaborService{
    @Autowired
    private IfaLaborMapper ifaLaborMapper;
    

    /**
     *@Description: 获取id list
     *@Author xieshuaishuai
     *@Date 2018/7/26 18:44
     */
    @Override
    public List<Long> getIdList(String tableName) {
        return ifaLaborMapper.getIdList(tableName);
    }
    /**
     *@Description:删除
     *@Author xieshuaishuai
     *@Date 2018/7/26 18:44
     */
    @Override
    public void del(String tableName) {
        ifaLaborMapper.del(tableName);
    }

    /**
     *@Description:获取上次记录的数字
     *@Author xieshuaishuai
     *@Date 2018/7/26 18:44
     */
    @Override
    public Long getLastNumber(String tableName) {
        return ifaLaborMapper.getLastNumber(tableName);
    }

    /**
     *@Description:添加
     *@Author xieshuaishuai
     *@Date 2018/7/26 18:45
     */
    @Override
    public boolean insert(IfaLabor ifaLabor) {
        return ifaLaborMapper.insert(ifaLabor);
    }

    @Override
    public int updateNumber(Long id, String tableName) {
        ifaLaborMapper.updateNumber(id,tableName);
        return 0;
    }

	@Override
	public int getLastNumberByTableName(String tableName) {
		return this.ifaLaborMapper.getLastNumberByTableName(tableName);
	}

	@Override
	public List<HashMap<String, Object>> findSynchro(Page<HashMap<String, Object>> page,Map<String,Object> map) {
		 return ifaLaborMapper.findSynchro(page, map);
	}

	@Override
	public void synchro(Map<String, Object> map) {
//		String type = map.get("type")+"";
//		String ids = (String)map.get("ids");
		//String数组转Long数组转List
//		String[] idsArr = ids.split(",");
//		Long[] strArrNum = (Long[]) ConvertUtils.convert(idsArr,Long.class);
//		List<Long> synIds= Arrays.asList(strArrNum);
		//调用登录实名制方法
//		SendDataTolfaTaskHandler send = new SendDataTolfaTaskHandler();
//		Map<String, String> m = send.loginSMZ();
//		if(StringUtils.isBlank(type) || type.equals(Constant.BUSS_SUB_CONTRACTOR)) {
//			subContractorService.getCompanyFromLabor(synIds,m);
//		}else if(type.equals(Constant.BUSS_PROJECT_MASTER)) {
//			projectMasterService.getProjectFromLabor(synIds,m);
//		}else if(type.equals(Constant.BUSS_PROJECT_WORKER)) {
//			workerMasterService.getPersonFromLabor(synIds,m);
//		}
	}
	@Override
	public void del(String tableName, List<Long> id) {
		this.ifaLaborMapper.delbatch(tableName, id);
	}


    @Override
    public boolean batchInsert(String tableName, List<Long> ids) {
        this.ifaLaborMapper.batchInsert(tableName, ids);
        return true;
    }
}
