package com.xywg.admin.modular.wages.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.wages.dao.AccountDetailMapper;
import com.xywg.admin.modular.wages.dao.AccountMapper;
import com.xywg.admin.modular.wages.model.Account;
import com.xywg.admin.modular.wages.model.AccountDetail;
import com.xywg.admin.modular.wages.model.AccountVo;
import com.xywg.admin.modular.wages.model.PayRollDetailVo;
import com.xywg.admin.modular.wages.service.IAccountDetailService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 计工明细
包工   money*number+/- money =amount   点工  record_day*price +/- money =amount 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-01
 */
@Service
public class AccountDetailServiceImpl extends ServiceImpl<AccountDetailMapper, AccountDetail> implements IAccountDetailService {
    @Resource
    private AccountMapper accountMapper;

    @Override
    public List<PayRollDetailVo> getList(Map map) {
        return baseMapper.getList(map);
    }

    @Override
    public List<PayRollDetailVo> selectList(Page<PayRollDetailVo> page, Map map) {
        return baseMapper.list(page , map);
    }

    @Override
    @Transactional(readOnly = false)
    public int addAccountAndAccountDetail(AccountVo accountVo) {
        //插计工单表
        Account account=new Account(accountVo.getTeamSysNo(),accountVo.getProjectCode(),accountVo.getClosingTime(),accountVo.getTotalAmount());
        accountMapper.insertAccount(account);
        //插计工单详情表
        List<Map> list=accountVo.getAccountDetailList();
        baseMapper.insertDetailList(list,account.getId());
        //都插完之后把主表的类型和这个计工单的总金额赋值 以及总人数
        accountMapper.updateTypeAndAmount(account.getId());
        return 0;
    }

    @Override
    public List<Map<String, Object>> getAccountDetailByAccountId(Map<String, Object> map,Page page) {
        return baseMapper.getAccountDetailByAccountId(map,page);
    }

    @Override
    public List<Map<String,Object>> getAccountDetailByAccountIds(String accountIds) {
        return baseMapper.getAccountDetailByAccountIds(accountIds);
    }

    @Override
    public List<Map<String,Object>> v116GetAccountDetailByAccountIds(String accountIds,Integer pageNo , Integer pageSize) {
        return baseMapper.v116GetAccountDetailByAccountIds(accountIds,(pageNo-1)*pageSize,pageSize);
    }

    @Override
    public int accountSign(AccountDetail accountDetail) {
        return baseMapper.accountSign(accountDetail);
    }

    @Override
    @Transactional(readOnly = false)
    public int updateDetail(AccountDetail accountDetail) {
        int id=baseMapper.getAccountIdByDetailId(accountDetail.getId());
         baseMapper.updateDetail(accountDetail);
         baseMapper.updateTotalAmount((long) id);
         return 1;
    }

    @Override
    public List<PayRollDetailVo> getListNoPages(Map map) {
        return this.baseMapper.getListNoPages(map);
    }

    @Override
    public List<Map<String, Object>> getAccountDetailByAccountIdNoPage(Map<String, Object> map) {
        return this.baseMapper.getAccountDetailByAccountIdNoPage(map);
    }


    @Override
    public void addAccountDetail(List<Object> addList) {
        for (Object o : addList) {
            //AccountDetail accountDetail = new AccountDetail();

            stringToDateException();
            AccountDetail accountDetail = JSON.parseObject(o.toString(), AccountDetail.class);
       /*     try {
                BeanUtils.copyProperties(accountDetail, o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }*/
            Map<String,Long> accountIdAndIdCard=baseMapper.selectIdByAccountIdAndIdCard(accountDetail.getAccountId(),accountDetail.getIdCardNumber());
            if (accountIdAndIdCard.get("num")==0){
                accountDetail.setId(null);
                insert(accountDetail);
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
                    if("".equals(value.toString())){
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
