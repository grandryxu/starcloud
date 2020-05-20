/*
package com.xywg.attendance.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.attendance.modular.system.model.WorkerMaster;
import org.apache.ibatis.annotations.Param;

import java.util.List;

*/
/**
 * <p>
 * 工人实名基础信息 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-22
 *//*

public interface WorkerMasterMapper extends BaseMapper<WorkerMaster> {
    */
/**
     * 根据项目查出项目里所有工人
     * @param projectCode
     * @return
     *//*

    List<WorkerMaster> getWorkerListByProjectCode(@Param("projectCode") String projectCode);

    */
/**
     * 获取人脸模版
     * @param idCardNumber
     * @return
     *//*

    String getFaceModel(String idCardNumber);
}
*/
