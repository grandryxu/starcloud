package com.xywg.admin.modular.longxin.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.project.model.ProjectAddress;
import com.xywg.admin.modular.project.model.ProjectMaster;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Created by tcw on 2019/7/9.
 */
public interface LxProjectService extends IService<ProjectMaster> {


    List<Map<String,Object>> selectList(Map<String ,Object> map , Page page);

    void updateStatus(Long projectMasterId);

    void deleteByIds(Long projectMasterId);


    ProjectMaster selectById2(String id);


    Map<String,Object> selectById(Long id);


    List<ProjectAddress> selectAddressByProjectCode(String projectCode);


    List<SubContractor> selectCompanyList(Map<String, Object> map, Page<SubContractor> page,String zbId);

    void insertInvite(String zbId, List<String> strings);

    List<String> selectCompanyListId(String zbId);


    Object uploadFtp(MultipartFile[] files);

    Object uploadTender(MultipartFile[] file, String id, String fileName, String resume, String proId, String priceTemp,String deadline,String startTime,String flag,String ids, String tenderCode,String tenderType);

    List<SubContractor> selectCompanyListAndYb(Map<String, Object> map, Page<SubContractor> page, String zbId);

    List<SubContractor> companyDbList(Map<String, Object> map, Page<SubContractor> page, String projectId);

    Long selectNumber(Long projectMasterId);

	Object flowAdd(String processId, String projectId, String flag, String ids, String fixFlag, String fixProcessId, String fixIds);

    Long selectProjectManCode(String projectManCode);

    Object getIdByProjectCode(Object projectCode);

    Map<String, Object> selectProjectCodeByProjectId(Integer projectMasterId);
}
