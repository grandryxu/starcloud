package com.xywg.admin.modular.report.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.report.model.WorkerReport;
import com.xywg.admin.modular.worker.model.WorkerMasterVO;

/**
 * <p>
 * 人员统计 服务类
 * </p>
 *
 * @author shily
 * @since 2018-06-20
 */
public interface IWorkerReportService extends IService<WorkerMasterVO> {

	/**
     * 获取人员统计列表
     * @param map
     * @author shily
     * @return
     */
    List<Map<String, Object>> selectWorkerReport(Page<WorkerMasterVO> page,Map<String, Object> map);

    /**
     * 获取人员统计列表 无分页
     * @param map
     * @author shily
     * @return
     */
    List<WorkerReport> getWorkerReport(Map<String, Object> map);
}
