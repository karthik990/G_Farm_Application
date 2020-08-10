package com.google.firebase.database.connection;

import java.net.URI;
import org.apache.http.HttpHost;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class HostInfo {
    private static final String LAST_SESSION_ID_PARAM = "ls";
    private static final String VERSION_PARAM = "v";
    private final String host;
    private final String namespace;
    private final boolean secure;

    public HostInfo(String str, String str2, boolean z) {
        this.host = str;
        this.namespace = str2;
        this.secure = z;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(HttpHost.DEFAULT_SCHEME_NAME);
        sb.append(this.secure ? "s" : "");
        sb.append("://");
        sb.append(this.host);
        return sb.toString();
    }

    public static URI getConnectionUrl(String str, boolean z, String str2, String str3) {
        String str4 = z ? "wss" : "ws";
        StringBuilder sb = new StringBuilder();
        sb.append(str4);
        sb.append("://");
        sb.append(str);
        sb.append("/.ws?ns=");
        sb.append(str2);
        sb.append("&");
        sb.append(VERSION_PARAM);
        sb.append("=");
        sb.append("5");
        String sb2 = sb.toString();
        if (str3 != null) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append("&ls=");
            sb3.append(str3);
            sb2 = sb3.toString();
        }
        return URI.create(sb2);
    }

    public String getHost() {
        return this.host;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public boolean isSecure() {
        return this.secure;
    }
}
