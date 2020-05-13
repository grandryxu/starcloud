package com.xywg.admin.modular.wages.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.wages.model.AccountDetail;
import com.xywg.admin.modular.wages.model.PayRollDetailVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 计工明细
包工   money*number+/- money =amount   点工  record_day*price +/- money =amount Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-01
 */
public interface AccountDetailMapper extends BaseMapper<AccountDetail> {

    /**
     * 根据计工单ids获取人的技工详情 生成工资
     * @param map
     * @return
     */
    List<PayRollDetailVo> getList(@Param("map") Map map);

    List<PayRollDetailVo> list(@Param("page") Page page , @Param("map") Map map);

    int insertDetailList(@Param("list") List<Map> list,@Param("id") Long id);

    List<Map<String,Object>> getAccountDetailByAccountId(@Param("map") Map<String,Object> map,@Param("page") Page page);

    List<Map<String,Object>> getAccountDetailByAccountIds(@Param("ids") String accountIds);

    List<Map<String,Object>> v116GetAccountDetailByAccountIds(@Param("ids") String accountIds
            ,@Param("index") Integer index,@Param("pageSize") Integer pageSize);

    int accountSign(AccountDetail accountDetails);

    int updateDetail(AccountDetail accountDetail);

    int getAccountIdByDetailId(@Param("id") Long id);

    int updateTotalAmount(@Param("id") Long id);

    /**
     * 根据计工单ids获取详情
     * @param map
     * @return
     */
    List<PayRollDetailVo> getListNoPages(@Param("map") Map map);

    /**
     * 根据计工单id获取计工单详情
     * @param map
     * @return
     */
    List<Map<String,Object>> getAccountDetailByAccountIdNoPage(@Param("map") Map<String, Object> map);

    Map<String, Long> selectIdByAccountIdAndIdCard(@Param("accountId") Long accountId, @Param("idCardNumber") String idCardNumber);
}
