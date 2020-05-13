package com.xywg.admin.modular.bad.dao;

import java.util.List;
import java.util.Map;

import com.xywg.admin.modular.bad.dto.WorkerBlackDto;
import com.xywg.admin.modular.team.model.TeamMaster;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.bad.model.WorkerBlackList;
import com.xywg.admin.modular.bad.model.WorkerBlackListVO;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.worker.model.WorkerMasterVO;

/**
 * <p>
 * 工人黑名单信息 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-06
 */
public interface WorkerBlackListMapper extends BaseMapper<WorkerBlackList> {

	/**
	 * 查询工人黑名单列表
	 * @param page
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectWorkerBlackList(@Param("page") Page<WorkerBlackList> page, @Param("map") Map<String, Object> map);
	
	/**
	 * 查询企业黑名单列表
	 * @param page
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectCompanyBlackList(@Param("page") Page<WorkerBlackList> page, @Param("map") Map<String, Object> map);
	
	/**
	 * 根据id查询工人信息
	 * @param id
	 * @param projectCodes
	 * @return
	 */
	List<WorkerMasterVO> selectWorkerInfoById(@Param("id") Integer id, @Param("projectCodes") List<String> projectCodes);
	
	/**
	 * 根据项目编码查询所属建设单位
	 * @param projectCode
	 * @return
	 */
	WorkerMasterVO getOwnerNameByProjectCode(@Param("projectCode") String projectCode);
	
	/**
	 * 根据项目编码获取班组信息
	 * @param projectCode
	 * @param deptId
     * @return
	 */
	List<WorkerMasterVO> getTeamInfoByProjectCode(@Param("projectCode") String projectCode, @Param("deptId") Integer deptId);
	
	/**
	 * 根据organizationCode查询企业信息
	 * @param id
	 * @return
	 */
	SubContractor selectCompanyInfoById(@Param("organizationCode") String organizationCode);
	
	/**
	 * 根据id查询工人黑名单信息
	 * @param id
	 * @return
	 */
	WorkerBlackListVO selectWorkerBlackListById(@Param("id") Integer id);
	
	/**
	 * 根据id查询企业黑名单信息
	 * @param id
	 * @return
	 */
	WorkerBlackListVO selectCompanyBlackListById(@Param("id") Integer id);
	
	/**
	 * 查询公司名称下拉列表
	 * @return
	 */
	List<Map<String,Object>> getCompanys(@Param("list") List<String> list, @Param("projectCodes") List<String> projectCodes);
	
	/**
	 * 查询项目名称下拉列表
	 * @return
	 */
	List<Map<String,Object>> getProjects(@Param("organizationCode") String organizationCode);

	/**
	 * 黑名单定时更新
	 * @return
	 */
    Integer updateBlack();

	List<WorkerMasterVO> selectWorkerInfoById(@Param("id") Integer id, @Param("projectCodes") List<String> projectCodes, @Param("deptId") Integer deptId);

	/**
	 * 获取切换公司下的所有总包项目
	 * @param deptId
	 * @param projectCodes
     * @return
	 */
	List<Map<String,Object>> getAllMainProjectMasterByDeptId(@Param("deptId") Integer deptId, @Param("projectCodes") List<String> projectCodes);

	/**
	 * 获取黑名单列表 分页 app
	 * @param type
	 * @param index
	 * @param pageSize
	 * @return
	 */
    List<WorkerBlackDto> getBlackListApp(@Param("type") Integer type, @Param("index") Integer index, @Param("pageSize") Integer pageSize);

	/**
	 * 根据项目编号获取所有班组
	 * @param projectCode
	 * @param isGeneralContractor
	 * @return
	 */
	List<TeamMaster> getTeamsByProjectCodeApp(@Param("organizationCode") String organizationCode , @Param("projectCode") String projectCode, @Param("isGeneralContractor") Integer isGeneralContractor);

	/**
	 * 根据班组获取工人 总包获取全部 参建获取本公司下
	 * @param organizationCode 登陆账号公司
	 * @param teamSysNo
	 * @param isGeneralContractor
	 * @return
	 */
	List<WorkerMaster> getWorkersByTeamSysNoApp(@Param("organizationCode") String organizationCode, @Param("teamSysNo") String teamSysNo, @Param("isGeneralContractor") Integer isGeneralContractor);

	/**
	 * 根据班组获取工人 总包获取全部 参建获取本公司下
	 * @param teamSysNo
	 * @param isGeneralContractor
	 * @param loginComSocialCreditNumbers
	 * @return
	 */
	List<WorkerMaster> getWorkersByTeamSysNo(@Param("teamSysNo") String teamSysNo, @Param("isGeneralContractor") Integer isGeneralContractor, @Param("loginComSocialCreditNumbers") List<String> loginComSocialCreditNumbers);

}
