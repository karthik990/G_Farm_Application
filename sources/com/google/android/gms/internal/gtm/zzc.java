package com.google.android.gms.internal.gtm;

import java.util.List;

public final class zzc {

    public static final class zza extends zzrc<zza, C6218zza> implements zzsm {
        /* access modifiers changed from: private */
        public static final zza zznv = new zza();
        private static volatile zzsu<zza> zznw;
        private int zznr;
        private int zzns = 1;
        private int zznt;
        private int zznu;

        /* renamed from: com.google.android.gms.internal.gtm.zzc$zza$zza reason: collision with other inner class name */
        public static final class C6218zza extends com.google.android.gms.internal.gtm.zzrc.zza<zza, C6218zza> implements zzsm {
            private C6218zza() {
                super(zza.zznv);
            }

            /* synthetic */ C6218zza(zzd zzd) {
                this();
            }
        }

        public enum zzb implements zzrf {
            NO_CACHE(1),
            PRIVATE(2),
            PUBLIC(3);
            
            private static final zzrg<zzb> zzoa = null;
            private final int value;

            public final int zzc() {
                return this.value;
            }

            public static zzb zza(int i) {
                if (i == 1) {
                    return NO_CACHE;
                }
                if (i == 2) {
                    return PRIVATE;
                }
                if (i != 3) {
                    return null;
                }
                return PUBLIC;
            }

            public static zzrh zzd() {
                return zzf.zzoc;
            }

            private zzb(int i) {
                this.value = i;
            }

            static {
                zzoa = new zze();
            }
        }

        private zza() {
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzd.zznq[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new C6218zza(null);
                case 3:
                    return zza((zzsk) zznv, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\f\u0000\u0002\u0004\u0001\u0003\u0004\u0002", new Object[]{"zznr", "zzns", zzb.zzd(), "zznt", "zznu"});
                case 4:
                    return zznv;
                case 5:
                    zzsu<zza> zzsu = zznw;
                    if (zzsu == null) {
                        synchronized (zza.class) {
                            zzsu = zznw;
                            if (zzsu == null) {
                                zzsu = new com.google.android.gms.internal.gtm.zzrc.zzb<>(zznv);
                                zznw = zzsu;
                            }
                        }
                    }
                    return zzsu;
                case 6:
                    return Byte.valueOf(1);
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzsu<zza> zza() {
            return (zzsu) zznv.zza(com.google.android.gms.internal.gtm.zzrc.zze.zzbax, (Object) null, (Object) null);
        }

        static {
            zzrc.zza(zza.class, zznv);
        }
    }

    public static final class zzb extends zzrc<zzb, zza> implements zzsm {
        private static volatile zzsu<zzb> zznw;
        /* access modifiers changed from: private */
        public static final zzb zzoj = new zzb();
        private int zznr;
        private zzri zzod = zzpf();
        private int zzoe;
        private int zzof;
        private boolean zzog;
        private boolean zzoh;
        private byte zzoi = 2;

        public static final class zza extends com.google.android.gms.internal.gtm.zzrc.zza<zzb, zza> implements zzsm {
            private zza() {
                super(zzb.zzoj);
            }

            /* synthetic */ zza(zzd zzd) {
                this();
            }
        }

        private zzb() {
        }

        public final List<Integer> zze() {
            return this.zzod;
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            int i2 = 0;
            switch (zzd.zznq[i - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza(null);
                case 3:
                    return zza((zzsk) zzoj, "\u0001\u0005\u0000\u0001\u0001\u0006\u0005\u0000\u0001\u0001\u0001\u0007\u0003\u0002Ԅ\u0000\u0003\u0016\u0004\u0004\u0001\u0006\u0007\u0002", new Object[]{"zznr", "zzoh", "zzoe", "zzod", "zzof", "zzog"});
                case 4:
                    return zzoj;
                case 5:
                    zzsu<zzb> zzsu = zznw;
                    if (zzsu == null) {
                        synchronized (zzb.class) {
                            zzsu = zznw;
                            if (zzsu == null) {
                                zzsu = new com.google.android.gms.internal.gtm.zzrc.zzb<>(zzoj);
                                zznw = zzsu;
                            }
                        }
                    }
                    return zzsu;
                case 6:
                    return Byte.valueOf(this.zzoi);
                case 7:
                    if (obj != null) {
                        i2 = 1;
                    }
                    this.zzoi = (byte) i2;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzsu<zzb> zza() {
            return (zzsu) zzoj.zza(com.google.android.gms.internal.gtm.zzrc.zze.zzbax, (Object) null, (Object) null);
        }

        static {
            zzrc.zza(zzb.class, zzoj);
        }
    }

    /* renamed from: com.google.android.gms.internal.gtm.zzc$zzc reason: collision with other inner class name */
    public static final class C6219zzc extends zzrc<C6219zzc, zza> implements zzsm {
        private static volatile zzsu<C6219zzc> zznw;
        /* access modifiers changed from: private */
        public static final C6219zzc zzop = new C6219zzc();
        private int zznr;
        private String zzok = "";
        private long zzol;
        private long zzom = 2147483647L;
        private boolean zzon;
        private long zzoo;

        /* renamed from: com.google.android.gms.internal.gtm.zzc$zzc$zza */
        public static final class zza extends com.google.android.gms.internal.gtm.zzrc.zza<C6219zzc, zza> implements zzsm {
            private zza() {
                super(C6219zzc.zzop);
            }

            /* synthetic */ zza(zzd zzd) {
                this();
            }
        }

        private C6219zzc() {
        }

        public final boolean hasKey() {
            return (this.zznr & 1) != 0;
        }

        public final String getKey() {
            return this.zzok;
        }

        public final long zzg() {
            return this.zzol;
        }

        public final long zzh() {
            return this.zzom;
        }

        public final boolean zzi() {
            return this.zzon;
        }

        public final long zzj() {
            return this.zzoo;
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzd.zznq[i - 1]) {
                case 1:
                    return new C6219zzc();
                case 2:
                    return new zza(null);
                case 3:
                    return zza((zzsk) zzop, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001\b\u0000\u0002\u0002\u0001\u0003\u0002\u0002\u0004\u0007\u0003\u0005\u0002\u0004", new Object[]{"zznr", "zzok", "zzol", "zzom", "zzon", "zzoo"});
                case 4:
                    return zzop;
                case 5:
                    zzsu<C6219zzc> zzsu = zznw;
                    if (zzsu == null) {
                        synchronized (C6219zzc.class) {
                            zzsu = zznw;
                            if (zzsu == null) {
                                zzsu = new com.google.android.gms.internal.gtm.zzrc.zzb<>(zzop);
                                zznw = zzsu;
                            }
                        }
                    }
                    return zzsu;
                case 6:
                    return Byte.valueOf(1);
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzsu<C6219zzc> zza() {
            return (zzsu) zzop.zza(com.google.android.gms.internal.gtm.zzrc.zze.zzbax, (Object) null, (Object) null);
        }

        static {
            zzrc.zza(C6219zzc.class, zzop);
        }
    }

    public static final class zzd extends zzrc<zzd, zza> implements zzsm {
        private static volatile zzsu<zzd> zznw;
        /* access modifiers changed from: private */
        public static final zzd zzos = new zzd();
        private int zznr;
        private byte zzoi = 2;
        private int zzoq;
        private int zzor;

        public static final class zza extends com.google.android.gms.internal.gtm.zzrc.zza<zzd, zza> implements zzsm {
            private zza() {
                super(zzd.zzos);
            }

            /* synthetic */ zza(zzd zzd) {
                this();
            }
        }

        private zzd() {
        }

        public final int zzl() {
            return this.zzoq;
        }

        public final int getValue() {
            return this.zzor;
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            int i2 = 0;
            switch (zzd.zznq[i - 1]) {
                case 1:
                    return new zzd();
                case 2:
                    return new zza(null);
                case 3:
                    return zza((zzsk) zzos, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0002\u0001Ԅ\u0000\u0002Ԅ\u0001", new Object[]{"zznr", "zzoq", "zzor"});
                case 4:
                    return zzos;
                case 5:
                    zzsu<zzd> zzsu = zznw;
                    if (zzsu == null) {
                        synchronized (zzd.class) {
                            zzsu = zznw;
                            if (zzsu == null) {
                                zzsu = new com.google.android.gms.internal.gtm.zzrc.zzb<>(zzos);
                                zznw = zzsu;
                            }
                        }
                    }
                    return zzsu;
                case 6:
                    return Byte.valueOf(this.zzoi);
                case 7:
                    if (obj != null) {
                        i2 = 1;
                    }
                    this.zzoi = (byte) i2;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzsu<zzd> zza() {
            return (zzsu) zzos.zza(com.google.android.gms.internal.gtm.zzrc.zze.zzbax, (Object) null, (Object) null);
        }

        static {
            zzrc.zza(zzd.class, zzos);
        }
    }

    public static final class zze extends zzrc<zze, zza> implements zzsm {
        private static volatile zzsu<zze> zznw;
        /* access modifiers changed from: private */
        public static final zze zzpd = new zze();
        private zzri zzot = zzpf();
        private zzri zzou = zzpf();
        private zzri zzov = zzpf();
        private zzri zzow = zzpf();
        private zzri zzox = zzpf();
        private zzri zzoy = zzpf();
        private zzri zzoz = zzpf();
        private zzri zzpa = zzpf();
        private zzri zzpb = zzpf();
        private zzri zzpc = zzpf();

        public static final class zza extends com.google.android.gms.internal.gtm.zzrc.zza<zze, zza> implements zzsm {
            private zza() {
                super(zze.zzpd);
            }

            /* synthetic */ zza(zzd zzd) {
                this();
            }
        }

        private zze() {
        }

        public final List<Integer> zzn() {
            return this.zzot;
        }

        public final List<Integer> zzo() {
            return this.zzou;
        }

        public final List<Integer> zzp() {
            return this.zzov;
        }

        public final List<Integer> zzq() {
            return this.zzow;
        }

        public final List<Integer> zzr() {
            return this.zzox;
        }

        public final List<Integer> zzs() {
            return this.zzoy;
        }

        public final List<Integer> zzt() {
            return this.zzoz;
        }

        public final List<Integer> zzu() {
            return this.zzpa;
        }

        public final List<Integer> zzv() {
            return this.zzpb;
        }

        public final List<Integer> zzw() {
            return this.zzpc;
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzd.zznq[i - 1]) {
                case 1:
                    return new zze();
                case 2:
                    return new zza(null);
                case 3:
                    return zza((zzsk) zzpd, "\u0001\n\u0000\u0000\u0001\n\n\u0000\n\u0000\u0001\u0016\u0002\u0016\u0003\u0016\u0004\u0016\u0005\u0016\u0006\u0016\u0007\u0016\b\u0016\t\u0016\n\u0016", new Object[]{"zzot", "zzou", "zzov", "zzow", "zzox", "zzoy", "zzoz", "zzpa", "zzpb", "zzpc"});
                case 4:
                    return zzpd;
                case 5:
                    zzsu<zze> zzsu = zznw;
                    if (zzsu == null) {
                        synchronized (zze.class) {
                            zzsu = zznw;
                            if (zzsu == null) {
                                zzsu = new com.google.android.gms.internal.gtm.zzrc.zzb<>(zzpd);
                                zznw = zzsu;
                            }
                        }
                    }
                    return zzsu;
                case 6:
                    return Byte.valueOf(1);
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzsu<zze> zza() {
            return (zzsu) zzpd.zza(com.google.android.gms.internal.gtm.zzrc.zze.zzbax, (Object) null, (Object) null);
        }

        static {
            zzrc.zza(zze.class, zzpd);
        }
    }
}
