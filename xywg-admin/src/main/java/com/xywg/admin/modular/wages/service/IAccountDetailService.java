package com.xywg.admin.modular.wages.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.wages.model.AccountDetail;
import com.xywg.admin.modular.wages.model.AccountVo;
import com.xywg.admin.modular.wages.model.PayRollDetailVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 计工明细
包工   money*number+/- money =amount   点工  record_day*price +/- money =amount 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-01
 */
public interface IAccountDetailService extends IService<AccountDetail> {

    /**
     * 根据计工单ids获取计工单详情
     * @param map
     * @return
     */
    List<PayRollDetailVo> getList(Map map);

    /**
     * 根据计工单ids获取计工单详情
     * @param page
     * @param map
     * @return
     */
    List<PayRollDetailVo> selectList(Page<PayRollDetailVo> page, Map map);

    int addAccountAndAccountDetail(AccountVo accountVo);

    List<Map<String,Object>> getAccountDetailByAccountId(Map<String,Object> map,Page page);

    List<Map<String,Object>> getAccountDetailByAccountIds(String accountIds);

    List<Map<String,Object>> v116GetAccountDetailByAccountIds(String accountIds,Integer pageNo , Integer pageSize);

    int accountSign(AccountDetail accountDetail);

    int updateDetail(AccountDetail accountDetail);

    List<PayRollDetailVo> getListNoPages(Map map);

    List<Map<String,Object>> getAccountDetailByAccountIdNoPage(Map<String, Object> map);

    void addAccountDetail(List<Object> addList);
}
