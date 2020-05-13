package com.xywg.admin.modular.report.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.report.dao.WorkKindReportMapper;
import com.xywg.admin.modular.report.model.WorkKindReport;
import com.xywg.admin.modular.report.service.WorkKindReportService;
import com.xywg.admin.modular.system.service.IDeptService;
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
public class WorkKindReportServiceImpl extends ServiceImpl<WorkKindReportMapper, WorkKindReport> implements WorkKindReportService {
    @Autowired
    private IDeptService deptService;
    @Autowired
    private WorkKindReportMapper workKindReportMapper;

    @Override
    public List<WorkKindReport> getWorkKindList(Integer deptId) {
        if (deptId == null) {
            deptId = ShiroKit.getUser().getDeptId();
        }
        //获取公司以及子公司社会信用代码集合
        List<String> list = deptService.getOrganizationCodeByDeptId(deptId);
        return workKindReportMapper.getWorkKindList(list);
    }
}
