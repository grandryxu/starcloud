package com.xywg.admin.modular.report.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.report.model.DistrictReport;

import java.util.List;

/**
 *
 * @author jingyun_hu
 * @date 2018/6/19
 */
public interface DistrictReportMapper extends BaseMapper<DistrictReport> {

    /**
     * 根据公司的社会信用代码按区域分组获取项目统计数据
     * @param list  社会信用代码
     * @return
     */
    List<DistrictReport> getReportListByOrganizationCodeList(List<String> list);

}
