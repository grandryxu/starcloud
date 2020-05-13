package com.xywg.admin.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.system.model.Dict;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author wangcw
 * @since 2017-07-11
 */
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 根据编码获取词典列表
     */
    List<Dict> selectByCode(@Param("code") String code);

    /**
     * 查询字典列表
     */
    List<Map<String, Object>> list(@Param("condition") String conditiion);

    /**
     * 根据编码获取词典列表
     */
    List<Dict> selectByName(@Param("name") String name);

    /**
     * 根据父级和子级名称查找num值
     * @param name1
     * @param name2
     * @author yuanyang
     * @return
     */
    Integer selectNumByName(@Param("name1") String name1,@Param("name2") String name2);
    String selectNameByCode(@Param("name1") String name1,@Param("num2") int num2);

    /**
     * 根据父id获取所有子
     * @param dictId
     * @return
     */
    List<Dict> getChildsByPid(Integer dictId);

    /**
     * 根据工种code集合获取工种名称集合
     * @param codes
     * @return
     */
    List<String> getWorkNameListByWorkSet(@Param("codes") List<String> codes);

    /**
     * 查询工种是否存在
     * <p>Title: queryWorkTypeByNum</p>  
     * <p>Description: </p>  
     * @author duanfen  
     * @date 2019年7月23日
     */
	int queryWorkTypeByNum(@Param("num")String num,@Param("name")String name,@Param("type")String type);
}