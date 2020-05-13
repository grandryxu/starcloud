package com.xywg.admin.modular.company.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.modular.company.dao.CompanyEmployeMapper;
import com.xywg.admin.modular.company.model.CompanyEmploye;
import com.xywg.admin.modular.company.service.ICompanyEmployeService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 企业从业人员聘用关系表 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-28
 */
@Service
public class CompanyEmployeServiceImpl extends ServiceImpl<CompanyEmployeMapper, CompanyEmploye> implements ICompanyEmployeService {


    @Override
    public boolean edit(CompanyEmploye companyEmploye) {
        EntityWrapper<CompanyEmploye> ew = new EntityWrapper<>();
        ew.eq("employe_sys_no",companyEmploye.getEmployeSysNo());
        return this.baseMapper.update(companyEmploye,ew)>0;
    }

    @Override
    public boolean deleteByEmployeeIds(Map<String,Object> map) {
            return this.baseMapper.deleteByEmployeeIds(map);

    }

    @Override
    public CompanyEmploye getByIdCard(String idCardNumber, Integer idCardType,String organizationCode) {
        return this.baseMapper.getByIdCard(idCardNumber,idCardType,organizationCode);
    }

    @Override
    public void addcompanyEmploye(List<Object> addList) {
        for (Object o : addList) {
            CompanyEmploye companyEmploye = new CompanyEmploye();
            stringToDateException();
            try {
                BeanUtils.copyProperties(companyEmploye, o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            Map<String,Long>companyEmployeMap=baseMapper.selectIdByOrgCodeAndEmploySysNo(companyEmploye.getOrganizationCode(),companyEmploye.getEmployeSysNo());
            if (companyEmployeMap.get("num")==0){
                companyEmploye.setId(null);
                insert(companyEmploye);
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
