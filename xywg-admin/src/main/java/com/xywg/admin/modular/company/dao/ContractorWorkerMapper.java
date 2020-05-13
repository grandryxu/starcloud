package com.xywg.admin.modular.company.dao;

import com.xywg.admin.modular.company.model.ContractorWorker;
import com.xywg.admin.modular.company.model.dto.ContractorWorkerDto;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 企业工人表 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-28
 */
public interface ContractorWorkerMapper extends BaseMapper<ContractorWorker> {
    /**
     * 根据身份证查询是否存在
     * @param idCardNumber
     * @param idCardType
     * @return
     */
    public ContractorWorker getByIdCard(@Param("idCardNumber")String idCardNumber, @Param("idCardType")Integer idCardType,@Param("organizationCode")String organizationCode);
    
    /**
     * 根据身份证查询是否存在于某公司
     * 2018年6月26日
     *上午9:53:07
     *@author wangshibo
     *
     */
    public ContractorWorker getByIdCardAndOrganization(@Param("idCardNumber")String idCardNumber, @Param("idCardType")Integer idCardType,@Param("organizationCode")String organizationCode);

    /**
     * 
     * @description 获取人员年龄分布
     * @author chupp
     * @date 2018年6月22日
     * @param list
     * @param projectCodes
     * @return
     *
     */
	public List<ContractorWorkerDto> getAgeRange(@Param("list") List<String> list,@Param("projectCodes") List<String> projectCodes);

	/**
	 * 
	 * @description 获取人员籍贯分布
	 * @author chupp
	 * @date 2018年6月22日
	 * @param list
	 * @return
	 *
	 */
	public List<ContractorWorkerDto> getPlaceCodeRange(List<String> list);

	/**
	 * 修改公司人员删除状态
	 * @param id
	 */
	void updateIsDel(Long id);

    Map<String, Long> selectIdByIdCardAndOrgCode(@Param("idCardNumber") String idCardNumber, @Param("organizationCode") String organizationCode);
}
