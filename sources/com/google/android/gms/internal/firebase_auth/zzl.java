package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzhs.zzc;

public final class zzl extends zzhs<zzl, zza> implements zzje {
    /* access modifiers changed from: private */
    public static final zzl zzr = new zzl();
    private static volatile zzjm<zzl> zzs;
    private int zzo;
    private String zzp;
    private String zzq;

    public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzl, zza> implements zzje {
        private zza() {
            super(zzl.zzr);
        }

        /* synthetic */ zza(zzn zzn) {
            this();
        }
    }

    private zzl() {
        String str = "";
        this.zzp = str;
        this.zzq = str;
    }

    /* access modifiers changed from: protected */
    public final Object zza(int i, Object obj, Object obj2) {
        switch (zzn.zzt[i - 1]) {
            case 1:
                return new zzl();
            case 2:
                return new zza(null);
            case 3:
                return zza((zzjc) zzr, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\b\u0000\u0002\b\u0001", new Object[]{"zzo", "zzp", "zzq"});
            case 4:
                return zzr;
            case 5:
                zzjm<zzl> zzjm = zzs;
                if (zzjm == null) {
                    synchronized (zzl.class) {
                        zzjm = zzs;
                        if (zzjm == null) {
                            zzjm = new zzc<>(zzr);
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
        zzhs.zza(zzl.class, zzr);
    }
}
