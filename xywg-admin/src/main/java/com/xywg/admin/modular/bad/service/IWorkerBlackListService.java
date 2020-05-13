package com.xywg.admin.modular.bad.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.bad.dto.WorkerBlackDto;
import com.xywg.admin.modular.bad.model.WorkerBlackList;
import com.xywg.admin.modular.bad.model.WorkerBlackListVO;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.team.model.TeamMaster;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import com.xywg.admin.modular.worker.model.WorkerMasterVO;

/**
 * <p>
 * 工人黑名单信息 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-06
 */
public interface IWorkerBlackListService extends IService<WorkerBlackList> {

	/**
	 * 查询工人黑名单列表
	 * @param page
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectWorkerBlackList(Page<WorkerBlackList> page,Map<String, Object> map);
	
	/**
	 * 查询工人黑名单列表
	 * @param page
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectCompanyBlackList(Page<WorkerBlackList> page,Map<String, Object> map);
	
	/**
	 * 根据id查询工人信息
	 * @param id
	 * @return
	 */
	List<WorkerMasterVO> selectWorkerInfoById(Integer id);
	
	/**
	 * 根据项目编码获取所属建设单位
	 * @param projectCode
	 * @return
	 */
	WorkerMasterVO getOwnerNameByProjectCode(String projectCode);
	
	/**
	 * 根据项目编码获取班组信息
	 * @param projectCode
	 * @return
	 */
	List<WorkerMasterVO> getTeamInfoByProjectCode(String projectCode);
	
	/**
	 * 根据organizationCode查询企业信息
	 * @param id
	 * @return
	 */
	SubContractor selectCompanyInfoById(String organizationCode);
	
	/**
	 * 根据id查询工人黑名单信息
	 * @param id
	 * @return
	 */
	WorkerBlackListVO selectWorkerBlackListById(Integer id);
	
	/**
	 * 根据id查询企业黑名单信息
	 * @param id
	 * @return
	 */
	WorkerBlackListVO selectCompanyBlackListById(Integer id);
	
	 /**
     * 获取总包公司
     * @return
     */
   List<Map<String, Object>> getCompanys();
   
   /**
    * 获取项目切换状态的所有总包项目
    * @return
    */
   List<Map<String, Object>> getProjects();

	/**
	 * 更新黑名单有效变无效
	 * @return
	 */
	Integer updateBlack();

	/**
	 * 获取黑名单列表 分页 app
	 * @param type
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
    List<WorkerBlackDto> getBlackListApp(Integer type, Integer pageNo, Integer pageSize);

	/**
	 * 根据项目获取班组 总包获取全部 参建获取本公司下
	 * @param organizationCode
	 * @param projectCode
	 * @return
	 */
	List<TeamMaster> getTeamsByProjectCodeApp(String organizationCode, String projectCode);

	/**
	 * 根据班组获取工人 总包获取全部 参建获取本公司下
	 * @param organizationCode 登陆账号公司
	 * @param projectCode
	 * @param teamSysNo
	 * @return
	 */
	List<WorkerMaster> getWorkersByTeamSysNoApp(String organizationCode, String projectCode, String teamSysNo);

	/**
	 * 根据班组获取工人 总包获取全部 参建获取本公司下
	 * @param projectCode
	 * @param teamSysNo
	 * @return
	 */
	List<WorkerMaster> getWorkersByTeamSysNo(String projectCode, String teamSysNo);

    void addWorkerBlackList(List<Object> addList);
}
