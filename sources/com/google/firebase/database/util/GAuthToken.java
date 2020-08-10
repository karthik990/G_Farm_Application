package com.google.firebase.database.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class GAuthToken {
    private static final String AUTH_KEY = "auth";
    private static final String TOKEN_KEY = "token";
    private static final String TOKEN_PREFIX = "gauth|";
    private final Map<String, Object> auth;
    private final String token;

    public GAuthToken(String str, Map<String, Object> map) {
        this.token = str;
        this.auth = map;
    }

    public static GAuthToken tryParseFromString(String str) {
        if (!str.startsWith(TOKEN_PREFIX)) {
            return null;
        }
        try {
            Map parseJson = JsonMapper.parseJson(str.substring(6));
            return new GAuthToken((String) parseJson.get(TOKEN_KEY), (Map) parseJson.get(AUTH_KEY));
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse gauth token", e);
        }
    }

    public String serializeToString() {
        HashMap hashMap = new HashMap();
        hashMap.put(TOKEN_KEY, this.token);
        hashMap.put(AUTH_KEY, this.auth);
        try {
            String serializeJson = JsonMapper.serializeJson(hashMap);
            StringBuilder sb = new StringBuilder();
            sb.append(TOKEN_PREFIX);
            sb.append(serializeJson);
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize gauth token", e);
        }
    }

    public String getToken() {
        return this.token;
    }

    public Map<String, Object> getAuth() {
        return this.auth;
    }
}
