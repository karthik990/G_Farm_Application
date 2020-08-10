package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

public final class zzap implements zzao {
    private final Proxy zzcs;

    public zzap() {
        this(null);
    }

    public zzap(Proxy proxy) {
        this.zzcs = proxy;
    }

    public final HttpURLConnection zza(URL url) throws IOException {
        Proxy proxy = this.zzcs;
        return (HttpURLConnection) (proxy == null ? url.openConnection() : url.openConnection(proxy));
    }
}
