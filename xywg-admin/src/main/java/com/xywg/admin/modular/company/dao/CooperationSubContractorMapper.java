package com.xywg.admin.modular.company.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.company.model.SubContractor;
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
public interface CooperationSubContractorMapper extends BaseMapper<SubContractor> {

    /**
     * 查询所有参建单位 分页
     * @param page
     * @param map
     * @return
     */
    List<Map<String,Object>> list(@Param("page") Page page, @Param("map") Map map);

    int changeState(@Param("map") Map<String, Object> map);

    /**
     * 查询所有承包商
     * @return
     */
    List<Map<String,Object>> selectGeneralContractors(@Param("generalContractorName") String generalContractorName);

    /**
     * 判断公司是否添加过
     * @param organizationCode
     * @return
     */
    int hasSubContractor(@Param("organizationCode")String organizationCode,@Param("companyName") String companyName);

    /**
     * 获取所有参建公司
     * @return
     */
    List<Map<String,Object>> getList(@Param("projectCode") String projectCode , @Param("deptId") Integer deptId);

    /**
     * 查询该项目除总包以外其他的参建单位
     * @param projectMasterId
     * @return
     */
    List<Map<String,Object>> getAllOtherCooperationSubContractor(Long projectMasterId);

    /**
     * 查询所有参建单位 无分页
     * @param map
     * @return
     */
    List<Map<String,Object>> listNoPage(@Param("map") Map<String, Object> map);

    Map<String, Object> selectCompanyById(@Param("id") Object id);
}
