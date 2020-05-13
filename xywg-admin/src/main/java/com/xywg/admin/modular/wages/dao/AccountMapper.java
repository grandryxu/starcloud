package com.xywg.admin.modular.wages.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.system.model.Dict;
import com.xywg.admin.modular.system.model.SwitchType;
import com.xywg.admin.modular.wages.model.Account;
import com.xywg.admin.modular.wages.model.AccountDetailDto;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 计工单表 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-01
 */
public interface AccountMapper extends BaseMapper<Account> {

    List<Map<String,Object>> getAccountListByTeamNo(@Param("map")Map<String,Object> map);

    /**
     * 新增工资单时 更新 salaryId字段
     * @param accountIds
     * @param id
     * @return
     */
    Integer updateSalaryId(@Param("accountIds") String accountIds, @Param("id") Long id);

    int insertAccount(Account account);

    List<Map<String,Object>> getList(@Param("map") Map<String, Object> map, @Param("page") Page page, @Param("switchType") SwitchType switchType);

    int del(@Param("id") Integer id);

    Account getById(@Param("id") Integer id);

    List<Map<String,Object>> getAccountListByProjectCodeAndIsSign(@Param("id")Integer id,@Param("teamSysNo") Integer teamSysNo,@Param("isSign") Integer isSign);

    List<Map<String,Object>> getWorkerMateList(@Param("projectCode") String projectCode,@Param("closeTime") String closeTime,@Param("payMonth")String payMonth);

    List<Map<String,Object>> v116GetWorkerMateList(@Param("projectCode") String projectCode,@Param("closeTime") String closeTime,@Param("payMonth")String payMonth,@Param("index") Integer index,@Param("pageSize") Integer pageSize);

    List<AccountDetailDto> getWorkerMateListByMonth(@Param("projectCode") String projectCode,@Param("start")Date start,@Param("end") Date end);

    int updateTypeAndAmount(@Param("id") Long id);

    List<Dict> getUnit(@Param("name") String name);

    List<Map<String,Object>> getAccountSelectList(@Param("teamSysNo") Integer teamSysNo);

    List<Map<String,Object>> v116GetAccountSelectList(@Param("teamSysNo") Integer teamSysNo,@Param("index") Integer index,@Param("pageSize") Integer pageSize);

    Integer updateSalaryIdWhenDeletePayRoll(String ids);

	List<Map<String, Object>> getAccountListByProjectCodeAndIsSignV116(@Param("id")Integer id,@Param("teamSysNo") Integer teamSysNo,@Param("isSign") Integer isSign,
			@Param("pn") int pageNo, @Param("ps") Integer pageSize);
	
	Long getSalaryIdSum(@Param("accountIds") String accountIds);
}
