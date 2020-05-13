package com.xywg.admin.modular.company.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.company.model.RegSubContractor;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.project.model.ProjectMasterHis;
import com.xywg.admin.modular.smz.model.CompanyMo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 企业表 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-22
 */
public interface SubContractorMapper extends BaseMapper<SubContractor> {

    List<Map<String,Object>> list(@Param("page") Page page ,@Param("map") Map map);
    
    List<Map<String,Object>> listSubs(@Param("page") Page page ,@Param("map") Map map);

    int changeState(@Param("map") Map<String,Object> map);

    /**
     * 查询所有承包商
     * @return
     */
    List<Map<String,Object>> selectGeneralContractors(@Param("generalContractorName") String generalContractorName);

    /**
     * 获取登陆者的所有公司
     * @param map
     * @return
     */
    List<SubContractor> getList(@Param("map") Map<String, Object> map);

    /**
     * 获取全部公司除自己
     * @param map
     * @return
     */
    List<Map<String,Object>> getAllSubContractor(@Param("map") Map<String, Object> map);

    /**
     * 判断新增的公司是否存在
     * @param organizationCode
     * @return
     */
    int hasSubContractor(@Param("organizationCode")String organizationCode,@Param("companyName")String companyName);

    /**
     * 判断外部注册的公司是否存在
     * @param organizationCode
     * @return
     */
    int hasRegSubContractor(@Param("organizationCode")String organizationCode,@Param("companyName")String companyName);

    /**
     * 判断新增的公司是否存在
     * @param organizationCode
     * @return
     */
    SubContractor selectSubContractor(String organizationCode);
    
    /**
     * 判断新增的公司是否存在
     * @param organizationCode
     * @return
     */
    SubContractor hasSubContractorByOrganizationCode(@Param("organizationCode")String organizationCode);

    /**
     * 模糊查询全国企业库中企业信息
     * @auth wangcw
     */
    List<SubContractor> getSubContractorByCondition(@Param("condition")String condition);

    /**
     * 根据公司名称查找社会组织信用代码
     * @param companyName
     * @return
     * @author yuanyang
     */
    SubContractor getByCompanyName(String companyName);

    Map<String,Object> getOneById(@Param("id") Integer id);

    /**
     * 根据组织机构代码获取公司
     * @param organizationCode
     * @return
     */
    Map<String,Object> getSubContractorByOrganizationCode(String organizationCode);

    /**
     * 根据组织机构代码获取注册公司
     * @return
     */
    RegSubContractor getRegSubContractorByOrganizationCode(String socialCreditNumber);

    /**
     * 删除登陆者集团下所有的参建公司
     * @param organizationCode
     * @param deptId
     * @return
     */
    Integer deleteConstruction(@Param("organizationCode") String organizationCode, @Param("deptId") Integer deptId);

    /**
     * @description 查询劳务通数据发送到实名制
     * @author xss
     * @date 2018年7月2日
     */
    List<CompanyMo> getCompanyFromLabor(List<Long> ids);



    /**
     * 获得三级区域(省市区都存在时)
     * @param areaId
     * @return
     */
    Map<String,String> getArea(String areaId);


    /**
     * 获得三级区域(当只存在省市的时候调用)
     * @param areaId
     * @return
     */
    Map<String,String> getAreaProvinceCity(String areaId);
    /**
     * 根据公司名称查询orgid
     * @param name
     * @return
     */
    SubContractor getOrgidByName(@Param("name")String name);

    /**
     * 获取当前登陆者下的参加公司列表
     * @param map
     * @return
     */
    List<Map<String,Object>> getSubContractor(@Param("map")Map<String, Object> map);

    /**
     * 获取所有企业 除自己以及自己无父公司的参建公司
     */
    List<Map<String,Object>> getAllNoParentSubContractor(@Param("map") Map<String, Object> map);

	SubContractor getCompanyInfo();

	List<ProjectMasterHis> selectHistoryPros(@Param("subContractorId")Integer subContractorId);

    boolean insertReg(SubContractor subContractor);

    boolean insertRegSubContractor(RegSubContractor regSubContractor);

    SubContractor selectRegById(String bussId);

    void deleteRegById(String bussId);
    SubContractor getcompanyNameAndOrgCodeById(@Param("id") Object id);

    String getCompanyOrgCodeById(@Param("companyId") Object companyId);

    void updateRegStatus(SubContractor subContractor);

    void updateRegSubContractorStatus(RegSubContractor regSubContractor);

    Map<String,Long> selectContractorbyOrgCode(@Param("organizationCode") String organizationCode);
}
