package com.xywg.admin.modular.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.system.model.Banner;

import java.util.List;
import java.util.Map;

/**
 * @author hjy
 * @date 2018/5/29
 * 鸡汤类接口
 */
public interface BannerService extends IService<Banner> {

    /**
     * 获取Banner
     * @param chooseType 轮播图类型 1：轮播图 2.名言警句
     * @param type  类型 1.系统端(企业端) 2.工人端
     */
    List<Banner>  getBanner(Integer chooseType,Integer type);

    /**
     * 获取轮播图列表
     * @param page
     * @param map
     * @return
     * @author yuanyang
     */
    List<Map<String,Object>> selectBanners(Page<Banner> page, Map<String, Object> map);

    /**
     * 删除banner
     * @param map
     * @author yuanyang
     */
    void deleteByIds(Map<String, Object> map);

    /**
     * 获取轮播图
     * @param map
     * @return
     * @author yuanyang
     */
    List<Banner> getBannersByType(Map<String, Object> map);

    /**
     * 轮播图排序
     * @param ids
     * @author
     */
    boolean updateOrder(int[] ids);

    void addBanner(List<Object> addList);
}
