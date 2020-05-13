package com.xywg.admin.modular.worker.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.worker.model.Contract;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.worker.model.ContractFile;

/**
 * <p>
 * 合同附件 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-08
 */
public interface ContractFileMapper extends BaseMapper<ContractFile> {

	/**
	 *
	 * @description 查询合同信息
	 * @author chupp
	 * @date 2018年6月8日
	 * @param projectCode
	 * @param organizationCode
	 * @param idCardType
	 * @param idCardNumber
	 * @return
	 *
	 */
	List<ContractFile> getContractFile(@Param("pc") String projectCode, @Param("oc") String organizationCode,
									   @Param("ict") Integer idCardType, @Param("icn") String idCardNumber);

	/**
	 * 获取一工人的所有合同
	 * @param page
	 * @param map
	 * @return
	 */
	List<ContractFile> getList(Page<ContractFile> page, @Param("map") Map<String, Object> map);

	/**
	 * 获取所有合同
	 * @param page
	 * @param map
	 * @return
	 */
	List<Contract> getContractList(Page<Contract> page, @Param("map") Map<String,Object> map);

	/**
	 * 批量审核
	 * @param ids
	 * @param status
	 * @return
	 * @author 蔡伟
	 */
	Integer batchReview(@Param("ids") String ids,@Param("status") Integer status);

	/**
	 * 通过工人id修改审核状态
	 * @param ids
	 * @param status
	 * @return
	 */
	Integer batchReviewByFileId(@Param("ids")int[] ids, @Param("status")Integer status);

	/**
	 * 批量修改
	 * @param ids
	 * @param value
	 */
	void updateSetting(@Param("ids") String[] ids, @Param("value") Integer value);

	/**
	 * 批量删除
	 * <p>Title: deletePatch</p>
	 * <p>Description: </p>
	 * @author duanfen
	 * @date 2019年4月10日
	 */
	void deletePatch(List<String> ids);

	/**
	 * 查询人员的合同信息
	 * <p>Title: selectWorkerContractFiles</p>
	 * <p>Description: </p>
	 * @author duanfen
	 * @date 2019年4月15日
	 */
	List<Map<String, Object>> selectWorkerContractFiles(Page<Map<String, Object>> page, @Param("map") Map<String,Object> map);

    Map<String, Object> selectIdByContractCode(@Param("contractCode") String contractCode);
}
