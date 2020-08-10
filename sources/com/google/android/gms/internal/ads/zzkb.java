package com.google.android.gms.internal.ads;

@zzadh
public final class zzkb {
    private static final Object sLock = new Object();
    private static zzkb zzarz;
    private final zzamu zzasa = new zzamu();
    private final zzjr zzasb;
    private final String zzasc;
    private final zzng zzasd;
    private final zznh zzase;
    private final zzni zzasf;

    static {
        zzkb zzkb = new zzkb();
        synchronized (sLock) {
            zzarz = zzkb;
        }
    }

    protected zzkb() {
        zzjr zzjr = new zzjr(new zzjh(), new zzjg(), new zzme(), new zzrv(), new zzahi(), new zzaao(), new zzrw());
        this.zzasb = zzjr;
        this.zzasc = zzamu.zzsi();
        this.zzasd = new zzng();
        this.zzase = new zznh();
        this.zzasf = new zzni();
    }

    private static zzkb zzie() {
        zzkb zzkb;
        synchronized (sLock) {
            zzkb = zzarz;
        }
        return zzkb;
    }

    public static zzamu zzif() {
        return zzie().zzasa;
    }

    public static zzjr zzig() {
        return zzie().zzasb;
    }

    public static String zzih() {
        return zzie().zzasc;
    }

    public static zznh zzii() {
        return zzie().zzase;
    }

    public static zzng zzij() {
        return zzie().zzasd;
    }

    public static zzni zzik() {
        return zzie().zzasf;
    }
}
