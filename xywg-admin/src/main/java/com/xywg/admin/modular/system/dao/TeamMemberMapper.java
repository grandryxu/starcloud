package com.xywg.admin.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.system.model.TeamMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 班组成员 Mapper 接口
 * </p>
 *
 * @author cw123
 * @since 2019-01-29
 */
public interface TeamMemberMapper extends BaseMapper<TeamMember> {

	/**
     * 删除用户原有班组记录
     * @param projectCode
     * @param idCardNumber
     */
    void deleteUserInProjectTeam(@Param("projectCode") String projectCode, @Param("idCardNumber") String idCardNumber, @Param("organizationCode") String organizationCode);

	/**
	 * 根据项目查询当前工人在该项目老的班组编号
	 * @param projectId
	 * @param idCardNumber
	 * @return Integer
	 */
	void updateByOldTeamSysNo(@Param("oldTeamSysNo") Integer oldTeamSysNo, @Param("t") TeamMember TeamMember);

	/**
	 * 根据项目工人表id删除班组人员表信息
	 * @param list
	 * @author duanfen
	 */
	void batchDeleteByPwId(@Param("list") List<String> list);

	/**
	 * 查询人员是否加入了班组 
	 * <p>Title: queryCountByIdCardNumberAndTeamSysNo</p>  
	 * <p>Description: </p>  
	 * @author duanfen  
	 * @date 2019年4月17日
	 */
	int queryCountByIdCardNumberAndTeamSysNo(@Param("t") TeamMember team);

    Map<String, Long> selectIdByTeamSysNoAndIdCard(@Param("teamSysNo") Integer teamSysNo, @Param("idCardNumber") String idCardNumber);
}
