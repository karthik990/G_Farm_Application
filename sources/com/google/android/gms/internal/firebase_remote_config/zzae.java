package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;

public class zzae extends IOException {
    private final int statusCode;
    private final transient zzx zzbb;
    private final String zzbv;
    private final String zzby;

    public zzae(zzad zzad) {
        this(new zzaf(zzad));
    }

    protected zzae(zzaf zzaf) {
        super(zzaf.message);
        this.statusCode = zzaf.statusCode;
        this.zzbv = zzaf.zzbv;
        this.zzbb = zzaf.zzbb;
        this.zzby = zzaf.zzby;
    }

    public final int getStatusCode() {
        return this.statusCode;
    }

    public static StringBuilder zzc(zzad zzad) {
        StringBuilder sb = new StringBuilder();
        int statusCode2 = zzad.getStatusCode();
        if (statusCode2 != 0) {
            sb.append(statusCode2);
        }
        String statusMessage = zzad.getStatusMessage();
        if (statusMessage != null) {
            if (statusCode2 != 0) {
                sb.append(' ');
            }
            sb.append(statusMessage);
        }
        return sb;
    }
}
