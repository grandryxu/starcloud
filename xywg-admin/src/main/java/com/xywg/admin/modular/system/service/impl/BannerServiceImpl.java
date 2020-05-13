package com.xywg.admin.modular.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.system.dao.BannerMapper;
import com.xywg.admin.modular.system.model.Banner;
import com.xywg.admin.modular.system.service.BannerService;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.worker.dao.WorkerMasterMapper;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import io.swagger.models.auth.In;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hjy
 * @date 2018/5/29
 * banner  Service
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {
    @Autowired
    private BannerMapper bannerMapper;
    @Resource
    private IDeptService deptService;

    /**
     * 获取Banner
     *
     * @param chooseType 轮播图类型 1：轮播图 2.名言警句
     * @param type       类型 1.系统端 2.工人端
     */
    @Override
    public List<Banner> getBanner(Integer chooseType, Integer type) {
        return bannerMapper.getBanner(chooseType, type);
    }

    @Override
    public List<Map<String, Object>> selectBanners(Page<Banner> page, Map<String, Object> map) {
        map.put("depts", deptService.getUserDeptAndSubdivisionOrganizationCode());
        return this.bannerMapper.selectBanners(page, map);
    }

    @Override
    public void deleteByIds(Map<String, Object> map) {
        map.put("updateUser", ShiroKit.getUser().getName());
        this.bannerMapper.deleteByIds(map);
    }

    @Override
    public List<Banner> getBannersByType(Map<String, Object> map) {
        return bannerMapper.getBannersByType(map);
    }

    @Override
    public boolean updateOrder(int[] ids) {
        List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
        for (int i = 0; i < ids.length; i++) {
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("order", i);
            map.put("id", ids[i]);
            list.add(map);
        }
        String updateUser = ShiroKit.getUser().getName();
        return bannerMapper.updateOrder(list, updateUser);
    }

    @Override
    public void addBanner(List<Object> addList) {
        for (Object o : addList) {
            Banner banner = new Banner();
            stringToDateException();
            try {
                org.apache.commons.beanutils.BeanUtils.copyProperties(banner, o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            Map<String, Long> bannerId = bannerMapper.selectIdByName(banner.getName());
            if (bannerId.get("num") == 0) {
                banner.setId(null);
                insert(banner);
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
