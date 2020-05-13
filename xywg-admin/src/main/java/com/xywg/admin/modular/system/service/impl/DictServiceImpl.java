package com.xywg.admin.modular.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.common.constant.factory.MutiStrFactory;
import com.xywg.admin.core.common.exception.BizExceptionEnum;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.modular.device.dao.DeviceMapper;
import com.xywg.admin.modular.device.model.Device;
import com.xywg.admin.modular.system.dao.DictMapper;
import com.xywg.admin.modular.system.model.Dict;
import com.xywg.admin.modular.system.service.IDictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

    @Resource
    private DictMapper dictMapper;
    @Resource
    private DeviceMapper deviceMapper;
    @Resource
    private WorkKindServiceImpl workKindServiceImpl;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addDict(String dictName, String dictValues) {
        //判断有没有该字典
        List<Dict> dicts = dictMapper.selectList(new EntityWrapper<Dict>().eq("name", dictName).and().eq("pid", 0));
        if (dicts != null && dicts.size() > 0) {
            throw new XywgException(BizExceptionEnum.DICT_EXISTED);
        }

        //解析dictValues
        List<Map<String, String>> items = MutiStrFactory.parseKeyValue(dictValues);

        //添加字典
        Dict dict = new Dict();
        dict.setName(dictName);
        dict.setNum(0);
        dict.setPid(0);
        this.dictMapper.insert(dict);

        //添加字典条目
        for (Map<String, String> item : items) {
            String num = item.get(MutiStrFactory.MUTI_STR_KEY);
            String name = item.get(MutiStrFactory.MUTI_STR_VALUE);
            Dict itemDict = new Dict();
            itemDict.setPid(dict.getId());
            itemDict.setName(name);
            try {
                itemDict.setNum(Integer.valueOf(num));
            } catch (NumberFormatException e) {
                throw new XywgException(BizExceptionEnum.DICT_MUST_BE_NUMBER);
            }
            this.dictMapper.insert(itemDict);
            
            //同步工种字典到工种表
            if ("工种字典数据".equals(dictName)) {
            	workKindServiceImpl.synWorkKinds(itemDict);
            }
            
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editDict(Integer dictId, String dictName, String dicts) {
        //删除设备管理中的某个num判断其是否使用
        if("设备类型".equals(dictName)){
            //获取要删除的num
            List<Dict> dictChilds = dictMapper.getChildsByPid(dictId);
            //未删除之前的num
            List<Integer> rawChildNums = new ArrayList<Integer>();
            for(int i=0;i<dictChilds.size();i++){
                rawChildNums.add(dictChilds.get(i).getNum());
            }
            String[] newChildNumsArray = dicts.split(";");
            for(int i=0;i<newChildNumsArray.length;i++){
                String singleNewChild = newChildNumsArray[i];
                if(singleNewChild!="" && singleNewChild!=null){
                    System.out.println(singleNewChild.split(":")[0]);
                    int removedIndex = rawChildNums.indexOf(Integer.parseInt(singleNewChild.split(":")[0]));
                    if(removedIndex>=0){
                        rawChildNums.remove(removedIndex);
                    }
                }
            }
            //判断删除的设备类型是否在使用
            if(rawChildNums.size()>0){
                List<Device> deviceList = deviceMapper.getDevicesByDeviceTypeList(rawChildNums);
                if(deviceList.size() >0){
                    throw new XywgException(BizExceptionEnum.DEVICE_CANNOT_DELETE);
                }
            }
        }

        //删除之前的字典
        this.delteDict(dictId);

        //重新添加新的字典
        this.addDict(dictName, dicts);
    }

    @Override
    public void delteDict(Integer dictId) {
        //删除这个字典的子词典
        Wrapper<Dict> dictEntityWrapper = new EntityWrapper<>();
        dictEntityWrapper = dictEntityWrapper.eq("pid", dictId);
        dictMapper.delete(dictEntityWrapper);

        //删除这个词典
        dictMapper.deleteById(dictId);
    }

    @Override
    public List<Dict> selectByCode(String code) {
        return this.baseMapper.selectByCode(code);
    }

    @Override
    public List<Map<String, Object>> list(String conditiion) {
        return this.baseMapper.list(conditiion);
    }

    @Override
    public List<Dict> selectByName(String sex) {
        return this.baseMapper.selectByName(sex);
    }

    @Override
    public Integer selectNumByName(String name1, String name2) {
        return this.baseMapper.selectNumByName(name1,name2);
    }


    @Override
    public List<String> getWorkNameListByWorkSet(String workSets) {

        List<String> result = Arrays.asList(workSets.split(","));
        return baseMapper.getWorkNameListByWorkSet(result);
    }


    @Override
    public List<Dict> getWelFaresApp() {
        return this.selectByName("福利");
    }

}
