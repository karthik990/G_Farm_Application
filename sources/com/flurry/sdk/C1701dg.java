package com.flurry.sdk;

import android.net.http.X509TrustManagerExtensions;
import android.os.Build.VERSION;
import android.util.Base64;
import com.mobiroller.constants.Constants;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;

/* renamed from: com.flurry.sdk.dg */
public final class C1701dg {

    /* renamed from: a */
    public static final Set<String> f925a;

    /* renamed from: b */
    private static X509TrustManagerExtensions f926b;

    static {
        HashSet hashSet = new HashSet(13);
        f925a = hashSet;
        hashSet.add("WoiWRyIOVNa9ihaBciRSC7XHjliYS9VwUGOIud4PB18=");
        f925a.add("SVqWumuteCQHvVIaALrOZXuzVVVeS7f4FGxxu6V+es4=");
        f925a.add("cAajgxHlj7GTSEIzIYIQxmEloOSoJq7VOaxWHfv72QM=");
        f925a.add("I/Lt/z7ekCWanjD0Cvj5EqXls2lOaThEA0H2Bg4BT/o=");
        f925a.add("Wd8xe/qfTwq3ylFNd3IpaqLHZbh2ZNCLluVzmeNkcpw=");
        f925a.add("JbQbUG5JMJUoI6brnx0x3vZF6jilxsapbXGVfjhN8Fg=");
        f925a.add("r/mIkG3eEpVdm+u/ko/cwxzOMo1bk4TyHIlByibiA5E=");
        f925a.add("UZJDjsNp1+4M5x9cbbdflB779y5YRBcV6Z6rBMLIrO4=");
        f925a.add("lnsM2T/O9/J84sJFdnrpsFp3awZJ+ZZbYpCWhGloaHI=");
        f925a.add("i7WTqTvh0OioIruIfFR4kMPnBqrS2rdiVPl/s2uC/CY=");
        f925a.add("uUwZgwDOxcBXrQcntwu+kYFpkiVkOaezL0WYEZ3anJc=");
        f925a.add("dolnbtzEBnELx/9lOEQ22e6OZO/QNb6VSSX2XHA3E7A=");
        f925a.add("2fRAUXyxl4A1/XHrKNBmc8bTkzA7y4FB/GLJuNAzCqY=");
        f926b = null;
        if (VERSION.SDK_INT >= 17) {
            f926b = new X509TrustManagerExtensions(m809a());
        }
    }

    /* renamed from: a */
    public static void m810a(HttpsURLConnection httpsURLConnection) throws SSLException {
        String str = "SslPinningValidator";
        if (VERSION.SDK_INT >= 17 && f926b != null) {
            String str2 = "";
            try {
                Certificate[] serverCertificates = httpsURLConnection.getServerCertificates();
                List<X509Certificate> checkServerTrusted = f926b.checkServerTrusted((X509Certificate[]) Arrays.copyOf(serverCertificates, serverCertificates.length, X509Certificate[].class), "RSA", httpsURLConnection.getURL().getHost());
                if (checkServerTrusted != null) {
                    MessageDigest instance = MessageDigest.getInstance("SHA-256");
                    for (X509Certificate x509Certificate : checkServerTrusted) {
                        byte[] encoded = x509Certificate.getPublicKey().getEncoded();
                        instance.update(encoded, 0, encoded.length);
                        String encodeToString = Base64.encodeToString(instance.digest(), 2);
                        if (f925a.contains(encodeToString)) {
                            C1685cy.m756a(str, "Found matched pin: ".concat(String.valueOf(encodeToString)));
                            return;
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append(str2);
                        sb.append("    sha256/");
                        sb.append(encodeToString);
                        sb.append(": ");
                        sb.append(x509Certificate.getSubjectDN().toString());
                        sb.append(Constants.NEW_LINE);
                        str2 = sb.toString();
                    }
                    throw new SSLPeerUnverifiedException("Certificate pinning failure!\n  Peer certificate chain:\n".concat(String.valueOf(str2)));
                }
                throw new SSLPeerUnverifiedException("Empty trusted chain Certificate.");
            } catch (NoSuchAlgorithmException e) {
                C1685cy.m757a(str, "Error in validating pinning: ", (Throwable) e);
            } catch (CertificateException e2) {
                C1685cy.m757a(str, "Error in getting certificate: ", (Throwable) e2);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0022 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0023  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static javax.net.ssl.X509TrustManager m809a() {
        /*
            java.lang.String r0 = "Error in getting trust manager: "
            java.lang.String r1 = "SslPinningValidator"
            r2 = 0
            java.lang.String r3 = javax.net.ssl.TrustManagerFactory.getDefaultAlgorithm()     // Catch:{ NoSuchAlgorithmException -> 0x001b, KeyStoreException -> 0x0015 }
            javax.net.ssl.TrustManagerFactory r3 = javax.net.ssl.TrustManagerFactory.getInstance(r3)     // Catch:{ NoSuchAlgorithmException -> 0x001b, KeyStoreException -> 0x0015 }
            r3.init(r2)     // Catch:{ NoSuchAlgorithmException -> 0x0013, KeyStoreException -> 0x0011 }
            goto L_0x0020
        L_0x0011:
            r4 = move-exception
            goto L_0x0017
        L_0x0013:
            r4 = move-exception
            goto L_0x001d
        L_0x0015:
            r4 = move-exception
            r3 = r2
        L_0x0017:
            com.flurry.sdk.C1685cy.m757a(r1, r0, r4)
            goto L_0x0020
        L_0x001b:
            r4 = move-exception
            r3 = r2
        L_0x001d:
            com.flurry.sdk.C1685cy.m757a(r1, r0, r4)
        L_0x0020:
            if (r3 != 0) goto L_0x0023
            return r2
        L_0x0023:
            javax.net.ssl.TrustManager[] r0 = r3.getTrustManagers()
            int r1 = r0.length
            r3 = 0
        L_0x0029:
            if (r3 >= r1) goto L_0x0038
            r4 = r0[r3]
            boolean r5 = r4 instanceof javax.net.ssl.X509TrustManager
            if (r5 == 0) goto L_0x0035
            r2 = r4
            javax.net.ssl.X509TrustManager r2 = (javax.net.ssl.X509TrustManager) r2
            goto L_0x0038
        L_0x0035:
            int r3 = r3 + 1
            goto L_0x0029
        L_0x0038:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.C1701dg.m809a():javax.net.ssl.X509TrustManager");
    }
}
