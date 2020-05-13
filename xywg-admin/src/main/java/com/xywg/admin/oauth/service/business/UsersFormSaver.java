package com.xywg.admin.oauth.service.business;

import com.xywg.admin.oauth.domain.shared.BeanProvider;
import com.xywg.admin.oauth.domain.users.Roles;
import com.xywg.admin.oauth.domain.users.Users;
import com.xywg.admin.oauth.domain.users.UsersAuthzRepository;
import com.xywg.admin.oauth.service.dto.UsersFormDto;

import java.util.List;

/**
 * 2016/6/7
 *
 * @author Shengzhao Li
 */
public class UsersFormSaver {

    private transient UsersAuthzRepository usersRepository = BeanProvider.getBean(UsersAuthzRepository.class);


    private UsersFormDto formDto;

    public UsersFormSaver(UsersFormDto formDto) {
        this.formDto = formDto;
    }

    public String save() {

        Users users = formDto.newUsers();
        final int id = usersRepository.saveUsers(users);

        final List<String> roleGuids = formDto.getRoleGuids();
        for (String roleGuid : roleGuids) {
            Roles roles = usersRepository.findRolesByGuid(roleGuid);
            usersRepository.insertUserRoles(id, roles.id());
        }

        return users.guid();
    }
}
