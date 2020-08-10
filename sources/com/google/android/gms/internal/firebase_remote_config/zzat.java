package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.Arrays;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public final class zzat extends zzah {
    private static final String[] zzca;
    private final HostnameVerifier hostnameVerifier;
    private final zzao zzcy;
    private final SSLSocketFactory zzcz;

    public zzat() {
        this(null, null, null);
    }

    private zzat(zzao zzao, SSLSocketFactory sSLSocketFactory, HostnameVerifier hostnameVerifier2) {
        zzap zzap;
        if (System.getProperty("com.google.api.client.should_use_proxy") != null) {
            zzap = new zzap(new Proxy(Type.HTTP, new InetSocketAddress(System.getProperty("https.proxyHost"), Integer.parseInt(System.getProperty("https.proxyPort")))));
        } else {
            zzap = new zzap();
        }
        this.zzcy = zzap;
        this.zzcz = null;
        this.hostnameVerifier = null;
    }

    public final boolean zzz(String str) {
        return Arrays.binarySearch(zzca, str) >= 0;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ zzai zzc(String str, String str2) throws IOException {
        Object[] objArr = {str};
        if (zzz(str)) {
            HttpURLConnection zza = this.zzcy.zza(new URL(str2));
            zza.setRequestMethod(str);
            boolean z = zza instanceof HttpsURLConnection;
            return new zzaq(zza);
        }
        throw new IllegalArgumentException(zzdz.zza("HTTP method %s not supported", objArr));
    }

    static {
        String[] strArr = {"DELETE", "GET", "HEAD", "OPTIONS", "POST", "PUT", "TRACE"};
        zzca = strArr;
        Arrays.sort(strArr);
    }
}
