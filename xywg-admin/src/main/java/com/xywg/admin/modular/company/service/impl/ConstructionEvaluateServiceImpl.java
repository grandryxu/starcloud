package com.xywg.admin.modular.company.service.impl;

import com.xywg.admin.modular.company.model.ConstructionEvaluate;
import com.xywg.admin.modular.company.dao.ConstructionEvaluateMapper;
import com.xywg.admin.modular.company.service.IConstructionEvaluateService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 参建单位评价 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-28
 */
@Service
public class ConstructionEvaluateServiceImpl extends ServiceImpl<ConstructionEvaluateMapper, ConstructionEvaluate> implements IConstructionEvaluateService {

    @Resource
    private ConstructionEvaluateMapper constructionEvaluateMapper;

    @Override
    public List<Map<String, Object>> list(Map<String, Object> map) {
        return constructionEvaluateMapper.list(map);
    }

    @Override
    public void addConstructionEvaluate(List<Object> addList) {
        for (Object o : addList) {
            ConstructionEvaluate constructionEvaluate = new ConstructionEvaluate();
            stringToDateException();
            try {
                BeanUtils.copyProperties(constructionEvaluate, o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            constructionEvaluate.setId(null);
            insert(constructionEvaluate);

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
