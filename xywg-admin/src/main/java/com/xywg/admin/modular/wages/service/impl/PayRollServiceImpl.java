package com.xywg.admin.modular.wages.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.util.ExcelImportUtils;
import com.xywg.admin.modular.system.service.AccountProjectService;
import com.xywg.admin.modular.wages.dao.PayRollDetailMapper;
import com.xywg.admin.modular.wages.dao.PayRollMapper;
import com.xywg.admin.modular.wages.model.PayRoll;
import com.xywg.admin.modular.wages.model.PayRollDetailVo;
import com.xywg.admin.modular.wages.model.PayRollFlow;
import com.xywg.admin.modular.wages.model.dto.PayRollDetailDto;
import com.xywg.admin.modular.wages.model.dto.PayRollDto;
import com.xywg.admin.modular.wages.service.IAccountService;
import com.xywg.admin.modular.wages.service.IPayRollFlowService;
import com.xywg.admin.modular.wages.service.IPayRollService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 工资单 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-01
 */
@Service
public class PayRollServiceImpl extends ServiceImpl<PayRollMapper, PayRoll> implements IPayRollService {
    
    @Resource
    private PayRollDetailMapper payRollDetailMapper;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IPayRollFlowService payRollFlowService;
    @Autowired
    private AccountProjectService accountProjectService;
    @Override
    public boolean insert(PayRoll payRoll) {
        return retBool(this.baseMapper.insert(payRoll));
    }

    @Override
    public List<PayRollDetailVo> selectList(Page<PayRollDetailVo> page, Map<String, Object> map) {
        map.put("projectCodes",accountProjectService.getProjectCodes());
        map.put("deptId",ShiroKit.getUser().getDeptId());
        return baseMapper.list(page , map ,accountProjectService.getSwitchType());
    }

    @Override
    public List<PayRollDetailVo> selectDetailByPayRollCode(Page<PayRollDetailVo> page, Map<String, Object> map) {
        return baseMapper.selectDetailByPayRollCode(page , map);
    }

    @Override
    public Integer deleteIds(String ids) {
        return baseMapper.deleteIds(ids);
    }

    @Override
    public Integer submit(String ids) {
        return baseMapper.submit(ids);
    }

    @Override
    public Integer grantPayRoll(String ids) {
        String[] idArray = ids.split(",");
        for(String id: idArray){
            baseMapper.grantPayRoll(Long.valueOf(id));
            //生成流水
            List<PayRollFlow> payRollFlowList =  baseMapper.getPayRollById(Long.valueOf(id));
            for(PayRollFlow payRollFlow:payRollFlowList){
                payRollFlow.setTime(new Date());
                payRollFlow.setType(1);
                payRollFlow.setCreateDate(new Date());
                payRollFlow.setCreateUser(ShiroKit.getUser().getName());
                payRollFlowService.insert(payRollFlow);
            }
        }
        return idArray.length;
    }


    @Override
    @Transactional
    public Integer grantPayRollApp(PayRollDto payRollDto) {
            //工资单发放
           Integer number =  baseMapper.grantPayRoll(payRollDto.getId());
           //更新详情 的付款人
            baseMapper.updateDetailsPayPerson(payRollDto);
            //生成流水
            List<PayRollFlow> payRollFlowList =  baseMapper.getPayRollById(payRollDto.getId());
            for(PayRollFlow payRollFlow:payRollFlowList){
                payRollFlow.setTime(new Date());
                payRollFlow.setType(1);
                payRollFlow.setCreateUser(payRollDto.getAccountName());
                payRollFlow.setCreateDate(new Date());
                payRollFlowService.insert(payRollFlow);
            }
        return number;
    }

    @Override
    @Transactional
    public Integer deleteByIdAndUpdateAccount(String ids) {
        accountService.updateSalaryIdWhenDeletePayRoll(ids);
        return this.deleteIds(ids);
    }

    @Override
    public List<PayRollDetailVo> selectDetailByPayRollCodeNoPage(Map<String, Object> map) {
        return this.baseMapper.selectDetailByPayRollCodeNoPage(map);
    }

    @Override
    public List<Map<String,Object>> getPayRollListByProjectCodeAndType(Integer id,String projectCode , String teamSysNo, Integer type) {
        return baseMapper.getPayRollListByProjectCodeAndType(id , projectCode ,teamSysNo ,type );
    }

    @Override
    public List<Map<String,Object>> v116GetPayRollListByProjectCodeAndType(Integer id,String projectCode , String teamSysNo, Integer type,Integer pageNo , Integer pageSize) {
        return baseMapper.v116GetPayRollListByProjectCodeAndType(id , projectCode ,teamSysNo ,type ,(pageNo-1)*pageSize,pageSize);
    }

    @Override
    @Transactional
    public Boolean savePayRollAndDetail(PayRollDto payRollDto) {
    	if (accountService.getSalaryIdSum(payRollDto.getAccountId()) > 0) {
    		throw new XywgException(600, "存在已结算记工单！");
    	} else {
    		PayRoll payRoll = new PayRoll();
            BeanUtils.copyProperties(payRollDto , payRoll);
            //新增工资单
            payRoll.setPayRollCode("GZD"+String.valueOf(System.currentTimeMillis()));
            this.insert(payRoll);

            //新增工资单详情
            payRollDetailMapper.addDetailList(payRollDto.getDetailList(), payRoll.getId(),"caiwei");
            //更新计工单的salaryId字段
            if(payRollDto.getAccountId()!=null){
                accountService.updateSalaryId(payRollDto.getAccountId(),payRoll.getId());
            }
            return true;
    	}
    }

    @Override
    public Integer examineSalary(PayRollDto payRollDto) {
        return baseMapper.examineSalary(payRollDto);
    }

    @Override
    public List<PayRollDetailDto> getPayRollByOrganizationCode(String organizationCode) {
        return baseMapper.getPayRollByOrganizationCode(organizationCode);
    }

    @Override
    public List<PayRollDetailDto> v116GetPayRollByOrganizationCode(String organizationCode,Integer pageNo , Integer pageSize) {
        return baseMapper.v116GetPayRollByOrganizationCode(organizationCode,(pageNo-1)*pageSize,pageSize);
    }

    @Override
    public PayRoll selectByPayRollCode(String payRollCode) {
        return baseMapper.selectByPayRollCode(payRollCode);
    }


}
