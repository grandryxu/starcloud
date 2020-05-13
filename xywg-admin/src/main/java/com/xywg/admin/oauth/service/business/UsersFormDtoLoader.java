package com.xywg.admin.oauth.service.business;

import com.xywg.admin.oauth.domain.shared.BeanProvider;
import com.xywg.admin.oauth.domain.users.Roles;
import com.xywg.admin.oauth.domain.users.UsersAuthzRepository;
import com.xywg.admin.oauth.service.dto.RolesDto;
import com.xywg.admin.oauth.service.dto.UsersFormDto;

import java.util.List;

/**
 * 2016/6/7
 *
 * @author Shengzhao Li
 */
public class UsersFormDtoLoader {

    private transient UsersAuthzRepository usersRepository = BeanProvider.getBean(UsersAuthzRepository.class);

    public UsersFormDtoLoader() {
    }

    public UsersFormDto load() {

        UsersFormDto formDto = new UsersFormDto();

        List<Roles> rolesList = usersRepository.findAvailableRolesList();
        formDto.setRolesDtos(RolesDto.toDtos(rolesList));

        return formDto;
    }
}
