package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzhs.zzc;

public final class zzkh extends zzhs<zzkh, zza> implements zzje {
    /* access modifiers changed from: private */
    public static final zzkh zzaej = new zzkh();
    private static volatile zzjm<zzkh> zzs;
    private long zzaeh;
    private int zzaei;

    public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzkh, zza> implements zzje {
        private zza() {
            super(zzkh.zzaej);
        }

        /* synthetic */ zza(zzkj zzkj) {
            this();
        }
    }

    private zzkh() {
    }

    public final long getSeconds() {
        return this.zzaeh;
    }

    /* access modifiers changed from: protected */
    public final Object zza(int i, Object obj, Object obj2) {
        switch (zzkj.zzt[i - 1]) {
            case 1:
                return new zzkh();
            case 2:
                return new zza(null);
            case 3:
                return zza((zzjc) zzaej, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0002\u0002\u0004", new Object[]{"zzaeh", "zzaei"});
            case 4:
                return zzaej;
            case 5:
                zzjm<zzkh> zzjm = zzs;
                if (zzjm == null) {
                    synchronized (zzkh.class) {
                        zzjm = zzs;
                        if (zzjm == null) {
                            zzjm = new zzc<>(zzaej);
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

    public static zzkh zzkl() {
        return zzaej;
    }

    static {
        zzhs.zza(zzkh.class, zzaej);
    }
}
