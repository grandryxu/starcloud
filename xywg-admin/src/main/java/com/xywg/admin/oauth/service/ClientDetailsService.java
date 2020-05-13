package com.xywg.admin.oauth.service;

import com.xywg.admin.oauth.service.dto.ClientDetailsDto;
import com.xywg.admin.oauth.service.dto.ClientDetailsFormDto;
import com.xywg.admin.oauth.service.dto.ClientDetailsListDto;

/**
 * 2016/6/8
 *
 * @author Shengzhao Li
 */

public interface ClientDetailsService {

    ClientDetailsListDto loadClientDetailsListDto(String clientId);

    ClientDetailsFormDto loadClientDetailsFormDto();

    String saveClientDetails(ClientDetailsFormDto formDto);

    ClientDetailsDto loadClientDetailsDto(String clientId);
}