package com.google.api.client.googleapis.testing.auth.oauth2;

import com.google.api.client.googleapis.auth.oauth2.GoogleOAuthConstants;
import com.google.api.client.googleapis.testing.TestUtils;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.Json;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.twitter.sdk.android.core.internal.oauth.OAuthConstants;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class MockTokenServerTransport extends MockHttpTransport {
    static final String EXPECTED_GRANT_TYPE = "urn:ietf:params:oauth:grant-type:jwt-bearer";
    static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final String LEGACY_TOKEN_SERVER_URL = "https://accounts.google.com/o/oauth2/token";
    private static final Logger LOGGER = Logger.getLogger(MockTokenServerTransport.class.getName());
    Map<String, String> clients;
    Map<String, String> refreshTokens;
    Map<String, String> serviceAccounts;
    final String tokenServerUrl;

    public MockTokenServerTransport() {
        this(GoogleOAuthConstants.TOKEN_SERVER_URL);
    }

    public MockTokenServerTransport(String str) {
        this.serviceAccounts = new HashMap();
        this.clients = new HashMap();
        this.refreshTokens = new HashMap();
        this.tokenServerUrl = str;
    }

    public void addServiceAccount(String str, String str2) {
        this.serviceAccounts.put(str, str2);
    }

    public void addClient(String str, String str2) {
        this.clients.put(str, str2);
    }

    public void addRefreshToken(String str, String str2) {
        this.refreshTokens.put(str, str2);
    }

    public LowLevelHttpRequest buildRequest(String str, String str2) throws IOException {
        if (str2.equals(this.tokenServerUrl)) {
            return buildTokenRequest(str2);
        }
        if (!str2.equals(LEGACY_TOKEN_SERVER_URL)) {
            return super.buildRequest(str, str2);
        }
        LOGGER.warning("Your configured token_uri is using a legacy endpoint. You may want to redownload your credentials.");
        return buildTokenRequest(str2);
    }

    private MockLowLevelHttpRequest buildTokenRequest(String str) {
        return new MockLowLevelHttpRequest(str) {
            public LowLevelHttpResponse execute() throws IOException {
                String str;
                Map parseQuery = TestUtils.parseQuery(getContentAsString());
                String str2 = (String) parseQuery.get("client_id");
                if (str2 == null) {
                    String str3 = OAuthConstants.PARAM_GRANT_TYPE;
                    if (parseQuery.containsKey(str3)) {
                        if (MockTokenServerTransport.EXPECTED_GRANT_TYPE.equals((String) parseQuery.get(str3))) {
                            JsonWebSignature parse = JsonWebSignature.parse(MockTokenServerTransport.JSON_FACTORY, (String) parseQuery.get("assertion"));
                            String issuer = parse.getPayload().getIssuer();
                            if (MockTokenServerTransport.this.serviceAccounts.containsKey(issuer)) {
                                String str4 = (String) MockTokenServerTransport.this.serviceAccounts.get(issuer);
                                String str5 = (String) parse.getPayload().get("scope");
                                if (str5 == null || str5.length() == 0) {
                                    throw new IOException("Scopes not found.");
                                }
                                str = str4;
                            } else {
                                throw new IOException("Service Account Email not found as issuer.");
                            }
                        } else {
                            throw new IOException("Unexpected Grant Type.");
                        }
                    } else {
                        throw new IOException("Unknown token type.");
                    }
                } else if (MockTokenServerTransport.this.clients.containsKey(str2)) {
                    String str6 = (String) parseQuery.get("client_secret");
                    String str7 = (String) MockTokenServerTransport.this.clients.get(str2);
                    if (str6 == null || !str6.equals(str7)) {
                        throw new IOException("Client secret not found.");
                    }
                    String str8 = (String) parseQuery.get("refresh_token");
                    if (MockTokenServerTransport.this.refreshTokens.containsKey(str8)) {
                        str = (String) MockTokenServerTransport.this.refreshTokens.get(str8);
                    } else {
                        throw new IOException("Refresh Token not found.");
                    }
                } else {
                    throw new IOException("Client ID not found.");
                }
                GenericJson genericJson = new GenericJson();
                genericJson.setFactory(MockTokenServerTransport.JSON_FACTORY);
                genericJson.put("access_token", (Object) str);
                genericJson.put("expires_in", (Object) Integer.valueOf(3600));
                genericJson.put("token_type", (Object) OAuthConstants.AUTHORIZATION_BEARER);
                return new MockLowLevelHttpResponse().setContentType(Json.MEDIA_TYPE).setContent(genericJson.toPrettyString());
            }
        };
    }
}
