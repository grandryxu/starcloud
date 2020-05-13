package com.xywg.admin.oauth.domain.rs;

import com.xywg.admin.oauth.domain.oauth.ClientDetails;
import org.apache.oltu.oauth2.rsfilter.OAuthClient;

/**
 * 2015/10/7
 * <p/>
 * Wrapper ClientDetails
 * implement OAuthClient
 *
 * @author Shengzhao Li
 * @see ClientDetails
 */
public class RSOAuthClient implements OAuthClient {

    private ClientDetails clientDetails;

    public RSOAuthClient(ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
    }

    @Override
    public String getClientId() {
        return clientDetails.getClientId();
    }

    public ClientDetails clientDetails() {
        return clientDetails;
    }
}
