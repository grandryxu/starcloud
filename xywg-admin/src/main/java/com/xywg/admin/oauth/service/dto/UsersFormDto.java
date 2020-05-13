package com.xywg.admin.oauth.service.dto;

import com.xywg.admin.oauth.domain.shared.GuidGenerator;
import com.xywg.admin.oauth.domain.users.PasswordHandler;
import com.xywg.admin.oauth.domain.users.Users;

import java.util.ArrayList;
import java.util.List;

/**
 * 2016/6/7
 *
 * @author Shengzhao Li
 */
public class UsersFormDto extends UsersDto {
    private static final long serialVersionUID = 3873686200178717100L;


    private List<String> roleGuids = new ArrayList<>();


    public UsersFormDto() {
    }

    public UsersFormDto(Users users, boolean fully) {
        super(users, fully);
    }


    public List<String> getRoleGuids() {
        return roleGuids;
    }

    public void setRoleGuids(List<String> roleGuids) {
        this.roleGuids = roleGuids;
    }


    public Users newUsers() {
        return new Users()
                .username(getUsername())
                .password(PasswordHandler.md5(getPassword()))
                .guid(GuidGenerator.generate());
    }
}
