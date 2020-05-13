package com.xywg.admin.modular.wages.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.system.model.Dict;
import com.xywg.admin.modular.wages.model.Account;
import com.xywg.admin.modular.wages.model.AccountDetailDto;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 计工单表 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-01
 */
public interface IAccountService extends IService<Account> {

    List<Map<String,Object>> getAccountListByTeamNo(Map<String,Object> map);

    /**
     * 更新计工单的salaryId字段
     * @param accountIds
     * @param id
     * @return
     */
    Integer updateSalaryId(String accountIds, Long id);

    List<Map<String,Object>> getList(Map<String,Object> map, Page page);

    int del(Integer id);

    Account getById(Integer id);

    List<Map<String,Object>> getAccountListByProjectCodeAndIsSign(Integer id,Integer teamSysNo,Integer isSign);

    List<Map<String,Object>> getWorkerMateList(String projectCode, String closeTime,String payMonth);

    List<Map<String,Object>> v116GetWorkerMateList(String projectCode, String closeTime,String payMonth,Integer pageNo , Integer pageSize);

    List<Dict> getUnit(String name);

    List<Map<String,Object>> getAccountSelectList(Integer teamSysNo);

    List<Map<String,Object>> v116GetAccountSelectList(Integer teamSysNo,Integer pageNo , Integer pageSize);

    /**
     * 删除工资单的时候解锁计工单
     * @param ids
     * @return Integer
     * @author 蔡伟
     */
    Integer updateSalaryIdWhenDeletePayRoll(String ids);

	List<Map<String,Object>> getAccountListByProjectCodeAndIsSignV116(Integer id, Integer teamSysNo, Integer isSign, Integer pageNo,
			Integer pageSize);

	Long getSalaryIdSum(String accountIds);

    void addAccount(List<Object> addList);
}
