package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzhs.zzc;

public final class zzu extends zzhs<zzu, zza> implements zzje {
    /* access modifiers changed from: private */
    public static final zzu zzfi = new zzu();
    private static volatile zzjm<zzu> zzs;
    private String zzaz;
    private String zzce;
    private String zzcj;
    private String zzct;
    private String zzdq;
    private String zzeo;
    private String zzfh;
    private int zzo;
    private String zzx;

    public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzu, zza> implements zzje {
        private zza() {
            super(zzu.zzfi);
        }

        /* synthetic */ zza(zzt zzt) {
            this();
        }
    }

    private zzu() {
        String str = "";
        this.zzx = str;
        this.zzcj = str;
        this.zzct = str;
        this.zzdq = str;
        this.zzaz = str;
        this.zzfh = str;
        this.zzeo = str;
        this.zzce = str;
    }

    public final String getProviderId() {
        return this.zzx;
    }

    public final String getDisplayName() {
        return this.zzcj;
    }

    public final String zzam() {
        return this.zzct;
    }

    public final String zzbo() {
        return this.zzdq;
    }

    public final String getEmail() {
        return this.zzaz;
    }

    public final String getPhoneNumber() {
        return this.zzce;
    }

    /* access modifiers changed from: protected */
    public final Object zza(int i, Object obj, Object obj2) {
        switch (zzt.zzt[i - 1]) {
            case 1:
                return new zzu();
            case 2:
                return new zza(null);
            case 3:
                return zza((zzjc) zzfi, "\u0001\b\u0000\u0001\u0001\t\b\u0000\u0000\u0000\u0001\b\u0000\u0002\b\u0001\u0003\b\u0002\u0004\b\u0003\u0005\b\u0004\u0006\b\u0005\u0007\b\u0006\t\b\u0007", new Object[]{"zzo", "zzx", "zzcj", "zzct", "zzdq", "zzaz", "zzfh", "zzeo", "zzce"});
            case 4:
                return zzfi;
            case 5:
                zzjm<zzu> zzjm = zzs;
                if (zzjm == null) {
                    synchronized (zzu.class) {
                        zzjm = zzs;
                        if (zzjm == null) {
                            zzjm = new zzc<>(zzfi);
                            zzs = zzjm;
                        }
                    }
                }
                return zzjm;
            case 6:
                return Byte.valueOf(1);
            case 7:
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }

    static {
        zzhs.zza(zzu.class, zzfi);
    }
}
