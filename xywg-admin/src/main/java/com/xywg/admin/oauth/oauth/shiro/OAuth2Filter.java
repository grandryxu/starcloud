package com.xywg.admin.oauth.oauth.shiro;

import cn.hutool.json.JSONObject;
import com.xywg.admin.modular.system.service.IUserService;
import com.xywg.admin.oauth.domain.oauth.AccessToken;
import com.xywg.admin.oauth.domain.shared.BeanProvider;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 2015/9/29
 * <p/>
 * 对需要保护的资源进行拦截过滤处理
 * 需要与SHIRO的安全整合并加入到SHIRO 流程中
 * 相关配置见 rs-security.xml 文件
 *
 * @author Shengzhao Li
 */
public class OAuth2Filter extends AuthenticatingFilter implements InitializingBean {


    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2Filter.class);


    private String resourceId="os-resource";

    @Autowired
    private IUserService userService;


    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        userService = BeanProvider.getBean(IUserService.class);
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        final String accessToken = getAccessToken(httpRequest);
        final AccessToken token = userService.findAccessTokenByTokenId(accessToken);

        String username = null;
        if (token != null) {
            LOGGER.debug("Set username and clientId from AccessToken: {}", token);
            username = token.username();
            httpRequest.setAttribute(OAuth.OAUTH_CLIENT_ID, token.clientId());
        } else {
            LOGGER.debug("Not found AccessToken by access_token: {}", accessToken);
        }
        OAuth2Token result = new OAuth2Token(accessToken, resourceId);
        result.setUsername(request.getParameter("username"));
        if(request.getParameter("password")!=null){
            result.setPassword(request.getParameter("password").toCharArray());
        }
        result.setUserId(username);
        return result;
    }

    private String getAccessToken(HttpServletRequest httpRequest) {
        final String authorization = httpRequest.getHeader("Authorization");
        if (authorization != null) {
            //bearer ab1ade69-d122-4844-ab23-7b109ad977f0
            return authorization.substring(6).trim();
        }
        return httpRequest.getParameter(OAuth.OAUTH_ACCESS_TOKEN);
    }


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request,
                                     ServletResponse response) {

        final OAuthResponse oAuthResponse;
//        try {
////            oAuthResponse = OAuthRSResponse.errorResponse(401)
////                    .setError(OAuthError.ResourceResponse.INVALID_TOKEN)
////                    .setErrorDescription(ae.getMessage())
////                    .buildJSONMessage();
//
//
//            //WebUtils.writeOAuthJsonResponse((HttpServletResponse) response, oAuthResponse);
////            WebUtils.writeOAuthJSONObjectResponse(response,jsonObject);
//
//        } catch (OAuthSystemException e) {
//            LOGGER.error("Build JSON message error", e);
//            throw new IllegalStateException(e);
//        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message",OAuthError.ResourceResponse.INVALID_TOKEN);
        return false;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public void setRsService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(resourceId, "resourceId is null");
        Assert.notNull(userService, "rsService is null");
    }
}
