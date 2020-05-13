package com.xywg.admin.core.common.constant.factory;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.xywg.admin.core.common.constant.cache.Cache;
import com.xywg.admin.core.common.constant.cache.CacheKey;
import com.xywg.admin.core.common.constant.state.ManagerStatus;
import com.xywg.admin.core.common.constant.state.MenuStatus;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.core.support.StrKit;
import com.xywg.admin.core.util.Convert;
import com.xywg.admin.core.util.PinyinUtil;
import com.xywg.admin.core.util.SpringContextHolder;
import com.xywg.admin.core.util.ToolUtil;
import com.xywg.admin.modular.device.utils.RedisUtil;
import com.xywg.admin.modular.project.dao.ProjectMasterMapper;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.system.dao.*;
import com.xywg.admin.modular.system.model.*;

import com.xywg.admin.modular.team.dao.TeamMasterMapper;
import com.xywg.admin.modular.team.model.TeamMaster;

import org.eclipse.jetty.util.security.Credential.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 常量的生产工厂
 *
 * @author wangcw
 * @date 2017年2月13日 下午10:55:21
 */
@Component
@DependsOn("springContextHolder")
public class ConstantFactory implements IConstantFactory {
    private WorkKindMapper workKindMapper=SpringContextHolder.getBean(WorkKindMapper.class);
    private RoleMapper roleMapper = SpringContextHolder.getBean(RoleMapper.class);
    private DeptMapper deptMapper = SpringContextHolder.getBean(DeptMapper.class);
    private DictMapper dictMapper = SpringContextHolder.getBean(DictMapper.class);
    private UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);
    private MenuMapper menuMapper = SpringContextHolder.getBean(MenuMapper.class);
    private NoticeMapper noticeMapper = SpringContextHolder.getBean(NoticeMapper.class);
    private AreaMapper areaMapper =SpringContextHolder.getBean(AreaMapper.class);
    private ProjectMasterMapper projectMasterMapper =SpringContextHolder.getBean(ProjectMasterMapper.class);
    private TeamMasterMapper teamMasterMapper=SpringContextHolder.getBean(TeamMasterMapper.class);
    private final static String AREA_KEY = "laborAreaName";
    @Autowired
    public RedisUtil redisUtil;
    public static IConstantFactory me() {
        return SpringContextHolder.getBean("constantFactory");
    }

    /**
     * 根据用户id获取用户名称
     *
     * @author wangcw
     * @Date 2017/5/9 23:41
     */
    @Override
    public String getUserNameById(Integer userId) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            return user.getName();
        } else {
            return "--";
        }
    }

    /**
     * 根据用户id获取用户账号
     *
     * @author wangcw
     * @date 2017年5月16日21:55:371
     */
    @Override
    public String getUserAccountById(Integer userId) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            return user.getAccount();
        } else {
            return "--";
        }
    }

    /**
     * 通过角色ids获取角色名称
     */
    @Override
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.ROLES_NAME + "'+#roleIds")
    public String getRoleName(String roleIds) {
        Integer[] roles = Convert.toIntArray(roleIds);
        StringBuilder sb = new StringBuilder();
        for (int role : roles) {
            Role roleObj = roleMapper.selectById(role);
            if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
                sb.append(roleObj.getName()).append(",");
            }
        }
        return StrKit.removeSuffix(sb.toString(), ",");
    }

    /**
     * 通过角色id获取角色名称
     */
    @Override
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.SINGLE_ROLE_NAME + "'+#roleId")
    public String getSingleRoleName(Integer roleId) {
        if (0 == roleId) {
            return "--";
        }
        Role roleObj = roleMapper.selectById(roleId);
        if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
            return roleObj.getName();
        }
        return "";
    }

    /**
     * 通过角色id获取角色英文名称
     */
    @Override
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.SINGLE_ROLE_TIP + "'+#roleId")
    public String getSingleRoleTip(Integer roleId) {
        if (0 == roleId) {
            return "--";
        }
        Role roleObj = roleMapper.selectById(roleId);
        if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
            return roleObj.getTips();
        }
        return "";
    }

    /**
     * 获取部门名称
     */
    @Override
    //不从缓存里取,及时刷新
    //@Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.DEPT_NAME + "'+#deptId")
    public String getDeptName(Integer deptId) {
        Dept dept = deptMapper.selectById(deptId);
        if (ToolUtil.isNotEmpty(dept) && ToolUtil.isNotEmpty(dept.getFullname())) {
            return dept.getFullname();
        }
        return "";
    }

    /**
     * 获取菜单的名称们(多个)
     */
    @Override
    public String getMenuNames(String menuIds) {
        Integer[] menus = Convert.toIntArray(menuIds);
        StringBuilder sb = new StringBuilder();
        for (int menu : menus) {
            Menu menuObj = menuMapper.selectById(menu);
            if (ToolUtil.isNotEmpty(menuObj) && ToolUtil.isNotEmpty(menuObj.getName())) {
                sb.append(menuObj.getName()).append(",");
            }
        }
        return StrKit.removeSuffix(sb.toString(), ",");
    }

    /**
     * 获取菜单名称
     */
    @Override
    public String getMenuName(Long menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            return "";
        } else {
            Menu menu = menuMapper.selectById(menuId);
            if (menu == null) {
                return "";
            } else {
                return menu.getName();
            }
        }
    }

    /**
     * 获取菜单名称通过编号
     */
    @Override
    public String getMenuNameByCode(String code) {
        if (ToolUtil.isEmpty(code)) {
            return "";
        } else {
            Menu param = new Menu();
            param.setCode(code);
            Menu menu = menuMapper.selectOne(param);
            if (menu == null) {
                return "";
            } else {
                return menu.getName();
            }
        }
    }

    /**
     * 获取字典名称
     */
    @Override
    public String getDictName(Integer dictId) {
        if (ToolUtil.isEmpty(dictId)) {
            return "";
        } else {
            Dict dict = dictMapper.selectById(dictId);
            if (dict == null) {
                return "";
            } else {
                return dict.getName();
            }
        }
    }

    /**
     * 获取通知标题
     */
    @Override
    public String getNoticeTitle(Integer dictId) {
        if (ToolUtil.isEmpty(dictId)) {
            return "";
        } else {
            Notice notice = noticeMapper.selectById(dictId);
            if (notice == null) {
                return "";
            } else {
                return notice.getTitle();
            }
        }
    }

    /**
     * 根据字典名称和字典中的值获取对应的名称
     */
    @Override
    public String getDictsByName(String name, Integer val) {
        Dict temp = new Dict();
        temp.setName(name);
        List<Dict> list = redisUtil.exists(PinyinUtil.getFullSpell(name))?(List<Dict>) redisUtil.get(PinyinUtil.getFullSpell(name)):new ArrayList<>();
        if(list != null && list.size()>0) {
        	for (Dict item : list) {
                if (item.getNum() != null && item.getNum().equals(val)) {
                    return item.getName();
                }
            }
        	return "";
        }else {
        	Dict dict = dictMapper.selectOne(temp);
            if (dict == null) {
                return "";
            } else {
                Wrapper<Dict> wrapper = new EntityWrapper<>();
                wrapper = wrapper.eq("pid", dict.getId());
                List<Dict> dicts = dictMapper.selectList(wrapper);
                redisUtil.set(PinyinUtil.getFullSpell(name), dicts);
                for (Dict item : dicts) {
                    if (item.getNum() != null && item.getNum().equals(val)) {
                        return item.getName();
                    }
                }
                
                return "";
            }
        }
        
    }

    /**
     * 获取性别名称
     */
    @Override
    public String getSexName(Integer sex) {
        return getDictsByName("性别", sex);
    }

    /**
     * 获取单位性质名称
     */
    @Override
    public String getOrganizationTypeName(Integer num) {
        return getDictsByName("单位性质", num);
    }

    /**
     * 获取币种
     * @param deptid
     * @return
     */
    @Override
    public String getcurrencyName(Integer deptid) {
        return getDictsByName("币种",deptid);
    }

    /**
     * 获取经营状态名称
     */
    @Override
    public String getBusinessStatusName(Integer num) {
        return getDictsByName("经营状态", num);
    }

    @Override
    public String getCertificationTypeName(Integer deptid) {
        return getDictsByName("证书类型",deptid);
    }

    @Override
    public String getStateName(Integer deptid) {
        return getDictsByName("设备状态",deptid);
    }

    @Override
    public String getQualificationStatusName(Integer deptid) {
        return getDictsByName("企业资质状态",deptid);
    }

    @Override
    public String getCertificationStatusName(Integer deptid) {
        return getDictsByName("企业资质证书状态",deptid);
    }

    @Override
    public String gettrainingTypeCodeName(Integer deptid) {
        return getDictsByName("项目培训类型",deptid);
    }

    @Override
	public String projectCategory(Integer projectCategory) {
		return getDictsByName("项目分类",projectCategory);
	}

    @Override
    public TeamMaster getTeamByTeamSysNo(Integer teamSysNo) {
        return teamMasterMapper.getByteamCode(teamSysNo);
    }

    @Override
    public String getIdcardTypeName(int pmIdcardType) {
        return getDictsByName("人员证件类型",pmIdcardType);
    }

    @Override
    public String getProjectType(Integer type) {
        return getDictsByName("项目培训类型",type);
    }

    @Override
    public String getAreaNameById(int areaCode) {
        return areaMapper.getAreaNameById(areaCode);
    }

    /**
     * 参建类型
     */
    @Override
    public String getContractorTypeName(Integer num){return getDictsByName("参建类型",num);}

    /**
     * 参建类型
     */
    @Override
    public String getWorkKindNameByNum(Integer num){return getDictsByName("工种字典数据",num);}

    /**
     * 根据id获取籍贯名称
     */
    @Override
    public String getAreaName(Integer deptid) {
    	Map<String,String> map = redisUtil.exists(PinyinUtil.getFullSpell(AREA_KEY))?(Map) redisUtil.get(PinyinUtil.getFullSpell(AREA_KEY)):new HashMap<>();
    	if(map != null && map.size()>0) {
    		return map.get(String.valueOf(deptid));
    	}else {
    		List<Area> areaList = areaMapper.getBirthPlaceCode();
    		Map<String,String> areaMap = new HashMap<>();
    		
    		if(areaList != null && areaList.size()>0) {
    			for(Area area : areaList) {
    				areaMap.put(String.valueOf(area.getId()), area.getShortname());
    			}
    			redisUtil.set(AREA_KEY, areaMap);
    		}
    		return areaMapper.getShortNameById(deptid);
    	}
        
    }

    @Override
    public String getWorkKindName(Integer num) {
        return workKindMapper.getWorkKindName(num);
    }

    /**
     * 根据项目编号获取项目名称
     */
    @Override
    public ProjectMaster getProjectByProjectCode(String projectCode) {
        return projectMasterMapper.getProjectByProjectCode(projectCode);
    }

    @Override
    public String getTypeIdNameByTypeId(Integer typeId) {
        return getDictsByName("设备类型",typeId);
    }

    @Override
    public String getTypeNameByType(Integer type) {
        return getDictsByName("出入类型",type);
    }

    @Override
    public String projectStatusName(int projectStatus) {
        return getDictsByName("项目状态",projectStatus);
    }

    /**
     * 获取用户登录状态
     */
    @Override
    public String getStatusName(Integer status) {
        return ManagerStatus.valueOf(status);
    }

    /**
     * 获取菜单状态
     */
    @Override
    public String getMenuStatusName(Integer status) {
        return MenuStatus.valueOf(status);
    }

    /**
     * 查询字典
     */
    @Override
    public List<Dict> findInDict(Integer id) {
        if (ToolUtil.isEmpty(id)) {
            return null;
        } else {
            EntityWrapper<Dict> wrapper = new EntityWrapper<>();
            List<Dict> dicts = dictMapper.selectList(wrapper.eq("pid", id));
            if (dicts == null || dicts.size() == 0) {
                return null;
            } else {
                return dicts;
            }
        }
    }

    /**
     * 获取被缓存的对象(用户删除业务)
     */
    @Override
    public String getCacheObject(String para) {
        return LogObjectHolder.me().get().toString();
    }

    /**
     * 获取子部门id
     */
    @Override
    public List<Integer> getSubDeptId(Integer deptid) {
        Wrapper<Dept> wrapper = new EntityWrapper<>();
        wrapper = wrapper.like("pids", "%[" + deptid + "]%");
        List<Dept> depts = this.deptMapper.selectList(wrapper);

        ArrayList<Integer> deptids = new ArrayList<>();

        if(depts != null && depts.size() > 0){
            for (Dept dept : depts) {
                deptids.add(dept.getId());
            }
        }

        return deptids;
    }

    /**
     * 获取所有父部门id
     */
    @Override
    public List<Integer> getParentDeptIds(Integer deptid) {
        Dept dept = deptMapper.selectById(deptid);
        String pids = dept.getPids();
        String[] split = pids.split(",");
        ArrayList<Integer> parentDeptIds = new ArrayList<>();
        for (String s : split) {
            parentDeptIds.add(Integer.valueOf(StrKit.removeSuffix(StrKit.removePrefix(s, "["), "]")));
        }
        return parentDeptIds;
    }

	

}
