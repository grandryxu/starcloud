package com.xywg.admin.oauth.oauth.validator;

import cn.hutool.json.JSONUtil;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthMessage;
import org.apache.oltu.oauth2.common.parameters.OAuthParametersApplier;

import java.util.Map;

/**
 * Created by wangcw on 2018/7/23.
 */
public class MyOAuthApplier implements OAuthParametersApplier {
    @Override
    public OAuthMessage applyOAuthParameters(OAuthMessage oAuthMessage, Map<String, Object> map) throws OAuthSystemException {
        oAuthMessage.setBody(JSONUtil.toJsonStr(map));
        return oAuthMessage;
    }
}
