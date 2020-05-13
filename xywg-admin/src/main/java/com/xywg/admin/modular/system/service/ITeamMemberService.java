package com.xywg.admin.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.system.model.TeamMember;

import java.util.List;

/**
 * <p>
 * 班组成员 服务类
 * </p>
 *
 * @author cw123
 * @since 2019-01-29
 */
public interface ITeamMemberService extends IService<TeamMember> {

	/**
     * 删除用户项目下原有班组记录
     * @param projectCode
     * @param idCardNumber
     */
    void deleteUserInProjectTeam(String projectCode, String idCardNumber, String organizationCode);

	/**
     *
     * @param oldTeamSysNo
     * @param TeamMember
     */
    void updateByOldTeamSysNo(Integer oldTeamSysNo, TeamMember TeamMember);

	/**
	 * 根据项目工人表id删除班组人员表信息
	 * @param list
	 * @author duanfen
	 */
	void batchDeleteByPwId(List<String> list);

	/**
	 * 查询人员是否加入了班组 
	 * <p>Title: queryCountByIdCardNumberAndTeamSysNo</p>  
	 * <p>Description: </p>  
	 * @author duanfen  
	 * @date 2019年4月17日
	 */
	int queryCountByIdCardNumberAndTeamSysNo(TeamMember team);

    void addTeamMemberList(List<Object> addList);
}
