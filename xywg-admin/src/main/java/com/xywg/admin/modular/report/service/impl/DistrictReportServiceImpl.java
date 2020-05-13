package com.xywg.admin.modular.report.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.shiro.ShiroUser;
import com.xywg.admin.modular.report.dao.DistrictReportMapper;
import com.xywg.admin.modular.report.model.DistrictReport;
import com.xywg.admin.modular.report.service.DistrictReportService;
import com.xywg.admin.modular.system.service.IDeptService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 区域统计项目service实现类
 *
 * @author jingyun_hu
 * @date 2018/6/19
 */
@Service
public class DistrictReportServiceImpl extends ServiceImpl<DistrictReportMapper, DistrictReport> implements DistrictReportService {
    @Autowired
    private DistrictReportMapper districtReportMapper;
    @Autowired
    private IDeptService deptService;

    /**
     * 根据部门id 按区域分组获取项目统计数据
     *
     * @param deptId 部门id
     * @return
     */
    @Override
    public List<DistrictReport> getReportListByDistrictAndDeptId(Integer deptId) {
        if (deptId == null) {
            deptId = ShiroKit.getUser().getDeptId();
        }
        //获取公司以及子公司社会信用代码集合
        List<String> list = deptService.getOrganizationCodeByDeptId(deptId);
        return districtReportMapper.getReportListByOrganizationCodeList(list);
    }
}
