package com.xywg.admin.modular.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.modular.system.dao.TeamMemberMapper;
import com.xywg.admin.modular.system.model.TeamMember;
import com.xywg.admin.modular.system.service.ITeamMemberService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 班组成员 服务实现类
 * </p>
 *
 * @author cw123
 * @since 2019-01-29
 */
@Service
public class TeamMemberServiceImpl extends ServiceImpl<TeamMemberMapper, TeamMember> implements ITeamMemberService {

	@Override
	public void deleteUserInProjectTeam(String projectCode, String idCardNumber, String organizationCode) {
		this.baseMapper.deleteUserInProjectTeam(projectCode, idCardNumber, organizationCode);
	}

	@Override
	public void updateByOldTeamSysNo(Integer oldTeamSysNo, TeamMember TeamMember) {
		this.baseMapper.updateByOldTeamSysNo(oldTeamSysNo, TeamMember);

	}

	@Override
	public void batchDeleteByPwId(List<String> list) {
		this.baseMapper.batchDeleteByPwId(list);
	}

	@Override
	public int queryCountByIdCardNumberAndTeamSysNo(TeamMember team) {
		return this.baseMapper.queryCountByIdCardNumberAndTeamSysNo(team);
	}

	@Override
	public void addTeamMemberList(List<Object> addList) {
		for (Object o : addList) {
			TeamMember teamMember = new TeamMember();
			stringToDateException();
			try {
				BeanUtils.copyProperties(teamMember, o);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			Map<String,Long> map =baseMapper.selectIdByTeamSysNoAndIdCard(teamMember.getTeamSysNo(),teamMember.getIdCardNumber());
			if (map.get("num")==0){
				teamMember.setId(null);
				insert(teamMember);
			}
		}
	}


	//解决 BeanUtils.copyProperties()的string转date异常
	private void stringToDateException() {
		ConvertUtils.register(new Converter() {
			@Override
			public Object convert(Class type, Object value) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					return simpleDateFormat.parse(value.toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return null;
			}
		}, java.util.Date.class);

	}
}
