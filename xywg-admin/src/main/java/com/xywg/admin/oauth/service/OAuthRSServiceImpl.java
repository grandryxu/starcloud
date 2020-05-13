package com.xywg.admin.oauth.service;

import com.xywg.admin.oauth.domain.oauth.AccessToken;
import com.xywg.admin.oauth.domain.oauth.ClientDetails;
import com.xywg.admin.oauth.domain.rs.OAuthRSRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 2015/7/8
 *
 * @author Shengzhao Li
 */
@Service("oAuthRSService")
public class OAuthRSServiceImpl implements OAuthRSService {


    private static final Logger LOG = LoggerFactory.getLogger(OAuthRSServiceImpl.class);


    @Autowired
    private OAuthRSRepository oAuthRSRepository;


    @Override
    public AccessToken loadAccessTokenByTokenId(String tokenId) {
        return oAuthRSRepository.findAccessTokenByTokenId(tokenId);
    }

    @Override
    public ClientDetails loadClientDetails(String clientId, String resourceIds) {
        LOG.debug("Load ClientDetails by clientId: {}, resourceIds: {}", clientId, resourceIds);
        return oAuthRSRepository.findClientDetailsByClientIdAndResourceIds(clientId, resourceIds);
    }


}
