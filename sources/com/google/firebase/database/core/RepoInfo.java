package com.google.firebase.database.core;

import java.net.URI;
import org.apache.http.HttpHost;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class RepoInfo {
    private static final String LAST_SESSION_ID_PARAM = "ls";
    private static final String VERSION_PARAM = "v";
    public String host;
    public String internalHost;
    public String namespace;
    public boolean secure;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(HttpHost.DEFAULT_SCHEME_NAME);
        sb.append(this.secure ? "s" : "");
        sb.append("://");
        sb.append(this.host);
        return sb.toString();
    }

    public String toDebugString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(host=");
        sb.append(this.host);
        sb.append(", secure=");
        sb.append(this.secure);
        sb.append(", ns=");
        sb.append(this.namespace);
        sb.append(" internal=");
        sb.append(this.internalHost);
        sb.append(")");
        return sb.toString();
    }

    public URI getConnectionURL(String str) {
        String str2 = this.secure ? "wss" : "ws";
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append("://");
        sb.append(this.internalHost);
        sb.append("/.ws?ns=");
        sb.append(this.namespace);
        sb.append("&");
        sb.append(VERSION_PARAM);
        sb.append("=");
        sb.append("5");
        String sb2 = sb.toString();
        if (str != null) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append("&ls=");
            sb3.append(str);
            sb2 = sb3.toString();
        }
        return URI.create(sb2);
    }

    public boolean isCacheableHost() {
        return this.internalHost.startsWith("s-");
    }

    public boolean isSecure() {
        return this.secure;
    }

    public boolean isDemoHost() {
        return this.host.contains(".firebaseio-demo.com");
    }

    public boolean isCustomHost() {
        return !this.host.contains(".firebaseio.com") && !this.host.contains(".firebaseio-demo.com");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RepoInfo repoInfo = (RepoInfo) obj;
        if (this.secure == repoInfo.secure && this.host.equals(repoInfo.host)) {
            return this.namespace.equals(repoInfo.namespace);
        }
        return false;
    }

    public int hashCode() {
        return (((this.host.hashCode() * 31) + (this.secure ? 1 : 0)) * 31) + this.namespace.hashCode();
    }
}
