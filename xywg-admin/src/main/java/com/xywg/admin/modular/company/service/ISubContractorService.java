package com.xywg.admin.modular.company.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.project.model.ProjectMasterHis;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 企业表 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-22
 */
public interface ISubContractorService extends IService<SubContractor> {

    List<Map<String,Object>> selectList(Page page ,Map map);

    int changeState(Map<String,Object> map);

    List<Map<String,Object>> selectGeneralContractors(String generalContractorName);

    List<SubContractor> getList(Map<String, Object> map);

    List<Map<String,Object>> getAllSubContractor(Map<String, Object> map);

    List<SubContractor> getSubContractorByCondition(String condition);

    /**
     * 根据公司名称查找社会组织信用代码
     * @param companyName
     * @return
     * @author yuanyang
     */
    SubContractor getByCompanyName(String companyName);

    Map<String,Object> getOneById(Integer id);

    Map<String,Object> getSubContractorByOrganizationCode(String organizationCode);

    /**
     * 删除登陆用户集团下所有参建单位
     * @param organizationCode
     * @return
     */
    Integer deleteConstruction(String organizationCode);

    /**
     * 
     * @description 获取实名制企业数据（盐城）
     * @author chupp
     * @param myc 
     * @date 2018年7月2日
     *
     */
	void saveCompanyFromSMZYC(Map<String, String> myc);
    /**
     * @description 查询劳务通数据发送到实名制
     * @author xss
     * @param map 
     * @date 2018年7月2日
     */
    boolean getCompanyFromLabor(List<Long> ids, Map<String, String> map);

    /**
     * 获得三级区域
     * @param areaId
     * @return
     */
    Map<String,String> getArea(String areaId);

    /**
     * 
     * @description 获取实名制企业数据（盐城企业版）
     * @author chupp
     * @param myc 
     * @date 2018年7月17日
     *
     */
	void saveCompanyFromSMZCompanyYC(Map<String, String> myc);

    /**
     * 获取当前登陆者下的参建公司列表
     * @return
     * @author yuanyang
     */
    List<Map<String,Object>> getSubContractor();

    /**
     * 获取所有企业 除自己以及自己的参建公司 无父公司
     */
    List<Map<String,Object>> getAllNoParentSubContractor(Map<String, Object> map);

	void updateLxById(SubContractor subContractor);

	List<com.xywg.admin.modular.project.model.ProjectMaster> selectHistoryPros(Integer subContractorId);

	List<ProjectMasterHis> selectHistoryProsa(Integer subContractorId);

    boolean insertReg(SubContractor subContractor);

    SubContractor selectRegById(String bussId);

    void deleteRegById(String bussId);
    SubContractor getcompanyNameAndOrgCodeById(Object id);


    String getCompanyOrgCodeById(Object companyId);

    void updateRegStatus(SubContractor subContractor);

    void addContractorList(List<Object> addList);
}
