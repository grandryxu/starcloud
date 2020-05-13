package com.xywg.admin.oauth.service.impl;

import com.xywg.admin.oauth.domain.oauth.ClientDetails;
import com.xywg.admin.oauth.domain.oauth.OauthRepository;
import com.xywg.admin.oauth.domain.users.Roles;
import com.xywg.admin.oauth.domain.users.UsersAuthzRepository;
import com.xywg.admin.oauth.service.ClientDetailsService;
import com.xywg.admin.oauth.service.business.ClientDetailsFormSaver;
import com.xywg.admin.oauth.service.dto.ClientDetailsDto;
import com.xywg.admin.oauth.service.dto.ClientDetailsFormDto;
import com.xywg.admin.oauth.service.dto.ClientDetailsListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 2016/6/8
 *
 * @author Shengzhao Li
 */
@Service("clientDetailsService")
public class ClientDetailsServiceImpl implements ClientDetailsService {


    @Autowired
    private OauthRepository oauthRepository;
    @Autowired
    private UsersAuthzRepository usersAuthzRepository;


    @Override
    public ClientDetailsListDto loadClientDetailsListDto(String clientId) {
        List<ClientDetails> clientDetailsList = oauthRepository.findClientDetailsListByClientId(clientId);
        return new ClientDetailsListDto(clientId, clientDetailsList);
    }

    @Override
    public ClientDetailsFormDto loadClientDetailsFormDto() {
        List<Roles> rolesList = usersAuthzRepository.findAvailableRolesList();
        return new ClientDetailsFormDto(rolesList);
    }

    @Override
    public String saveClientDetails(ClientDetailsFormDto formDto) {
        ClientDetailsFormSaver saver = new ClientDetailsFormSaver(formDto);
        return saver.save();
    }

    @Override
    public ClientDetailsDto loadClientDetailsDto(String clientId) {
        ClientDetails clientDetails = oauthRepository.findClientDetails(clientId);
        return new ClientDetailsDto(clientDetails);
    }
}
