package com.xywg.admin.modular.longxin.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.longxin.model.LxEvaluate;

import java.util.List;
import java.util.Map;

public interface LxEvaluateService extends IService<LxEvaluate> {


    List<LxEvaluate> selectEvaluatelist(Page<LxEvaluate> page , Map<String, Object> map);

    void selectIdDelete(Long id);

    LxEvaluate selectId(long parseLong);

    String selectStar(Integer score);

    Long selectItems(Object items);

    void updateScore(Object projectCode, Object organizationCode, Object score);


    Long selectItemsOutItem(Object items);

   List<LxEvaluate> getScoreByCompanyAndProject(Object projectCode, Object organizationCode,Page<LxEvaluate>page);
}
