package com.xywg.admin.oauth.domain.rs;

import com.xywg.admin.oauth.domain.oauth.AccessToken;
import com.xywg.admin.oauth.domain.oauth.ClientDetails;
import com.xywg.admin.oauth.domain.shared.Repository;

/**
 * 2015/10/7
 *
 * @author Shengzhao Li
 */

public interface OAuthRSRepository extends Repository {

    AccessToken findAccessTokenByTokenId(String tokenId);

    ClientDetails findClientDetailsByClientIdAndResourceIds(String clientId, String resourceIds);
}