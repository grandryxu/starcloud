package com.xywg.admin.modular.wages.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.system.model.Dict;
import com.xywg.admin.modular.system.service.AccountProjectService;
import com.xywg.admin.modular.wages.dao.AccountMapper;
import com.xywg.admin.modular.wages.model.Account;
import com.xywg.admin.modular.wages.service.IAccountService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 计工单表 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-01
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

    @Autowired
    private AccountProjectService accountProjectService;
    @Resource AccountMapper accountMapper;

    @Override
    public List<Map<String,Object>> getAccountListByTeamNo(Map<String, Object> map) {
        return accountMapper.getAccountListByTeamNo(map);
    }

    @Override
    public Integer updateSalaryId(String accountIds, Long id) {
        return accountMapper.updateSalaryId(accountIds , id);
    }
    
    @Override
    public Long getSalaryIdSum(String accountIds) {
        return accountMapper.getSalaryIdSum(accountIds);
    }

    @Override
    public List<Map<String,Object>> getList(Map<String, Object> map,Page page) {
        map.put("deptId", ShiroKit.getUser().getDeptId());
        map.put("projectCodes",accountProjectService.getProjectCodes());
        return accountMapper.getList(map,page ,accountProjectService.getSwitchType());
    }

    @Override
    public int del(Integer id) {
        Long salaryId=accountMapper.getById(id).getSalaryId();
        if(salaryId!=null){
            return 0;
        }else{
            accountMapper.del(id);
            return 1;
        }
    }

    @Override
    public Account getById(Integer id) {
        Account account=accountMapper.getById(id);
        return account;
    }

    @Override
    public List<Map<String, Object>> getAccountListByProjectCodeAndIsSign(Integer id,Integer teamSysNo,Integer isSign) {
        return accountMapper.getAccountListByProjectCodeAndIsSign(id,teamSysNo,isSign);
    }

    @Override
    public List<Map<String,Object>> getWorkerMateList(String projectCode, String closeTime,String payMonth) {
        List<Map<String,Object>> list=accountMapper.getWorkerMateList(projectCode,closeTime,payMonth);
        return list;
    }

    @Override
    public List<Map<String,Object>> v116GetWorkerMateList(String projectCode, String closeTime,String payMonth,Integer pageNo , Integer pageSize) {
        List<Map<String,Object>> list=accountMapper.v116GetWorkerMateList(projectCode,closeTime,payMonth,(pageNo-1)*pageSize,pageSize);
        return list;
    }

    @Override
    public List<Dict> getUnit(String name) {
        return accountMapper.getUnit(name);
    }

    @Override
    public List<Map<String, Object>> getAccountSelectList(Integer teamSysNo) {
        return accountMapper.getAccountSelectList(teamSysNo);
    }

    @Override
    public List<Map<String, Object>> v116GetAccountSelectList(Integer teamSysNo,Integer pageNo , Integer pageSize) {
        return accountMapper.v116GetAccountSelectList(teamSysNo,(pageNo-1)*pageSize,pageSize);
    }

    @Override
    public Integer updateSalaryIdWhenDeletePayRoll(String ids) {
        return this.baseMapper.updateSalaryIdWhenDeletePayRoll(ids);
    }

	@Override
	public List<Map<String, Object>> getAccountListByProjectCodeAndIsSignV116(Integer id, Integer teamSysNo,
			Integer isSign, Integer pageNo, Integer pageSize) {
		return accountMapper.getAccountListByProjectCodeAndIsSignV116(id,teamSysNo,isSign,(pageNo-1)*pageSize,pageSize);
	}

    @Override
    public void addAccount(List<Object> addList) {
        for (Object o : addList) {
            Account account = new Account();
            stringToDateException();
            try {
                BeanUtils.copyProperties(account, o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            account.setId(null);
            insert(account);
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
