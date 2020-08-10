package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzhs.zzc;
import com.google.android.gms.internal.firebase_auth.zzhs.zzd;

public final class zzlg {

    public static final class zza extends zzhs<zza, C6213zza> implements zzje {
        /* access modifiers changed from: private */
        public static final zza zzagx = new zza();
        private static volatile zzjm<zza> zzs;
        private String zzagt;
        private String zzagu;
        private String zzagv;
        private String zzagw;
        private String zzbb;

        /* renamed from: com.google.android.gms.internal.firebase_auth.zzlg$zza$zza reason: collision with other inner class name */
        public static final class C6213zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zza, C6213zza> implements zzje {
            private C6213zza() {
                super(zza.zzagx);
            }

            public final C6213zza zzdn(String str) {
                zzid();
                ((zza) this.zzaah).zzdm(str);
                return this;
            }

            public final C6213zza zzdo(String str) {
                zzid();
                ((zza) this.zzaah).zzcm(str);
                return this;
            }

            /* synthetic */ C6213zza(zzli zzli) {
                this();
            }
        }

        private zza() {
            String str = "";
            this.zzagt = str;
            this.zzagu = str;
            this.zzbb = str;
            this.zzagv = str;
            this.zzagw = str;
        }

        /* access modifiers changed from: private */
        public final void zzdm(String str) {
            if (str != null) {
                this.zzagt = str;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public final void zzcm(String str) {
            if (str != null) {
                this.zzbb = str;
                return;
            }
            throw new NullPointerException();
        }

        public static C6213zza zzkz() {
            return (C6213zza) zzagx.zzij();
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzli.zzt[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new C6213zza(null);
                case 3:
                    return zza((zzjc) zzagx, "\u0000\u0005\u0000\u0000\u0001\u0005\u0005\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ\u0003Ȉ\u0004Ȉ\u0005Ȉ", new Object[]{"zzagt", "zzagu", "zzbb", "zzagv", "zzagw"});
                case 4:
                    return zzagx;
                case 5:
                    zzjm<zza> zzjm = zzs;
                    if (zzjm == null) {
                        synchronized (zza.class) {
                            zzjm = zzs;
                            if (zzjm == null) {
                                zzjm = new zzc<>(zzagx);
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
            zzhs.zza(zza.class, zzagx);
        }
    }

    public static final class zzb extends zzhs<zzb, zza> implements zzje {
        /* access modifiers changed from: private */
        public static final zzb zzahc = new zzb();
        private static volatile zzjm<zzb> zzs;
        private String zzagy;
        private String zzagz;
        private String zzaha;
        private long zzahb;
        private String zzaw;
        private String zzbb;
        private long zzbc;

        public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzb, zza> implements zzje {
            private zza() {
                super(zzb.zzahc);
            }

            /* synthetic */ zza(zzli zzli) {
                this();
            }
        }

        private zzb() {
            String str = "";
            this.zzagy = str;
            this.zzagz = str;
            this.zzbb = str;
            this.zzaw = str;
            this.zzaha = str;
        }

        public final String getAccessToken() {
            return this.zzagy;
        }

        public final long zzt() {
            return this.zzbc;
        }

        public final String zzeu() {
            return this.zzagz;
        }

        public final String zzs() {
            return this.zzbb;
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzli.zzt[i - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza(null);
                case 3:
                    return zza((zzjc) zzahc, "\u0000\u0007\u0000\u0000\u0001\u0007\u0007\u0000\u0000\u0000\u0001Ȉ\u0002\u0002\u0003Ȉ\u0004Ȉ\u0005Ȉ\u0006Ȉ\u0007\u0002", new Object[]{"zzagy", "zzbc", "zzagz", "zzbb", "zzaw", "zzaha", "zzahb"});
                case 4:
                    return zzahc;
                case 5:
                    zzjm<zzb> zzjm = zzs;
                    if (zzjm == null) {
                        synchronized (zzb.class) {
                            zzjm = zzs;
                            if (zzjm == null) {
                                zzjm = new zzc<>(zzahc);
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

        public static zzjm<zzb> zzm() {
            return (zzjm) zzahc.zza(zzd.zzaat, (Object) null, (Object) null);
        }

        static {
            zzhs.zza(zzb.class, zzahc);
        }
    }
}
