package com.xywg.admin.modular.longxin.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.modular.longxin.dao.EvaluateMapper;
import com.xywg.admin.modular.longxin.model.LxEvaluate;
import com.xywg.admin.modular.longxin.service.LxEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class LxEvaluateServiceImpl extends ServiceImpl<EvaluateMapper, LxEvaluate> implements LxEvaluateService {


    @Autowired
    private EvaluateMapper evaluateMapper;
    @Override
    public List<LxEvaluate> selectEvaluatelist(Page<LxEvaluate> page, Map<String, Object> map) {
        List<LxEvaluate>lxEvaluateList=evaluateMapper.selectEvaluatelist(page,map);
        return lxEvaluateList;
    }

    @Override
    @Transactional
    public void selectIdDelete(Long id) {
        evaluateMapper.selectIdDelete(id);
    }

    @Override
    public LxEvaluate selectId(long parseLong) {

        return evaluateMapper.selectId(parseLong);
    }

    @Override
    public String selectStar(Integer score) {
        String star;
        if (score<60){
            star="C";
        }else{
            star= evaluateMapper.selectStar(score);
        }
        return star;
    }


    @Override
    public Long selectItems(Object items) {
        return this.evaluateMapper.selectItems(items);
    }


    @Override
    public void updateScore(Object projectCode, Object organizationCode, Object score) {
        this.evaluateMapper.updateScore(projectCode,organizationCode,score);
    }

    @Override
    public  Long selectItemsOutItem(Object items) {
        List<String> itemsOutItem= this.evaluateMapper.selectItemsOutItem(items);
        Integer num = 0;
        for (String item : itemsOutItem) {
            if (items.equals(item)){
                num=num+1;
            }
        }
        return Long.valueOf(num);
    }

    @Override
    public List<LxEvaluate> getScoreByCompanyAndProject(Object projectCode, Object organizationCode,Page<LxEvaluate>page) {
        return this.evaluateMapper.getScoreByCompanyAndProject(projectCode,organizationCode,page);
    }
}
