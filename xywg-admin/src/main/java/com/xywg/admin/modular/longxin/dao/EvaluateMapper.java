package com.xywg.admin.modular.longxin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.longxin.model.LxEvaluate;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EvaluateMapper   extends BaseMapper<LxEvaluate> {


    List<LxEvaluate> selectEvaluatelist(@Param("page") Page<LxEvaluate> page, @Param("map") Map<String, Object> map);

    void selectIdDelete(@Param("id") Long id);

    LxEvaluate selectId(@Param("parseLong") long parseLong);

    String selectStar(@Param("score") Integer score);

    Long selectItems(@Param("items") Object items);

    void updateScore(@Param("projectCode") Object projectCode, @Param("organizationCode") Object organizationCode, @Param("score") Object score);

    List<String> selectItemsOutItem(@Param("items") Object items);

    List<LxEvaluate> getScoreByCompanyAndProject(@Param("projectCode") Object projectCode, @Param("organizationCode") Object organizationCode, @Param("page") Page<LxEvaluate> page);
}
