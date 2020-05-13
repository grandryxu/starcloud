package com.xywg.admin.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.system.model.Banner;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * @author hjy
 */
public interface BannerMapper extends BaseMapper<Banner> {

    /**
     * 获取Banner
     *
     * @param chooseType 轮播图类型 1：轮播图 2.名言警句
     * @param type       类型 1.系统端 2.工人端
     */
    List<Banner> getBanner(@Param("chooseType") Integer chooseType, @Param("type") Integer type);

    /**
     * 获取bannaer列表
     * @param page
     * @param map
     * @return
     * @author yuanyang
     */
    List<Map<String,Object>> selectBanners(@Param("page") Page<Banner> page, @Param("map") Map<String, Object> map);

    /**
     * 删除banner
     * @param map
     * @author yuanyang
     */
    void deleteByIds( @Param("map") Map<String, Object> map);

    /**
     * 获取轮播图
     * @param map
     * @return
     * @author yuanyang
     */
    List<Banner> getBannersByType( @Param("map")Map<String, Object> map);

    /**
     * 修改排序
     * @param map
     * @param updateUser
     * @return
     * @author yuanyang
     */
    boolean updateOrder( @Param("list")List<Map<String, Integer>> map, @Param("updateUser")String updateUser);

    Map<String, Long> selectIdByName(@Param("name") String name);
}