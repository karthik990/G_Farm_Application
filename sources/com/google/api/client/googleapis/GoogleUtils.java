package com.google.api.client.googleapis;

import com.google.api.client.util.SecurityUtils;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

public final class GoogleUtils {
    public static final Integer BUGFIX_VERSION = Integer.valueOf(0);
    public static final Integer MAJOR_VERSION = Integer.valueOf(1);
    public static final Integer MINOR_VERSION = Integer.valueOf(26);
    public static final String VERSION;
    static KeyStore certTrustStore;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(MAJOR_VERSION);
        String str = ".";
        sb.append(str);
        sb.append(MINOR_VERSION);
        sb.append(str);
        sb.append(BUGFIX_VERSION);
        sb.append("-SNAPSHOT");
        VERSION = sb.toString().toString();
    }

    public static synchronized KeyStore getCertificateTrustStore() throws IOException, GeneralSecurityException {
        KeyStore keyStore;
        Class<GoogleUtils> cls = GoogleUtils.class;
        synchronized (cls) {
            if (certTrustStore == null) {
                certTrustStore = SecurityUtils.getJavaKeyStore();
                SecurityUtils.loadKeyStore(certTrustStore, cls.getResourceAsStream("google.jks"), "notasecret");
            }
            keyStore = certTrustStore;
        }
        return keyStore;
    }

    private GoogleUtils() {
    }
}
