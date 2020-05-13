package com.xywg.admin.oauth.oauth;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xywg.admin.oauth.domain.oauth.AccessToken;
import com.xywg.admin.oauth.domain.oauth.ClientDetails;
import com.xywg.admin.oauth.domain.shared.BeanProvider;
import com.xywg.admin.oauth.service.OauthService;
import org.apache.commons.lang.StringUtils;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

/**
 * 2015/7/3
 * <p/>
 * 对OAUTH各种流程的操作进行抽象,
 * 将没用的行为(方法) 放于此
 *
 * @author Shengzhao Li
 */
public abstract class OAuthHandler {


    private static final Logger LOG = LoggerFactory.getLogger(OAuthHandler.class);

    protected transient OauthService oauthService = BeanProvider.getBean(OauthService.class);


    private ClientDetails clientDetails;


    protected ClientDetails clientDetails() {
        if (clientDetails == null) {
            final String clientId = clientId();
            clientDetails = oauthService.loadClientDetails(clientId);
            LOG.debug("Load ClientDetails: {} by clientId: {}", clientDetails, clientId);
        }
        return clientDetails;
    }


    /**
     * Create  AccessToken response
     *
     * @param accessToken AccessToken
     * @param queryOrJson True is QueryMessage, false is JSON message
     * @return OAuthResponse
     * @throws OAuthSystemException
     */
    protected JSONObject createTokenResponse(AccessToken accessToken, boolean queryOrJson) throws OAuthSystemException {
        final ClientDetails tempClientDetails = clientDetails();

        final OAuthASResponse.OAuthTokenResponseBuilder builder = OAuthASResponse
                .tokenResponse(HttpServletResponse.SC_OK)
                .location(tempClientDetails.getRedirectUri())
                .setAccessToken(accessToken.tokenId())
                .setExpiresIn(String.valueOf(accessToken.currentTokenExpiredSeconds()))
                .setTokenType(accessToken.tokenType());

        final String refreshToken = accessToken.refreshToken();
        if (StringUtils.isNotEmpty(refreshToken)) {
            builder.setRefreshToken(refreshToken);
        }
        return JSONUtil.parseObj(accessToken.toString());
        //return queryOrJson ? builder.buildQueryMessage() : builder.buildJSONMessage();
    }


    protected abstract String clientId();

}
