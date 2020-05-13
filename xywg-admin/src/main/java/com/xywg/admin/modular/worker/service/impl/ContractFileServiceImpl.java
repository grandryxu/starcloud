package com.xywg.admin.modular.worker.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.tips.Tip;
import com.xywg.admin.modular.worker.dao.ContractFileMapper;
import com.xywg.admin.modular.worker.model.Contract;
import com.xywg.admin.modular.worker.model.ContractFile;
import com.xywg.admin.modular.worker.service.IContractFileService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 合同附件 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-08
 */
@Service
public class ContractFileServiceImpl extends ServiceImpl<ContractFileMapper, ContractFile> implements IContractFileService {

    @Override
    public List<ContractFile> getList(Page<ContractFile> page, Map<String, Object> map) {
        return null;
    }

    @Override
    public Boolean add(ContractFile contractFile) {
        return null;
    }

    @Override
    public Tip updateSetting(String ids, String value) {
        return null;
    }

    @Override
    public List<Contract> getContractList(Page<Contract> page, Map<String, Object> map) {
        return null;
    }

    @Override
    public Integer addContract(MultipartFile file, ContractFile contractFile) {
        return null;
    }

    @Override
    public Integer batchReview(String ids, Integer status) {
        return null;
    }

    @Override
    public void deleteAccessory(List<String> ids) {

    }

    @Override
    public List<Map<String, Object>> selectWorkerContractFiles(Page<Map<String, Object>> page, Map<String, Object> map) {
        return null;
    }

    @Override
    public void addContractFile(List<Object> addList) {
        for (Object o : addList) {
            ContractFile contractFile = new ContractFile();
            stringToDateException();
            try {
                BeanUtils.copyProperties(contractFile, o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            Map<String,Object> contractFileMap=baseMapper.selectIdByContractCode(contractFile.getContractCode());
            if (contractFileMap.isEmpty()){
                contractFile.setId(null);
                insert(contractFile);
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
                    return simpleDateFormat.parse(value.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }, java.util.Date.class);
    }
}
