package com.google.android.gms.internal.firebase_remote_config;

import com.google.android.gms.internal.firebase_remote_config.zzhi.zze;

final class zzid implements zzjk {
    private static final zzin zzuz = new zzie();
    private final zzin zzuy;

    public zzid() {
        this(new zzif(zzhh.zzgv(), zzhw()));
    }

    private zzid(zzin zzin) {
        this.zzuy = (zzin) zzhk.zza(zzin, "messageInfoFactory");
    }

    public final <T> zzjj<T> zzj(Class<T> cls) {
        zzjl.zzl(cls);
        zzim zzh = this.zzuy.zzh(cls);
        if (zzh.zzie()) {
            if (zzhi.class.isAssignableFrom(cls)) {
                return zziu.zza(zzjl.zzis(), zzgz.zzgl(), zzh.zzif());
            }
            return zziu.zza(zzjl.zziq(), zzgz.zzgm(), zzh.zzif());
        } else if (zzhi.class.isAssignableFrom(cls)) {
            if (zza(zzh)) {
                return zzis.zza(cls, zzh, zziy.zzii(), zzhy.zzhv(), zzjl.zzis(), zzgz.zzgl(), zzil.zzib());
            }
            return zzis.zza(cls, zzh, zziy.zzii(), zzhy.zzhv(), zzjl.zzis(), null, zzil.zzib());
        } else if (zza(zzh)) {
            return zzis.zza(cls, zzh, zziy.zzih(), zzhy.zzhu(), zzjl.zziq(), zzgz.zzgm(), zzil.zzia());
        } else {
            return zzis.zza(cls, zzh, zziy.zzih(), zzhy.zzhu(), zzjl.zzir(), null, zzil.zzia());
        }
    }

    private static boolean zza(zzim zzim) {
        return zzim.zzid() == zze.zztl;
    }

    private static zzin zzhw() {
        try {
            return (zzin) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return zzuz;
        }
    }
}
