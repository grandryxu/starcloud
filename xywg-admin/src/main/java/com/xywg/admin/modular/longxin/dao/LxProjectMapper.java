package com.xywg.admin.modular.longxin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.project.model.ProjectAddress;
import com.xywg.admin.modular.project.model.ProjectMaster;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LxProjectMapper  extends BaseMapper<ProjectMaster> {


    List<Map<String, Object>> list(@Param("map") Map<String, Object> map, @Param("page") Page page);

    void updateStatus(@Param("projectMasterId") Long projectMasterId);

    void deleteByIds(@Param("projectMasterId") Long projectMasterId);


    ProjectMaster selectById2(String id);

    Map<String,Object> selectById(@Param("id") Long id);

    List<ProjectAddress> selectAddressByProjectCode(@Param("projectCode") String projectCode);



    List<SubContractor> selectCompanyList(@Param("map") Map<String, Object> map, @Param("page") Page<SubContractor> page, @Param("zbId") String zbId);

    void insertInvite(@Param("companyId") String string, @Param("zbId") String zbId);

    List<String> selectCompanyListId(@Param("zbId") String zbId);

    List<SubContractor> selectCompanyListAndYb(@Param("map") Map<String, Object> map, @Param("page") Page<SubContractor> page, @Param("zbId") String zbId);

    List<SubContractor> companyDbList(@Param("map") Map<String, Object> map, @Param("page") Page<SubContractor> page, @Param("projectId") String projectId);

    Long selectNumber(@Param("projectMasterId") Long projectMasterId);

    Long selectProjectManCode(@Param("projectManCode") String projectManCode);

    Object getIdByProjectCode(@Param("projectCode") Object projectCode);

    void insertTender(@Param("companyId") String string, @Param("zbId") String zbId);

    Long inviteBidCount(@Param("companyId") String string, @Param("zbId") String zbId);

    Map<String, Object> selectProjectCodeByProjectId(@Param("projectMasterId") Integer projectMasterId);
}
