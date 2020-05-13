package com.xywg.admin.oauth.web.controller;

/**
 * 2015/7/3
 * <p/>
 * URL: oauth/token
 *
 * @author Shengzhao Li
// */
//@Controller
//@RequestMapping("oauth/")
//public class OauthTokenController {
//
//
//    /**
//     * Handle grant_types as follows:
//     * <p/>
//     * grant_type=authorization_code
//     * grant_type=password
//     * grant_type=refresh_token
//     * grant_type=client_credentials
//     *
//     * @param request  HttpServletRequest
//     * @param response HttpServletResponse
//     * @throws OAuthSystemException
//     */
//    @RequestMapping("token")
//    public void authorize(HttpServletRequest request, HttpServletResponse response) throws OAuthSystemException {
//        try {
//            OAuthTokenxRequest tokenRequest = new OAuthTokenxRequest(request);
//
//            OAuthTokenHandleDispatcher tokenHandleDispatcher = new OAuthTokenHandleDispatcher(tokenRequest, response);
//            tokenHandleDispatcher.dispatch();
//
//        } catch (OAuthProblemException e) {
//            //exception
//            OAuthResponse oAuthResponse = OAuthASResponse
//                    .errorResponse(HttpServletResponse.SC_FOUND)
//                    .location(e.getRedirectUri())
//                    .error(e)
//                    .buildJSONMessage();
//            WebUtils.writeOAuthJsonResponse(response, oAuthResponse);
//        }
//
//    }
//}
