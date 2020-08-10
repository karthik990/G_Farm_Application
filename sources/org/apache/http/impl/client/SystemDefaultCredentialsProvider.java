package org.apache.http.impl.client;

import java.net.Authenticator;
import java.net.Authenticator.RequestorType;
import java.net.PasswordAuthentication;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.util.Args;

public class SystemDefaultCredentialsProvider implements CredentialsProvider {
    private static final Map<String, String> SCHEME_MAP = new ConcurrentHashMap();
    private final BasicCredentialsProvider internal = new BasicCredentialsProvider();

    static {
        String str = "Basic";
        SCHEME_MAP.put(str.toUpperCase(Locale.ROOT), str);
        String str2 = "Digest";
        SCHEME_MAP.put(str2.toUpperCase(Locale.ROOT), str2);
        String str3 = "NTLM";
        SCHEME_MAP.put(str3.toUpperCase(Locale.ROOT), str3);
        SCHEME_MAP.put("Negotiate".toUpperCase(Locale.ROOT), "SPNEGO");
        String str4 = "Kerberos";
        SCHEME_MAP.put(str4.toUpperCase(Locale.ROOT), str4);
    }

    private static String translateScheme(String str) {
        if (str == null) {
            return null;
        }
        String str2 = (String) SCHEME_MAP.get(str);
        if (str2 != null) {
            str = str2;
        }
        return str;
    }

    public void setCredentials(AuthScope authScope, Credentials credentials) {
        this.internal.setCredentials(authScope, credentials);
    }

    private static PasswordAuthentication getSystemCreds(String str, AuthScope authScope, RequestorType requestorType) {
        return Authenticator.requestPasswordAuthentication(authScope.getHost(), null, authScope.getPort(), str, null, translateScheme(authScope.getScheme()), null, requestorType);
    }

    public Credentials getCredentials(AuthScope authScope) {
        Args.notNull(authScope, "Auth scope");
        Credentials credentials = this.internal.getCredentials(authScope);
        if (credentials != null) {
            return credentials;
        }
        if (authScope.getHost() != null) {
            HttpHost origin = authScope.getOrigin();
            String str = "https";
            String str2 = HttpHost.DEFAULT_SCHEME_NAME;
            String str3 = origin != null ? origin.getSchemeName() : authScope.getPort() == 443 ? str : str2;
            PasswordAuthentication systemCreds = getSystemCreds(str3, authScope, RequestorType.SERVER);
            if (systemCreds == null) {
                systemCreds = getSystemCreds(str3, authScope, RequestorType.PROXY);
            }
            if (systemCreds == null) {
                systemCreds = getProxyCredentials(str2, authScope);
                if (systemCreds == null) {
                    systemCreds = getProxyCredentials(str, authScope);
                }
            }
            if (systemCreds != null) {
                String property = System.getProperty("http.auth.ntlm.domain");
                if (property != null) {
                    return new NTCredentials(systemCreds.getUserName(), new String(systemCreds.getPassword()), null, property);
                }
                return "NTLM".equalsIgnoreCase(authScope.getScheme()) ? new NTCredentials(systemCreds.getUserName(), new String(systemCreds.getPassword()), null, null) : new UsernamePasswordCredentials(systemCreds.getUserName(), new String(systemCreds.getPassword()));
            }
        }
        return null;
    }

    private static PasswordAuthentication getProxyCredentials(String str, AuthScope authScope) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(".proxyHost");
        String property = System.getProperty(sb.toString());
        if (property == null) {
            return null;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(".proxyPort");
        String property2 = System.getProperty(sb2.toString());
        if (property2 == null) {
            return null;
        }
        try {
            if (authScope.match(new AuthScope(property, Integer.parseInt(property2))) >= 0) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str);
                sb3.append(".proxyUser");
                String property3 = System.getProperty(sb3.toString());
                if (property3 == null) {
                    return null;
                }
                StringBuilder sb4 = new StringBuilder();
                sb4.append(str);
                sb4.append(".proxyPassword");
                String property4 = System.getProperty(sb4.toString());
                return new PasswordAuthentication(property3, property4 != null ? property4.toCharArray() : new char[0]);
            }
        } catch (NumberFormatException unused) {
        }
        return null;
    }

    public void clear() {
        this.internal.clear();
    }
}
