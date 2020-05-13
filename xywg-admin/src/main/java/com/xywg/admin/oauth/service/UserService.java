package com.xywg.admin.oauth.service;

import com.xywg.admin.oauth.service.dto.UsersFormDto;
import com.xywg.admin.oauth.service.dto.UsersOverviewDto;

/**
 * 2016/6/3
 *
 * @author Shengzhao Li
 */

public interface UserService {

    UsersOverviewDto loadUsersOverviewDto(String username);

    UsersFormDto loadUsersFormDto();

    boolean isExistedUsername(String username);

    String saveUsers(UsersFormDto formDto);
}